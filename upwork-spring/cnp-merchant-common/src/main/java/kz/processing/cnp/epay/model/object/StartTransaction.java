package kz.processing.cnp.epay.model.object;

public class StartTransaction {
	
	private String signedOrderB64;
    private String shopID;
    private String email;
    private String backLink;
    private String failureBackLink;
    private String postLink;
    private String failurePostLink;
    private String language;
    private String appendix;
    private String showCurr;
    private String showAmount;
    
	public String getSignedOrderB64() {
		return signedOrderB64;
	}
	
	public void setSignedOrderB64(String signedOrderB64) {
		this.signedOrderB64 = signedOrderB64;
	}
	
	public String getShopID() {
		return shopID;
	}
	
	public void setShopID(String shopID) {
		this.shopID = shopID;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBackLink() {
		return backLink;
	}
	
	public void setBackLink(String backLink) {
		this.backLink = backLink;
	}
	
	public String getFailureBackLink() {
		return failureBackLink;
	}
	
	public void setFailureBackLink(String failureBackLink) {
		this.failureBackLink = failureBackLink;
	}
	
	public String getPostLink() {
		return postLink;
	}
	
	public void setPostLink(String postLink) {
		this.postLink = postLink;
	}
	
	public String getFailurePostLink() {
		return failurePostLink;
	}
	
	public void setFailurePostLink(String failurePostLink) {
		this.failurePostLink = failurePostLink;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getAppendix() {
		return appendix;
	}
	
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	
	public String getShowCurr() {
		return showCurr;
	}
	
	public void setShowCurr(String showCurr) {
		this.showCurr = showCurr;
	}
	
	public String getShowAmount() {
		return showAmount;
	}
	
	public void setShowAmount(String showAmount) {
		this.showAmount = showAmount;
	}
}
