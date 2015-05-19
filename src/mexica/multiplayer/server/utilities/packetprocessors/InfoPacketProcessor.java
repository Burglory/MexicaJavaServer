package mexica.multiplayer.server.utilities.packetprocessors;

import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;
import mexica.multiplayer.server.Server;
import mexica.multiplayer.server.core.Connection;
import mexica.multiplayer.server.core.User;

public abstract class InfoPacketProcessor {

	public static void process(Connection connection, Packet p) {
		User sender = connection.getUser();
		switch (p.getType()) {
		case PacketType.Client.Info.OnlinePlayers:
			sender.getConnection().send(
					new Packet(PacketType.Server.Info.OnlinePlayers, sender
							.getConnection().getServer().getConnectionManager()
							.getUsers().toString()));
			break;
		case PacketType.Client.Info.Lobbys:
			sender.getConnection().send(
					new Packet(PacketType.Server.Info.Lobbys, sender
							.getConnection().getServer().getLobbymanager()
							.getOnlineGameLobbyInfo().toString()));
			break;
		case PacketType.Client.Info.ServerVersion:
			sender.getConnection().send(
					new Packet(PacketType.Server.Info.ServerVersion,
							Server.VERSION));
			break;
		default:

		}

	}

}
