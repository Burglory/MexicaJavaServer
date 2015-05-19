package mexica.multiplayer.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import mexica.multiplayer.client.ClientLogger.ClientLogLevel;
import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;
import mexica.multiplayer.core.Packets;

class ClientConnection implements Runnable {

	private Socket socket;
	private DataOutputStream outputstream;
	private DataInputStream inputstream;
	private Client client;
	private boolean keepConnection;
	private String token;
	private ClientSession clientsession;

	ClientConnection(Client client, Socket socket) throws IOException {
		this.client = client;
		this.socket = socket;
		this.keepConnection = true;
		this.outputstream = new DataOutputStream(socket.getOutputStream());
		this.inputstream = new DataInputStream(socket.getInputStream());
		this.token = null;
		this.clientsession = new ClientSession(this);
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Client getClient() {
		return this.client;
	}

	public void setKeepConnection(boolean keepConnection) {
		this.keepConnection = keepConnection;
	}

	public boolean getKeepConnection() {
		return this.keepConnection;
	}

	public boolean send(Packet p) {
		try {
			byte[] packet = Packets.getBytes(p);
			this.outputstream.writeInt(Packets.getBytes(p).length);
			this.outputstream.write(packet);
			return true;
		} catch (IOException e) {
			this.client
					.getLogger()
					.Log("(Connection) Failed to send packet: " + p.toString()
							+ " to: " + socket.toString(), ClientLogLevel.ERROR);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void run() {
		while (keepConnection) {
			try {
				int length = this.inputstream.readInt();
				byte[] packet = new byte[length];
				this.inputstream.read(packet);
				Packet p = Packets.generatePacketWrapper(packet);
				ClientPacketHandler.process(this, p);
			} catch (EOFException e) {
				this.client.getLogger().Log("Server Closed",
						ClientLogLevel.ERROR);
				break;
			} catch (IOException e) {
				this.client.getLogger().Log(
						"Some error occurred while processing packet.",
						ClientLogLevel.ERROR);
				e.printStackTrace();
				break;
			}
		}

		try {
			this.inputstream.close();
			this.outputstream.close();
			this.socket.close();
		} catch (IOException e) {
			this.client
					.getLogger()
					.Log("Exception occurred while closing connection. Is it already closed?",
							ClientLogLevel.INFO);
			e.printStackTrace();
		}

	}

	public boolean LogIn(String username, String password) {
		try {
			String hashedpassword = Securities.hashPassword(password);
			this.client.getLogger().Log("Hashed password: " + hashedpassword,
					ClientLogger.ClientLogLevel.DEBUG);
			if (this.token != null) {
				hashedpassword = Securities.toHex(Securities.hashPasswordToken(
						hashedpassword, token));
				this.client.getLogger().Log(
						"Sent auth packet: password: " + hashedpassword
								+ ", token: " + token,
						ClientLogger.ClientLogLevel.DEBUG);
				this.token = null;

				this.send(new Packet(
						PacketType.Client.Connection.Authentication.Login.Request,
						"u{" + username + "}p{" + password + "}"));
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public ClientSession getClientSession() {
		return this.clientsession;
	}

}
