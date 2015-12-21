package kz.processing.cnp.epay.merchant.service.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import kz.processing.cnp.epay.merchant.service.config.ConfigFactory;
import kz.processing.cnp.epay.model.util.Constants;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUtil {

	private static final Logger LOG = LoggerFactory.getLogger(SignUtil.class);
	private static final boolean invert = true;

	public static boolean verify(String originXML, String certId, String merchantSign) {
		if(merchantSign == null 
				|| Constants.EMPTY_STRING.equals(merchantSign) 
				|| "null".equals(merchantSign)) {
			return false;
		}
		
		if(certId == null) {
			LOG.warn("certId is null!!");
			
			return false;
		}
		String merchantNodeStr = originXML.substring(originXML.indexOf("<document>") + 10, originXML.indexOf("<merchant_sign "));
		LOG.debug("certId={}", certId);
		merchantSign = merchantSign.replace(" ", "+");
		LOG.debug("merchantSign={}", merchantSign);
		byte[] verifyBytes = Base64.decodeBase64(merchantSign.getBytes());
		if(invert){
			int i = 0;
			for (int j = verifyBytes.length; i < j / 2; i++){
				byte k = verifyBytes[i];
				verifyBytes[i] = verifyBytes[(j - i - 1)];
				verifyBytes[(j - i - 1)] = k;
			}
		}
		try {
			StringBuilder sb = new StringBuilder("keystore/");
			sb.append(ConfigFactory.getProperty(Constants.CNP_MERCHANT_WEB_APP_PROFILE));
			sb.append("/");
			sb.append(certId);
			sb.append(".pub");
			InputStream is = SignUtil.class.getClassLoader().getResourceAsStream(sb.toString());
			Signature signature = Signature.getInstance(Constants.SIGNATURE_ALGORITHM_SHA1_RSA);
			if(is != null) {
				KeyFactory keyFactory = KeyFactory.getInstance(Constants.SIGNATURE_ALGORITHM_RSA);
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
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
				LOG.debug("merchantNode: {}", merchantNodeStr);
				signature.initVerify(publicKey);
				signature.update(merchantNodeStr.getBytes());
				boolean v = signature.verify(verifyBytes);
				LOG.debug("signature.verify: {}", v);

				return v;
			}
			LOG.debug("couldn't find merchant public key, try to get public key from jks store =>");
			sb = new StringBuilder("keystore/");
			sb.append(ConfigFactory.getProperty(Constants.CNP_MERCHANT_WEB_APP_PROFILE));
			sb.append(".jks");
			LOG.debug("keystore: {}", sb.toString());
			is = SignUtil.class.getClassLoader().getResourceAsStream(sb.toString());

			if(is == null) {
				LOG.debug("couldn't find keystore!!");
				return false;
			}

			KeyStore keyStore = KeyStore.getInstance(Constants.KEYSTORE_TYPE);
			keyStore.load(is, ConfigFactory.getProperty(Constants.KEYSTORE_PWD).toCharArray());
			Certificate c = keyStore.getCertificate(certId);
			LOG.debug("merchantNode: {}", merchantNodeStr);
			if(c == null) {
				LOG.debug("couldn't find merchants certificate!!");
				return false;
			}
			signature.initVerify(c);
			signature.update(merchantNodeStr.getBytes());
			boolean v = signature.verify(verifyBytes);
			LOG.debug("signature.verify={}", v);

			return v;
		} catch(Exception ex) {
			LOG.error("error occurred when verified merchantSign: {}", ex.getMessage(), ex);
			return false;
		}
	}

	public static String sign(String bankStr){
		if(bankStr == null || Constants.EMPTY_STRING.equals(bankStr)){
			return bankStr;
		}
		StringBuilder sb = new StringBuilder("keystore/");
		sb.append(ConfigFactory.getProperty(Constants.CNP_MERCHANT_WEB_APP_PROFILE));
		sb.append("/processingkz_prv.pk8");
		LOG.debug("keystore: {}", sb.toString());
		try{
			InputStream is = SignUtil.class.getClassLoader().getResourceAsStream(sb.toString());
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] privKeyBytes = new byte[is.available()];
			while ((nRead = is.read(privKeyBytes, 0, privKeyBytes.length)) != -1) {
				buffer.write(privKeyBytes, 0, nRead);
			}
			buffer.flush();
			is.close();
			Signature signature = Signature.getInstance(Constants.SIGNATURE_ALGORITHM_SHA1_RSA);
			/** **/
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec ks = new PKCS8EncodedKeySpec(privKeyBytes);
			RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(ks);
			/** **/
			signature.initSign(privKey);
			signature.update(bankStr.getBytes());
			byte[] signBytes = signature.sign();
			if(invert){
				int i = 0;
				for (int j = signBytes.length; i < j / 2; i++){
					byte k = signBytes[i];
					signBytes[i] = signBytes[(j - i - 1)];
					signBytes[(j - i - 1)] = k;
				}
			}
			byte[] signEncodedBytes = Base64.encodeBase64(signBytes);
			String signEcnodedStr = new String(signEncodedBytes);
			LOG.debug("processing signed string: {}", signEcnodedStr);
			return signEcnodedStr;
		} catch(Exception ex) {
			LOG.error("error occurred when processing sign: {}", ex.getMessage(), ex);
			return null;
		}
	}
}
