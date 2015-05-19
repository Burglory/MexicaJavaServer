package mexica.network;

import java.util.Set;

public class _NetworkGame extends NetworkGame {

	private Set<User> players;
	private _Room onlinegamelobby;
	private int onlinegamestatus;

	public _NetworkGame(_Room o) {
		this.onlinegamelobby = o;
		this.players = o.getPlayers();
		this.onlinegamestatus = NetworkGame.CREATED;
		this.setGameForPlayers();
	}

	private void setGameForPlayers() {
		for (User u : players) {
			((_User) u).setOnlineGame(this);
		}
	}

	private void unSetGameForPlayers() {
		for (User u : players) {
			((_User) u).unsetOnlineGame();
		}
	}

	@Override
	public int getOnlineGameStatus() {
		return this.onlinegamestatus;
	}

	@Override
	public User getUser(String username) {
		for (User u : this.players) {
			if (u.getUsername().equals(username))
				return u;
		}
		return null;
	}

	@Override
	public Set<User> getPlayers() {
		return this.players;
	}

	@Override
	public boolean isStarted() {
		return this.onlinegamestatus >= NetworkGame.STARTED;
	}

	@Override
	public Room getOnlineGameLobby() {
		return this.onlinegamelobby;
	}

	@Override
	public void setOnlineGameStatus(int i) {
		this.onlinegamestatus = i;
	}

	
}
