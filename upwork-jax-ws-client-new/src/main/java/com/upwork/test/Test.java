package com.upwork.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Test {

	public static void main(String args[]) {
		try {
			
			List<String> list = new ArrayList<String>();
			list.add("Hello");
			list.add(" World!!");
			
			System.out.println(list);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream(bos);
		    oos.writeObject(list);
		    byte[] text = bos.toByteArray();
			
			KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
			SecretKey myDesKey = keygenerator.generateKey();
			Cipher desCipher;
			desCipher = Cipher.getInstance("DES");

			desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
			byte[] textEncrypted = desCipher.doFinal(text);
			
			desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
			byte[] textDecrypted = desCipher.doFinal(textEncrypted);
			
			ByteArrayInputStream bis = new ByteArrayInputStream(textDecrypted);
		    ObjectInputStream ois = new ObjectInputStream(bis);
		    List<String> result = (List<String>) ois.readObject();
			
			System.out.println(result);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
