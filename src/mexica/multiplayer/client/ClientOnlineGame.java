package mexica.multiplayer.client;

import java.util.ArrayList;

public class ClientOnlineGame {

	private final String host;
	private final String gamename;
	private String gamesettings;
	private ArrayList<String> users;

	public ClientOnlineGame(final String host, final String gamename,
			final String string) {
		this.host = host;
		this.gamename = gamename;
		this.gamesettings = string;
		this.users = new ArrayList<String>();
		this.addUser(host);
	}

	public void addUser(String username) {
		this.users.add(username);
	}

	public void removeUser(String username) {
		this.users.remove(username);
	}

	public ArrayList<String> getUsers() {
		return this.users;
	}

	public void setGameSettings(String gamesettings) {
		this.gamesettings = gamesettings;
	}

}
