package com.fdmgroup.week2_generics;

public class Pair<F extends Number, S extends Number> {

	private F f;
	private S s;

	public F getF() {
		return f;
	}

	public void setF(F f) {
		this.f = f;
	}

	public S getS() {
		return s;
	}

	public void setS(S s) {
		this.s = s;
	}
	
	public double getTotal() {
		return f.doubleValue() + s.doubleValue();
	}
}
