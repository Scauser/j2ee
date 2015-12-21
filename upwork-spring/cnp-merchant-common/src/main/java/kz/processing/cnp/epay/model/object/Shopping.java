package kz.processing.cnp.epay.model.object;

import java.util.ArrayList;
import java.util.List;

public class Shopping {
	private String merchantId;
	private String terminalId;
	private String customerReference;
	private String description;
	private String currencyCode;
	private String totalAmount;
	private String orderId;
	private String returnUrl;
	private String languageCode;
	private String purchaserName;
	private String purchaserEmail;
	private String merchantLocalDate;
	
	private List<GoodsItem> goodsItems = new ArrayList<GoodsItem>();
	
	public String getMerchantId() {
		return merchantId;
	}
	
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getTerminalId() {
		return terminalId;
	}
	
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	public String getCustomerReference() {
		return customerReference;
	}
	
	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getReturnUrl() {
		return returnUrl;
	}
	
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public String getLanguageCode() {
		return languageCode;
	}
	
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	public String getPurchaserName() {
		return purchaserName;
	}
	
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	public String getPurchaserEmail() {
		return purchaserEmail;
	}
	
	public void setPurchaserEmail(String purchaserEmail) {
		this.purchaserEmail = purchaserEmail;
	}

	public List<GoodsItem> getGoodsItems() {
		return goodsItems;
	}

	public void setGoodsItems(List<GoodsItem> goodsItems) {
		this.goodsItems = goodsItems;
	}

	public String getMerchantLocalDate() {
		return merchantLocalDate;
	}

	public void setMerchantLocalDate(String merchantLocalDate) {
		this.merchantLocalDate = merchantLocalDate;
	}
}
