/* 
 * Calculator.java 
 * 
 * Version: 
 *     $1$ 
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
	public static String precedence [] = {"+","-","%","*","/"};	
	
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
   	* @param    first    first operand
   	* 			oper	 Operator of expression
   	* 			second   second operand
   	* 
   	* @return   		  Returns the result of maths operation between two arg
   	*/	
	public static double calculateExpression(double first, String oper, 
											 double second)
	{
		double result = 0;  // Stores the calculated result
		
		// The below code checks which operation needs to be performed on two
		// input operators
		switch(oper)
		{
			case "+":
				result = first + second;
				break;
			case "-":
				result = first - second;
				break;
			case "%":
				result = first % second;
				break;
			case "*":
				result = first * second;
				break;
			case "/":
				if (first == 0.0)
				{
					System.out.println("Divide by 0 is not a allowed");
					System.exit(0);
				}				
				result =  first / second;				
				break;
			default:
				System.out.println("Something went wrong");
				break;				
		}
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
		if (arg[0].equals("+") || arg[0].equals("-") || arg[0].equals("%")
			|| arg[0].equals("*") || arg[0].equals("/") 
			|| arg[lastElement].equals("+") || arg[lastElement].equals("-") 
			|| arg[lastElement].equals("%") || arg[lastElement].equals("*") 
			|| arg[lastElement].equals("/"))
		{
			System.out.println("Invalid Expression");
			return 0;
		}
		
		// Loops through the entire expression to get the result		
		for (String input : arg)
		{	
			boolean isNumber = input.matches( "[0-9]+");
			
			if (isNumber)
				numberStack.push(input);	
			
			else if(input.equals("+") || input.equals("-") || input.equals("%")
			        || input.equals("/") || input.equals("*"))
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
						String topOperator = operatorStack.pop();
						double numRight= Double.parseDouble(numberStack.pop());														    
						double numLeft = Double.parseDouble(numberStack.pop());														
						double result   = calculateExpression(numLeft, 
													    topOperator, numRight);
						// After we have performed the arithmetic operation
						// on top two elements of the Numbers Stack, we are 
						// pushing the result on top of the Number stack
						numberStack.push(Double.toString(result));							
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
		}
		
		// This is the final loop which checks if there are any elements on the
		// stacks, perform the arithmetic operation on every element one by one
		while (!(operatorStack.isEmpty() || numberStack.isEmpty()))
		{
			String topOperator = operatorStack.pop();
			double numRight = Double.parseDouble(numberStack.pop());
			double numLeft = Double.parseDouble(numberStack.pop());
			calculatedValue = calculateExpression(numLeft, topOperator, 
												  numRight);
			numberStack.push(Double.toString(calculatedValue));
		}
		
		// This just checks if we have only one number in expression, print it.
		if(operatorStack.isEmpty()&& !numberStack.isEmpty() && arg.length == 1 )
			calculatedValue = Double.parseDouble(arg[0]);
		
		return calculatedValue;
	}	
}
