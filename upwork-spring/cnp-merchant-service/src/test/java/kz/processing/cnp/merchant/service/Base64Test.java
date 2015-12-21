package kz.processing.cnp.merchant.service;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {

	public static void main(String args[]) throws UnsupportedEncodingException {
		System.out.println(new String(Base64.decodeBase64("0JDQoNCi0KvQmtCe0JIg0JTQkNCd0JjQr9CgINCk0JDQpdCg0JjQotCU0JjQndCe0JLQmNCn")));
		System.out.println(Base64.encodeBase64String("АРТЫКОВ ДАНИЯР ФАХРИТДИНОВИЧ".getBytes("UTF8")));
	}
}
