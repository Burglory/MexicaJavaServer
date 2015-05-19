package mexica.network;

import java.util.Set;

public abstract class Room {

	public abstract String getUUID();

	public abstract NetworkGame getNetworkGame();

	public abstract Set<User> getSpectators();

	public abstract Set<User> getUsers();

	public abstract Set<User> getPlayers();

	/**
	 * Returns the {@link User} that created the Room and is
	 * considered the admin of this room.
	 */
	public abstract User getAdmin();

	/** Creates a new OnlineGame for this Room. */
	public abstract boolean newNetworkGame();

	/**
	 * Join the Room as a observer. To become a player, use
	 * {@link #becomePlayer(User u)}
	 */
	public abstract boolean joinLobby(User u);

	/**
	 * Flags the {@link User} as a player, if the maximum of players is not
	 * reached.
	 */
	public abstract boolean becomePlayer(User u);

	/**
	 * Flags the {@link User} as a observer, if the maximum of observers is not
	 * reached.
	 */
	public abstract boolean becomeSpectator(User u);

	/** Removes the {@link User} from the room. */
	public abstract boolean leaveLobby(User u);
	
}
