package mexica.multiplayer.server;

import java.io.IOException;
import java.net.ServerSocket;

import mexica.multiplayer.server.core.PacketHandler;
import mexica.multiplayer.server.managers.ConnectionManager;
import mexica.multiplayer.server.managers.LobbyManager;
import mexica.multiplayer.server.utilities.ServerLogger;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;
import mexica.network.events.NetworkEventDispatcher;

public class Server {

	public static final String VERSION = "1.0";
	private ServerSocket serverSocket;
	private PacketHandler packethandler;
	private ConnectionManager connectionmanager;
	private boolean keeprunning;
	private ServerLogger serverLogger;
	private int port;
	private final LobbyManager lobbymanager;

	public Server(int port, ServerLogLevel loglevel) {
		this.serverLogger = new ServerLogger(loglevel);
		this.port = port;
		keeprunning = true;
		this.lobbymanager = new LobbyManager(this);
		this.connectionmanager = new ConnectionManager(this);
		this.packethandler = new PacketHandler(this, this.connectionmanager);

	}

	public ServerLogger getLogger() {
		return this.serverLogger;
	}

	public boolean getKeepRunning() {
		return this.keeprunning;
	}

	public void setKeepRunning(boolean keeprunning) {
		this.keeprunning = keeprunning;
	}

	public void Initialize() {
		this.getLogger().Log(
				"(Server) Setting up server at port: " + this.port,
				ServerLogLevel.INFO);
		try {
			// System.setProperty("javax.net.ssl.keyStore", "sharedkey");
			// System.setProperty("javax.net.ssl.keyStorePassword", "password");
			// this.serverSocket =
			// SSLServerSocketFactory.getDefault().createServerSocket(Integer.parseInt(port));
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(this.connectionmanager);
		thread.start();
	}

	public ConnectionManager getConnectionManager() {
		return this.connectionmanager;
	}

	public PacketHandler getPacketHandler() {
		return this.packethandler;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public LobbyManager getLobbymanager() {
		return lobbymanager;
	}

}
