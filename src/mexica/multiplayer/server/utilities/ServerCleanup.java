package mexica.multiplayer.server.utilities;

import mexica.multiplayer.server.Server;
import mexica.multiplayer.server.core.Connection;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;

public class ServerCleanup implements Runnable {

	private Server server;

	public ServerCleanup(Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		server.getLogger().Log("Disconnecting all users.", ServerLogLevel.INFO);
		for (Connection c : server.getConnectionManager().getConnections()) {
			server.getConnectionManager().disConnect(c);
		}
		server.getLogger().Log("Closing server.", ServerLogLevel.INFO);
		server.setKeepRunning(false);
	}

}
