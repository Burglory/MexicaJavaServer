package mexica.network;

import java.util.HashSet;
import java.util.Set;

public class _Room extends Room {

	private String uuid;
	private NetworkGame networkgame;
	private Set<User> spectators;
	private HashSet<User> inhabitants;
	private HashSet<User> players;
	private User admin;

	public _Room(String uuid, User admin) {
		this.uuid = uuid;
		this.networkgame = null;
		this.spectators = new HashSet<User>();
		this.inhabitants = new HashSet<User>();
		this.players = new HashSet<User>();
		this.admin = admin;
	}

	@Override
	public String getUUID() {
		return uuid;
	}

	@Override
	public NetworkGame getNetworkGame() {
		return this.networkgame;
	}

	@Override
	public Set<User> getSpectators() {
		return this.spectators;
	}

	@Override
	public Set<User> getUsers() {
		return this.inhabitants;
	}

	@Override
	public Set<User> getPlayers() {
		return this.players;
	}

	@Override
	public User getAdmin() {
		return this.admin;
	}

	@Override
	public boolean newNetworkGame() {
		this.networkgame = new _NetworkGame(this);
		return true;
	}

	@Override
	public boolean joinLobby(User u) {
		this.inhabitants.add(u);
		return this.becomeSpectator(u);
	}

	@Override
	public boolean becomePlayer(User u) {
		this.spectators.remove(u);
		return this.players.add(u);
	}

	@Override
	public boolean becomeSpectator(User u) {
		this.players.remove(u);
		return this.spectators.add(u);
	}

	@Override
	public boolean leaveLobby(User u) {
		this.players.remove(u);
		this.spectators.remove(u);
		return this.inhabitants.remove(u);
	}

}
