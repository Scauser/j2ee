/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.processing.cnp.epay.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author daniyar.artykov
 */
@Entity
@Table(name = "cnp_transaction_data")
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5866180464603858779L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "merchant_id")
	private String merchantId;
	@Column(name = "cert_id")
	private String certId;
	@Column(name = "order_id")
	private String orderId;
	@Column(name = "customer_reference")
	private String customerReference;
	@Column(name = "signed_order_base64", columnDefinition="text")
	private String signedOrderB64;
	@Column(name = "email")
	private String email;
	@Column(name = "backlink")
	private String backlink;
	@Column(name = "failure_backlink")
	private String failureBackLink;
	@Column(name = "postlink")
	private String postLink;
	@Column(name = "failure_postlink")
	private String failurePostLink;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "transaction_date")
	private Date transactionDate;
	@Column(name = "success")
	private boolean success;
	@Column(name = "verified")
	private boolean verified;
	@Column(name="post_link_content", columnDefinition="text")
	private String postLinkContent;
	@Column(name = "status")
	private String status;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "status_date")
	private Date statusDate;
	@Column(name = "error_description")
	private String errorDescription;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public String getSignedOrderB64() {
		return signedOrderB64;
	}

	public void setSignedOrderB64(String signedOrderB64) {
		this.signedOrderB64 = signedOrderB64;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBacklink() {
		return backlink;
	}

	public void setBacklink(String backlink) {
		this.backlink = backlink;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String isPostLinkContent() {
		return postLinkContent;
	}

	public void setPostLinkContent(String postLinkContent) {
		this.postLinkContent = postLinkContent;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
