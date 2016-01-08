package com.fdmgroup.java_week1_assessment;

public class GradeCalculator {

	//public GradeCalculator(double mark, String result){

	//this.mark = mark;
	//	this.result = result;

	public String getClassification(double mark) {

		if (mark < 75.0) {
			return "fail";
		} else if (mark < 80.0) {
			return "pass";
		} else if (mark < 90.0) {
			return "merit";
		} else {
			return "distinction";
		}
	}
}

//public String getResult(){
//return result;
//}

//public Double getMark(){
//return mark;
//}
//}
