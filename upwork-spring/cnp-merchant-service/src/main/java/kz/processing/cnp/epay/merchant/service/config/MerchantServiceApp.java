package kz.processing.cnp.epay.merchant.service.config;

import org.glassfish.jersey.server.ResourceConfig;

public class MerchantServiceApp extends ResourceConfig {

	public MerchantServiceApp() {
		packages("kz.processing.cnp.epay.merchant.service");
	}
}
