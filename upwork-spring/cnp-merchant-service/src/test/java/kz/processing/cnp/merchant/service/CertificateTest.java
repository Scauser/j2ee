package kz.processing.cnp.merchant.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

public class CertificateTest {
	public static void main(String [] args) {
		try {
//			System.out.println(new String(Base64.decodeBase64("0JzQntCg0JbQmNCa0J7QkiDQktCb0JDQlNCY0KHQm9CQ0JIg0JDQndCU0KDQldCV0JLQmNCn".getBytes())));
			
			InputStream is = ProductionKeysTest.class.getResourceAsStream("/keystore/production/processingkz.cer");
			BufferedInputStream bis = new BufferedInputStream(is);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Certificate cert = cf.generateCertificate(bis);

			System.out.println("-----BEGIN CERTIFICATE-----");
	        System.out.println(DatatypeConverter.printBase64Binary(cert.getEncoded()));
	        System.out.println("-----END CERTIFICATE-----");
			
//			byte[] derCert = cert.getEncoded();
//			String pemCertPre = new String(Base64.encodeBase64(derCert));
//
//			System.out.println(pemCertPre);

			PublicKey pk = cert.getPublicKey();
			
			System.out.println(new String(Base64.encodeBase64(pk.getEncoded())));
			
			//			System.out.println(pk);
			Signature s = Signature.getInstance("SHA1withRSA");
			byte[] verifyBytes = Base64.decodeBase64("".getBytes());
			if(true){
				int i = 0;
				for (int j = verifyBytes.length; i < j / 2; i++){
					byte k = verifyBytes[i];
					verifyBytes[i] = verifyBytes[(j - i - 1)];
					verifyBytes[(j - i - 1)] = k;
				}
			}

			s.initVerify(pk);
			s.update("".getBytes());

			//			System.out.println(s.verify(verifyBytes));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
