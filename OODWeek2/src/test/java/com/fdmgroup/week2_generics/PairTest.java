package com.fdmgroup.week2_generics;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PairTest {

	private Pair<Number, Number> pair;

	@Before
	public void setUp() {
		// create new instance of the class Pair
		pair = new Pair<Number, Number>();
	}

	@Test
	public void testFGetSet() {
		// arrange
		pair.setF(1);
		// act
		Number f = pair.getF();
		// assert
		assertEquals(1, f);
	}

	@Test
	public void testSGetSet() {
		// arrange
		pair.setS(2);
		// act
		Number s = pair.getS();
		// assert
		assertEquals(2, s);
	}

	@Test
	public void testGetTotal() {
		// arrange
		pair.setF(1);
		pair.setS(2);
		// act
		double total = pair.getTotal();
		// assert
		assertEquals(3.0, total, 0.01);
	}
}
