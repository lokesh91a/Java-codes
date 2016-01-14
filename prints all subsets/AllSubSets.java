/* 
 * AllSubSets.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */

import java.util.*;

/**
 * This program determines all possible subsets of people
 * attending a party
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */

public class AllSubSets 
{
	// Stores the list of subsets of people attending the party
	static ArrayList<String> listofSubSets = new ArrayList<String>();	
	
    /**
     * The main program.
     *
     * @param    args    command line arguments (ignored)
     */		
	public static void main(String arg[])
	{						
		String inputString;  // Stores the input from the user
		
		System.out.println("Enter the number of friends: ");
		
		try
		{
		//Get the input from the user
		Scanner input = new Scanner(System.in);				    
	    inputString = input.nextLine();
	    
	    // Closing scanner
	    input.close();		
	    
	    // Add empty subset at start of the list
	    listofSubSets.add("");
	    
	    // Loop through the number of friends entering the party
		for(int friend = 1; friend <= Integer.parseInt(inputString); friend++)
		{
			// Go through the list of existing friends already in the party
			// and one by one union the new member with the existing subsets
			int containerSize = listofSubSets.size();
			for(int subSet = 0; subSet < containerSize; subSet++)
			{
				// This check ensures to make a single subset of new member
				// Its like we are looking at the empty subset so union new
			    // new member with empty subset
				if (subSet == 0)
					listofSubSets.add (Integer.toString(friend));
				else
					listofSubSets.add(listofSubSets.get(subSet)
												+ Integer.toString(friend));
			}			
		}
		
		// This step is optional. We are just sorting the list of subsets
		// to have a organized view of all subsets.
		sortListofSubSets(listofSubSets);
		
		// Print the list of all subsets formed.
		for(int setElem = 0;setElem < listofSubSets.size() - 1; setElem++)
		{
			System.out.print("{" + listofSubSets.get(setElem)+"}," + " ");			
		}
		
		 // If last element is remaining then print without appending comma
		 System.out.print("{"+ listofSubSets.get(listofSubSets.size()-1) +"}");			   
		
		}
		catch (NumberFormatException e)
		{
			System.out.println("No Input or Invalid Input Entered.");
		}
	}		
	
	/**
   	* Sort the list of all subsets formed by program  
   	*
   	* @param    listofSubSets    List of all subsets that needs to be sorted
   	* 
   	* @return   		  None
   	*/	
	public static void sortListofSubSets(ArrayList<String> listofSubSets)
	{	
		// We are using bubble sort algorithm to sort the subset elements
		for(int i = 1; i < listofSubSets.size(); i++)
		{		
			// Iterate through the entire list to get the greatest element
			// and push it at the end of the list
			for(int element = 1; element < listofSubSets.size()-i; element++)
			{		
				String firstElem = listofSubSets.get(element);
				String secondElem = listofSubSets.get(element + 1);
				
				// Compare and push the larger subset towards the end
				// at each iteration
				if(Integer.parseInt(firstElem) > Integer.parseInt(secondElem))
				{
					listofSubSets.set(element, secondElem);
					listofSubSets.set(element + 1, firstElem);
				}
			}
		}		
	}
	
}
