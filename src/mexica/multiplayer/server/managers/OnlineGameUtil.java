package mexica.multiplayer.server.managers;

import mexica.multiplayer.server.core.OnlineGame;
import mexica.multiplayer.server.core.User;

public final class OnlineGameUtil {

	/**
	 * Returns wether the game can be joined. (If the OnlineGame has not already
	 * been started)
	 */
	public static boolean canJoin(User user, OnlineGame onlinegame) {
		return onlinegame.isStarted();
	}

}
