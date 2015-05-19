package mexica.multiplayer.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

class Securities {

	static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] databasepasswordbytes = (password).getBytes();
		byte[] passhash = sha256.digest(databasepasswordbytes);
		String thehash = DatatypeConverter.printBase64Binary(passhash);
		return thehash;
	}

	static byte[] hashPasswordToken(String password, String token)
			throws NoSuchAlgorithmException {
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] databasepasswordbytes = (token + password).getBytes();
		byte[] passhash = sha256.digest(databasepasswordbytes);
		return passhash;
	}

	public static String toHex(byte[] bytes) {
		BigInteger bi = new BigInteger(1, bytes);
		return String.format("%0" + (bytes.length << 1) + "X", bi);
	}

}
