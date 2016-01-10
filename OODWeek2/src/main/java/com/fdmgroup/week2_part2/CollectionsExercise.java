package com.fdmgroup.week2_part2;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class CollectionsExercise {

	public Set<String> eliminateDuplicates(String[] firstNames) {

		Set<String> set = new HashSet<String>();

		if(firstNames != null) {
			for(String firstName : firstNames) {
				set.add(firstName);
			}
		}

		return set;
	}

	public Map<String, Color> getColorsMap() {

		Map<String, Color> map = new HashMap<String, Color>();

		map.put("black", Color.BLACK);
		map.put("blue", Color.BLUE);
		map.put("cyan", Color.CYAN);
		map.put("dark_gray", Color.DARK_GRAY);
		map.put("gray", Color.GRAY);
		map.put("green", Color.GREEN);
		map.put("light_gray", Color.LIGHT_GRAY);
		map.put("magenta", Color.MAGENTA);
		map.put("orange", Color.ORANGE);
		map.put("pink", Color.PINK);
		map.put("red", Color.RED);
		map.put("white", Color.WHITE);
		map.put("yellow", Color.YELLOW);

		return map;
	}

	public Map<Character, Integer> countLetters(String string) {
		int len = string.length();
		Map<Character, Integer> numChars = 
				new HashMap<Character, Integer>(Math.min(len, 26));
		// we do a for loop over all the string's characters and save the current char in the charAt variable
		for (int i = 0; i < len; ++i) {
			char charAt = string.charAt(i);
			// We check if our HashMap already has a charAt key inside it
			if (!numChars.containsKey(charAt))  {
				// If it's false (we never found a char like this in the string), we add 1 because we found a new char
				numChars.put(charAt, 1);
			} else {
				// If it's true we will just get the current value and add one.. this means the string has already been found to have this char.
				numChars.put(charAt, numChars.get(charAt) + 1);
			}
		}

		return numChars;
	}

	public Map<String, Integer> getDuplicateWords(String string) {
		// convert string to lowercase
		string = string.toLowerCase();

		// replace all punctuation symbols with space
		string = string.replace('.', ' ');
		string = string.replace(',', ' ');
		string = string.replace('!', ' ');
		string = string.replace('?', ' ');

		// split string to array around space
		String [] splitWords = string.split(" ");

		// create instance of map to save occurrence of each string by count
		Map<String, Integer> occurrences = new HashMap<String, Integer>();

		for ( String word : splitWords ) {
			if(!"".equals(word) && !" ".equals(word)) {
				Integer oldCount = occurrences.get(word);
				if ( oldCount == null ) {
					oldCount = 0;
				}
				occurrences.put(word, oldCount + 1);
			}
		}

		return occurrences;
	}

	public LinkedList<Integer> reverse(LinkedList<Integer> origin) {
		LinkedList<Integer> reversed = new LinkedList<Integer>();

		for(int i = origin.size() - 1; i >= 0; i--) {
			reversed.add(origin.get(i));
		}

		return reversed;
	}

	public TreeSet<String> getTreeSet(String input) {
		// split string to array by " "
		String[] tokens = input.split(" ");
		
		TreeSet<String> treeSet = new TreeSet<String>();
		// iterate over array and add string to set
		for(String token : tokens) {
			treeSet.add(token);
		}

		return treeSet;
	}

	public PriorityQueue<Double> getOrderedQueue(double arg0, double arg1) {
		// create new instance of the comparator (look 128 line)
		DoubleComparatorDescending comparator = new DoubleComparatorDescending();
		// create new instance of the PriorityQueue with initial size and comparator
		PriorityQueue<Double> queue = new PriorityQueue<Double>(2, comparator);
		// add given variables into queue
		queue.add(arg0);
		queue.add(arg1);

		// return result
		return queue;
	}
}

class DoubleComparatorDescending implements Comparator<Double> {

	@Override
	public int compare(Double arg0, Double arg1) {
		if (arg1 < arg0) {
			return -1;
		} else if (arg1 > arg0) {
			return 1;
		} else {
			return 0;
		}
	}
}
