package hangman;

import java.io.*;
import java.util.*;
import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

public class Main
{
	public static void main(String[] args) {
		EvilHangman game = new EvilHangman();
		File dictionary = new File(args[0]);
		int wordLength = Integer.parseInt(args[1]);
		int guesses = Integer.parseInt(args[2]);
		game.startGame(dictionary, wordLength);
		String guess;
		int blanks = wordLength;
		Scanner scanner = new Scanner(System.in);
		TreeSet<String> words = new TreeSet<String>();
		TreeSet<Character> usedLetters = new TreeSet<Character>();
		char[] word = new char[wordLength];
		for(int i = 0; i < wordLength; i++) {
			word[i] = '_';
		}
		while(guesses > 0 && blanks > 0) {
			System.out.println("You have " + guesses + " guesses left.");
			System.out.println("Used letters: " + usedLetters.toString());
			System.out.println("Word: " + String.valueOf(word));
			System.out.println("Guess a letter: ");
			guess = scanner.nextLine();
			while(guess.isEmpty()) {
				System.out.println("Invalid input.  Guess a letter.");
				guess = scanner.nextLine();
			}
			char g = guess.charAt(0);
			while(!Character.isLetter(g) || guess.length() > 1) {
				System.out.println("Invalid input.  Guess a letter.");
				guess = scanner.nextLine();
				while(guess.isEmpty()) {
					System.out.println("Invalid input.  Guess a letter.");
					guess = scanner.nextLine();
				}
				g = guess.charAt(0);
			}
			g = Character.toLowerCase(g);
			try {
				words = (TreeSet<String>) game.makeGuess(g);
				String c = Character.toString(g);
				int numberOfLetters = 0;
				if(!words.first().contains(c)) {
					System.out.println("Sorry, there are no " + g + "'s.\n");
					guesses--;
				}
				else {
					String temp = game.makePattern(words.first(),g);
					for(int i = 0; i < wordLength; i++) {
						if(temp.toCharArray()[i] != '_') {
							word[i] = temp.toCharArray()[i];
							blanks--;
							numberOfLetters++;
						}
					}
					System.out.println("Yes, there are " + numberOfLetters + " " + g + "'s.\n");
				}
				usedLetters.add(g);
			}
			catch (GuessAlreadyMadeException e) {
				System.out.println("You already used that letter.\n");
			}
		}
		if(words.size() == 1 && blanks == 0) {
			System.out.println("You Win! :" + words.first() + '\n');
		}
		else {
			String[] array = new String[words.size()];
			words.toArray(array);
			Random rnd = new Random(31);
			System.out.println("You lose.  The word was: " + array[rnd.nextInt(array.length)] + '\n');
		}
		scanner.close();
	}
}
