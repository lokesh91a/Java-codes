/* 
 * Factorization.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */

import java.util.*;

/**
 * This program takes a integer n from the command line and 
 * finds all prime factors of the number n. 
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */

public class Factorization {

	// Array used to store the prime factors of the input number
	public static ArrayList<Integer> primeFactors = new ArrayList<Integer>();
	
    /**
     * The main program.
     *
     * @param    args    command line arguments are used as for input number
     */	
	public static void main(String arg[])
	{
		try
		{
			// Find out the prime factors of the input number
			if (arg.length != 0 && arg.length == 1)
			{
				calculatePrimeFactors(Integer.parseInt(arg[0]));
			
				// print the calculated prime factors
				for(int i = 0; i < primeFactors.size()-1; i++)		
					System.out.print(primeFactors.get(i) + " * "); 
				
				// we are printing the last element separately
				// This is just for the formatting purpose as we don't want
				// * sign to be printed after the last factor
				System.out.println(primeFactors.get(primeFactors.size()-1));
			}
			else
				System.out.println("No input or invalid input entered. ");
		}
		catch (NumberFormatException e)
		{
			System.out.println("Not a number. Please enter number ");
		}
	}
	
	/**
   	* Calculates the prime factors of the given input.   
   	*
   	* @param    number    prime factor of the number to be found out
   	* 
   	* @return   		  None
   	*/
	public static void calculatePrimeFactors(int number)
	{					
		// The below logic is to calculate the factors of the given number
		double sqroot = Math.sqrt(number);
		int tempSqroot = (int)sqroot;
		
		if(sqroot > tempSqroot)		
			tempSqroot++;		
		
		// we are calculating the prime factors of the given number
		// by looping through all the numbers that can be prime factors.
		for (int divisor = 2; divisor <= tempSqroot; divisor++)
		{
			// Take the number and check whether it is divisible by
			// numbers starting from number 2.
			int primeNumber = (int)number % divisor;
			if(primeNumber == 0)
			{	
				// If number is divisible then get its quotient
				// This quotient is sent to this same function again
				// making this a recursive call.
				number = number / divisor;
				
				// Save the prime factor found
				primeFactors.add(divisor);
				
				// Take the quotient and start calculating its factor again
				calculatePrimeFactors(number);
				return;
			}
		}
		// If the last factor found is prime then it will come at this place.
		// We need to save that last factor too.
		primeFactors.add(number);
	}		
}
