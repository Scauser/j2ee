package kz.processing.cnp.epay.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author daniyar.artykov
 */
@Entity
@Table(name = "cfg_epay_merchants")
public class Merchant implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2989705521070208306L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "merchant_id")
	private String merchantId;

	@Column(name = "merchant_name")
	private String merchantName;
	
	@Column(name = "private_key", columnDefinition="text")
	private String privateKey;

	@Column(name = "public_key", columnDefinition="text")
	private String publicKey;
	
	@Column(name = "post_link_scheduler")
	private boolean postLinkScheduler;

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

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public boolean isPostLinkScheduler() {
		return postLinkScheduler;
	}

	public void setPostLinkScheduler(boolean postLinkScheduler) {
		this.postLinkScheduler = postLinkScheduler;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
