package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Word  {
	private String[] words = null;
	private int lenghtList = 0;
	private String fileName = "./words.txt";
	private String word = "";
	Random rand = new Random();
	
	public final String alphabet = "ÀÁÂÃÄÅÆÇÈÛÚÝÉÊËÌÍÎÏÐÑÒÓÔÕ×ÖØÙÜÞß";
	
	private void readFileWords() throws IOException  {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))  {
		    String value = reader.readLine();
		    
		    if(value.length() > 0) {
		    	words = value.split(";");
		    	lenghtList = words.length;
		    }
		    
		} catch (Exception e) {
			System.out.println("cannot read file");
		}
	}
	
	public String takeRandomWord() throws IOException {
		int randWord;
		
		readFileWords();
		
		if(lenghtList > 0) {
			randWord = rand.nextInt(lenghtList);
			word = words[randWord];
		} 
		
		return word;		
	}
	
	
}
