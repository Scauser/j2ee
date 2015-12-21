/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.processing.cnp.epay.model.util;

import java.text.SimpleDateFormat;

/**
 *
 * @author daniyar.artykov
 */
public class Constants {

	public static final SimpleDateFormat SDF_FULL = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public static final String CNP_PROCESSING_KZ = "CNP Processing KZ"; 

	public static final String EMPTY_STRING = "";
	public static final String ZERO_STRING = "0";
	public static final String TRUE_STRING = "true";
	public static final String FALSE_STRING = "false";

	public static final String YES = "yes";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failed";
	public static final String FAILURE_REDIRECT = "failedRedirect";

	public static final String AUTH_RS_TEMPLATE_FILE = "templates/auth_rs_template.xml";
	public static final String GET_STATUS_RS_TEMPLATE_FILE = "templates/get_status_rs_template.xml";
	public static final String COMPLETE_RS_TEMPLATE_FILE = "templates/complete_rs_template.xml";

	/**
	 * request constants
	 */
	public static final String REQUEST_CONTENT_TYPE = "Content-Type";
	public static final String REQUEST_CONTENT_TYPE_VALUE = "text/html; charset=windows-1251";
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";
	/**
	 * properties
	 */
	public static final String CNP_MERCHANT_WEB_APP_PROFILE = "cnp.merchant.profile";
	public static final String CNP_MERCHANT_WS_URL = "cnp.merchant.ws.url";
	public static final String CNP_INTERNAL_WS_URL = "cnp.internal.ws.url";
	public static final String CNP_TRANSACTION_WS_URL = "cnp.transaction.ws.url";
	public static final String BACKLINK = "cnp.merchant.ws.backlink";
	public static final String KEYSTORE_PWD = "keystore.pass";

	public static final String PROCESSING_ALIAS = "processing.alias";
	public static final String PROCESSING_PRKEY_PASS = "processing.prkey.pass";
	/**
	 * languages
	 */
	public static final String LANGUAGE_KAZ = "kaz";
	public static final String LANGUAGE_RUS = "rus";
	public static final String LANGUAGE_ENG = "eng";
	public static final String LANGUAGE_KZ = "kz";
	public static final String LANGUAGE_RU = "ru";
	public static final String LANGUAGE_EN = "en";
	/**
	 * xml nodes and attributes constants
	 */
	public static final String NODE_DOCUMENT = "document";
	public static final String NODE_COMMAND = "command";
	public static final String NODE_PAYMENT = "payment";
	public static final String NODE_MERCHANT = "merchant";
	public static final String NODE_DEPARTMENT = "department";
	public static final String NODE_MERCHANT_SIGN = "merchant_sign";
	public static final String NODE_ORDER = "order";
	public static final String NODE_ITEM = "item";
	public static final String NODE_BANK = "bank";
	public static final String NODE_BANK_SIGN = "bank_sign";

	public static final String ATTRIBUTE_TYPE = "type";
	public static final String ATTRIBUTE_ID = "id";
	public static final String ATTRIBUTE_REFERENCE = "reference";
	public static final String ATTRIBUTE_TERMINAL = "terminal";
	public static final String ATTRIBUTE_APPR_CODE = "approval_code";
	public static final String ATTRIBUTE_AMOUNT = "amount";
	public static final String ATTRIBUTE_NAME = "name";
	public static final String ATTRIBUTE_QUANTITY = "quantity";
	public static final String ATTRIBUTE_CURRENCY = "currency";
	public static final String ATTRIBUTE_CURRENCY_CODE = "currency_code";
	public static final String ATTRIBUTE_MERCHANT_NAME = "name";
	public static final String ATTRIBUTE_MERCHANT_ID = "merchant_id";
	public static final String ATTRIBUTE_PURCHASER_NAME = "purchaser_name";
	public static final String ATTRIBUTE_ORDER_ID = "order_id";
	public static final String ATTRIBUTE_CERT_ID = "cert_id";
	public static final String ATTRIBUTE_PARENT_ORDER_ID = "parent_order_id";

	public static final String OMIT_XML_DECLARATION = "omit-xml-declaration";
	public static final String METHOD = "method";
	public static final String METHOD_HTML = "html";

	public static final String SIGNATURE_ALGORITHM_SHA1_RSA = "SHA1withRSA";
	public static final String SIGNATURE_ALGORITHM_RSA = "RSA";
	public static final String KEYSTORE_TYPE = "JKS";

	/**
	 * Transaction statuses:
	 * авторизована
	 */
	public static final String AUTHORISED = "AUTHORISED";

	public static final String REVERSE = "reverse";
	public static final String COMPLETE = "complete";

	/**
	 * оплачена
	 */
	public static final String PAID = "PAID";
	/**
	 * данная транзакция не существует 
	 */
	public static final String NO_SUCH_TRANSACTION = "NO_SUCH_TRANSACTION";
	/**
	 * ожидание ввода данных покупателем
	 */
	public static final String PENDING_CUSTOMER_INPUT = "PENDING_CUSTOMER_INPUT";
	/**
	 * ожидание результата авторизации
	 */
	public static final String PENDING_AUTH_RESULT = "PENDING_AUTH_RESULT";
	/**
	 * отклонена
	 */
	public static final String DECLINED = "DECLINED";
	/**
	 * отменена
	 */
	public static final String REVERSED = "REVERSED";
	/**
	 * возвращена
	 */
	public static final String REFUNDED = "REFUNDED";
	/**
	 * неверный ID ТСП
	 */
	public static final String INVALID_MID = "INVALID_MID";
	/**
	 * ID ТСП заблокирован
	 */
	public static final String MID_DISABLED = "MID_DISABLED";

	/**
	 * status
	 */
	public static final String STATUS_0 = "0";
	public static final String STATUS_2 = "2";
	public static final String STATUS_3 = "3";
	public static final String STATUS_4 = "4";
	public static final String STATUS_7 = "7";
	public static final String STATUS_8 = "8";
	public static final String STATUS_9 = "9";
	public static final String STATUS_X = "Х";
}
