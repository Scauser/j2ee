package kz.processing.cnp.epay.merchant.service.util;

import kz.processing.cnp.epay.model.util.BeanUtil;
import kz.processing.cnp.epay.model.util.Constants;
import kz.processing.cnp.epay.model.util.DOMUtil;
import kz.processing.cnp.merchant_ws_client.MerchantWebServiceStub.GoodsItem;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class CommonBuilderTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonBuilderTest.class);
	private static final String APPENDIX = "PGRvY3VtZW50PjxpdGVtIG51bWJlcj0iMSIgbmFtZT0iMSIgcXVhbnRpdHk9IjEiIGFtb3VudD0iMSIgLz48aXRlbSBudW1iZXI9IjEiIG5hbWU9IiIgcXVhbnRpdHk9IiIgYW1vdW50PSIiIC8+PGl0ZW0gbnVtYmVyPSIxIiBuYW1lPSIxIiBxdWFudGl0eT0iMSIgYW1vdW50PSIxIiAvPjwvZG9jdW1lbnQ+";
	
	@Test
	public void testAppendix() {
		try {
			String appendixDecoded = new String(Base64.decodeBase64(APPENDIX));
			LOG.debug("appendixDecoded={}", appendixDecoded);
			Document appendixDocument = DOMUtil.createDOMFromString(appendixDecoded);
//			GoodsItem [] items = CommonBuilder.getGoodsItemFromDOM(Constants.NODE_ITEM, appendixDocument);
//			
//			for(GoodsItem i : items) {
//				LOG.debug(BeanUtil.dump(i));
//			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
