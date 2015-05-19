package mexica.multiplayer.server.managers;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import mexica.multiplayer.core.Packet;
import mexica.multiplayer.server.Server;
import mexica.multiplayer.server.core.Connection;
import mexica.multiplayer.server.core.User;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;

public class ConnectionManager implements Runnable {

	private HashMap<String, Connection> usernames_connections;
	private HashMap<Connection, User> connections_users;
	private Server server;

	public ConnectionManager(Server server) {
		this.server = server;
		this.usernames_connections = new HashMap<String, Connection>();
		this.connections_users = new HashMap<Connection, User>();
	}

	public Server getServer() {
		return this.server;
	}

	/** Disconnects a User from the Server and terminates the Connection. */
	public void disConnect(User u) {

		this.server.getLogger().Log(
				"(ConnectionManager) Disconnecting user: " + u.getUsername(),
				ServerLogLevel.DEBUG);

		u.getOnlineGame().removeOnlinePlayer(u);

		this.disConnect(u.getConnection());
	}

	/** Terminates the Connection. */
	public void disConnect(Connection connection) {
		this.server.getLogger().Log(
				"(ConnectionManager) Disconnecting connection: "
						+ connection.getSocket().getInetAddress().toString()
						+ ":" + connection.getSocket().getPort(),
				ServerLogLevel.DEBUG);
		this.removeConnection(connection);
		connection.setRunning(false);
	}

	/** Returns an ArrayList with all User having a Connection. */
	public ArrayList<User> getUsers() {
		return (ArrayList<User>) this.connections_users.values();
	}

	/** Adds a Connection to this manager. */
	public void addConnection(Connection connection) {
		this.connections_users.put(connection, null);
	}

	/**
	 * Removes a Connection from this manager. Does not terminate the
	 * Connection.
	 */
	public void removeConnection(Connection connection) {
		User user = this.connections_users.get(connection);
		if (user != null)
			this.usernames_connections.remove(user.getUsername());
		this.connections_users.remove(connection);
	}

	/** Returns an ArrayList of Connection managed by this manager. */
	public ArrayList<Connection> getConnections() {
		return new ArrayList<Connection>(this.connections_users.keySet());
	}

	/** Set the User that is using this Connection. */
	public void setUserConnection(Connection connection, User user) {
		this.usernames_connections.put(user.getUsername(), connection);
		this.connections_users.put(connection, user);
	}

	/** Gets the Connection belonging to a Socket. */
	public Connection getConnection(Socket s) {
		for (Connection connection : this.getConnections()) {
			if (connection.getSocket().equals(s))
				return connection;
		}
		this.server.getLogger().Log(
				"(ConnectionManager) No Connection found for Socket: "
						+ s.toString(), ServerLogLevel.ERROR);
		return null;
	}

	/** Gets the Connection belonging to a User username. */
	public Connection getConnection(String username) {
		return this.usernames_connections.get(username);
	}

	/** Send a Packet to all Connection managed by this manager. */
	public void broadcast(Packet p) {
		for (Connection connection : this.getConnections()) {
			connection.send(p);
		}
	}

	/** Gets the User associated with the Connection. */
	public User getUser(Connection connection) {
		return this.connections_users.get(connection);
	}

	/** Gets the User for the given username. */
	public User getUser(String name) {
		for (User u : this.getUsers()) {
			if (u.getUsername().equals(name))
				return u;
		}
		return null;
	}

	@Override
	public void run() {
		this.server
				.getLogger()
				.Log("(ConnectionManager) Started listening for incoming connections.",
						ServerLogLevel.INFO);
		while (this.server.getKeepRunning()) {
			try {
				Socket newsocket = this.server.getServerSocket().accept();
				this.server.getLogger().Log(
						"(ConnectionManager) New connection from: "
								+ newsocket.getInetAddress(),
						ServerLogLevel.INFO);
				Connection newconnection = new Connection(server, newsocket);
				this.addConnection(newconnection);
				Thread clienthread = new Thread(newconnection);
				clienthread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.server
				.getLogger()
				.Log("(ConnectionManager) Stopped listening for incoming connections.",
						ServerLogLevel.INFO);
		this.server.getLogger().Log(
				"(ConnectionManager) Closing ServerSocket.",
				ServerLogLevel.INFO);
		try {
			this.server.getServerSocket().close();
		} catch (IOException e) {
			this.server
					.getLogger()
					.Log("(ConnectionManager) Failed to close ServerSocket. Is it already closed?",
							ServerLogLevel.ERROR);
			e.printStackTrace();
		}
		this.server.getLogger().Log("(ConnectionManager) ServerSocket Closed.",
				ServerLogLevel.INFO);
	}

}
