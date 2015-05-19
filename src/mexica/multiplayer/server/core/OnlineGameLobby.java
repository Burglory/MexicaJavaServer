package mexica.multiplayer.server.core;

import java.util.ArrayList;

public class OnlineGameLobby {

	private OnlineGame onlinegame;
	private final User administrator;
	private ArrayList<User> onlineplayers;
	private String password;
	private int maxplayers;
	private int maxobservers;
	private String lobbyname;
	private ArrayList<User> observers;
	private boolean passwordprotected;

	/**
	 * OnlineGameLobby around a certain OnlineGame. The lobby can persist after
	 * a certain OnlineGame has ended.
	 */
	public OnlineGameLobby(User administrator, String lobbyname,
			String password, int maxplayers, int maxobservers) {
		onlineplayers = new ArrayList<>();
		observers = new ArrayList<>();
		this.administrator = administrator;
		this.lobbyname = lobbyname;
		this.password = password;
		this.passwordprotected = !password.isEmpty();
		this.maxplayers = maxplayers;
		this.maxobservers = maxobservers;
	}

	public OnlineGame getOnlinegame() {
		return onlinegame;
	}

	public void setOnlinegame(OnlineGame onlinegame) {
		this.onlinegame = onlinegame;
	}

	public User getAdministrator() {
		return administrator;
	}

	public void addOnlinePlayer(User user) {
		this.onlineplayers.add(user);
	}

	public void removeOnlinePlayer(User user) {
		this.onlineplayers.remove(user);
	}

	public ArrayList<User> getOnlinePlayers() {
		return this.onlineplayers;
	}

	public void addObserver(User user) {
		this.observers.add(user);
	}

	public void removeObserver(User user) {
		this.observers.remove(user);
	}

	public ArrayList<User> getObservers() {
		return this.observers;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		this.passwordprotected = !password.isEmpty();
	}

	public boolean isPasswordProtected() {
		return this.passwordprotected;
	}

	public int getMaxplayers() {
		return maxplayers;
	}

	public void setMaxplayers(int maxplayers) {
		this.maxplayers = maxplayers;
	}

	public int getMaxobservers() {
		return maxobservers;
	}

	public void setMaxobservers(int maxobservers) {
		this.maxobservers = maxobservers;
	}

	public String getInfoString() {
		return "n{"
				+ this.lobbyname
				+ "}a{"
				+ this.administrator.getUsername()
				+ "}l{"
				+ !this.password.isEmpty()
				+ "}mp{"
				+ this.maxplayers
				+ "}mo{"
				+ this.maxobservers
				+ "}p{"
				+ this.onlineplayers.toString().replace("[", "")
						.replace("]", "").replace(" ", "")
				+ "}o{"
				+ this.observers.toString().replace("[", "").replace("]", "")
						.replace(" ", "") + "}";
	}

	public String getLobbyname() {
		return lobbyname;
	}

	public void setLobbyname(String lobbyname) {
		this.lobbyname = lobbyname;
	}

}
