package kz.processing.cnp.merchant.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

public class MerchantPublicKeyTest {
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(System.in);
			
			byte[] verifyBytes = Base64.decodeBase64(sc.nextLine().getBytes());
			//			byte[] verifyBytes = sc.nextLine().getBytes();

			if(true){
				int i = 0;
				for (int j = verifyBytes.length; i < j / 2; i++){
					byte k = verifyBytes[i];
					verifyBytes[i] = verifyBytes[(j - i - 1)];
					verifyBytes[(j - i - 1)] = k;
				}
			}

			InputStream is = ProductionKeysTest.class.getResourceAsStream("/keystore/production/ticketon.pub");
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Signature s = Signature.getInstance("SHA1withRSA");
			int nRead;
			byte [] pubKeyBytes = new byte[is.available()];
			while ((nRead = is.read(pubKeyBytes, 0, pubKeyBytes.length)) != -1) {
				buffer.write(pubKeyBytes, 0, nRead);
			}
			buffer.flush();
			is.close();
			String pubKey = new String(pubKeyBytes, "UTF-8");
			pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
			X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decodeBase64(pubKey));
			PublicKey publicKey = keyFactory.generatePublic(spec);
			
			s.initVerify(publicKey);
			s.update(sc.nextLine().getBytes());

			System.out.println(s.verify(verifyBytes));
		} catch (Exception ex) {
			
		}
	}
}
