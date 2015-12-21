package kz.processing.cnp.merchant.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Scanner;

import kz.processing.cnp.epay.merchant.service.MainService;
import kz.processing.cnp.epay.merchant.service.util.HttpUtil;
import kz.processing.cnp.epay.model.bind.Merchant;
import kz.processing.cnp.epay.model.bind.document.CompleteRequest;
import kz.processing.cnp.epay.model.bind.document.GetStatusRequest;
import kz.processing.cnp.epay.model.bind.document.StartResponse;
import kz.processing.cnp.epay.model.entity.Transaction;
import kz.processing.cnp.epay.model.util.BindUtil;
import kz.processing.cnp.epay.model.util.Constants;
import kz.processing.cnp.epay.model.util.KeyValuePair;

import org.apache.commons.codec.binary.Base64;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author daniyar.artykov
 */
public class MainServiceTest { 
	//extends JerseyTest {

	private static final Logger LOG = LoggerFactory.getLogger(MainServiceTest.class);
	private static final String serviceURL = "https://payment.processinggmbh.ch/CNPMerchantService/"; // test
	//	private static final String serviceURL = "http://localhost:9080/CNPMerchantService/"; // local

	private static final String DATA = "PGRvY3VtZW50PjxiYW5rIG5hbWU9IktLQiIgY2FsbGNlbnRlcj0iKDcyNykgMjU4LTU0LTQ0LCAyNTgtNTItNTcsIDI5NS0yOS0zMCwgOC04MDAwLTgwMDAtOTAiPjxjdXN0b21lciBwaG9uZT0iIiBuYW1lPSJEYW5peWFyIiBtYWlsPSJlbWFpbEB0ZXN0Lmt6Ij48bWVyY2hhbnQgbmFtZT0iQU8gTklUIiBjZXJ0X2lkPSI3NzcwMDAwMDAwMDAwMDIiPjxvcmRlciBvcmRlcl9pZD0iRTEwMDAwMDI3NjkyNjEiIGN1cnJlbmN5PSIzOTgiIGFtb3VudD0iMTIzMTAwIj48ZGVwYXJ0bWVudCBtZXJjaGFudF9pZD0iNzc3MDAwMDAwMDAwMDAyIiBrbnA9Ijk5MSIga25vPSIwMzAyIiBrYms9IjEwNDEwMiIgYW1vdW50PSIxMjMxMDAiLz48L29yZGVyPjwvbWVyY2hhbnQ+PG1lcmNoYW50X3NpZ24gdHlwZT0iUlNBIj5tSFE5WTAzTFlIR0J1Kys0YkdqNmlOZHZpaGtUYkp0alBlc0tsdVAzd3FYY1FyejBqMHJHdEpXcnJlc2hTNE5WeDBUc1dQQnkyZjVoc1hYR2w3NFl6Vld4dkF3eGlmN1lQcHhJRWZzQlBhMFBHNEpEV1Z2UEZuYTNFdUxSRVNEL1dsWlNsbzByM1Q3M3VWLzNGT1plR2h5Q0JveG44MHVXN0lqTnoyT0Z5aDVsR3BENTcxZ3k5eHpWcENRMmhSK2hmL3MvemliWng3NXE1R1JCN2wyQmJyWWxJeDJmcnpuMWw1eDhFR0t2RTk4L1YxUnJjN0M4ZjhxM0R4ZEQ4SWhEckMxL3pZMUl5TkcvVmNwY21kZkxGNDI1ZVBaWmF6aWNxV09ldGJOQWVYS3k5MnVTQjJjL2lIMHE5d3hIRit3S0h6TEZSWTM4TDBVY2RibnFZZ0htU3c9PTwvbWVyY2hhbnRfc2lnbj48L2N1c3RvbWVyPjxjdXN0b21lcl9zaWduIHR5cGU9Im5vbmUiLz48cmVzdWx0cyB0aW1lc3RhbXA9IjE1LjA5LjIwMTUgMTA6MTU6NTgiPjxwYXltZW50IHN0YXR1cz0iMCIgc2VjdXJlPSJOTyIgcmVzdWx0PSIwIiByZXNwb25zZV9jb2RlPSIwMCIgcmVmZXJlbmNlPSI1MjU4NzcwMzc5NTMiIHBheW1lbnQ9InRydWUiIG1lcmNoYW50X2lkPSI3NzcwMDAwMDAwMDAwMDIiIGN1cnJlbmN5Y29kZT0iMzk4IiBjYXJkbWFzaz0iNDAxMjAwKioqKioqMzMzNSIgYXBwcm92YWxfY29kZT0iUlQyMDAwIiBhbW91bnQ9IjEzMzEwMCIvPjwvcmVzdWx0cz48L2Jhbms+PGJhbmtfc2lnbiB0eXBlPSJSU0EiIGNlcnRfaWQ9ImNucHRlc3QiPkx6dWQ4UldjcC96YzlmWWwvanl5aEVsL1ZRY2ZCQjVDZnJqMGozYm9iUHljalhJbWt5eGFQSHp6UHN5WFQxM2xVSDdtbW45bGNGWGlQZkZhbk5yMEdCdmVMSnFFQWt1THFyYmpHMXlveTFwa1E3NVpzcFNzdzVoUWI4TTNHczlzd2E1NEZuQjVNcmF0TUw2c1pqZmd4czhhOTBrSlVtTDQ3Rkp3dDRyVm1raDYyMkFBZWs2NDJLODI3cS8zYmRiSzkwVzcwT1J6LzRpSDBNSXdQc0lZY0pENjlLRVlJTXJkeGpWL3dtUHBxeFV1bHVHVnBYaUd2c3ZBMTZLSW1ydFVETHFFZXZNckRESGYwckJCU1RKaVZFZXJZUEo3NUxpUk5vRzR3K041bytHNXkyT2dWejVSOXBYNDhtbGt4bEZDUXlkSU5HeU95dldYRC9xYnphOW1udz09PC9iYW5rX3NpZ24+PC9kb2N1bWVudD4=";
	
