package kz.processing.cnp.epay.model.object;

public class GoodsItem {
	
	private String amount;
	private Integer currencyCode;
	private String merchantsGoodsID;
	private String nameOfGoods;
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public Integer getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getMerchantsGoodsID() {
		return merchantsGoodsID;
	}
	
	public void setMerchantsGoodsID(String merchantsGoodsID) {
		this.merchantsGoodsID = merchantsGoodsID;
	}
	
	public String getNameOfGoods() {
		return nameOfGoods;
	}
	
	public void setNameOfGoods(String nameOfGoods) {
		this.nameOfGoods = nameOfGoods;
	}
}
