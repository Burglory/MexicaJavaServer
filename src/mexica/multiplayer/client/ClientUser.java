package mexica.multiplayer.client;

public class ClientUser {

	private String username;
	private ClientConnection connection;

	public ClientUser(ClientConnection connection, String username) {
		this.username = username;
		this.connection = connection;
	}

	public ClientConnection getConnection() {
		return this.connection;
	}

	public String getUsername() {
		return this.username;
	}

}
