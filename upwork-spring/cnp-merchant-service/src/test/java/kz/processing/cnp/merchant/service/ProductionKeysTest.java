/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.processing.cnp.merchant.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author daniyar.artykov
 */
public class ProductionKeysTest {

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			InputStream is = ProductionKeysTest.class.getResourceAsStream("/keystore/production/ticketon_prv.pk8");

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] privKeyBytes = new byte[is.available()];

			while ((nRead = is.read(privKeyBytes, 0, privKeyBytes.length)) != -1) {
				buffer.write(privKeyBytes, 0, nRead);
			}

			buffer.flush();
			is.close();
			System.out.println("Enter string: ");
			String original = sc.nextLine();

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec ks = new PKCS8EncodedKeySpec(privKeyBytes);
			RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(ks);

			Signature s = Signature.getInstance("SHA1withRSA");

			s.initSign(privKey);
			s.update(original.getBytes());

			byte[] signBytes = s.sign();

			if(true){
				int i = 0;
				for (int j = signBytes.length; i < j / 2; i++){
					byte k = signBytes[i];
					signBytes[i] = signBytes[(j - i - 1)];
					signBytes[(j - i - 1)] = k;
				}
			}

			byte[] signEncodedBytes = Base64.encodeBase64(signBytes);

			String signEcnodedStr = new String(signEncodedBytes);

			System.out.println(signEcnodedStr);



			/*--- Verify ---*/


			byte[] verifyBytes = Base64.decodeBase64(signEcnodedStr.getBytes());
			//			byte[] verifyBytes = sc.nextLine().getBytes();

			if(true){
				int i = 0;
				for (int j = verifyBytes.length; i < j / 2; i++){
					byte k = verifyBytes[i];
					verifyBytes[i] = verifyBytes[(j - i - 1)];
					verifyBytes[(j - i - 1)] = k;
				}
			}

			//			is = ProductionKeysTest.class.getResourceAsStream("/keystore/production/ticketon.pem");
			//			buffer = new ByteArrayOutputStream();
			//			byte [] pubKeyBytes = new byte[is.available()];
			//			while ((nRead = is.read(pubKeyBytes, 0, pubKeyBytes.length)) != -1) {
			//				buffer.write(pubKeyBytes, 0, nRead);
			//			}
			//			buffer.flush();
			//			is.close();
			//			String certStr = new String(pubKeyBytes, "UTF-8");
			//			byte [] decoded = Base64.decodeBase64(certStr.replaceAll("-----BEGIN CERTIFICATE-----", "").replaceAll("-----END CERTIFICATE-----", ""));
			//
			//			X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));

			//2
			//			CertificateFactory fact = CertificateFactory.getInstance("X.509");
			//			X509Certificate cert = (X509Certificate) fact.generateCertificate(is);


			is = ProductionKeysTest.class.getResourceAsStream("/keystore/production/ticketon.pub");
			buffer = new ByteArrayOutputStream();
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

			//			PublicKey publicKey = cert.getPublicKey();

			System.out.println(publicKey.getAlgorithm());

//			is = ProductionKeysTest.class.getResourceAsStream("/keystore/production/processingkz.cer");
//			BufferedInputStream bis = new BufferedInputStream(is);
//			CertificateFactory cf = CertificateFactory.getInstance("X.509");
//			Certificate cert = cf.generateCertificate(bis);

			s.initVerify(publicKey);
			s.update(original.getBytes());

			System.out.println(s.verify(verifyBytes));


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	public X509Certificate convertToX509Certificate(String pem) throws CertificateException, IOException {
	//        X509Certificate cert = null;
	//        StringReader reader = new StringReader(pem);
	//        PEMReader pr = new PEMReader(reader);
	//        cert = (X509Certificate)pr.readObject();
	//        return cert;
	//    }
}
