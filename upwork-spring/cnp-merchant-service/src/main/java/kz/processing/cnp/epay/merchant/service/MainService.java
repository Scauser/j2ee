package kz.processing.cnp.epay.merchant.service;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import kz.processing.cnp.epay.merchant.service.config.ConfigFactory;
import kz.processing.cnp.epay.merchant.service.util.CommonBuilder;
import kz.processing.cnp.epay.merchant.service.util.HibernateUtils;
import kz.processing.cnp.epay.merchant.service.util.HttpUtil;
import kz.processing.cnp.epay.merchant.service.util.SignUtil;
import kz.processing.cnp.epay.model.bind.CustomerSign;
import kz.processing.cnp.epay.model.bind.document.CompleteRequest;
import kz.processing.cnp.epay.model.bind.document.GetStatusRequest;
import kz.processing.cnp.epay.model.bind.document.StartRequest;
import kz.processing.cnp.epay.model.entity.Transaction;
import kz.processing.cnp.epay.model.util.BeanUtil;
import kz.processing.cnp.epay.model.util.BindUtil;
import kz.processing.cnp.epay.model.util.Constants;
import kz.processing.cnp.epay.model.util.KeyValuePair;
import kz.processing.cnp.internal_ws_client.InternalWebServiceStub.KazBank;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StartTransactionResult;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StoredTransactionStatusCode;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.StoredTransactionStatusExtended;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class MainService {

	private final static Logger LOG = LoggerFactory.getLogger(MainService.class);

	@GET
	@Path("/start")
	public Response start() {
		return Response.status(Status.OK).build();
	}

	@POST
	@Path("/start")
	@Produces(MediaType.APPLICATION_XML)
	public Object start(@FormParam("signed_Order_B64") String signedOrderB64, 
			@FormParam("shopID") String shopID, 
			@FormParam("email") String email, 
			@FormParam("backLink") String backLink, 
			@FormParam("failureBackLink") String failureBackLink, 
			@FormParam("postLink") String postLink, 
			@FormParam("failurePostLink") String failurePostLink, 
			@FormParam("language") String language, 
			@FormParam("appendix") String appendix, 
			@FormParam("showCurr") String showCurr, 
			@FormParam("showAmount") String showAmount,
			@FormParam("fio") String fullName,
			@FormParam("rnn") String iinbin,
			@FormParam("vin") String vin,
			@FormParam("template") String template) {
		LOG.debug("start => signedOrderB64={}", signedOrderB64); 
		LOG.debug("backLink={}", backLink);
		LOG.debug("failureBackLink={}", failureBackLink);
		LOG.debug("postLink={}", postLink);
		LOG.debug("failurePostLink={}", failurePostLink);
		LOG.debug("fio={}, rnn={}, vin={}", fullName, iinbin, vin);
		LOG.debug("language={}, email={}", language, email);
		LOG.debug("showCurr={}, showAmount={}, shopID={}", showCurr, showAmount, shopID);
		LOG.debug("template={}, appendix={}", template, appendix);
		Transaction tr = null;
		String xml = null;
		if(signedOrderB64 == null || Constants.EMPTY_STRING.equals(signedOrderB64)
				|| backLink == null || Constants.EMPTY_STRING.equals(backLink)
				|| postLink == null || Constants.EMPTY_STRING.equals(postLink)) {
			LOG.debug("Required fields are missing or merchant sign is not verified!!");
			String orderId = "";
			String merchantId = "";
			if(signedOrderB64 != null) {
				StartRequest document = (StartRequest) BindUtil.unmarshall(
						new String(Base64.decodeBase64(signedOrderB64.getBytes())), StartRequest.class);
				orderId = document.getMerchant().getOrder().getOrderId();
				merchantId = document.getMerchant().getCertId();
			}
			String responseErrorXML = CommonBuilder.buildStartErrorResponseXML(merchantId, orderId, 
					"99", "Required fields are missing or merchant sign is not verified!!", "system");
			if(!isEmptyString(failurePostLink) || !isEmptyString(postLink)) {
				String doPostURL = isEmptyString(failurePostLink) ? postLink : failurePostLink;
				LOG.debug("postLink content={}", HttpUtil.doPost(doPostURL, "response", responseErrorXML));
			}
			if(!isEmptyString(failureBackLink) || !isEmptyString(backLink)) {
				URI uri = UriBuilder.fromUri(isEmptyString(failureBackLink) ? backLink : failureBackLink).build();
				return Response.seeOther(uri).build();
			}
			return (Object) "<error>Required fields are missing or merchant sign is not verified!!</error>";
		} else if(signedOrderB64 != null){
			xml = new String(Base64.decodeBase64(signedOrderB64.getBytes()));
		}
		StartRequest document = (StartRequest) BindUtil.unmarshall(xml, StartRequest.class);
		if(document == null){
			LOG.debug("Required fields are missing or merchant sign is not verified!!");
			String responseErrorXML = CommonBuilder.buildStartErrorResponseXML("", "", 
					"99", "Required fields are missing or merchant sign is not verified!!", "system");
			if(!isEmptyString(failurePostLink) || !isEmptyString(postLink)) {
				String doPostURL = isEmptyString(failurePostLink) ? postLink : failurePostLink;
				LOG.debug("postLink content={}", HttpUtil.doPost(doPostURL, "response", responseErrorXML));
			}
			if(!isEmptyString(failureBackLink) || !isEmptyString(backLink)) {
				URI uri = UriBuilder.fromUri(isEmptyString(failureBackLink) ? backLink : failureBackLink).build();
				return Response.seeOther(uri).build();
			}
			return (Object) "<error>Required fields are missing or merchant sign is not verified!!</error>";
		}
		try {
			LOG.debug("document={}", xml);
			if(document.getMerchant().getOrder().getOrderId().length() > 20 
					|| document.getMerchant().getOrder().getOrderId().length() < 6) {
				LOG.debug("OrderId must be unique and must consist of not less than 6 and not more than 20 digits!!");
				String responseErrorXML = CommonBuilder.buildStartErrorResponseXML(
						document.getMerchant().getCertId(), 
						document.getMerchant().getOrder().getOrderId(), "99", 
						"OrderId must be unique and must consist of not less than 6 and not more than 20 digits!!", "system");
				if(!isEmptyString(failurePostLink) || !isEmptyString(postLink)) {
					String doPostURL = isEmptyString(failurePostLink) ? postLink : failurePostLink;
					LOG.debug("postLink content={}", HttpUtil.doPost(doPostURL, "response", responseErrorXML));
				}
				if(!isEmptyString(failureBackLink) || !isEmptyString(backLink)) {
					URI uri = UriBuilder.fromUri(isEmptyString(failureBackLink) ? backLink : failureBackLink).build();
					return Response.seeOther(uri).build();
				}
				return (Object) "<error>OrderId must be unique and must consist of not less than 6 and not more than 20 digits!!</error>";
			} else if(getTransCount(
					document.getMerchant().getOrder().getDepartment().getMerchantId(), 
					document.getMerchant().getOrder().getOrderId()) > 0) {
				LOG.debug("Transaction with same orderId has already been found in database!!");
				String responseErrorXML = CommonBuilder.buildStartErrorResponseXML(
						document.getMerchant().getCertId(), 
						document.getMerchant().getOrder().getOrderId(), "99", 
						"Transaction with same orderId has already been found in database!!", "system");
				if(!isEmptyString(failurePostLink) || !isEmptyString(postLink)) {
					String doPostURL = isEmptyString(failurePostLink) ? postLink : failurePostLink;
					LOG.debug("postLink content={}", HttpUtil.doPost(doPostURL, "response", responseErrorXML));
				}
				if(!isEmptyString(failureBackLink) || !isEmptyString(backLink)) {
					URI uri = UriBuilder.fromUri(isEmptyString(failureBackLink) ? backLink : failureBackLink).build();

					return Response.seeOther(uri).build();
				}
				return (Object) "<error>Transaction with same orderId has already been found in database!!</error>";
			} else if(document.getMerchantSign().getSign() == null 
					|| "null".equals(document.getMerchantSign().getSign())){
				LOG.debug("Merchant sign not found!!");
				String responseErrorXML = CommonBuilder.buildStartErrorResponseXML(
						document.getMerchant().getCertId(), 
						document.getMerchant().getOrder().getOrderId(), "99", 
						"Merchant sign not found!!", "system");
				if(!isEmptyString(failurePostLink) || !isEmptyString(postLink)) {
					String doPostURL = isEmptyString(failurePostLink) ? postLink : failurePostLink;
					LOG.debug("postLink content={}", HttpUtil.doPost(doPostURL, "response", responseErrorXML));
				}
				if(!isEmptyString(failureBackLink) || !isEmptyString(backLink)) {
					URI uri = UriBuilder.fromUri(isEmptyString(failureBackLink) ? backLink : failureBackLink).build();

					return Response.seeOther(uri).build();
				}
				return (Object) "<error>Transaction with same orderId has already been found in database!!</error>";
			}
			if(SignUtil.verify(xml, document.getMerchant().getCertId(), 
					document.getMerchantSign().getSign())) {
				String merchantId = document.getMerchant().getOrder().getDepartment().getMerchantId();
				String orderId = document.getMerchant().getOrder().getOrderId();
				tr = CommonBuilder.createTransaction(signedOrderB64, email, backLink, 
						failureBackLink, postLink, failurePostLink, merchantId, orderId);
				HibernateUtils.save(tr);
				StringBuilder sb = new StringBuilder(ConfigFactory.getProperty(Constants.BACKLINK));
				sb.append(tr.getId());
				sb.append("&customerReference=");
				sb.append(tr.getCustomerReference());
				LOG.debug("backLink={}", sb.toString());
				StartTransactionResult result = WebServiceClientHandler.startTransaction(
						tr.getId(), merchantId, tr.getCustomerReference(), language, appendix, 
						email, sb.toString(), document, fullName, iinbin, vin, template);
				tr.setCertId(document.getMerchant().getCertId());
				tr.setSuccess(result.getSuccess());
				tr.setErrorDescription(result.getErrorDescription());
				HibernateUtils.merge(tr);
				if (result.getSuccess()) {
					URI uri = UriBuilder.fromUri(result.getRedirectURL()).build();
					return Response.seeOther(uri).build();
				} else {
					String responseErrorXML = CommonBuilder.buildStartErrorResponseXML(
							document.getMerchant().getCertId(), 
							document.getMerchant().getOrder().getOrderId(), "99", 
							result.getErrorDescription(), "system");
					if(!isEmptyString(failurePostLink) || !isEmptyString(postLink)) {
						String doPostURL = isEmptyString(failurePostLink) ? postLink : failurePostLink;
						LOG.debug("postLink content={}", HttpUtil.doPost(doPostURL, "response", responseErrorXML));
					}
					if(!isEmptyString(failureBackLink) || !isEmptyString(backLink)) {
						URI uri = UriBuilder.fromUri(isEmptyString(failureBackLink) ? backLink : failureBackLink).build();

						return Response.seeOther(uri).build();
					}

					return (Object) "<error>" + result.getErrorDescription() + "</error>";
				}
			} else {
				String responseErrorXML = CommonBuilder.buildStartErrorResponseXML(
						document.getMerchant().getCertId(), 
						document.getMerchant().getOrder().getOrderId(), "99", 
						"Required fields are missing or merchant sign is not verified!!", "system");
				if(!isEmptyString(failurePostLink) || !isEmptyString(postLink)) {
					String doPostURL = isEmptyString(failurePostLink) ? postLink : failurePostLink;
					LOG.debug("postLink content= {}", HttpUtil.doPost(doPostURL, "response", responseErrorXML));
				}
				if(!isEmptyString(failureBackLink) || !isEmptyString(backLink)) {
					URI uri = UriBuilder.fromUri(isEmptyString(failureBackLink) ? backLink : failureBackLink).build();

					return Response.seeOther(uri).build();
				}

				return (Object) "<error>Required fields are missing or merchant sign is not verified!!</error>";
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			String responseErrorXML = CommonBuilder.buildStartErrorResponseXML(
					document.getMerchant().getCertId(), 
					document.getMerchant().getOrder().getOrderId(), "99", 
					"System malfunction", "system");
			if(!isEmptyString(failurePostLink) || !isEmptyString(postLink)) {
				String doPostURL = isEmptyString(failurePostLink) ? postLink : failurePostLink;
				LOG.debug("postLink content={}", HttpUtil.doPost(doPostURL, "response", responseErrorXML));
			}
			if(!isEmptyString(failureBackLink) || !isEmptyString(backLink)) {
				URI uri = UriBuilder.fromUri(isEmptyString(failureBackLink) ? backLink : failureBackLink).build();
				return Response.seeOther(uri).build();
			}
			return (Object) "<error>System malfunction</error>";
		}
	}

	@GET
	@Path("/backlink")
	public Response backlink(@QueryParam("transactionId") String transactionId, 
			@QueryParam("customerReference") String customerReference) {
		try {
			LOG.debug("transactionId={}, customerReference={}", transactionId, customerReference);
			if(transactionId == null || Constants.EMPTY_STRING.equals(transactionId) 
					|| customerReference == null 
					|| Constants.EMPTY_STRING.equals(customerReference)) {
				try {
					Long.parseLong(transactionId);
				} catch (Exception ex) {
					return Response.status(404).entity("<error>transactionId must be a number!!</error>").build();
				}
				return Response.status(404).entity("<error>Required fields are missing!!</error>").build();
			}
			Transaction tr = HibernateUtils.getObject(Transaction.class, Long.parseLong(transactionId));
			if(tr == null) {
				return Response.status(404).entity("<error>coudn't find transaction data!!</error>").build();
			}
			StoredTransactionStatusCode status = WebServiceClientHandler
					.getTransactionStatus(tr.getMerchantId(), customerReference);
			StoredTransactionStatusExtended extendedStatus = WebServiceClientHandler
					.getExtendedTransactionStatus(tr.getMerchantId(), customerReference);
			KazBank issuerBank = (extendedStatus == null || extendedStatus.getMaskedCardNumber() == null) 
					? null : WebServiceClientHandler.getIssuerBank(extendedStatus.getMaskedCardNumber());
			LOG.debug("backLink={}, failureBacklink={}", tr.getBacklink(), tr.getFailureBackLink());
			if (status != null) {
				String transactionStatus = status.getTransactionStatus();
				tr.setCustomerReference(customerReference);
				tr.setStatus(transactionStatus);
				tr.setStatusDate(new Date());
				String responseXml = "";
				if(Constants.AUTHORISED.equals(transactionStatus) 
						|| Constants.PAID.equals(transactionStatus)) {
					responseXml = CommonBuilder.buildStartResponseXML(
							issuerBank, status, extendedStatus, tr);
				} else if(Constants.REVERSED.equals(transactionStatus)) {
					responseXml = CommonBuilder.buildStartErrorResponseXML(
							tr.getMerchantId(), 
							tr.getOrderId(), "17", "Customer cancellation", "auth");
				} else {
					responseXml = CommonBuilder.buildStartErrorResponseXML(
							tr.getMerchantId(), 
							tr.getOrderId(), status.getRspCode(), status.getRspCodeDesc(), "auth");
				}
				String doPostURL = "";
				LOG.debug("status={}", transactionStatus);
				if(Constants.AUTHORISED.equals(transactionStatus) 
						|| Constants.PAID.equals(transactionStatus)
						|| isEmptyString(tr.getFailurePostLink())) {
					doPostURL = tr.getPostLink();
				} else {
					doPostURL = tr.getFailurePostLink();
				}

				String postLinkContent = HttpUtil.doPost(doPostURL, "response", responseXml);
				tr.setPostLinkContent(postLinkContent);
				HibernateUtils.merge(tr);
				if (Constants.AUTHORISED.equals(transactionStatus) 
						|| Constants.PAID.equals(transactionStatus)) {
					LOG.debug("redirecting to backlink {}", tr.getBacklink());
					URI uri = UriBuilder.fromUri(tr.getBacklink()).build();
					return Response.seeOther(uri).build();
				} else if (!isEmptyString(tr.getFailureBackLink())) {
					LOG.info("redirecting to failureBacklink {}", tr.getFailureBackLink());
					URI uri = UriBuilder.fromUri(tr.getFailureBackLink()).build();
					return Response.seeOther(uri).build();
				} else {
					LOG.info("failureBacklink didn't defined redirecting to backlink {}", tr.getBacklink());
					URI uri = UriBuilder.fromUri(tr.getBacklink()).build();
					return Response.seeOther(uri).build();
				}
			} else if (tr.getFailureBackLink() != null
					&& !Constants.EMPTY_STRING.equals(tr.getFailureBackLink())) {
				LOG.info("redirecting to failureBacklink {}", tr.getFailureBackLink());
				URI uri = UriBuilder.fromUri(tr.getFailureBackLink()).build();
				return Response.seeOther(uri).build();
			} else {
				LOG.info("failureBacklink didn't defined redirecting to backlink {}", tr.getBacklink());
				URI uri = UriBuilder.fromUri(tr.getBacklink()).build();
				return Response.seeOther(uri).build();
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);

			return Response.status(404).entity("<error>System malfunction</error>").build();
		}
	}

	@GET
	@Path("/complete")
	@Produces(MediaType.APPLICATION_XML)
	public Object complete(@QueryParam("request") String request) {
		try {
			if(request != null && !Constants.EMPTY_STRING.equals(request)){
				CompleteRequest document = (CompleteRequest) BindUtil.unmarshall(request, CompleteRequest.class);
				if (SignUtil.verify(request, document.getMerchant().getId(), 
						document.getMerchantSign().getSign())) {
					String merchantId = document.getMerchant().getId();
					String commandType = document.getMerchant().getCommand().getType();
					
//					String customerRef = CommonBuilder.getRRNFromOrderId(document.getMerchant().getPayment().getOrderId());
					String customerRef = getCustomerReference(merchantId, 
							document.getMerchant().getPayment().getOrderId());
					
					if(isEmptyString(customerRef)) {
						LOG.warn("Couldn't find transaction by orderId!!");
						return (Object) "<error>Couldn't find transaction by orderId!!</error>";
					}
					
					String amount = document.getMerchant().getPayment().getAmount();
					LOG.info("merchantId={}, customerReference={}", merchantId, customerRef);	
					boolean success = false;
					if(Constants.COMPLETE.equals(commandType)) {
						success = true;
					} else if(Constants.REVERSE.equals(commandType)) {
						success = false;
					} else {
						LOG.warn("Command type is required!!");
						return (Object) "<error>Command type is required!!</error>";
					}
					Boolean completed = WebServiceClientHandler.complete(merchantId, customerRef, amount, success);
					LOG.debug("complete response={}", completed);
					return CommonBuilder.buildCompleteResponse(merchantId, document, completed);
				} else {
					return (Object) "<error>Required fields are missing or merchant sign is not verified!!</error>";
				}
			} else {
				return (Object) "<error>Field request is missing!!</error>";
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return (Object) "<error>SYSTEM_ERROR</error>";
		}
	}

	@GET
	@Path("/getStatus")
	@Produces(MediaType.APPLICATION_XML)
	public Object getStatus(@QueryParam("request") String request) {
		try {
			if(request != null && !Constants.EMPTY_STRING.equals(request)){
				GetStatusRequest document = (GetStatusRequest) BindUtil.unmarshall(request, GetStatusRequest.class);
				if (SignUtil.verify(request, document.getMerchant().getId(), document.getMerchantSign().getSign())) {
					String merchantId = document.getMerchant().getId();
					String orderId = document.getMerchant().getOrder().getId();
				
					String customerRef = getCustomerReference(merchantId, orderId);
					
					LOG.info("merchantId={}, customerReference={}", merchantId, customerRef);	
					if(isEmptyString(customerRef)) {
						LOG.warn("Couldn't find transaction by orderId!!");
						return (Object) "<error>NO_SUCH_TRANSACTION</error>";
					}
									
					StoredTransactionStatusCode status = WebServiceClientHandler
							.getTransactionStatus(merchantId, customerRef);
					if(status == null) {
						return (Object) "<error>SYSTEM_ERROR</error>";
					}
					StoredTransactionStatusExtended extendedStatus = WebServiceClientHandler
							.getExtendedTransactionStatus(merchantId, customerRef);
					KazBank issuerBank = WebServiceClientHandler.getIssuerBank(
							extendedStatus!=null?extendedStatus.getMaskedCardNumber() : null);
					LOG.debug("transactionStatus={}", status.getTransactionStatus());
					return CommonBuilder.buildGetStatusResponse(merchantId, customerRef, 
							orderId, status, extendedStatus, issuerBank);
				} else {
					return (Object) "<error>Required fields are missing or merchant sign is not verified!!</error>";
				}
			}else {
				return (Object) "<error>Field request is missing!!</error>";
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return (Object) "<error>System malfunction</error>";
		}
	}
	
//	@GET
//	@Path("/refund")
//	@Produces(MediaType.APPLICATION_XML)
//	public Object refund(@QueryParam("request") String request) {
//		try {
//			if(request != null && !Constants.EMPTY_STRING.equals(request)){
//				RefundRequest document = (RefundRequest) BindUtil.unmarshall(request, RefundRequest.class);
//				if (SignUtil.verify(request, document.getMerchant().getId(), 
//						document.getMerchantSign().getSign())) {
//					String merchantId = document.getMerchant().getId();
//					String commandType = document.getMerchant().getCommand().getType();
//					
////					String customerRef = CommonBuilder.getRRNFromOrderId(document.getMerchant().getPayment().getOrderId());
//					String customerRef = getCustomerReference(merchantId, 
//							document.getMerchant().getPayment().getOrderId());
//					
//					if(isEmptyString(customerRef)) {
//						LOG.warn("Couldn't find transaction by orderId!!");
//						return (Object) "<error>Couldn't find transaction by orderId!!</error>";
//					}
//					
//					String amount = document.getMerchant().getPayment().getAmount();
//					LOG.info("merchantId={}, customerReference={}", merchantId, customerRef);	
//					boolean success = false;
//					if(Constants.COMPLETE.equals(commandType)) {
//						success = true;
//					} else if(Constants.REVERSE.equals(commandType)) {
//						success = false;
//					} else {
//						LOG.warn("Command type is required!!");
//						return (Object) "<error>Command type is required!!</error>";
//					}
//					Boolean completed = WebServiceClientHandler.complete(merchantId, customerRef, amount, success);
//					LOG.debug("complete response={}", completed);
////					return CommonBuilder.buildCompleteResponse(merchantId, document, completed);
//				} else {
//					return (Object) "<error>Required fields are missing or merchant sign is not verified!!</error>";
//				}
//			} else {
//				return (Object) "<error>Field request is missing!!</error>";
//			}
//		} catch (Exception ex) {
//			LOG.error(ex.getMessage(), ex);
//			return (Object) "<error>SYSTEM_ERROR</error>";
//		}
//	}

	private Integer getTransCount(String orderId, String merchantId) {
		List<Transaction> ts = HibernateUtils.getObjects(Transaction.class, 
				"from Transaction where orderId=:orderId and merchantId=:merchantId", 
				new KeyValuePair("orderId", orderId), 
				new KeyValuePair("merchantId", merchantId));
		if(ts == null || ts.isEmpty()) {
			return 0;
		} else {
			return ts.size();
		}
	}
	
	private String getCustomerReference(String merchantId, String orderId) {
		List<Transaction> ts = HibernateUtils.getObjects(Transaction.class, 
				"from Transaction where orderId=:orderId and merchantId=:merchantId", 
				new KeyValuePair("orderId", orderId), 
				new KeyValuePair("merchantId", merchantId));
		if(ts == null || ts.isEmpty()) {
			return null;
		} else {
			return ts.get(0).getCustomerReference();
		}
	}

	private boolean isEmptyString(String s) {
		return (s == null || "".equals(s));
	}

	private CustomerSign getCustomerSign(HttpServletRequest request) {
		CustomerSign customerSign = new CustomerSign();
		X509Certificate cert = null;
		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
		if(certs != null && certs.length > 0) {
			cert = certs[0];
		}

		LOG.debug(BeanUtil.dump(cert));

		if(cert != null) {
			customerSign.setType(cert.getType());
			if("SSL".equals(cert.getType())) {
				customerSign.setValue(new String(cert.getSerialNumber().toByteArray()));
			}
		}

		return customerSign;
	}

	@GET
	@Path("/test")
	public Response test(@Context HttpServletRequest request) {
		LOG.debug(BeanUtil.dump(getCustomerSign(request)));

		return Response.status(200).entity("OK!").build();
	}
}
