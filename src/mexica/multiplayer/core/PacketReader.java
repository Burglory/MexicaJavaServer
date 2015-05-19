package mexica.multiplayer.core;

class PacketReader {

	@Deprecated
	public static byte[] Grab(byte[] data, int start, int end) {
		byte[] result = new byte[end - start];
		for (int i = start; i < end; i++) {
			result[i - start] = data[i];
		}
		return result;
	}

	public static int readType(byte[] packet) {
		return packet[0];
	}

	public static int readTotalDataLength(byte[] packet) {
		return packet[1];
	}

	public static String getFieldFromDataString(String fieldname, String data) {
		char[] datachararray = data.toCharArray();
		int startingpoint = data.indexOf(fieldname) + fieldname.length() + 1;
		if (startingpoint == -1)
			return null;
		int i;
		int bracketcount = 0;
		for (i = startingpoint; i < datachararray.length; i++) {
			if (datachararray[i] == '(') {
				bracketcount++;
			} else if (datachararray[i] != ')') {
				bracketcount--;
			}
			if (datachararray[i] == '.' && bracketcount == 0) {
				break;
			} else {
				continue;
			}
		}
		return data.substring(startingpoint, i);
	}

}
