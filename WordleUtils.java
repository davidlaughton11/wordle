package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Methods useful for implementing the game Wordle. None of the methods
 * assume that the guess or answer words have exactly 5 letters.
 */
public class WordleUtils {

	/**
	 * Returns a new string formed by replacing one character in the string
	 * {@code s} with the replacement character {@code c}. The position of the
	 * replaced character is given by {@code index}.
	 * 
	 * @param s     a string to replace one character in
	 * @param index the index of the replaced character
	 * @param c     the character to use as the replacement character
	 * @return a new string formed by replacing one character in s with the
	 *         replacement character c
	 * @throws IndexOutOfBoundsException if index is not a valid index for the
	 *                                   string s
	 */
	public static String replace(String s, int index, char c) {
		if(index >= s.length() || index < 0) {
			throw new IllegalArgumentException("index too large");
		}
		//segmenting the string to slip the new char in between
		String beforeIndex = s.substring(0, index);
		String afterIndex = s.substring(index + 1, s.length());
		//returning the new string
		return beforeIndex + c + afterIndex;
	}

	/**
	 * Returns a list indicating which letters in the {@code guess} string match
	 * letters in the {@code answer} string. Letters match if they are equal and
	 * have the same location in both strings.
	 * 
	 * <p>
	 * The returned list contains the value {@code true} at index {@code i} if the
	 * characters at index {@code i} of the {@code guess} and {@code answer} strings
	 * are equal, otherwise the the value at index {@code i} is {@code false}.
	 * 
	 * @param guess  a string to compare to the answer
	 * @param answer the answer string
	 * @return a list of boolean values indicating whether characters in the guess
	 *         and answer strings match
	 * @throws IllegalArgumentException if guess and answer have different lengths
	 */
	public static ArrayList<Boolean> isGreen(String guess, String answer) {
		if(guess.length() != answer.length()) {
			throw new IllegalArgumentException("guess is improper length " + guess);
		}
		ArrayList<Boolean> match = new ArrayList<Boolean>();
		//loop through all the chars in the answer comparing on each iteration
		for(int i = 0; i < answer.length(); i++) {
			if(guess.charAt(i) == answer.charAt(i)) {
				match.add(i, true);
			}else {
				match.add(i, false);
			}
		}
		return match;
	}

	/**
	 * Returns a list indicating which letters in the {@code guess} string also
	 * appear in the {@code answer} string taking into account how many times a
	 * letter appears; see the assignment document for some examples.
	 * 
	 * <p>
	 * The returned list contains the value {@code true} at index {@code i} if the
	 * character at index {@code i} of the {@code guess} appears in the
	 * {@code answer} string (again, accounting for the number of times the letter
	 * appears), otherwise the the value at index {@code i} is {@code false}.
	 * 
	 * @param guess  a string to compare to the answer
	 * @param answer the answer string
	 * @return a list of boolean values indicating whether characters in the guess
	 *         string appear in the answer string accounting for the number of times
	 *         that the character appears
	 * @throws IllegalArgumentException if guess and answer have different lengths
	 */
	public static ArrayList<Boolean> isYellow(String guess, String answer) {
		if(guess.length() != answer.length()) {
			throw new IllegalArgumentException("guess is improper length");
		}
		//treemaps for answer and guess
		HashMap<Integer, Character> answerMap = new HashMap<Integer, Character>();
		HashMap<Integer, Character> guessMap = new HashMap<Integer, Character>();
		for(int i = 0; i < guess.length(); i++) {
			answerMap.put(i, answer.charAt(i));
			guessMap.put(i, guess.charAt(i));
		}
		
		ArrayList<Boolean> containsG = new ArrayList<Boolean>();
		//contains is now true in all spots where the guess matches the answer
		containsG = WordleUtils.isGreen(guess, answer);
		//loop to change all the green characters to a non letter symbol
		for(int i = 0; i < answer.length(); i++) {
				if(containsG.get(i) == true) {
					//replacing all the green letters with a non-letter symbol
					answerMap.replace(i, '*');
				}
		}
		//guess map contains the guesses at the points
		ArrayList<Boolean> containsY = new ArrayList<Boolean>();
		//initialize the arraylist
		for(int i = 0; i< guess.length(); i++) {
			containsY.add(false);
		}
		for(int i = 0; i < guess.length(); i++) {
			for(int j = 0; j < answer.length(); j++) {//issue is the second answer is being replaced
				if(answerMap.get(j) == guessMap.get(i)) {//if there is a common letter in both
					containsY.set(i,true);
					//add it to containsY and remove from the answer map as to not get repeats
					answerMap.replace(j, '@');
					guessMap.replace(i, '@');
				}
			}
		}
		
		return containsY;
	}
	
	

	/**
	 * Returns a list of the colorings for the given guess and answer words. See the
	 * assignment document for details on how letters of the guess word are colored
	 * in the game Wordle.
	 * 
	 * @param guess  a guess word
	 * @param answer the answer word
	 * @return a list of the colorings for the given guess and answer words
	 * @throws IllegalArgumentException if guess and answer have different lengths
	 */
	public static ArrayList<WordleColor> getColors(String guess, String answer) {
		if(guess.length() != answer.length()) {
			throw new IllegalArgumentException("guess is improper length");
		}
		
		ArrayList<WordleColor> colours = new ArrayList<WordleColor>();
		ArrayList<Boolean> green = isGreen(guess, answer);
		ArrayList<Boolean> yellow = isYellow(guess, answer);
		//run through all the elements in the guess
		for(int i = 0; i < guess.length(); i++) {
			//add the colours from the other methods
			if(green.get(i) == true) {
				colours.add(i, WordleColor.GREEN);
			}
			else if(yellow.get(i) == true) {
				colours.add(i, WordleColor.YELLOW);
			}
			else {
				colours.add(i, WordleColor.GRAY);
			}
		}
		return colours;
	}

