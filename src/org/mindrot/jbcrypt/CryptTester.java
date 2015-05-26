package org.mindrot.jbcrypt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptTester {

	public static void main(String[] args) {
		try {
		String password = "kdmglkdmglkdrmglkmgldrmgdlrkmdgd";
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_16));
		
		MessageDigest digest2 = MessageDigest.getInstance("SHA-256");
		byte[] hash2 = digest2.digest(password.getBytes(StandardCharsets.UTF_16));
		
		System.out.println(new String(hash, StandardCharsets.UTF_8));
		System.out.println(new String(hash2, StandardCharsets.UTF_8));
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("If all fails....");
		}
	}
	
}
