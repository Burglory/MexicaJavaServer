package mexica.network.server.serverprotocols;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import mexica.network.NetworkStandards;
import mexica.network.User;

import com.nionetframework.common.PacketInbound;
import com.nionetframework.common.PacketOutbound;
import com.nionetframework.common.data.DataBuilder;

/**Do we need typing of the Packets?*/
public class _PKI_Protocol extends Protocol {

	private static final int RECEIVE_CLIENT_PUBKEY = 1;
	private static final int INACTIVE = 0;
	private static final int RECEIVE_CLIENT_CONFIRMATION = 2;
	private static final String SESSION = "s";
	private static final String CHALLENGE = "c";
	private static final int COMPLETED = 3;
	private static final int ABORTED = -1;
	private User user;
	private boolean completed;
	private boolean terminated;
	private byte[] userpubkey;
	private byte[] session;

	private int currentStage = INACTIVE;
	private byte[] challenge;
	private String violation;

	_PKI_Protocol(User u) {
		this.user = u;
	}

	@Override
	public PacketOutbound validate(PacketInbound packet) {
		PacketOutbound p = null;
		if (!packet.getSource().equals(user.getConnection())) {
			throw new RuntimeException(
					"Connection from user is not the same as the source connection for this protocol!");
		}
		switch (currentStage) {
		case INACTIVE: {
			throw new RuntimeException("This Protocol is INACTIVE. No User has been linked to this Protocol instance!");
		}
		case ABORTED: {
			throw new RuntimeException("This Protocol has been aborted. The User should be disconnected, or retry the Protocol! ");
		}
		case COMPLETED: {
			throw new RuntimeException("This Protocol has already been completed.");
		}
		case RECEIVE_CLIENT_PUBKEY: {
			byte[] clientspubkeyencrypted = packet.getBytes();
			byte[] clientspubkeydecrypted = PKI_Util
					.decrypt(clientspubkeyencrypted);
			userpubkey = clientspubkeydecrypted;
			session = PKI_Util.generateRandomSession();
			byte[] sessionencrypted = PKI_Util.encrypt(session,
					clientspubkeydecrypted);
			byte[] sessionencryptedsigned = PKI_Util.encrypt(sessionencrypted);
			String signedsession = PKI_Util.encode(sessionencryptedsigned);
			DataBuilder db = new DataBuilder();
			challenge = PKI_Util.generateChallenge();
			String challengestring = PKI_Util.encode(challenge);
			db.add(SESSION).wrap(signedsession).add(CHALLENGE).wrap(challengestring);
			p = new PacketOutbound(db.getString().getBytes(NetworkStandards.charset), user.getConnection());
			this.currentStage = RECEIVE_CLIENT_CONFIRMATION;
		}
		case RECEIVE_CLIENT_CONFIRMATION: {
			byte[] clientconfirmation = packet.getBytes();
			byte[] clientconfirmationunsigned = PKI_Util.decrypt(clientconfirmation, userpubkey);
			byte[] clientconfirmationdecrypted = PKI_Util.decrypt(clientconfirmationunsigned);
			if(Arrays.equals(clientconfirmationdecrypted, challenge)) {
				this.currentStage = COMPLETED;
				this.completed = true;
				this.terminated = false;
			} else {
				this.violation = "Client failed to provide a valid response to the challenge.";
				this.currentStage = ABORTED;
				this.completed = false;
				this.terminated = true;
			}
		}
		}
		return p;
	}

	@Override
	public Protocol engageFor(User u) {
		_PKI_Protocol p = new _PKI_Protocol(u);
		u.setCurrentProtocol(p);
		p.currentStage = RECEIVE_CLIENT_PUBKEY;
		return p;
	}

	@Override
	public boolean isCompleted() {
		return this.completed;
	}

	@Override
	public boolean isTerminated() {
		return this.terminated;
	}

	@Override
	public String getTerminationReason() {
		if(!isTerminated()) return null;
		return this.violation;
	}

}
