package mexica.network.security.protocols.pubkeypass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class RSA {

	/**
	 * String to hold name of the encryption algorithm.
	 */
	public static final String ALGORITHM = "RSA";
	public static final String ENCRYPT_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
	// public static final String ENCRYPT_ALGORITHM = "RSA/ECB/NoPadding";

	private PrivateKey privatekey;
	private PublicKey publickey;

	/**
	 * Generate key which contains a pair of private and public key using 1024
	 * bytes. Store the set of keys in Prvate.key and Public.key files.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void generateKeys() {
		try {

			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.nextBytes(new byte[1024]);

			final KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(ALGORITHM);
			keyGen.initialize(1024, sr);

			final KeyPair key = keyGen.generateKeyPair();

			this.privatekey = key.getPrivate();
			this.publickey = key.getPublic();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateKeys(byte[] seed) {
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.setSeed(seed);
			//sr.nextBytes(new byte[1024]);

			final KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(ALGORITHM);
			keyGen.initialize(1024, sr);

			final KeyPair key = keyGen.generateKeyPair();

			this.privatekey = key.getPrivate();
			this.publickey = key.getPublic();
		} catch (Exception e) {

		}
	}
	
	public static byte[] sign(byte[] text, PrivateKey key) {
		byte[] cipherText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	/**
	 * Encrypt the plain text using public key.
	 * 
	 * @param text
	 *            : original plain text
	 * @param key
	 *            :The public key
	 * @return Encrypted text
	 * @throws java.lang.Exception
	 */
	public static byte[] encrypt(byte[] text, PublicKey key) {
		byte[] cipherText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	/**
	 * Decrypt text using private key.
	 * 
	 * @param text
	 *            :encrypted text
	 * @param key
	 *            :The private key
	 * @return plain text
	 * @throws java.lang.Exception
	 */
	public static byte[] decrypt(byte[] text, PrivateKey key) {
		byte[] decryptedtext = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);

			// decrypt the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedtext = cipher.doFinal(text);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return decryptedtext;
	}


	public static void main(String[] args) {

		try {

			String username = "testusername";
			String password = "1241982419j2in29f2i32o";
			 RSA r= new RSA();
			 String seedstring = username + password;
			 r.generateKeys(seedstring.getBytes(StandardCharsets.UTF_8));
			 System.out.println(new String(r.publickey.getEncoded(), StandardCharsets.UTF_8));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}