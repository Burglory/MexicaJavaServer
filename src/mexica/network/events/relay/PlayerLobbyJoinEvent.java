package mexica.network.events.relay;

import mexica.network.Room;
import mexica.network.User;
import mexica.network.events.NetworkEvent;
import mexica.network.events.PlayerLobbyEvent;
import mexica.network.events.PlayerNetworkEvent;

import com.nionetframework.common.data.DataBuilder;
import com.nionetframework.common.data.DataString;

public class PlayerLobbyJoinEvent extends PlayerLobbyEvent {
	
	public static final String EVENTNAME = PlayerLobbyEvent.EVENT_NAME + "j";

	public PlayerLobbyJoinEvent(User u, Room l) {
		super(u, l);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getString() {
		return new DataBuilder().add(NetworkEvent.EVENTNAME_FIELD)
				.wrap(EVENTNAME)
				.add(PlayerNetworkEvent.USER_FIELD)
				.wrap(getUser().getUsername())
				.add(PlayerLobbyEvent.LOBBY_FIELD)
				.wrap(getLobby().getUUID())
				.getString();
	}

}