	@Test
	@Ignore
	public void testPostRequest() {
//		String certificatesTrustStorePath = "C:/Java/jdk/jdk1.8.0_25/jre/lib/security/cacerts";
//		System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
		HttpUtil.doPost("https://epay.nitec.kz/PEP/Epay.Processing.KZ/PostLink.aspx", null, DATA);
	}
	
	
	@Test
	@Ignore
	public void testStart() {
		String orderId = Long.toString(new Date().getTime());
		Merchant m = new Merchant();
		m.setCertId("NR0000077000019");
		m.setName("Ticketon");
		m.getOrder().setOrderId(orderId.length()>12?orderId.substring(orderId.length()-12):orderId);
		m.getOrder().setAmount("10000");
		m.getOrder().setCurrency("398");
		m.getOrder().getDepartment().setAmount("10000");
		m.getOrder().getDepartment().setMerchantId("NR0000077000019");
		m.getOrder().setPurchaserName("Ivan Ivanov");
		String requestXML = BindUtil.marshall(m, Merchant.class);

		String merchantSign = sign(requestXML);
		StringBuilder signedSb = new StringBuilder("<document>");
		signedSb.append(requestXML);
		signedSb.append("<merchant_sign type=\"RSA\">");
		signedSb.append(merchantSign);
		signedSb.append("</merchant_sign>");
		signedSb.append("</document>");

		LOG.info("startXML={}", signedSb.toString());
		LOG.info(new String(Base64.encodeBase64(signedSb.toString().getBytes())));
		KeyValuePair kv[] = new KeyValuePair[]{
				new KeyValuePair("signed_Order_B64", new String(Base64.encodeBase64(signedSb.toString().getBytes()))),
				new KeyValuePair("email", "test@mail.ru"),
				new KeyValuePair("backLink", "http://test.kz"),
				new KeyValuePair("postLink", "http://localhost:9080/CNPMerchantWebApp/postlink")
		};
		//		HttpUtil.doPost(serviceURL + "start", kv);
		//		LOG.info("{}", verify(new String(Base64.encodeBase64(signedSb.toString().getBytes()))));
	}

	@Test
	@Ignore
	public void testComplete() throws UnsupportedEncodingException {
		CompleteRequest.Merchant merchant = new CompleteRequest.Merchant();
		merchant.getCommand().setType("reverse");
		merchant.setId("777000000000006");
		merchant.getPayment().setAmount("10000");
		merchant.getPayment().setApprovalCode("RT1000");
		merchant.getPayment().setCurrencyCode("398");
		merchant.getPayment().setOrderId("413176799305");
		merchant.getPayment().setReference("413176799305");

		String requestXML = BindUtil.marshall(merchant, CompleteRequest.Merchant.class);
		String merchantSign = sign(requestXML);
		StringBuilder signedSb = new StringBuilder("<document>");
		signedSb.append(requestXML);
		signedSb.append("<merchant_sign type=\"RSA\">");
		signedSb.append(merchantSign);
		signedSb.append("</merchant_sign>");
		signedSb.append("</document>");

		LOG.info("getStatusXML={}", signedSb.toString());
		LOG.info(HttpUtil.doGet(serviceURL + "complete?request=" 
				+ URLEncoder.encode(signedSb.toString(), "UTF-8")));
	}

	@Test
	@Ignore
	public void testGetStatus() throws UnsupportedEncodingException {
		GetStatusRequest.Merchant merchant = new GetStatusRequest.Merchant();
		merchant.setId("NR0000077000019");
		merchant.getOrder().setId("000000510426");
		String requestXML = BindUtil.marshall(merchant, GetStatusRequest.Merchant.class);
		String merchantSign = sign(requestXML);
		StringBuilder signedSb = new StringBuilder("<document>");
		signedSb.append(requestXML);
		signedSb.append("<merchant_sign type=\"RSA\">");
		signedSb.append(merchantSign);
		signedSb.append("</merchant_sign>");
		signedSb.append("</document>");

		LOG.info("getStatusXML={}", signedSb.toString());
		LOG.info(HttpUtil.doGet(serviceURL + "getStatus?request=" 
				+ URLEncoder.encode(signedSb.toString(), "UTF-8")));
		//		GetStatusRequest rq = (GetStatusRequest) BindUtil.unmarshall(signedSb.toString(), GetStatusRequest.class);
	}

