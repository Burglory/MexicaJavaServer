package mexica.network;

import java.util.ArrayList;
import java.util.Set;

public abstract class RoomManager {

	public static final RoomManager getDefault() {
		return new _RoomManager();
	}

	public abstract boolean removeLobby(String uuid);

	public abstract Set<Room> getOnlineGameLobbies();

	public abstract Room getOnlineGameLobby(String uuid);

	public abstract boolean addLobby(Room l);

	public abstract void setLounge(Lounge l);
	
	public abstract Lounge getLounge();

}
