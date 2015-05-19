package mexica.multiplayer.client;

import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;

class ClientPacketHandler {

	static void process(ClientConnection connection, Packet p) {
		int type = p.getType();
		if (PacketType.isConnectionPacket(type)) {
			PacketProcessor.processConnectionPacket(connection, p);
		} else if (PacketType.isChatPacket(type)) {
			PacketProcessor.processChatPacket(connection, p);
		} else if (PacketType.isGamePacket(type)) {
			PacketProcessor.processGamePacket(connection, p);
		} else if (PacketType.isPlayPacket(type)) {
			PacketProcessor.processPlayPacket(connection, p);
		} else if (PacketType.isInfoPacket(type)) {
			PacketProcessor.processInfoPacket(connection, p);
		} else {

		}

	}

}
