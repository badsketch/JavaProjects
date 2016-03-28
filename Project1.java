/**
 * 
 * @author Daniel Wang	
 * @PCC_ID 10018210
 * 
 * CS 3B Programming Project 1
 * 
 * Short description of the program here:
 *  Prompts user for input to be encrypted or decrypted
 *  based on an encryption key
 * 
 */
import java.util.Scanner;


public class Project1 {
	
	
	public static void main(String[] args) {
		
		// get input from the user and encode/decode until user quits
		Scanner in = new Scanner(System.in);
		
		String userInput; 
		
		//prompts user for input 
		System.out.print("Enter 'e' to encode, 'd' to decode, 'q' to quit: ");
		userInput = in.nextLine();
		
		
		//while the user does not enter q
		while (!userInput.equals("q") && !userInput.equals("Q"))
		{
			//encryption
			if(userInput.equals("e") || userInput.equals("E"))
			{
				//prompts user for input and stores in encodedMessage then outputs
				System.out.println("Enter the text to encode: ");
				String userMessage = in.nextLine();
				String encodedMessage = codeMessage(userMessage, true);
				System.out.println(encodedMessage);
			} 
			//decryption
			else if (userInput.equals("d") || userInput.equals("D"))
			{
				//prompts user for input and stores in decodedMessage then outputs
				System.out.println("Enter the text to decode: ");
				String userMessage = in.nextLine();
				String decodedMessage = codeMessage(userMessage, false);
				System.out.println(decodedMessage);
			} 
			//if e,d,q is not entered, continue prompting user (serves as catch all for unknown input)
			System.out.print("Enter 'e' to encode, 'd' to decode, 'q' to quit: ");
			userInput = in.nextLine();
			
		
				
		}
		//If while loop exits, logically assume q was entered, and thus end program with goodbye message
		System.out.println("Good bye!");
		
		
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
	 * Check if a given character is an upper case letter
	 *  
	 * @param c The character
	 * @return true if upper case letter, false otherwise
	 */
	public static boolean isUpperCaseLetter(char c) {
		return c>= 'A' && c<= 'Z'; 
		
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
	 * Convert a lower case letter to upper case
	 * 
	 * @param c The lower case character
	 * @return The corresponding upper case letter
	 */
	public static char toUpperCase(char c) {
		//since upper case, use index obtained by summing lower and uppercase offset, then casting
		return (char) (c - 'a' + 'A');
	}
	
	/**
	 * Encode or decode a character according to the fixed substitution pattern.
	 * 
	 * @param c The character to encode
	 * @param encode true for encoding, false for decoding
	 * @return The encoded character
	 */
	public static char codeChar(char c, boolean encode) {	
		
		//String letters = "abcdefghijklmnopqrstuvwxyz";
		String ENC_KEY = "kngcadsxbvfhjtiumylzqropwe";
		String DEC_KEY = "eidfzkclomasqbwxuvgnpjyhrt";
		
		//initialize new character
		char newChar = c;
		
		if(isLetter(c))
		{
			
			if(isUpperCaseLetter(c))
			{
				//if encode is true, encrypt using char at based on encryption key
				if (encode)
					//new character is based off its ascii offset (65 for capital, 97 for lowercase) and then corresponding index in alphabet
					newChar = ENC_KEY.charAt(c-'A');
				//if decode, use decryption key
				else
				{
					newChar = DEC_KEY.charAt(c- 'A');
				}
				//since keys are in lower case, convert to upper case afterwards
				newChar = toUpperCase(newChar);
			}
			//perform same operation if lower case
			else
			{
				if (encode)
					newChar = ENC_KEY.charAt(c-'a');
				else
					newChar = DEC_KEY.charAt(c - 'a');
			}
		}
				
		
		return newChar;
	}
	
	/**
	 * Encode or decode a message 
	 * 
	 * @param message is the desired message to be encrypted or decrypted
	 * @param encryption flag, encode true for encryption, false for decryption
	 * @return The message after en/decryption
	 */
	public static String codeMessage(String message, boolean encode)
	{
		//initialize string to hold new message
		String newMessage = "";
		//for each letter in desired message
		for(int i = 0;i<message.length();i++)
		{
			//add onto new message by calling the corresponding en/decrypted character
			newMessage += codeChar(message.charAt(i), encode);
		}
		return newMessage;
	}
	
}