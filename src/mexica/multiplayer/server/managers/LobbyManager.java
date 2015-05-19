package mexica.multiplayer.server.managers;

import java.util.ArrayList;

import mexica.multiplayer.server.Server;
import mexica.multiplayer.server.core.Lobby;
import mexica.multiplayer.server.core.OnlineGameLobby;
import mexica.multiplayer.server.core.User;

public class LobbyManager {

	private final Server server;
	private final ArrayList<OnlineGameLobby> onlinegamelobbys;
	private final Lobby mainlobby;

	/** RoomManager is used for OnlineGameLobby. */
	public LobbyManager(Server server) {
		this.server = server;
		this.mainlobby = new Lobby(getServer());
		this.onlinegamelobbys = new ArrayList<OnlineGameLobby>();
	}

	public Server getServer() {
		return server;
	}

	public ArrayList<OnlineGameLobby> getOnlinegamelobbys() {
		return onlinegamelobbys;
	}

	public Lobby getMainlobby() {
		return mainlobby;
	}

	public void removeLobby(User sender) {
		for (OnlineGameLobby ogl : this.getOnlinegamelobbys()) {
			if (ogl.getAdministrator().equals(sender)) {
				this.getOnlinegamelobbys().remove(ogl);
				return;
			}
		}
	}

	public String getOnlineGameLobbyInfo() {
		String result = "LobbyInfo:{";
		for (OnlineGameLobby ogl : this.getOnlinegamelobbys()) {
			result += ogl.getInfoString();
		}
		result += "}";
		return result;
	}

}
