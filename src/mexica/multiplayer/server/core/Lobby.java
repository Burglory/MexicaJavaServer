package mexica.multiplayer.server.core;

import java.util.ArrayList;

import mexica.multiplayer.core.Packet;
import mexica.multiplayer.server.Server;

public class Lobby {

	private ArrayList<User> userlist;
	private Server server;
	private String id;

	/**
	 * The place where Users are put after logging in. From here they can choose
	 * to join certain games.
	 */
	public Lobby(Server server, String id) {
		this.server = server;
		this.id = id;
		this.userlist = new ArrayList<User>();
	}

	/** Get a list containing all User in this lobby. */
	public ArrayList<User> getUsers() {
		return userlist;
	}

	/** Add a user to the lobby. */
	public void addUser(User playerclient) {
		this.userlist.add(playerclient);
	}

	/** Remove a User from the lobby. */
	public void removeUser(Connection playerclient) {
		this.userlist.remove(playerclient);
	}

	/** Broadcast a Packet for all people in this lobby. */
	public void send(Packet p) {
		for (User pc : this.getUsers()) {
			pc.getConnection().send(p);
		}
	}

	public Server getServer() {
		return server;
	}

	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

}