	/**
	 * Update the sets of different letter categories in a game of Wordle given the
	 * guess and answer words. This method is meant to be called after the player
	 * makes a new guess. The first time that the method is called for a new game of
	 * Wordle, the sets {@code included} and {@code excluded} should be empty, and
	 * the set {@code possible} should contain the letters {@code 'A'} through
	 * {@code 'Z'}.
	 * 
	 * <p>
	 * The set {@code included} is the set of letters that the player has determined
	 * must be in the answer word.
	 * 
	 * <p>
	 * The set {@code excluded} is the set of letters that the player has determined
	 * must not be in the answer word.
	 * 
	 * <p>
	 * The set {@possible} is the set of letters that have not been used yet by the
	 * player. If {@code guess.equals(answer)} is {@code true} then the set
	 * {@possible} will be empty.
	 * 
	 * @param guess    the most recent guess word
	 * @param answer   the answer word
	 * @param included the set of letters that the player has determined are in the
	 *                 answer word
	 * @param excluded the set of letters that the player has determined are not in
	 *                 the answer word
	 * @param possible the set of letters not in included or excluded
	 * @throws IllegalArgumentException if guess and answer have different lengths
	 */
	public static void updateLetters(String guess, String answer, 
			TreeSet<Character> included,
			TreeSet<Character> excluded, 
			TreeSet<Character> possible) {
		
		if(guess.length() != answer.length()) {
			throw new IllegalArgumentException("guess is improper length");
		}
		ArrayList<WordleColor> colors = getColors(guess, answer);
		
		//run through the guess to update the sets
		//included for loop
		for(int i = 0; i < guess.length(); i++) {
			possible.remove(guess.charAt(i));
			if(colors.get(i) == WordleColor.GREEN||
					colors.get(i) == WordleColor.YELLOW) {
				included.add(guess.charAt(i));
			}
		}
		//to get the excluded characters
		for(int i = 0; i < guess.length(); i++) {
			if(!included.contains(guess.charAt(i))) {
				excluded.add(guess.charAt(i));
			}
		}
		//for the correct case 
		if(guess == answer) {
			int k = possible.size();
			for(int j = 0; j < k;j++) {
				excluded.add(possible.first());
				possible.remove(possible.first());
			}
		}
		
	}

	public static void main(String[] args) {
		// simulates one game of Wordle
		
		// set up the sets of letters
		TreeSet<Character> in = new TreeSet<>();
		TreeSet<Character> notIn = new TreeSet<>();
		TreeSet<Character> mightBeIn = new TreeSet<>();
		for (char c = 'A'; c <= 'Z'; c++) {
			mightBeIn.add(c);
		}
		
		// the answer
		String ans = "HELLO";

		// the guesses
		final String[] GUESSES = { "LLXXL"};
		int guessNum = 0;
		for (String g : GUESSES) {
			guessNum++;
			System.out.println("answer  : " + ans);
			System.out.println("guess " + guessNum + " : " + g);
			System.out.println("isGreen : " + WordleUtils.isGreen(g, ans));
			System.out.println("isYellow: " + WordleUtils.isYellow(g, ans));
			System.out.println("colors  : " + WordleUtils.getColors(g, ans));
			WordleUtils.updateLetters(g, ans, in, notIn, mightBeIn);
			System.out.println("in      : " + in);
			System.out.println("not in  : " + notIn);
			System.out.println("maybe?  : " + mightBeIn);
			System.out.println();
	
		// simulates one game of Wordle
		
		// set up the sets of letters
//		TreeSet<Character> in = new TreeSet<>();
//		TreeSet<Character> notIn = new TreeSet<>();
//		TreeSet<Character> mightBeIn = new TreeSet<>();
//		for (char c = 'A'; c <= 'Z'; c++) {
//			mightBeIn.add(c);
//		}
//		
//		// the answer
//		String ans = "CORAL";
//
//		// the guesses
//		final String[] GUESSES = { "VEGAN", "HULAS", "BLOAT", "LOYAL",  "FOCAL", "CORAL"};
//		int guessNum = 0;
//		for (String g : GUESSES) {
//			guessNum++;
//			System.out.println("answer  : " + ans);
//			System.out.println("guess " + guessNum + " : " + g);
//			System.out.println("isGreen : " + WordleUtils.isGreen(g, ans));
//			System.out.println("isYellow: " + WordleUtils.isYellow(g, ans));
//			System.out.println("colors  : " + WordleUtils.getColors(g, ans));
//			WordleUtils.updateLetters(g, ans, in, notIn, mightBeIn);
//			System.out.println("in      : " + in);
//			System.out.println("not in  : " + notIn);
//			System.out.println("maybe?  : " + mightBeIn);
//			System.out.println();
		}

	}

}
