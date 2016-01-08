package com.fdmgroup.java_week1_assessment;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GradeCalculatorTest {

	private GradeCalculator calc;

	@Before
	public void setup() {
		// create new instance of the class GradeCalculator for testing
		calc = new GradeCalculator();
	}

	@Test
	public void test_GetClassification_ReturnsStringFail_IfInputDoubleIsLessThan75() {
		// arrange
		double mark = 70.0;
		// act
		// get result from method getClassification for variable mark
		String result = calc.getClassification(mark);
		// assert
		// check result, if method getClassification works correctly result for 70.0 will be equal to "fail"
		// see GradeCalculator 12 line
		assertEquals("fail", result);
	}

	@Test
	public void test_GetClassification_ReturnsStringPass_IfInputDoubleIsGreaterThanOrEqualTo75AndLessThan80() {
		double mark = 76;
		String result = calc.getClassification(mark);
		// check result, if method getClassification works correctly result for 76 will be equal to "pass"
		// see GradeCalculator 14 line
		assertEquals("pass", result);
	}

	@Test
	public void test_GetClassification_ReturnsStringMerit_IfInputDoubleIsGreaterThanOrEqualTo80AndLessThan90() {
		double mark = 82;
		String result = calc.getClassification(mark);
		// check result, if method getClassification works correctly result for 76 will be equal to "pass"
		// see GradeCalculator 16 line
		assertEquals("merit", result);
	}

	@Test
	public void test_GetClassification_ReturnsStringDistinction_IfInputDoubleIsGreaterThanOrEqualTo90() {
		double mark = 92;
		String result = calc.getClassification(mark);
		// check result, if method getClassification works correctly result for 76 will be equal to "pass"
		// see GradeCalculator 19 line
		assertEquals("distinction", result);
	}
}
