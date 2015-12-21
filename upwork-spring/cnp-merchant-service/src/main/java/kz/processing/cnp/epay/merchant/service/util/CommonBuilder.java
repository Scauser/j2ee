package kz.processing.cnp.epay.merchant.service.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kz.processing.cnp.internal_ws_client.InternalWebServiceStub.KazBank;
import kz.processing.cnp.epay.merchant.service.config.ConfigFactory;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.AdditionalInformation;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.GoodsItem;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StoredTransactionStatusCode;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StoredTransactionStatusExtended;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.TransactionDetails;
import kz.processing.cnp.epay.model.bind.Bank;
import kz.processing.cnp.epay.model.bind.BankSign;
import kz.processing.cnp.epay.model.bind.Customer;
import kz.processing.cnp.epay.model.bind.CustomerSign;
import kz.processing.cnp.epay.model.bind.Payment;
import kz.processing.cnp.epay.model.bind.Result;
import kz.processing.cnp.epay.model.bind.document.CompleteRequest;
import kz.processing.cnp.epay.model.bind.document.CompleteResponse;
import kz.processing.cnp.epay.model.bind.document.GetStatusResponse;
import kz.processing.cnp.epay.model.bind.document.StartErrorResponse;
import kz.processing.cnp.epay.model.bind.document.StartRequest;
import kz.processing.cnp.epay.model.bind.document.StartResponse;
import kz.processing.cnp.epay.model.entity.Transaction;
import kz.processing.cnp.epay.model.util.BeanUtil;
import kz.processing.cnp.epay.model.util.BindUtil;
import kz.processing.cnp.epay.model.util.Constants;
import kz.processing.cnp.epay.model.util.DOMUtil;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CommonBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(CommonBuilder.class);
	private static final SimpleDateFormat SDF_FULL_1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	private static final SimpleDateFormat SDF_FULL_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String eGovMerchantId;
	//	private static String chocolifeMerchantId;

	static { 
		eGovMerchantId = ConfigFactory.getProperty("egov.merchant.id");
		//		chocolifeMerchantId = ConfigFactory.getProperty("chocolife.merchant.id");
	}

	public static String buildStartResponseXML(KazBank issuerBank, 
			StoredTransactionStatusCode status, 
			StoredTransactionStatusExtended extendedStatus, 
			Transaction tr) {
		String signedOrderB64  = new String(Base64.decodeBase64(tr.getSignedOrderB64().getBytes()));
		StartRequest request = (StartRequest) BindUtil.unmarshall(signedOrderB64, StartRequest.class);
		StartResponse document = new StartResponse();
		Bank bank = new Bank();
		String issuerBankContact = "";
		if(issuerBank != null && issuerBank.getContactEn() != null) {
			Pattern pattern1 = Pattern.compile("\\([0-9]*\\)");
			Pattern pattern2 = Pattern.compile("\\d{3}-\\d{2}-\\d{2}");
			Pattern pattern3 = Pattern.compile("\\d{1}-\\d{4}-\\d{4}-\\d{2}");
			Pattern pattern4 = Pattern.compile("\\d{1}-\\d{4}-\\d{3}-\\d{3}");
			Matcher matcher = pattern1.matcher(issuerBank.getContactEn());
			StringBuilder sb = new StringBuilder();
			if (matcher.find()) {
				sb.append(matcher.group());
			}
			sb.append(" ");
			matcher = pattern2.matcher(issuerBank.getContactEn());
			while (matcher.find()) {
				sb.append(matcher.group());
				sb.append(", ");
			}
			matcher = pattern3.matcher(issuerBank.getContactEn());
			if (matcher.find()) {
				sb.append(matcher.group());
				sb.append(", ");
			}
			matcher = pattern4.matcher(issuerBank.getContactEn());
			if (matcher.find()) {
				sb.append(matcher.group());
				sb.append(", ");
			}
			issuerBankContact = sb.substring(0, sb.length() - 2).toString();
		}
		bank.setName(issuerBank!=null && issuerBank.getBank()!=null ? issuerBank.getBank() : "");
		bank.setCallCenter(issuerBankContact);
		Customer c = new Customer();
		c.setMail(status.getPurchaserEmail()==null ? "" : status.getPurchaserEmail().trim());
		c.setName(status.getPurchaserName().trim());
		c.setPhone(status.getPurchaserPhone()==null ? "" : status.getPurchaserPhone().trim());
		c.setMerchant(request.getMerchant());
		c.setMerchantSign(request.getMerchantSign());
		bank.setCustomer(c);
		CustomerSign cs = new CustomerSign();
		cs.setType("none");
		bank.setCustomerSign(cs);
		Result result = new Result();
		result.setTimestamp(Constants.SDF_FULL.format(tr.getStatusDate()));
		String p = getPaymentFromTranStatus(status.getTransactionStatus());
		Payment payment = new Payment();

		//		payment.setAmount(eGovMerchantId.equals(tr.getMerchantId()) 
		//				? Integer.toString(Integer.parseInt(status.getAmountAuthorised()) - 10000) 
		//						: status.getAmountAuthorised());

		payment.setAmount(!isEmptyString(status.getAmountAuthorised())?status.getAmountAuthorised():"");

		payment.setApprovalCode(status.getAuthCode()==null?"":status.getAuthCode());
		//		payment.setCardhash(extendedStatus!=null && extendedStatus.getCardHashNumber()!=null 
		//				? extendedStatus.getCardHashNumber() : "");
		payment.setCardmask(extendedStatus!=null && extendedStatus.getMaskedCardNumber()!=null 
				? extendedStatus.getMaskedCardNumber() : "");
		payment.setCurrencyCode(status.getTransactionCurrencyCode() != null 
				? status.getTransactionCurrencyCode() : "");
		payment.setMerchantId(request.getMerchant().getCertId());
		payment.setPayment(p);
		payment.setReference(status.getBankRRN());
		payment.setResponseCode(status.getRspCode());
		payment.setResult(getResultFromTranStatus(status.getTransactionStatus()));
		payment.setSecure(extendedStatus.getVerified3D() == null ? "" : extendedStatus.getVerified3D());
		payment.setStatus(getStatusFromTranStatus(status.getTransactionStatus()));
		result.setPayment(payment);
		bank.setResult(result);
		BankSign bankSign = new BankSign();
		bankSign.setCertId(ConfigFactory.getProperty(Constants.PROCESSING_ALIAS));
		bankSign.setType(Constants.SIGNATURE_ALGORITHM_RSA);
		bankSign.setSign(SignUtil.sign(BindUtil.marshall(bank, Bank.class)));
		document.setBank(bank);
		document.setBankSign(bankSign);
		String responseXML = BindUtil.marshall(document, StartResponse.class);
		LOG.info("original responseXML={}", responseXML);
		if(eGovMerchantId.equals(request.getMerchant().getCertId())) {
			return (new String(Base64.encodeBase64(responseXML.getBytes())));
		}

		return responseXML;
	}

	public static String buildStartErrorResponseXML(String merchantId, String orderId, 
			String rspCode, String error, String type) { 
		StartErrorResponse response = new StartErrorResponse();
		response.setOrderId(orderId);
		response.getError().setCode(rspCode);
		response.getError().setError(error);
		response.getError().setTime(SDF_FULL_2.format(new Date()));
		response.getError().setType(type);
		response.getSession().setId(Long.toString((new Date()).getTime()));

		String responseErrorXML = BindUtil.marshall(response, StartErrorResponse.class);
		LOG.info("responseErrorXML={}", responseErrorXML);
		if(eGovMerchantId.equals(merchantId)) {
			return (new String(Base64.encodeBase64(responseErrorXML.getBytes())));
		}

		return responseErrorXML;
	}

	public static CompleteResponse buildCompleteResponse(String merchantId, 
			CompleteRequest request, boolean success) {
		CompleteResponse response = new CompleteResponse();
		CompleteResponse.Bank bank = new CompleteResponse.Bank();
		bank.setMerchant(request.getMerchant());
		bank.setMerchantSign(request.getMerchantSign());
		bank.getResponse().setCode(success ? "00" : "51");
		bank.getResponse().setMessage(success ? "Approved" : "Declined");

		response.setBank(bank);

		BankSign bankSign = new BankSign();
		bankSign.setCertId(ConfigFactory.getProperty(Constants.PROCESSING_ALIAS));
		bankSign.setType(Constants.SIGNATURE_ALGORITHM_RSA);
		bankSign.setSign(SignUtil.sign(BindUtil.marshall(bank, CompleteResponse.Bank.class)));
		response.setBankSign(bankSign);

		LOG.info(BeanUtil.dump(response));

		return response;
	}

	public static GetStatusResponse buildGetStatusResponse(String merchantId, String rrn, String orderId, 
			StoredTransactionStatusCode rs, StoredTransactionStatusExtended rse, KazBank issuerBank) {
		GetStatusResponse response = new GetStatusResponse();
		String issuerBankContact = "";
		if(issuerBank != null && issuerBank.getContactEn() != null) {
			Pattern pattern1 = Pattern.compile("\\([0-9]*\\)");
			Pattern pattern2 = Pattern.compile("\\d{3}-\\d{2}-\\d{2}");
			Pattern pattern3 = Pattern.compile("\\d{1}-\\d{4}-\\d{4}-\\d{2}");
			Pattern pattern4 = Pattern.compile("\\d{1}-\\d{4}-\\d{3}-\\d{3}");
			Matcher matcher = pattern1.matcher(issuerBank.getContactEn());
			StringBuilder sb = new StringBuilder();
			if (matcher.find()) {
				sb.append(matcher.group());
			}
			sb.append(" ");
			matcher = pattern2.matcher(issuerBank.getContactEn());
			while (matcher.find()) {
				sb.append(matcher.group());
				sb.append(", ");
			}
			matcher = pattern3.matcher(issuerBank.getContactEn());
			if (matcher.find()) {
				sb.append(matcher.group());
				sb.append(", ");
			}
			matcher = pattern4.matcher(issuerBank.getContactEn());
			if (matcher.find()) {
				sb.append(matcher.group());
				sb.append(", ");
			}
			issuerBankContact = sb.substring(0, sb.length() - 2).toString();
		}

		GetStatusResponse.Bank bank = new GetStatusResponse.Bank();
		bank.setName(issuerBank!=null && issuerBank.getBank()!=null ? issuerBank.getBank() : "");
		bank.setCallCenter(issuerBankContact);

		//		if(eGovMerchantId.equals(merchantId)) {
		//			bank.getMerchant().getOrder().setAmount(rs.getAmountRequested() == null 
		//					? "" : Integer.toString(Integer.parseInt(rs.getAmountAuthorised()) - 10000));
		//			bank.getResponse().setAmount(rs.getAmountRequested() == null 
		//					? "" : Integer.toString(Integer.parseInt(rs.getAmountAuthorised()) - 10000));
		//		} else {
		bank.getMerchant().getOrder().setAmount(rs.getAmountRequested() == null 
				? "" : rs.getAmountRequested());
		bank.getResponse().setAmount(rs.getAmountAuthorised() != null ? rs.getAmountAuthorised() : "");
		//		}

		bank.getMerchant().getOrder().setCurrency(rs.getTransactionCurrencyCode() != null 
				? rs.getTransactionCurrencyCode() : "");
		bank.getMerchant().getOrder().setId(orderId);
		bank.getMerchant().setId(merchantId);
		String payment = getPaymentFromTranStatus(rs.getTransactionStatus());
		bank.getResponse().setApprovalCode(rs.getAuthCode() == null ? "" : rs.getAuthCode());

		if(rse != null) {
			//			bank.getResponse().setCardhash(rse.getCardHashNumber());
			bank.getResponse().setCardmask(rse.getMaskedCardNumber());
			bank.getResponse().setSecure(rse.getVerified3D());
		}

		bank.getResponse().setCurrencyCode(rs.getTransactionCurrencyCode() != null 
				? rs.getTransactionCurrencyCode() : "");
		bank.getResponse().setPayment(payment);
		bank.getResponse().setReference(rs.getBankRRN());

		//		if(chocolifeMerchantId.equals(merchantId)) {
		bank.getResponse().setCustomerReference(rrn);
		//		}

		bank.getResponse().setResult(getResultFromTranStatus(rs.getTransactionStatus()));
		bank.getResponse().setStatus(getStatusFromTranStatus(rs.getTransactionStatus()));
		bank.getResponse().setTimestamp(rs.getMerchantLocalDateTime()!=null ? rs.getMerchantLocalDateTime():"");

		BankSign bankSign = new BankSign();
		bankSign.setCertId(ConfigFactory.getProperty(Constants.PROCESSING_ALIAS));
		bankSign.setType(Constants.SIGNATURE_ALGORITHM_RSA);
		bankSign.setSign(SignUtil.sign(BindUtil.marshall(bank, GetStatusResponse.Bank.class)));

		response.setBank(bank);
		response.setBankSign(bankSign);

		LOG.info(BeanUtil.dump(response));

		return response;
	}

	public static TransactionDetails createTransactionDetails(Long transactionId, 
			String merchantId, String rrn, String returnUrl, String email, 
			String language, String appendix, StartRequest document, String fullName, 
			String iinbin, String vin, String template) {
		TransactionDetails td = new TransactionDetails();
		td.setMerchantId(merchantId);
		td.setTerminalID(document.getMerchant().getOrder().getDepartment().getTerminal());
		td.setOrderId(document.getMerchant().getOrder().getOrderId());
		// esli merchant eGov -> totalAmount=amount+10000 (100 tg)
		td.setTotalAmount(eGovMerchantId.equals(merchantId) 
				? Integer.toString(Integer.parseInt(document.getMerchant().getOrder().getAmount()) + 10000) 
						: document.getMerchant().getOrder().getAmount());
		td.setCurrencyCode(Integer.parseInt(document.getMerchant().getOrder().getCurrency()));
		td.setCustomerReference(rrn);
		if (language != null) {
			if (Constants.LANGUAGE_KAZ.equals(language)) {
				td.setLanguageCode(Constants.LANGUAGE_KZ);
			} else if (Constants.LANGUAGE_RUS.equals(language)) {
				td.setLanguageCode(Constants.LANGUAGE_RU);
			} else if (Constants.LANGUAGE_ENG.equals(language)) {
				td.setLanguageCode(Constants.LANGUAGE_EN);
			} else {
				td.setLanguageCode(language);
			}
		} else {
			td.setLanguageCode(Constants.LANGUAGE_RU);
		}

		if(!isEmptyString(appendix)){
			try {
				String appendixDecoded = new String(Base64.decodeBase64(appendix.getBytes()));
				LOG.debug("appendixDecoded={}", appendixDecoded);
				Document appendixDocument = DOMUtil.createDOMFromString(appendixDecoded);
				td.setGoodsList(getGoodsItemFromDOM(Constants.NODE_ITEM, appendixDocument));
			} catch (Exception e) {
				LOG.warn("format of appendix isn't valid!!", e);
			}
		}

		td.setMerchantLocalDateTime(SDF_FULL_1.format(new Date()));
		td.setPurchaserEmail(email);
		String purchaserName = document.getMerchant().getOrder().getPurchaserName();
		if(purchaserName != null) {
			td.setPurchaserName(purchaserName);
		}
		td.setReturnURL(returnUrl);
		String kbk = document.getMerchant().getOrder().getDepartment().getKbk();
		String kno = document.getMerchant().getOrder().getDepartment().getKno();
		String knp = document.getMerchant().getOrder().getDepartment().getKnp();
		List<AdditionalInformation> adds = new ArrayList<AdditionalInformation>();

		AdditionalInformation a = null;

		if(!isEmptyString(template)) {
			if("mobile".equals(template)) {
				a = new AdditionalInformation();
				a.setKey("IS_MOBILE");
				a.setValue("1");
				adds.add(a);
			} else {
				a = new AdditionalInformation();
				a.setKey("TEMPLATE");
				a.setValue(template);
				adds.add(a);
			}
		}
		if(!isEmptyString(kbk)) {
			a = new AdditionalInformation();
			a.setKey("KBK");
			a.setValue(kbk);
			adds.add(a);
		} 
		if(!isEmptyString(kno)) {
			a = new AdditionalInformation();
			a.setKey("KNO");
			a.setValue(kno);
			adds.add(a);
		}
		if(!isEmptyString(knp)) {
			a = new AdditionalInformation();
			a.setKey("KNP");
			a.setValue(knp);
			adds.add(a);
		}
		if(!isEmptyString(fullName)) {
			String fio = null; 
			try {
				fio = Base64.encodeBase64String(fullName.getBytes("UTF8"));
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
			}
			a = new AdditionalInformation();
			a.setKey("FIO");
			a.setValue(fio);
			adds.add(a);
		}
		if(!isEmptyString(iinbin)) {
			a = new AdditionalInformation();
			a.setKey("IINBIN");
			a.setValue(iinbin);
			adds.add(a);
		}
		if(!isEmptyString(vin)) {
			a = new AdditionalInformation();
			a.setKey("VIN");
			a.setValue(vin);
			adds.add(a);
		}

		AdditionalInformation array[] = new AdditionalInformation[adds.size()];
		adds.toArray(array);
		td.setMerchantAdditionalInformationList(array);

		if(eGovMerchantId.equals(merchantId)) {
			td.setPaymentType(4);
		}

		return td;
	}

	public static Transaction createTransaction(String signedOrderB64, String email, 
			String backLink, String failureBackLink, String postLink, 
			String failurePostLink, String merchantId, String orderId) {
		Transaction tr = new Transaction();
		tr.setSignedOrderB64(signedOrderB64);
		tr.setCustomerReference(generateCustomerReference());
		tr.setMerchantId(merchantId);
		tr.setOrderId(orderId);
		tr.setEmail(email);
		tr.setBacklink(backLink);
		tr.setFailureBackLink(failureBackLink);
		tr.setPostLink(postLink);
		tr.setFailurePostLink(failurePostLink);
		tr.setTransactionDate(new Date());

		return tr;
	}

	private static String getPaymentFromTranStatus(String status){
		if(Constants.AUTHORISED.equals(status)
				|| Constants.PAID.equals(status)
				//				|| Constants.PENDING_AUTH_RESULT.equals(status)
				//				|| Constants.PENDING_CUSTOMER_INPUT.equals(status)
				) {
			return Constants.TRUE_STRING;
		} 
		return Constants.FALSE_STRING;
	}

	private static String getStatusFromTranStatus(String status){
		try{
			if(Constants.PAID.equals(status)
					|| Constants.REFUNDED.equals(status)
					|| Constants.REVERSED.equals(status)
					|| Constants.DECLINED.equals(status)) {
				return Constants.STATUS_2;
			} else if(Constants.AUTHORISED.equals(status)) {
				return Constants.STATUS_0;
			} else if(Constants.NO_SUCH_TRANSACTION.equals(status)) {
				return Constants.STATUS_7;
			} else if(Constants.PENDING_CUSTOMER_INPUT.equals(status)
					|| Constants.PENDING_AUTH_RESULT.equals(status)) {
				return Constants.STATUS_8;
			}
			return Constants.STATUS_X;
		} catch (Exception ex){
			LOG.error(ex.getMessage());
			return Constants.STATUS_9;
		}
	}

	private static String getResultFromTranStatus(String status){
		try{
			if(Constants.REFUNDED.equals(status)
					|| Constants.REVERSED.equals(status)) {
				return Constants.STATUS_3;
			} else if(Constants.DECLINED.equals(status)) {
				return Constants.STATUS_4;
			} else if(Constants.AUTHORISED.equals(status)
					||Constants.PAID.equals(status)) {
				return Constants.STATUS_0;
			} else if(Constants.NO_SUCH_TRANSACTION.equals(status)) {
				return Constants.STATUS_7;
			} else if(Constants.PENDING_CUSTOMER_INPUT.equals(status)
					|| Constants.PENDING_AUTH_RESULT.equals(status)) {
				return Constants.STATUS_8;
			}
			return Constants.STATUS_9;
		} catch (Exception ex){
			LOG.error(ex.getMessage());
			return Constants.STATUS_9;
		}
	}

	public static String generateCustomerReference() {
		String customerReference = String.valueOf(new Date().getTime());

		if (customerReference.length() > 12) {
			customerReference = customerReference.substring(0,12);
		}
		while (customerReference.length() < 12) {
			customerReference = "0" + customerReference;
		}	

		return customerReference;
	}

	private static boolean isEmptyString(String str) {
		return (str == null || Constants.EMPTY_STRING.equals(str));
	}

	public static GoodsItem[] getGoodsItemFromDOM(String tagName, Document doc) {
		NodeList nList = doc.getElementsByTagName(tagName);
		List<MerchantWebServiceStub.GoodsItem> goodsItem
		= new ArrayList<MerchantWebServiceStub.GoodsItem>();
		for (int i = 0; i < nList.getLength(); i++) {
			if (nList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) nList.item(i);
				for (int j = 0; j < Integer.parseInt(e.getAttribute(Constants.ATTRIBUTE_QUANTITY)); j++) {
					MerchantWebServiceStub.GoodsItem item = new MerchantWebServiceStub.GoodsItem();
					item.setAmount(e.getAttribute(Constants.ATTRIBUTE_AMOUNT));
					item.setNameOfGoods(e.getAttribute(Constants.ATTRIBUTE_NAME));
					goodsItem.add(item);
				}
			}
		}
		return goodsItem.toArray(new MerchantWebServiceStub.GoodsItem[0]);
	}
}
