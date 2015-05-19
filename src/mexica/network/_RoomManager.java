package mexica.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class _RoomManager extends RoomManager {

	private Set<Room> onlinegamelobbies;

	public _RoomManager() {
		this.onlinegamelobbies = Collections
				.newSetFromMap(new ConcurrentHashMap<Room, Boolean>());
	}

	@Override
	public Set<Room> getOnlineGameLobbies() {
		return this.onlinegamelobbies;
	}

	@Override
	public Room getOnlineGameLobby(String uuid) {
		for (Room o : this.onlinegamelobbies) {
			if (o.getUUID().equals(uuid))
				return o;
		}
		return null;
	}

	@Override
	public boolean removeLobby(String uuid) {
		for (Room o : this.onlinegamelobbies) {
			if (o.getUUID().equals(uuid)) {
				return this.onlinegamelobbies.remove(o);
			}
		}
		return false;
	}

	@Override
	boolean addLobby(Room l) {
		return this.onlinegamelobbies.add(l);
	}

}
