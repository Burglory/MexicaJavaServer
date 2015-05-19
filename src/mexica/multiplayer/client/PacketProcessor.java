package mexica.multiplayer.client;

import mexica.core.utilities.DataString;
import mexica.multiplayer.core.CommStandards;
import mexica.multiplayer.core.CommStandards.CommChat;
import mexica.multiplayer.core.CommStandards.CommGameLobby;
import mexica.multiplayer.core.CommStandards.CommOnlineGame;
import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;

class PacketProcessor {

	public static void processConnectionPacket(ClientConnection connection,
			Packet p) {
		DataString data = new DataString(p.getData());
		switch (p.getType()) {
		case PacketType.Server.Connection.Authentication.Token:
			String token = data.get(CommStandards.CommConnection.Token);
			connection.setToken(token);
			break;
		case PacketType.Server.Connection.Authentication.Login.Response:
			boolean answer = CommStandards.parseStringBoolean(data
					.get(CommStandards.CommConnection.Answer));
			if (answer) {
				System.out.println("Received auth response: Login succesful.");
			} else {
				System.out.println("Received auth response: Login failed.");
			}
		}
	}

	public static void processChatPacket(ClientConnection connection, Packet p) {
		DataString data = new DataString(p.getData());
		switch (p.getType()) {
		case PacketType.Server.Chat.General:
			String sender = data.get(CommChat.Sender);
			String message = data.get(CommChat.ChatMessage);
			System.out.println("Message from " + sender + ": " + message);
		}
	}

	public static void processGamePacket(ClientConnection connection, Packet p) {
		DataString data = new DataString(p.getData());
		switch (p.getType()) {
		case PacketType.Server.GameLobby.Join.Response:
			boolean answer = CommStandards.parseStringBoolean(data
					.get(CommGameLobby.Answer));
			if (answer) {
				System.out.println("You can join the game!");
			} else {
				System.out.println("You cant join the game!");
			}
		case PacketType.Server.GameLobby.Create:
			// TODO: Klopt niet!
			ClientSession session = connection.getClientSession();
			session.addOnlineGame(new ClientOnlineGame(data
					.get(CommGameLobby.Answer), data
					.get(CommGameLobby.GameLobbyName), data
					.get(CommOnlineGame.Settings)));
		}
	}

	public static void processPlayPacket(ClientConnection connection, Packet p) {
		// TODO Auto-generated method stub

	}

	public static void processInfoPacket(ClientConnection connection, Packet p) {
		// TODO Auto-generated method stub

	}

}
