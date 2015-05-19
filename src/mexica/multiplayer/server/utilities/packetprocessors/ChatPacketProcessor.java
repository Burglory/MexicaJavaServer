package mexica.multiplayer.server.utilities.packetprocessors;

import mexica.core.utilities.DataString;
import mexica.multiplayer.core.CommStandards;
import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;
import mexica.multiplayer.server.core.Connection;
import mexica.multiplayer.server.core.OnlineGame;
import mexica.multiplayer.server.core.User;

public abstract class ChatPacketProcessor {

	public static void process(Connection connection, OnlineGame onlinegame,
			Packet p) {
		DataString data = new DataString(p.getData());
		User sender = connection.getUser();

		String tobesendtoname = data
				.get(CommStandards.CommChat.ChatDestination);
		String message = data.get(CommStandards.CommChat.ChatMessage);

		switch (p.getType()) {
		case PacketType.Client.Chat.General:
			User tobesendto = connection.getServer().getConnectionManager()
					.getUser(tobesendtoname);
			if (tobesendto != null) {
				tobesendto.getConnection().send(
						PacketType.Server.Chat.generateGeneralChatPacket(
								sender.getUsername(), message));
			} else {
				sender.getConnection().send(
						PacketType.Server.Chat.generateGeneralChatPacket(
								"SERVER", "Username does not exist: "
										+ tobesendtoname));
			}
		}

	}

}
