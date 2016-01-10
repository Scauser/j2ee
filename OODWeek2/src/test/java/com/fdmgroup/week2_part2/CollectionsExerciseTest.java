package com.fdmgroup.week2_part2;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class CollectionsExerciseTest {

	private CollectionsExercise exercise;

	@Before
	public void setUp() {
		// create instance of the class CollectionsExercise
		exercise = new CollectionsExercise();
	}

	@Test
	public void testEliminateDuplicates() {
		// arrange
		String[] firstNames = {"Daval", "Daniyar", "Daval", "Daniyar1"};
		// act
		Set<String> set = exercise.eliminateDuplicates(firstNames);
		// assert
		// check that size of the set is 3, because firstNames array has 2 "Daval"
		assertEquals(3, set.size());
	}

	@Test
	public void testGetColorsMap() {
		// act
		Map<String, Color> colorsMap = exercise.getColorsMap();
		// assert
		// check that size of the map is 13, it should be 13 according to task desc
		assertEquals(13, colorsMap.size());
	}

	@Test
	public void testCountLetters() {
		// act
		Map<Character, Integer> numChars = 
				exercise.countLetters("Hello world!");
		// assert
		Integer lCount = numChars.get('l');
		// in the word "Hello world!" letter 'l' meets 3 times
		assertEquals(3, lCount.intValue()); 
	}

	@Test
	public void testGetDuplicateWords() {
		String str = "Hello world, hello to everybody!";
		// act
		Map<String, Integer> occurrences = exercise.getDuplicateWords(str);
		// assert
		Integer helloCount = occurrences.get("hello");
		// in the string "Hello world, hello to everybody!" word "hello" occurs 2 times
		assertEquals(2, helloCount.intValue());
	}
	
	@Test
	public void testReverse() {
		LinkedList<Integer> origin = new LinkedList<Integer>();
		origin.add(0);
		origin.add(1);
		origin.add(2);
		origin.add(3);
		// act
		LinkedList<Integer> reversed = exercise.reverse(origin);
		// assert
		// check the first element origin list is equal to last element of reversed list
		assertEquals(origin.get(0).intValue(), reversed.get(3).intValue());
		// check the first element origin list is equal to last element of reversed list
		assertEquals(origin.get(1).intValue(), reversed.get(2).intValue());
		// check the first element origin list is equal to last element of reversed list
		assertEquals(origin.get(2).intValue(), reversed.get(1).intValue());
		// check the first element reversed list is equal to last element of origin list
		assertEquals(origin.get(3).intValue(), reversed.get(0).intValue());
	}
	
	@Test
	public void testGetTreeSet() {
		String input = "3 1 2";
		// act
		TreeSet<String> treeSet = exercise.getTreeSet(input);
		// assert
		// check that size of the returned set is 3
//		System.out.println(treeSet);
		assertEquals(3, treeSet.size());
	}
	
	@Test
	public void testGetOrderedQueue() {
		double arg0 = 3.2;
		double arg1 = 9.8;
		// act
		PriorityQueue<Double> queue = exercise.getOrderedQueue(arg0, arg1);
		// assert
		// check that the first element of the queue is 9.8
		assertEquals(9.8, queue.poll().doubleValue(), 0.01);
		// check that the second element of the queue is 3.2
		assertEquals(3.2, queue.poll().doubleValue(), 0.01);
	}
}
