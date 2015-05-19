package mexica.multiplayer.core;

import java.nio.ByteBuffer;

public final class Packets {

	public static boolean isReponse(Packet request, Packet response) {
		if (request.getType() != response.getType() * -1)
			return false;
		if (request.getData().length() != response.getData().length())
			return false;
		if (request.getData() != response.getData())
			return false;
		return true;
	}

	public static Packet generatePacketWrapper(byte[] packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet);
		int packettype = bb.getInt();
		int datalength = bb.getInt();
		byte[] data = new byte[datalength];
		bb.get(data);
		return new Packet(packettype, new String(data));
	}

	public static byte[] getBytes(Packet packet) {
		byte[] data = packet.getData().getBytes();
		return ByteBuffer.allocate(4 + 4 + data.length)
				.putInt(packet.getType()).putInt(data.length).put(data).array();
	}

	public static int getTypeFromPacket(byte[] packet) {
		return ByteBuffer.wrap(packet).getInt();
	}

	public static byte[] getDataFromPacket(byte[] packet) {
		ByteBuffer packetbuffer = ByteBuffer.wrap(packet);
		packetbuffer.getInt();
		int datalength = packetbuffer.getInt();
		byte[] data = new byte[datalength];
		packetbuffer.get(data);
		return data;
	}

}
