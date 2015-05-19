package mexica.multiplayer.core;

public class Packet {

	private final String data;
	private final int type;

	/**
	 * Creates a new Packet based on the packet type and the data.
	 * 
	 * @param type
	 *            an int defining the packet type grabbed from PacketType class.
	 * @see PacketType
	 * @param data
	 *            a byte[] defining the data. Maximum length of 92 bytes; The
	 *            data to be defined depends on the type of packet. Must use
	 *            format: (int) datalength (byte[]) data etc...
	 */
	public Packet(int type, String data) {
		this.type = type;
		this.data = data;
	}

	public int getLength() {
		return this.data.length();
	}

	public String getData() {
		return data;
	}

	public int getType() {
		return type;
	}

}
