package mexica.network.events;

import mexica.network.Room;
import mexica.network.User;

public abstract class PlayerLobbyEvent extends PlayerNetworkEvent {

	public static final String EVENT_NAME = PlayerNetworkEvent.EVENT_NAME + "l";
	public static final String LOBBY_FIELD = "l";
	private Room room;

	public PlayerLobbyEvent(User u, Room l) {
		super(u);
		this.room = l;
	}
	
	public Room getLobby() {
		return this.room;
	}

}
