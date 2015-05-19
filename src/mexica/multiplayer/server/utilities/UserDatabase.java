package mexica.multiplayer.server.utilities;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class UserDatabase {

	private HashMap<String, String> uuid_hashedpasswords;
	private static String uuid = "1";
	private static String hashedpassword = "a9nL6rXyvJGfhkbXDyCHFy6Zq9mfNzZ/e+nbY3k6puU=";
	private static String plainpassword = "testingserver123";

	public UserDatabase() {
		this.uuid_hashedpasswords = new HashMap<String, String>();
		this.uuid_hashedpasswords.put(uuid, hashedpassword);
	}

	public static boolean Contains(String username, String hashedpassword,
			String token) {
		try {
			System.out.println("Hashing disabled for now. Ignore messages.");
			System.out
					.println("Hashing original password with token: " + token);
			String databasepassword = SecurityUtilities.toHex(SecurityUtilities
					.hashDBPWandToken(UserDatabase.hashedpassword, token));
			System.out.println("Original (hashed) password: "
					+ databasepassword);
			System.out.println("Received version: " + hashedpassword);
			if (plainpassword.equals(hashedpassword)) {
				return true;
			} else {
				System.out.println("Hashmismatch!");
				return false;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getUUID(String username) {
		return UserDatabase.uuid;
	}

	public static boolean containsUsername(String string) {
		return false;
	}

	public static void add(String string, String string2) {
		// TODO Auto-generated method stub

	}

}
