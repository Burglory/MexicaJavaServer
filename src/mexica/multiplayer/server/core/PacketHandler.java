package mexica.multiplayer.server.core;

import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;
import mexica.multiplayer.server.Server;
import mexica.multiplayer.server.managers.ConnectionManager;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;
import mexica.multiplayer.server.utilities.packetprocessors.ChatPacketProcessor;
import mexica.multiplayer.server.utilities.packetprocessors.ConnectionPacketProcessor;
import mexica.multiplayer.server.utilities.packetprocessors.InfoPacketProcessor;
import mexica.multiplayer.server.utilities.packetprocessors.LobbyPacketProcessor;

public class PacketHandler {

	private Server server;
	private ConnectionManager connectionmanager;

	public PacketHandler(Server s, ConnectionManager connectionmanager) {
		this.server = s;
		this.connectionmanager = connectionmanager;
	}

	public void process(Connection connection, Packet p) {
		User sender = this.connectionmanager.getUser(connection);
		OnlineGame onlinegame = null;
		if (sender != null) {
			onlinegame = sender.getOnlineGame();
		}

		int type = p.getType();
		if (PacketType.isConnectionPacket(type)) {
			// Connection
			ConnectionPacketProcessor.process(connection, p);
		} else if (PacketType.isChatPacket(type)) {
			// Chat
			ChatPacketProcessor.process(connection, onlinegame, p);
		} else if (PacketType.isGamePacket(type)) {
			// Game
			LobbyPacketProcessor.process(connection, onlinegame, p);
		} else if (PacketType.isPlayPacket(type)) {
			// Play
			if (onlinegame != null) {
				onlinegame.processPlayPacket(connection, p);
			} else {
				this.server
						.getLogger()
						.Log("(PacketHandler) Received Play packet but User is not associated with a game.",
								ServerLogLevel.INFO);
			}

		} else if (PacketType.isInfoPacket(type)) {
			// Info
			InfoPacketProcessor.process(connection, p);
		} else {
			this.server.getLogger().Log(
					"(PacketHandler) Unkown packettype: " + type + " from "
							+ connection.getSocket().getInetAddress(),
					ServerLogLevel.INFO);
		}

	}

}
