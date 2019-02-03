package hangman;

import java.io.*;
import java.util.*;

public class EvilHangman implements IEvilHangmanGame {
	
	private Set<String> wordList;
	private Set<String> currentList;
	private Set<Character> guesses;
	
	public EvilHangman() {
		guesses = new TreeSet<Character>();
	}

	@Override
	public void startGame(File dictionary, int wordLength) {
		try {
			FileReader reader = new FileReader(dictionary);
			Scanner s = new Scanner(reader);
			wordList = new TreeSet<String>();
			while(s.hasNext()) {
				wordList.add(s.next().toLowerCase());
			}
			s.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		currentList = new TreeSet<String>();
		for(String word:wordList) {
			if(word.length() == wordLength) {
				currentList.add(word);
			}
		}
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		if(guesses.contains(guess)) {
			throw new GuessAlreadyMadeException();
		}
		else {
			Map<String, TreeSet<String>> patterns = new TreeMap<String, TreeSet<String>>();
			for(String word:currentList) {
				String pattern = makePattern(word, guess);
				if(patterns.get(pattern) == null) {
					TreeSet<String> patternSet = new TreeSet<String>();
					patternSet.add(word);
					patterns.put(pattern, patternSet);
				}
				else {
					patterns.get(pattern).add(word);
				}
			}
			int largest = 0;
			Set<String> currentSet = new TreeSet<String>();
			for(Map.Entry<String, TreeSet<String>> set:patterns.entrySet()) {
				if(set.getValue().size() > largest) {
					largest = set.getValue().size();
					currentSet = set.getValue();
				}
			}
			guesses.add(guess);
			currentList = currentSet;
			return currentSet;
		}
	}
	
	public String makePattern(String word, char guess) {
		StringBuilder builder = new StringBuilder();
		for(char c:word.toCharArray()) {
			if(c == guess) {
                		builder.append(c);
			}
			else {
				builder.append('_');
			}
		}
		return builder.toString();
	}
}
