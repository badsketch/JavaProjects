/**
 * 
 * @author Daniel Wang	
 * @PCC_ID 10018210
 * 
 * CS 3B Programming Project 2
 * 
 * Various methods for generating encryption keys, corresponding 
 * decryption keys, determining letter counts for files, corresponding
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Project2 {
	
	public static void main(String[] args) throws FileNotFoundException
	{
		//Part 4
		
		
		String newEncKey = generateEncryptionKey();//generate encryption key
		int[] letterCount = getLetterCounts("secret.txt");//get letter count for secret.txt
		//print them
		double[] relativeFreq = getRelativeFrequencies(letterCount);//get relative letter frequencies

		PrintWriter out = new PrintWriter("project2Output.txt");
		
		//output random encryption key
		out.println("Random encryption key: "+newEncKey);
		
		//output corresponding decryption key
		out.println("Matching decryption key: "+getDecryptionKey(newEncKey));
		out.println();
		
		//display letter counts
		out.println("Letter counts: ");
		out.println(Arrays.toString(letterCount));
		out.println();
		
		//display relative frequencies in hundreds decimal format
		out.println("Relative Frequencies: ");
		out.print("[");
		for(int i = 0;i<relativeFreq.length - 1;i++)
		{
			out.printf("%.2f, ",relativeFreq[i]);
		}
		out.printf("%.2f",relativeFreq[relativeFreq.length - 1]);
		out.print("]");
		
		
		out.println();
		out.println();
		
		//print frequency chart
		printFrequencyChart(relativeFreq,out);
		out.close();
		
		
		
	}
	
	/**Part 1 a)
	 * returns array with all lower case letters of alphabet
	 * 
	 * @param none
	 * @return char array of lower case alphabet
	 */
	static char[] getAllLetters() {
		
		char[] alpha = new char[26];
		//use ascii mapping
		for(int i = 0;i<alpha.length;i++)
			alpha[i] = (char)('a' + i);
		
		return alpha;
		
	}
	
	/**Part 1 b)
	 * takes array of chars and shuffles elements 
	 * @param alphaArra the character array to be shuffled
	 * @return shuffled character array
	 */
	static char[] shuffleRandomly(char[] alphaArr) {
		Random rand = new Random();
		char temp;//used for swapping
		for(int i = 0;i<alphaArr.length;i++)
		{
			//generate random number between [i, length - 1]
			int j = rand.nextInt(25 - i + 1) + i;
			//swap the two letters
			temp = alphaArr[i];
			alphaArr[i] = alphaArr[j];
			alphaArr[j] = temp;
			
		}
		return alphaArr;

	}
	
	/**Part 1  c)
	 * generates an encryption key string\
	 * @param none
	 * @return String of encryption key
	 */
	static String generateEncryptionKey() {
		
		String newEncryptionKey = "";
		char[] myalpha = new char[26];
		//shuffle the alphabet and store in new array
		myalpha = getAllLetters();
		myalpha = shuffleRandomly(myalpha);
		
		//transfer array values into a string
		for(int i = 0;i<myalpha.length;i++)
			newEncryptionKey += myalpha[i];
		
		return newEncryptionKey;
		
	}
	
	/** Part 2 
	 * determines corresponding decryption key given an encryption key string
	 * @param encKey String of encryption key
	 * @return DecKey decryption string
	 */
	static String getDecryptionKey(String encKey) {
		
		String letters = "abcdefghijklmnopqrstuvwxyz";
		String decKey = "";
		//look through alphabet
		for (int i = 0;i<26;i++)
		{
			//search for letter in encryption key
			for(int j = 0;j<encKey.length();j++)
			{
				//if found, then the corresponding letter at that index
				//is added to decryption key
				if(encKey.charAt(j) == (char)(i + 'a'))
				{
					decKey += letters.charAt(j);
				}
					
			}
		}
		
		return decKey;
	}
	
	/**Part 3 a)
	 * finds letter counts given a file
	 * @param fileName, file to be read and processed
	 * @throws FileNotFoundException 
	 * @returns int array of letter counts
	 */
	static int[] getLetterCounts(String fileName) throws FileNotFoundException
	{
		//used for processing file
		File inputFile = new File(fileName);
		Scanner in = new Scanner(inputFile);
		
		int[] letterCount = new int[26];
		//read through file
		while(in.hasNextLine())
		{
			//take in entire line
			String word = in.next();
			for(int i = 0;i<word.length();i++)
			{
				//examine character
				char c = word.charAt(i);
				if(isLetter(c))
				{
					//so upper/lower case count same
					if(isUpperCaseLetter(c))
						c = toLowerCase(c);
					//[use ascii index to get array index]increment count
					letterCount[c - 'a']++;
				}
			}
			
				
		}
		
		in.close();
		return letterCount;
	}
	
	/**Part 3 b)
	 * determines relative frequencies by dividing count by total count
	 * @param letterCounts array holding letter counts
	 * @return array of type double storing frequencies
	 */
	static double[] getRelativeFrequencies(int[] letterCounts) 
	{
		double [] relativeFreq = new double[26];
		
		int total = 0;
		//find total by summing each element
		for(int i = 0;i<letterCounts.length;i++)
			total += letterCounts[i];
		//find corresponding relative frequencies
		for(int i = 0;i<relativeFreq.length;i++)
			//determine percentage (count/total)*100
			relativeFreq[i] = ((float)letterCounts[i]/total) * 100;
		
		
		return relativeFreq;
	}

	/** Part 3 c)
	 * outputs formatted frequency chart
	 * @param freqArr array of type double of relative frequencies
	 * @param output, desired output printwriter
	 * @throws FileNotFoundException
	 */
	static void printFrequencyChart(double[] freqArr, PrintWriter output) throws FileNotFoundException
	{
		//height of the chart will be greatest frequency
		int chartHeight = getMaximum(freqArr);
		
		//since we print top to bottom, we begin at "top" of chart
		for(int i = chartHeight;i>=1;i--)
		{
			//output percentage
			output.printf(" %3d ",i);
			//traverse through each letter
			for(int j = 0;j<26;j++)
			{
				output.print(" ");
				//if the current row is greater than its count
				if(freqArr[j] >= i)
					//output a "*" since it has at most that many
					output.print("*");
				else
					//otherwise it does not have that many
					output.print(" ");
			}
			output.println();
		}
		//bottom x axis of chart
		output.println("Freq. a b c d e f g h i j k l m n o p q r s t u v w x y z");

		
	}
	
	/**
	 * Convert an upper case letter to lower case
	 * 
	 * @param c The upper case character
	 * @return The corresponding lower case letter
	 */
	public static char toLowerCase(char c) {
		//char casts based on ascii offset mapping
		return (char) (c + 'a' - 'A');
	}
	
	/**
	 * Check if a given character is an upper case letter
	 *  
	 * @param c The character
	 * @return true if upper case letter, false otherwise
	 */
	public static boolean isUpperCaseLetter(char c) {
		return c>= 'A' && c<= 'Z'; 
		
	}
	
	/**
	 * Check if a given character is a lower case letter
	 *  
	 * @param c The character
	 * @return true if lower case letter, false otherwise
	 */
	public static boolean isLowerCaseLetter(char c) {
		//based on ascii mapping
		return c >= 'a' && c <= 'z';
	}
	
	/**
	 * Check if a given character is a letter
	 *  
	 * @param c The character
	 * @return true if a letter, false otherwise
	 */
	public static boolean isLetter(char c) {
		//use previous methods to compare if letter
		return isLowerCaseLetter(c) || isUpperCaseLetter(c);
	}

	
	/**
	 * determines maximum number of array
	 * used for printFrequency to determine height of chart
	 * @param freqArr type double of array
	 * @return returns maximum integer of array
	 */
	static int getMaximum(double[] freqArr)
	{
		//use (int) to floor double values
		//chart cannot have a fraction of an asterisk
		int max = 0;
		//traverse array
		for(int i = 0;i<freqArr.length;i++)
			//if the current value is greater than the current max
			if((int)freqArr[i]>max)
				//set the current max to new value
				max = (int)freqArr[i];
		return max;
	}
	
	
	
	/**
	 * USED FOR EXTRA CREDIT
	 * bubble sort algorithm used for determining secret text
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void bubbleSort(int[] vals)
	{
		int index;
		boolean swap = true;
		//when there is no more to be swapped, we have completed sorting
		while(swap)
		{
			swap = false;
			//traverse through values before the last one
			for(index = 0;index<vals.length - 1;index++)
			{
				//swap two values if the current is smaller than the previous
				if(vals[index]<vals[index + 1])
				{
					int temp = vals[index];
					vals[index] = vals[index + 1];
					vals[index + 1] = temp;
					//swapping has occurred
					swap = true;
				}
			}
		}
	}
	
	
	
}