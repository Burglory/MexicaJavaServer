package mexica.multiplayer.server.utilities.packetprocessors;

import mexica.core.utilities.DataString;
import mexica.multiplayer.core.CommStandards;
import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;
import mexica.multiplayer.server.core.Connection;
import mexica.multiplayer.server.core.OnlineGame;
import mexica.multiplayer.server.core.OnlineGame.OnlineGameStatus;
import mexica.multiplayer.server.core.OnlineGameLobby;
import mexica.multiplayer.server.core.User;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;

public abstract class LobbyPacketProcessor {

	public static void process(Connection connection, OnlineGame onlinegame,
			Packet p) {
		DataString data = new DataString(p.getData());
		User sender = connection.getUser();
		switch (p.getType()) {
		case PacketType.Client.GameLobby.Join.Request:
			sender.getConnection().send(
					PacketType.Server.GameLobby.Join
							.generateResponsePacket(onlinegame.isStarted()));
			break;
		case PacketType.Client.GameLobby.ClientStatus:

			break;
		case PacketType.Client.GameLobby.Create:
			String name = data.get(CommStandards.CommGameLobby.GameLobbyName);
			String password = data
					.get(CommStandards.CommGameLobby.GameLobbyPassword);
			int maxplayers = Integer.parseInt(data
					.get(CommStandards.CommGameLobby.MaximumPlayers));
			int maxobservers = Integer.parseInt(data
					.get(CommStandards.CommGameLobby.MaximumObservers));
			sender.getConnection()
					.getServer()
					.getLobbymanager()
					.getOnlinegamelobbys()
					.add(new OnlineGameLobby(sender, name, password,
							maxplayers, maxobservers));
			break;
		case PacketType.Client.GameLobby.LobbySettings:
			if (sender.isHost(onlinegame)) {
				onlinegame.broadcast(PacketType.Server.GameLobby
						.generateLobbySettingsPacket(p.getData()));
			}
			break;
		case PacketType.Client.GameLobby.Kick:
			if (sender.isHost(onlinegame)) {
				String username = data
						.get(CommStandards.CommOnlineGame.CommOnlinePlayer.Username);
				String reason = data.get(CommStandards.CommGameLobby.Reason);
				User tobekicked = onlinegame.getOnlinePlayer(username);
				tobekicked.getConnection().send(
						PacketType.Server.GameLobby.generateKickPacket(reason));
				onlinegame.removeOnlinePlayer(tobekicked);
			}
			break;
		case PacketType.Client.GameLobby.Leave:
			onlinegame.removeOnlinePlayer(sender);
			onlinegame.broadcast(PacketType.Server.Chat
					.generateGeneralChatPacket(sender.getUsername(),
							(sender.getUsername() + " left the game.")));
			break;
		case PacketType.Client.GameLobby.Remove:
			sender.getConnection().getServer().getLobbymanager()
					.removeLobby(sender);
			break;
		case PacketType.Client.GameLobby.Status:
			if (sender.isHost(onlinegame)) {
				OnlineGameStatus ogs = OnlineGameStatus
						.getOnlineGameStatus(data
								.get(CommStandards.CommGameLobby.Status));
				onlinegame.setStatus(ogs);
				onlinegame.broadcast(PacketType.Server.GameLobby
						.generateGameStatusPacket(ogs.getShort()));
			}
			break;
		default:
			sender.getConnection()
					.getServer()
					.getLogger()
					.Log("(PacketProcessor) Received unknown Game Packet from: "
							+ sender.getUsername(), ServerLogLevel.INFO);
			break;
		}
	}

}