	@Test
	@Ignore
	public void testRecurrentStart() {
		String orderId = Long.toString(new Date().getTime());
		Merchant m = new Merchant();
		m.setCertId("777000000000006");
		m.setName("Ticketon");
		m.getOrder().setOrderId(orderId.length()>12?orderId.substring(orderId.length()-12):orderId);
		m.getOrder().setParentOrderId("413176526814");
		m.getOrder().setAmount("10000");
		m.getOrder().setCurrency("398");
		m.getOrder().getDepartment().setAmount("10000");
		m.getOrder().getDepartment().setMerchantId("777000000000006");
		m.getOrder().setPurchaserName("Ivan Ivanov");
		String requestXML = BindUtil.marshall(m, Merchant.class);

		String merchantSign = sign(requestXML);
		StringBuilder signedSb = new StringBuilder("<document>");
		signedSb.append(requestXML);
		signedSb.append("<merchant_sign type=\"RSA\">");
		signedSb.append(merchantSign);
		signedSb.append("</merchant_sign>");
		signedSb.append("</document>");

		LOG.info("recurrentStartXML={}", signedSb.toString());
		KeyValuePair kv[] = new KeyValuePair[]{
				new KeyValuePair("signed_Order_B64", new String(Base64.encodeBase64(signedSb.toString().getBytes()))),
				new KeyValuePair("email", "test@mail.ru"),
				new KeyValuePair("backLink", "http://test.kz"),
				new KeyValuePair("postLink", "http://localhost:9080/CNPMerchantWebApp/postlink")
		};
		//		HttpUtil.doPost(serviceURL + "recurrentStart", kv);
	}

	@Test
	@Ignore
	public void test() {
		Transaction tr = new Transaction();
		tr.setPostLink("post");
		tr.setFailurePostLink("failurePost");
		String status = "PAID";
		String doPostURL = "";
		if(Constants.AUTHORISED.equals(status) 
				|| Constants.PAID.equals(status)
				|| isEmptyString(tr.getFailurePostLink())) {
			doPostURL = tr.getPostLink();
		} else {
			doPostURL = tr.getFailurePostLink();
		}
		LOG.debug(doPostURL);
		//		Scanner sc = new Scanner(System.in);
		//		String d = sc.nextLine();
		//
		//		LOG.debug(d);
		//		LOG.debug(HttpUtil.doPost("https://epay.nitec.kz/Processing.KZ/FailurePostLink.aspx", "response", d));
	}

	public static String sign(String bankStr){
		if(bankStr == null || Constants.EMPTY_STRING.equals(bankStr)){
			return bankStr;
		}
		StringBuilder sb = new StringBuilder("keystore/test/");
		sb.append("kassa24_prv.pk8");
		LOG.debug("keystore: {}", sb.toString());
		try{
			InputStream is = MainService.class.getClassLoader().getResourceAsStream(sb.toString());
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
			LOG.debug("signed string: {}", signEcnodedStr);
			return signEcnodedStr;
		} catch(Exception ex){
			LOG.error("error occurred when signing: {}", ex.getMessage(), ex);
			return null;
		}
	}

	public static boolean verify(String signedBank) {
		Scanner sc = new Scanner(System.in);
		String originXML = sc.nextLine();
		//		String originXML = new String(Base64.decodeBase64(signedBank.getBytes()));
		StartResponse rs = (StartResponse) BindUtil.unmarshall(originXML, StartResponse.class);
		String merchantNodeStr = originXML.substring(originXML.indexOf("<document>") + 10, originXML.indexOf("<bank_sign "));
		String merchantSign = rs.getBankSign().getSign();
		merchantSign = merchantSign.replace(" ", "+");
		LOG.debug("merchantSign={}", merchantSign);
		byte[] verifyBytes = Base64.decodeBase64(merchantSign.getBytes());
		if(true){
			int i = 0;
			for (int j = verifyBytes.length; i < j / 2; i++){
				byte k = verifyBytes[i];
				verifyBytes[i] = verifyBytes[(j - i - 1)];
				verifyBytes[(j - i - 1)] = k;
			}
		}
		try{
			StringBuilder sb = new StringBuilder("keystore/");
			sb.append("test");
			sb.append("/");
			sb.append("processingkz.pub");
			InputStream is = MainServiceTest.class.getClassLoader().getResourceAsStream(sb.toString());
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
		}catch(Exception ex){
			LOG.error("error occurred when verified merchantSign: {}", ex.getMessage(), ex);
			return false;
		}

		return false;
	}

	private boolean isEmptyString(String s) {
		return (s == null || "".equals(s));
	}
}
