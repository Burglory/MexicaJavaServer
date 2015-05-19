package mexica.network;

import java.util.Set;

import com.nionetframework.common.ConnectionManager;

public abstract class NetworkGame {

	public static final int STOPPED = 2;
	public static final int STARTED = 1;
	public static final int CREATED = 0;

	/** Returns the {@link OnlineGameStatus} this OnlineGame is in. */
	abstract int getOnlineGameStatus();

	/**
	 * Convenience method. Usually faster than
	 * {@link ConnectionManager#getConnection(String)}, because of a smaller
	 * Set.
	 */
	abstract User getUser(String username);

	abstract Set<User> getPlayers();

	/** Returns wether this OnlineGame has started. */
	@Deprecated
	abstract
	boolean isStarted();

	/** Returns the {@link Room} associated with this OnlineGame. */
	abstract Room getOnlineGameLobby();

	/** Sets the {@link OnlineGameStatus}. */
	abstract void setOnlineGameStatus(int i);
	
}
