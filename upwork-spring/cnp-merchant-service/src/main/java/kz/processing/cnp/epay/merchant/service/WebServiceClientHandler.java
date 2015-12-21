package kz.processing.cnp.epay.merchant.service;

import java.rmi.RemoteException;

import kz.processing.cnp.internal_ws_client.InternalWebServiceStub;
import kz.processing.cnp.internal_ws_client.InternalWebServiceStub.GetIssuerData;
import kz.processing.cnp.internal_ws_client.InternalWebServiceStub.GetIssuerDataResponse;
import kz.processing.cnp.internal_ws_client.InternalWebServiceStub.KazBank;
import kz.processing.cnp.epay.merchant.service.config.ConfigFactory;
import kz.processing.cnp.epay.merchant.service.util.CommonBuilder;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.CompleteTransaction;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.CompleteTransactionResponse;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.GetExtendedTransactionStatus;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.GetExtendedTransactionStatusResponse;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.GetTransactionStatusCode;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.GetTransactionStatusCodeResponse;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.RecurrentTransaction;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.RecurrentTransactionResponse;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StartTransaction;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StartTransactionResponse;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StartTransactionResult;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StoredTransactionStatus;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StoredTransactionStatusCode;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StoredTransactionStatusExtended;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.TransactionDetails;
import kz.processing.cnp.epay.model.bind.document.StartRequest;
import kz.processing.cnp.epay.model.util.Constants;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServiceClientHandler {

	private static final Logger LOG = LoggerFactory.getLogger(WebServiceClientHandler.class);
	private static MerchantWebServiceStub merchantWSStub;
	private static InternalWebServiceStub internalWSStub;
	private static String eGovMerchantId;

	static { 
		try {
			merchantWSStub = new MerchantWebServiceStub(ConfigFactory.getProperty(Constants.CNP_MERCHANT_WS_URL));
			merchantWSStub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED, false);
			internalWSStub = new InternalWebServiceStub(ConfigFactory.getProperty(Constants.CNP_INTERNAL_WS_URL));
			internalWSStub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED, false);
			eGovMerchantId = ConfigFactory.getProperty("egov.merchant.id");
		} catch(AxisFault e) {
			LOG.error("couldn't create ws stub: {}", e.getMessage(), e);
		}
	}

	public static KazBank getIssuerBank(String maskedCardNumber) {
		if(maskedCardNumber == null) {
			return null;
		}
		try {
			GetIssuerData iss = new GetIssuerData();
			iss.setBin(maskedCardNumber.substring(0, 6));
			GetIssuerDataResponse resp = internalWSStub.getIssuerData(iss);
			return resp.get_return();
		} catch(Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return null;
		}
	}

	public static Boolean complete(String merchantId, String customerRef, 
			String amount, boolean success){
		CompleteTransaction ct = new CompleteTransaction();
		ct.setMerchantId(merchantId);
		ct.setReferenceNr(customerRef);
		ct.setTransactionSuccess(success);
		if(amount != null && !Constants.EMPTY_STRING.equals(amount)) {
			if(eGovMerchantId.equals(merchantId)) {
				ct.setOverrideAmount(Integer.toString(Integer.parseInt(amount) + 10000));
			} else {
				ct.setOverrideAmount(amount);
			}
		}
		CompleteTransactionResponse rs = null;
		try {
			rs = merchantWSStub.completeTransaction(ct);
			return rs.get_return();
		} catch (RemoteException e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	public static StoredTransactionStatusCode getTransactionStatus(String merchantId, 
			String customerReference) {
		try {
			GetTransactionStatusCode rq = new GetTransactionStatusCode();
			rq.setMerchantId(merchantId);
			rq.setReferenceNr(customerReference);
			GetTransactionStatusCodeResponse rs = merchantWSStub.getTransactionStatusCode(rq);
			return rs.get_return();
		} catch(Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return null;
		}
	}

	public static StoredTransactionStatusExtended getExtendedTransactionStatus(
			String merchantId, String customerReference) {
		try {
			GetExtendedTransactionStatus rq = new GetExtendedTransactionStatus();
			rq.setMerchantId(merchantId);
			rq.setReferenceNr(customerReference);
			GetExtendedTransactionStatusResponse rs = merchantWSStub.getExtendedTransactionStatus(rq);
			return rs.get_return();
		} catch(Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return null;
		}
	}

	public static StoredTransactionStatus recurrentTransaction(String orifinalCustomerRef, 
			TransactionDetails td){
		try {
			RecurrentTransaction rq = new RecurrentTransaction();
			rq.setOriginalCustomerReference(orifinalCustomerRef);
			rq.setTransaction(td);
			RecurrentTransactionResponse rs = merchantWSStub.recurrentTransaction(rq);
			return rs.get_return();
		} catch(Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return null;
		}
	}

	public static StartTransactionResult startTransaction(Long transactionId,
			String merchantId, String rrn, String language, String appendix, 
			String email, String returnUrl, StartRequest document, String fullName,
			String iinbin, String vin, String template) {
		try {
			TransactionDetails td = CommonBuilder.createTransactionDetails(transactionId, 
					merchantId, rrn, returnUrl, email, language, appendix, document,
					fullName, iinbin, vin, template);

			StartTransaction st = new StartTransaction();
			st.setTransaction(td);
			StartTransactionResponse rs = merchantWSStub.startTransaction(st);
			
			return rs.get_return();
		} catch(Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return null;
		}
	}
}
