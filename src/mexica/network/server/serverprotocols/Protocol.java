package mexica.network.server.serverprotocols;

import mexica.network.User;

import com.nionetframework.common.Packet;
import com.nionetframework.common.PacketInbound;
import com.nionetframework.common.PacketOutbound;

public abstract class Protocol {

	public static Protocol PKI_Protocol = new _PKI_Protocol(null);

	/**
	 * Processes the packet for this Protocol. Returns the next Packet to send
	 * to the Source to continue the Protocol. Returns null if this Protocol is
	 * finished or terminated. Use {@link #isTerminated()} and
	 * {@link #isCompleted()} to find out.
	 */
	public abstract PacketOutbound validate(PacketInbound packet);

	/** Starts the Protocol for this User. Returns the Protocol. */
	public abstract Protocol engageFor(User u);

	/**
	 * Used to determine if the Protocol has completed succesfully. Returns
	 * false if the Protocol has not yet completed or is terminated.
	 */
	public abstract boolean isCompleted();

	/**
	 * Used to determine if the Protocol has terminated. A Protocol is
	 * terminated if the Client does not follow Protocol.
	 */
	public abstract boolean isTerminated();

	/**
	 * Returns the reason of the termination of this protocol. Returns null if
	 * the Protocol has not been violated.
	 */
	public abstract String getTerminationReason();

}
