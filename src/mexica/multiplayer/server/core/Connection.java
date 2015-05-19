package mexica.multiplayer.server.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;
import mexica.multiplayer.core.Packets;
import mexica.multiplayer.server.Server;
import mexica.multiplayer.server.utilities.SecurityUtilities;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;

public class Connection implements Runnable {

	private Socket socket;
	private Server server;
	private DataInputStream in;
	private DataOutputStream out;
	private Packet p;
	private boolean isrunning;
	private String token;

	public Connection(Server s, Socket socket) throws IOException {
		this.socket = socket;
		this.server = s;
		this.isrunning = true;
		this.out = new DataOutputStream(socket.getOutputStream());
		this.in = new DataInputStream(socket.getInputStream());
		this.token = SecurityUtilities.generateRandomToken();
		this.send(new Packet(PacketType.Server.Connection.Authentication.Token,
				token));
		this.server.getLogger().Log("Send token: " + token + " to client.",
				ServerLogLevel.DEBUG);
	}

	public Server getServer() {
		return this.server;
	}

	/**
	 * Replaces the token for this Connection by a new token from
	 * SecurityUtilities.
	 */
	public String renewToken() {
		this.token = SecurityUtilities.generateRandomToken();
		return this.token;
	}

	/** Get the token for this Connection. */
	public String getToken() {
		return this.token;
	}

	/** Get the Socket for this Connection. */
	public Socket getSocket() {
		return this.socket;
	}

	/** Get the DataInputStream for this Connection. */
	public DataInputStream getDataInputStream() {
		return this.in;
	}

	/** Get the DataOutputStream for this Connection. */
	public DataOutputStream getDataOutputStream() {
		return this.out;
	}

	/** Send a Packet through this Connection. */
	public synchronized boolean send(Packet p) {
		byte[] packet = Packets.getBytes(p);
		try {
			this.out.writeInt(packet.length);
			this.out.write(packet);
			return true;
		} catch (IOException e) {
			this.server
					.getLogger()
					.Log("(Connection) Failed to send packet: " + p.toString()
							+ " to: " + socket.toString(), ServerLogLevel.ERROR);
			e.printStackTrace();
			return false;
		}
	}

	/** Get the status of this Connection. */
	public boolean getRunning() {
		return this.isrunning;
	}

	/**
	 * Set the status of this Connection. False will terminate the Thread that
	 * is listening for Packets from this Connection.
	 */
	public void setRunning(boolean running) {
		this.isrunning = running;
	}

	/** Returns the User associated with this Connection. */
	public User getUser() {
		return getServer().getConnectionManager().getUser(this);
	}

	/** Start listening for Packet from the Connection. */
	@Override
	public void run() {
		while (isrunning) {
			try {
				int size = this.getDataInputStream().readInt();
				byte[] packet = new byte[size];
				this.getDataInputStream().read(packet);
				this.server.getLogger().Log(
						"(Connection) Received packet from: "
								+ this.getSocket().getInetAddress().toString(),
						ServerLogLevel.DEBUG);
				this.p = Packets.generatePacketWrapper(packet);
				this.server.getPacketHandler().process(this, p);
			} catch (EOFException eofe) {
				String message = "(Connection) _Client disconnected: "
						+ socket.toString();
				User user = this.server.getConnectionManager().getUser(this);
				if (user != null)
					message += " (" + user.getUsername() + ")";
				this.server.getLogger().Log(message, ServerLogLevel.INFO);
				this.server.getConnectionManager().removeConnection(this);
				break;
			} catch (IOException ioe) {
				this.server.getLogger().Log(
						"(Connection) Exception reading stream: "
								+ socket.toString(), ServerLogLevel.ERROR);
				ioe.printStackTrace();
				break;
			}

		}
		this.server.getLogger().Log(
				"(Connection) Closing Connection: "
						+ this.getSocket().getInetAddress().toString(),
				ServerLogLevel.INFO);
		try {
			this.getDataOutputStream().close();
			this.getDataInputStream().close();
			this.getSocket().close();
			this.server.getLogger().Log("(Connection) Connection closed.",
					ServerLogLevel.INFO);
		} catch (IOException e) {
			this.server
					.getLogger()
					.Log("(Connection) Failed to close connection. Is it already closed?",
							ServerLogLevel.ERROR);
			e.printStackTrace();
		}

	}

}
