package com.upwork.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.upwork.test.WsseSecurity.Assertion;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestedSecurityToken")
public class RequestedSecurityToken {

	@XmlElement(name = "Assertion")
	protected Assertion assertion;

	public Assertion getAssertion() {
		return assertion;
	}

	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}
}
