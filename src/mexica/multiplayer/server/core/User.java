package mexica.multiplayer.server.core;

public class User {

	private String displayname;
	private OnlineGame onlinegame;
	private Connection connection;
	private String uuid;

	public User(Connection connection, String displayname, String uuid) {
		this.connection = connection;
		this.displayname = displayname;
		this.uuid = uuid;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void setOnlineGame(OnlineGame onlinegame) {
		this.onlinegame = onlinegame;
	}

	public OnlineGame getOnlineGame() {
		return this.onlinegame;
	}

	public String getUsername() {
		return this.displayname;
	}

	public String getUuid() {
		return uuid;
	}

	public boolean isHost(OnlineGame onlinegame) {
		if (onlinegame.getHost().equals(this))
			return true;
		return false;
	}

}
