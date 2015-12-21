package kz.processing.cnp.epay.merchant.service.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;

import kz.processing.cnp.epay.merchant.service.config.ConfigFactory;
import kz.processing.cnp.epay.model.util.KeyValuePair;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);
	private static String chocolifeMerchantId = "777000000000023";

	static { 
		chocolifeMerchantId = ConfigFactory.getProperty("chocolife.merchant.id");
	}

	public static CloseableHttpClient prepareClient() {
		try {
			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustSelfSignedStrategy()).useTLS().build();
			HttpClientBuilder builder = HttpClientBuilder.create();
			SSLConnectionSocketFactory sslConnectionFactory = 
					new SSLConnectionSocketFactory(sslContext, 
							SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			builder.setSSLSocketFactory(sslConnectionFactory);
			Registry<ConnectionSocketFactory> registry = 
					RegistryBuilder.<ConnectionSocketFactory>create()
					.register("https", sslConnectionFactory)
					.register("http", new PlainConnectionSocketFactory())
					.build();
			HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);
			builder.setConnectionManager(ccm);
			return builder.build();
		} catch (Exception ex) {
			LOG.error("couldn't create httpClient!! {}", ex.getMessage(), ex);
			return null;
		}
	}

	public static String doGet(String url){
		LOG.debug("doGet {}", url);
		CloseableHttpClient httpClient = prepareClient();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);	
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			return EntityUtils.toString(entity, "UTF-8").trim();
		} catch (Exception e) {
			LOG.error("couldn't send request!! {}", e.getMessage(), e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				LOG.warn("couldn't close httpClient!! {} ", e.getMessage(), e);
			}
		}
		return "";
	}

	public static int doGetReturnStatusCode(String url){
		CloseableHttpClient httpClient = prepareClient();
		HttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);	
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			LOG.debug(EntityUtils.toString(entity, "UTF-8"));
			return response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			LOG.error("couldn't send request!! {}", e.getMessage(), e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				LOG.warn("couldn't close httpClient!! {} ", e.getMessage(), e);
			}
		}
		return 0;
	}

	public static String doPost(String url, String key, String value) {
		LOG.debug("sending data={} via post to url={}", value, url);
		CloseableHttpClient httpClient = prepareClient();
		HttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
			pairList.add(new BasicNameValuePair(key == null ? "param" : key, value));
			StringEntity stringEntity = new UrlEncodedFormEntity(pairList);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String rs = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("statusCode={}; content={}", response.getStatusLine().getStatusCode(), "0".equals(rs));
			return rs;
		} catch (Exception e) {
			LOG.error("couldn't send request!! {}", e.getMessage(), e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				LOG.warn("couldn't close httpClient!! {} ", e.getMessage(), e);
			}
		}
		return "";
	}

	@SuppressWarnings("unused")
	private static String doPost(String url, String mid, String key, String value) {
		LOG.debug("sending data={} via post to url={}", value, url);
		CloseableHttpClient httpClient = prepareClient();
		HttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
			pairList.add(new BasicNameValuePair(key == null ? "param" : key, value));
			StringEntity stringEntity = new UrlEncodedFormEntity(pairList);
			httpPost.setEntity(stringEntity);
			String rs = "";
			while(true) {
				try {
					response = httpClient.execute(httpPost);
					HttpEntity entity = response.getEntity();
					rs = EntityUtils.toString(entity, "UTF-8");
					LOG.debug("statusCode={}; content={}", 
							response.getStatusLine().getStatusCode(), "0".equals(rs));
					if(!chocolifeMerchantId.equals(mid) 
							|| (chocolifeMerchantId.equals(mid) 
									&& response.getStatusLine().getStatusCode() == 200 
									&& rs.equals("0"))) {
						break;
					}
					LOG.debug("try to call again {}", url);
				} catch(Exception ex) {
					LOG.warn("ignore {}", ex.getMessage());
					continue;
				}
			}
			return rs;
		} catch (Exception e) {
			LOG.error("couldn't send request!! {}", e.getMessage(), e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				LOG.warn("couldn't close httpClient!! {} ", e.getMessage(), e);
			}
		}
		return "";
	}

	public static String doPost(String url, KeyValuePair ... keyValuePairs){
		LOG.debug("sending data via post to url {}", url);
		CloseableHttpClient httpClient = prepareClient();
		HttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
			for(KeyValuePair kv:keyValuePairs) {
				pairList.add(new BasicNameValuePair(kv.getKey(), (String) kv.getValue()));
			}
			StringEntity stringEntity = new UrlEncodedFormEntity(pairList);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String rs = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("statusCode={}; content={}", response.getStatusLine().getStatusCode(), "0".equals(rs));
			return rs;
		} catch (Exception e) {
			LOG.error("couldn't send request!! {}", e.getMessage(), e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				LOG.warn("couldn't close httpClient!! {} ", e.getMessage(), e);
			}
		}
		return "";
	}
}
