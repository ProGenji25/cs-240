package spell;

import java.io.*;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {
	
	private Trie dictionary;
	
	public SpellCorrector() {
		dictionary = new Trie();
	}

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		File file = new File(dictionaryFileName);	//load dictionary file into program
		FileReader reader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(reader);
		Scanner scanner = new Scanner(buffer);
		scanner.useDelimiter("([^a-zA-Z]+)");		//ignores non-alphabetic characters
		while(scanner.hasNext()) {					//adds every valid word to trie
			String word = scanner.next();
			dictionary.add(word);
		}
		scanner.close();
	}

	@Override
	public String suggestSimilarWord(String inputWord) {
		inputWord = inputWord.toLowerCase();
		if (getFrequency(inputWord) > 0) {			//if word is in trie, return word
			return inputWord;
		}
		String suggestion = "";
		int best_freq = 0;
		String temp;
		int temp_freq = 0;
		List<String> list1 = editor(inputWord);		//editor for 1-distance edits
		for(int i = 0; i < list1.size(); i++) {
			temp = list1.get(i);
			temp_freq = getFrequency(temp);
			if(temp_freq > 0) {						//find most frequent word
				if(temp_freq > best_freq) {
					best_freq = temp_freq;
					suggestion = temp;
				}
				else if (temp_freq == best_freq) {	//find most frequent word that is first alphabetically
					if (temp.compareTo(suggestion) < 0) {
						suggestion = temp;
					}
				}
			}
		}
		if(best_freq > 0) {
			return suggestion;
		}
		
		for(int i = 0; i < list1.size(); i++) {
			List<String> list2 = editor(list1.get(i));	//editor for 2-distance edits
			for(int j = 0; j < list2.size(); j++) {
				temp = list2.get(j);
				temp_freq = getFrequency(temp);
				if(temp_freq > 0) {
					if(temp_freq > best_freq) {			//find most frequent word
						best_freq = temp_freq;
						suggestion = temp;
					}
					else if (temp_freq == best_freq) {	//find most frequent word that is first alphabetically
						if (temp.compareTo(suggestion) < 0) {
							suggestion = temp;
						}
					}
				}
			}
		}
		if(best_freq > 0) {
			return suggestion;
		}
		else {
			return null;
		}
	}
	
	public int getFrequency(String word) {
		Node temp = (Node)dictionary.find(word);
		if(temp != null) {
			return temp.getValue();
		}
		return 0;
	}
	
	public List<String> editor(String word){
		List<String> list = new ArrayList<String>();
		int length = word.length();
		for (int i = 0; i < length; i++) {	//deletion and transposition edits
			if (i > 0) {
				list.add(word.substring(0, i) + word.substring(i + 1));
				list.add(word.substring(0, i - 1) + word.charAt(i) + word.charAt(i - 1) + word.substring(i + 1));
			}
			else {
				list.add(word.substring(i + 1));
			}
		}
		for (int i = 0; i < length; i++) {	//alteration edits
			for (char c = 'a'; c <= 'z'; c++) {
				list.add(word.substring(0, i) + c + word.substring(i + 1));
			}
		}
		for (int i = 0; i <= length; i++) {	//insertion edits
			for (char c = 'a'; c <= 'z'; c++) {
				list.add(word.substring(0, i) + c + word.substring(i));
				//System.out.println(list.get(list.size() - 1));
			}
		}
		return list;
	}
}
