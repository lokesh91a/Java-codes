/* 
 * Calculator.java 
 * 
 * Version: 
 *     $2$ 
 * 
 * Revisions: 
 *     $1$ 
 */

import java.util.*;

/**
 * This Program Works as a Calculator and evaluates mathematical expressions
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */

public class Calculator {
	
	// Stack for storing the input numbers
	public static Stack<String> numberStack = new Stack<String>();
	
	// Stack for storing the input operators
	public static Stack<String> operatorStack = new Stack<String>();
	
	// Stores the precedence order of the operators
	public static String precedence [] = {"+","-","%","*","/","^","("};	
	
    /**
     * The main program.
     *
     * @param    args    command line arguments are used as an input expression
     */	
	public static void main(String arg[])
	{
		// Evaluate the incoming expression
		double outputResult = evaluateExpression(arg);
		
		// If Result of the calculation does not have decimal point value
		// then print the number without the decimal point
		if(outputResult == Math.floor(outputResult))
		{
			System.out.println("OutPut is: "+ (int)outputResult);
		}
		else
			System.out.println("OutPut is: "+ outputResult);	
	}
	
	/**
   	* This method is for the mathematical evaluation of of the expression   
   	*
   	* @param    None
   	* 
   	* @return   		  Returns the result of maths operation between two arg
   	*/	
	public static double calculateExpression()
	{
		double result = 0;  // Stores the calculated result
		
		// Get the top operator and perform arithmetic operation
		// on top two numbers from the number stack
		String topOperator = operatorStack.pop();
		double numRight = Double.parseDouble(numberStack.pop());
		double numLeft = Double.parseDouble(numberStack.pop());			
		
		// The below code checks which operation needs to be performed on two
		// input operators
		switch(topOperator)
		{
			case "+":
				result = numLeft + numRight;
				break;
			case "-":
				result = numLeft - numRight;
				break;
			case "%":
				result = numLeft % numRight;
				break;
			case "*":
				result = numLeft * numRight;
				break;
			case "/":
				if (numRight == 0.0)
				{
					System.out.println("Divide by 0 is not a allowed");
					System.exit(0);
				}				
				result =  numLeft / numRight;				
				break;
			case "^":
				result = Math.pow(numLeft, numRight);
				break;
			default:
				System.out.println("Something went wrong");
				break;				
		}
		// After we have performed the arithmetic operation
		// on top two elements of the Numbers Stack, we are 
		// pushing the result on top of the Number stack
		numberStack.push(Double.toString(result));
		
		return result;
	}

	/**
   	* This method tells which operator has higher precedence.    
   	*
   	* @param    first    first operator
   	* 			second   second operator
   	* 
   	* @return   		  If first operator's precedence is higher than
   	* 					  second operator then return true else return false
   	*/		
	public static boolean checkPrecedance(String first, String second)
	{
		int firstArg = 0,secArg = 0; // used to compare the precedence
		
		// Calculate the precedence value of first operator
		for(int i = 0; i < precedence.length; i++)
		{
			if(precedence[i].equals(first))
			{
				firstArg = i;
				break;
			}
		}
		
		// Calculate the precedence value of second operator
		for(int i = 0; i < precedence.length; i++)
		{
			if(precedence[i].equals(second))
			{
				secArg = i;
				break;
			}
		}		
		
		// if precedence of first operator higher than second then return true
		if(firstArg > secArg)
		{
			return true;
		}
		
		return false;
	}

	/**
   	* This is the core method which takes the decision on how to evaluate the
   	* input expression    
   	*
   	* @param    args    command line arguments are used as an input expression
   	* 
   	* @return   		Result of the evaluated expression
   	*/
	public static double evaluateExpression(String arg[])
	{
		double calculatedValue = 0; // Stores intermediate results of expr 
		
		// Prints invalid expression if expression starts or ends
		// with a operator (e.g. expressions like: + 2 or 2 +)
		int lastElement = arg.length - 1;
		if (!arg[0].matches( "[0-9]+") || !arg[lastElement].matches( "[0-9]+"))
		{
			System.out.println("Invalid Expression");
			return 0;
		}
		
		// Loops through the entire expression to get the result		
		for (String input : arg)
		{	
			// Just after pushing the "(" on top of stack we can directly
			// push the next operator without going through the operator
			// precedence code.
			if((!operatorStack.isEmpty()) && operatorStack.peek().equals("(") 
					&& validOperator(input))
				operatorStack.push(input);
			
			// We have encountered the closing bracket so loop through 
			// both the stacks to perform the arithmetic operation till 
			// opening bracket is found on top of the stack
			else if(input.equals(")"))
			{
				while(!operatorStack.peek().equals("("))
				{					
					calculatedValue = calculateExpression();									
				}
				operatorStack.pop();												
			}
			else
			   calculatedValue = mainCalculator(input);						
		}
		
		// This is the final loop which checks if there are any elements on the
		// stacks, perform the arithmetic operation on every element one by one
		while (!(operatorStack.isEmpty() || numberStack.isEmpty()))
		{
			calculatedValue = calculateExpression();			
		}
		
		// This just checks if we have only one number in expression, print it.
		if(operatorStack.isEmpty()&& !numberStack.isEmpty() && arg.length == 1 )
			calculatedValue = Double.parseDouble(arg[0]);
		
		return calculatedValue;
	}

	/**
   	* mainCalculator function checks the incoming operator precedence and then
   	* takes decision whether to push the operator or to perform arithmetic
   	* operations    
   	*
   	* @param    input   an element of an expression
   	* 
   	* @return   		Result of the evaluated expression
   	*/	
	public static double mainCalculator(String input)	
	{
		double calculatedValue = 0;
		boolean isNumber = input.matches( "[0-9]+");
		
		if (isNumber)
			numberStack.push(input);	
		
		else if(validOperator(input))
		{
			// Used to compare operator precedence
			boolean checkPrec = false;  
			
			// While operator stack is not empty perform the operations
			while(!operatorStack.isEmpty())
			{
				String topOfStack = operatorStack.peek();
				checkPrec =	checkPrecedance(input, topOfStack);
				
				// If incoming operator precedence is higher just push
				// the operator on top of the Operators stack
				if(checkPrec)
				{
					operatorStack.push(input);
					break;
				}
				// If the operator on the top of the Operators stack  
				// has lower priority than the incoming operator, then  
				// we perform arithmetic operation between the top two  
				// elements of Number stack and the top operator on the
				// Operators stack.
				else
				{																	
					calculatedValue   = calculateExpression();							
				}
			}
			
			// This is the first operator so push on to the stack
			if(operatorStack.isEmpty())
				operatorStack.push(input);				
		}
		else
		{
			System.out.println("Invalid Expression");
			System.exit(0);
		}
		return calculatedValue;
	}

	/**
   	* This function checks if the input operator is a valid operator or not    
   	*
   	* @param    input   an element of an expression
   	* 
   	* @return   		True if a valid operator is passed else returns false
   	*/	
	public static boolean validOperator(String input)
	{
		// Check if the input passed is the valid operator
		if(input.equals("+") || input.equals("-") || input.equals("%")
		    || input.equals("/") || input.equals("*") || input.equals("^")
			|| input.equals("("))
		{
			return true;		
		}
		else
			return false;
	}
}
