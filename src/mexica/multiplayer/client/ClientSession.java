package mexica.multiplayer.client;

public class ClientSession {

	private ClientConnection clientconnection;

	public ClientSession(ClientConnection clientConnection) {
		this.clientconnection = clientConnection;
	}

	public void addOnlineGame(ClientOnlineGame clientOnlineGame) {
		// this.onlinegames.add(clientOnlineGame);
	}

}
