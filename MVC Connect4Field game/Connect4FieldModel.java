package connect4FieldMVC;
import java.util.InputMismatchException;
import java.util.Scanner;


/* 
 * Connect4FieldModel.java
 * 
 * Revisions: 
 *     $1$ 
 */


/**
 * This is a Connect4Field  game
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * Class Connect4FieldModel implements the interface Connect4FieldInterface and 
 * hence implements all its methods and it contains all the logic of MVC
 */
public class Connect4FieldModel implements Connect4FieldInterface {
	
	//Maximum Rows of the board of the game will remain final
	private final int MAX_ROWS = 10;
	
	//Maximum Columns of the board of the game will remain final
	private final int MAX_COLUMNS = 20;
	
	//For validating the success of the player after every move
	private final int SUCCESS = 1;
	
	//For calculating last move win condition 
	private int lastMoveRow = 0;
	
	//For calculating last move win condition
	private int lastMoveColumn = 0;
	
	//Parameter for checking consecutive number of game pieces of a player
	//to the left of the column in which last move was made
	public int leftHorizontalWinCount = 0;
	
	//Parameter for checking consecutive number of game pieces of a player
	//to the right of the column in which last move was made
	public int rightHorizontalWinCount = 0;
	
	//Parameter for checking consecutive number of game pieces of a player
	//to the south of the row in which last move was made
	public int verticalColumnWinCount = 0;
	
	//Parameter for checking consecutive number of game pieces of a player
	//to the south-east of the row in which last move was made
	public int diagonalDownRightWinCount = 0;
	
	//Parameter for checking consecutive number of game pieces of a player
	//to the south-west of the row in which last move was made
	public int diagonalDownLeftWinCount = 0;
	
	//Parameter for checking consecutive number of game pieces of a player
	//to the north-east of the row in which last move was made
	public int diagonalUpRightWinCount = 0;
	
	//Parameter for checking consecutive number of game pieces of a player
	//to the north-west of the row in which last move was made
	public int diagonalUpLeftWinCount = 0;
	
	
	public PlayerInterface playerArrayInterface[] = new Player[2];
	
	//2-D String Array for drawing the game board and displaying on it
	private String connect4FieldBoard[][] = new String[MAX_ROWS][MAX_COLUMNS];
	
	/**
	 * Default Constructor
	 * 
	 * Another method is called to initialize the game board
	 */
	public Connect4FieldModel()
	{
		initConnect4FieldBoard();
	}
	
	/**
	 * This method initializes the game board by updating every place in 2-D
	 * Array to a value " " and when it is done then this method calls an 
	 * another method fillConnect4FieldBoard(0)
	 * 
	 * @param	None
	 * 
	 * @return	None
	 */
	public void initConnect4FieldBoard()
	{
		//loop for initializing all rows
		for (int row = 0; row < MAX_ROWS; row++)
		{
			//loop for initializing all the columns in a particular row
			for(int column = 0; column < MAX_COLUMNS; column++)
			{
				//initializing with a value = " "
				connect4FieldBoard[row][column] = " ";
			}			
		}
		//This method is called after initializing the game board
		fillConnect4FieldBoard(0);
	}
	
	/**
	 * This is a recursive function for updating all the places in the game
	 * board by a char "o" where player can make a move. 
	 *  
	 * @param	row		number of rows in the game board
	 * 
	 * @return	None
	 */
	public void fillConnect4FieldBoard(int row)
	{
		//Base condition of the recursive function
		if(row > MAX_ROWS-1)
			return;
		
		/*
		 * Fill the following columns with value "o"
		 * from column = current row number to column = maximum number of 
		 * columns - 1
		 */
		for(int column = row; column < MAX_COLUMNS - row; column++)
		{
			connect4FieldBoard[row][column] = "o";
		}
		
		//Method fillConnect4FieldBoard is called again with the row number
		//greater than 1 from its last value
		fillConnect4FieldBoard(row+1);			
	}
	
	/**
	 * This method prints the game board made by the method 
	 * "fillCoonect4FieldBoard"
	 *  
	 * @param	None
	 * 
	 * @return	String It returns board as a string
	 */
	public String printConnect4FieldBoard()
	{
		String board = "";
		
		//This loop executes once for every row
		for (int row = 0; row < MAX_ROWS; row++)
		{
			//This loop executes for all columns for each row
			for(int column = 0; column < MAX_COLUMNS; column++)
			{
				//Stores the value in board of the particular index of the array
				board = board + connect4FieldBoard[row][column];
				
			}
			//For jumpng to the enxt line
			board = board + "\n";
		}
	return board;
	}
	
	/**
	 * This method checks if a player can drop a game piece at a particular 
	 * column or not. If not then auto generated message is displayed that 
	 * "The piece cannot be dropped at this particular column"
	 * 
	 * @param	column	Column number in which piece is dropped
	 * 
	 * @return	boolean	true: if piece can be dropped
	 * 					false: if piece can not be dropped
	 */
	public boolean checkIfPiecedCanBeDroppedIn(int column)
	{
    		/*
    		 * Below if checks for the following conditions:
    		 * 1.Dropped Column should be in the range of game board
    		 * 2.Element at [row][column] should be equal to "o" and "+" and
    		 *   "*"
    		 * If above conditions are true then if returns true
    		 */
    		if(column >=0 && column < MAX_COLUMNS && connect4FieldBoard[0]
    				[column].equals("o"))
    		{
    			return true;
    		}
    	
    	//If any one mentioned condition fails then this method returns false
    	System.out.println("The Piece cannot be dropped at column: " + column);
		return false;
	}
	
	/**
	 * This method drops the game piece of the player till the level it can be
	 * dropped. It starts by checking from the bottom. If at any row
	 * (corresponding to the column) it finds "o" then it drops the game piece
	 * 
	 * @param	column	Column number in which piece is dropped
	 * 			gamePiece game piece assigned to the player(out of two)
	 * 
	 * @return	None
	 */
    public void dropPieces(int column, char gamePiece)
    {
    	if(checkIfPiecedCanBeDroppedIn(column))
    	{	
    		//Checking the below condition every row
	    	for(int row = MAX_ROWS-1; row >= 0; row--)
	    	{
	    		//When it finds "o"
	    		if(connect4FieldBoard[row][column].equals("o"))
	    		{	
	    			//game piece is dropped at this position
	    			connect4FieldBoard[row][column] = 
	    					Character.toString(gamePiece);
	    			
	    			/*
	    			 * This comes in play when a human plays against a computer
	    			 * and for checking if this move wins
	    			 */
	    			lastMoveRow = row;
	    			lastMoveColumn = column;	    			
	    			break;
	    		}
	    	}
    	}
    }
    
    /**
	 * This method checks whether last move win! by calling other methods
	 * 
	 * @param	None
	 * 
	 * @return	true: If last move wins
	 * 			false: If last move doesn't win 
	 */
    public boolean didLastMoveWin()
    {
    	
    	if(checkVerticalWin())
    		return true;
    	else if(checkHorizontalWin())
    		return true;
    	else if(checkDiagonalWin())
    		return true;
    	
    	//If he doesn't win then false is returned
    	else
    		return false;
    }
    
    /**
	 * This method checks if last move of a player gets a win by checking 
	 * presence of consecutive 4 game pieces in any diagonal
	 * 
	 * @param	None
	 * 
	 * @return	true: If player gets 4 consecutive game pieces diagonally(in 
	 * 				  any one out of two)
	 * 			false: If player doesn't get 4 consecutive game pieces
	 * 				   diagonally(in any one out of two)
	 */
    public boolean checkDiagonalWin()
    {
    	int winCount = 0;
    	int row = 0;
    	int column = lastMoveColumn;
    	diagonalDownRightWinCount = 0;
    	diagonalDownLeftWinCount = 0;
    	diagonalUpRightWinCount = 0;
    	diagonalUpLeftWinCount = 0;    	
    	// Going down Right search
    	
    	//This loop checks for the consecutive 3 game pieces in the north east
    	//direction
		for(row = lastMoveRow; row > 0 && column < MAX_COLUMNS-1; row--)
		{
			if(connect4FieldBoard[row][column].equals(" "))
				continue;
			
			/*
			 * If it gets a last moved game piece then it increments win count 
			 * and if win count becomes 3 then player wins 
			 */
    		if(connect4FieldBoard[row][column].
    				equals(connect4FieldBoard[row-1][column+1]))
    		{
    			winCount++;
    			if(winCount == 3)
    				return true;	    			
    		}
    		
    		/*
    		 *If at any point of time it gets anything apart from last moved 
    		 *game piece then this loop breaks and then it
    		 *starts searching in other direction  
    		 */
    		else
    			break;
    		column++;
		}
		
    	diagonalUpRightWinCount = winCount;
    	
    	// going up Left Search
    	winCount = 0;
    	row = 0;
    	column = lastMoveColumn;
    	
    	/*
    	 * This loop checks for the consecutive 3 game pieces in the north west
    	 * direction
    	 */
    	
    	for(row = lastMoveRow; row < MAX_ROWS-1 && column > 0; row++)
		{
    		if(connect4FieldBoard[row][column].equals(" "))
				continue;
    		
    		/*
    		 * If it gets a last moved game piece then it increments win count
    		 * and if win count becomes 3 then player wins
    		 */
    		if(connect4FieldBoard[row][column].
    				equals(connect4FieldBoard[row+1][column-1]))
    		{
    			winCount++;
    			if(winCount == 3)
    				return true;	    			
    		}
    		
    		/*
    		 *If at any point of time it gets anything apart from last moved 
    		 *game piece then this loop break  and then it
    		 *starts searching in other direction  
    		 */
    		else
    			break;
    		column--;
		}
    	diagonalDownLeftWinCount = winCount;
    	// Going down left
    	winCount = 0;
    	row = 0;
    	column = lastMoveColumn;
    	for(row = lastMoveRow; row > 0  && column > 0; row--)
		{
    		if(connect4FieldBoard[row][column].equals(" "))
				continue;
    		
    		/*
    		 * If it gets a last moved game piece then it increments win count 
    		 * and if win count becomes 3 then player wins
    		 */
    		if(connect4FieldBoard[row][column].
    				equals(connect4FieldBoard[row-1][column-1]))
    		{
    			winCount++;
    			if(winCount == 3)
    				return true;	    			
    		}
    		
    		/*
    		 *If at any point of time it gets anything apart from last moved 
    		 *game piece then this loop breaks and then it
    		 *starts searching in other direction  
    		 */
    		else
    			break;
    		column--;
		}
    	diagonalUpLeftWinCount = winCount;
    	// Going up Right
    	winCount = 0;
    	row = 0;
    	column = lastMoveColumn;
    	for(row = lastMoveRow; row < MAX_ROWS-1  && column < MAX_COLUMNS-1; 
    			row++)
		{
    		if(connect4FieldBoard[row][column].equals(" "))
				continue;
    		
    		/*
    		 * If it gets a last moved game piece then it increments win count
    		 * and if win count becomes 3 then player wins 
    		 */
    		if(connect4FieldBoard[row][column].
    				equals(connect4FieldBoard[row+1][column+1]))
    		{
    			winCount++;
    			if(winCount == 3)
    				return true;	    			
    		}
    		
    		/*
    		 *If at any point of time it gets anything apart from last moved 
    		 *game piece then this loop breaks, it returns false
    		 */
    		else
    			break;
    		column++;
		}    	
    	diagonalDownRightWinCount = winCount;
    		return false;
    }
    
    /**
	 * This method checks if last move of a player gets a win by checking 
	 * presence of consecutive 4 game pieces horizontally(in the row in which
	 * the game piece dropped)
	 * 
	 * @param	None
	 * 
	 * @return	true: If player gets 4 consecutive game pieces horizontally
	 * 			false: If player doesn't get 4 consecutive game pieces
	 * 				   horizontally
	 */
    public boolean checkHorizontalWin()
    {	
    	//initiates win count to 3
    	int winCount = 0;
    	
    	/*
    	 * rightHorizontalWinCount and leftHorizontalWinCount are calculated 
    	 * to check the win condition in which the player wins in this type
    	 * of case "++ +" and he drops the game piece at 3 column
    	 */
    	rightHorizontalWinCount = 0;
    	leftHorizontalWinCount = 0;
    	
    	// Right search
    	/* 
    	 * Loop will work from column in which last game piece was dropped
    	 * to the last column
    	 */
    	for(int column = lastMoveColumn; column < MAX_COLUMNS-1; column++)
    	{
    		if(connect4FieldBoard[lastMoveRow][column].equals(" "))
				continue;
    		
    		/* 
    		 * If in next column same game piece is found then value of win 
    		 * count is increased  by 1
    		 */
    		if(connect4FieldBoard[lastMoveRow][column].
    				equals(connect4FieldBoard[lastMoveRow][column+1]))
    		{
    			winCount++;
    			
    			/*
    			 * After incrementing if value of win count is equal to 3 then 
    			 * true is returned 
    			 */
    			if(winCount == 3)
    				return true;
    		}
    		
    		/*
    		 *If at any point of time it gets anything apart from last moved 
    		 *game piece then this loop breaks, and then it
    		 *starts searching in other direction  
    		 */
    		else
    			break;
    	}
    	rightHorizontalWinCount = winCount;
    	// Left Search
    	
    	/* 
    	 * Loop will work from column in which last game piece was dropped
    	 * to the first column
    	 */
    	for(int column = lastMoveColumn; column > 0; column--)
    	{	
    		if(connect4FieldBoard[lastMoveRow][column].equals(" "))
				continue;
    		
    		/* 
    		 * If in next column same game piece is found then value of win 
    		 * count is increased  by 1
    		 */
    		if(connect4FieldBoard[lastMoveRow][column].
    				equals(connect4FieldBoard[lastMoveRow][column-1]))
    		{
    			winCount++;
    			
    			/*
    			 * After incrementing if value of win count is equal to 3 then 
    			 * true is returned 
    			 */
    			if(winCount == 3)
    				return true;
    		}
    		
    		/*
    		 *If at any point of time it gets anything apart from last moved 
    		 *game piece then this loop breaks, it returns false
    		 */
    		else
    			break;
    	}
    	leftHorizontalWinCount = winCount;
    		return false;    	
    }
    
    
    /**
	 * This method checks if last move of a player gets a win by checking 
	 * presence of consecutive 4 game pieces vertically(in the column in which
	 * the game piece dropped)
	 * 
	 * @param	None
	 * 
	 * @return	true: If player gets 4 consecutive game pieces vertically
	 * 			false: If player doesn't get 4 consecutive game pieces
	 * 				   vertically
	 */
    public boolean checkVerticalWin()
    {
    	int winCount = 0;
    	verticalColumnWinCount = 0;
    	
    	/*
    	 * This loop checks for the same dropped game piece from the row in 
    	 * which it was drooped to the last row or till the time it either 
    	 * founds a win conditions or any other character as compared to the
    	 * dropped game piece
    	 */
    	for(int row = lastMoveRow; row < MAX_ROWS-1; row++)
    	{
    		if(connect4FieldBoard[row][lastMoveColumn].equals(" "))
				continue;
    		
    		/* 
    		 * If in next column same game piece is found then value of win 
    		 * count is increased  by 1
    		 */
    		if(connect4FieldBoard[row][lastMoveColumn].
    				equals(connect4FieldBoard[row+1][lastMoveColumn]))
    		{
    			winCount++;
    			
    			/*
    			 * After incrementing if value of win count is equal to 3 then 
    			 * true is returned 
    			 */
    			if (winCount >= 3)
    	    		return true;
    		}
    		
    		/*
    		 *If at any point of time it gets anything apart from last moved 
    		 *game piece then this loop breaks, it returns false
    		 */
    		else
    			break;
    	}
    	verticalColumnWinCount = winCount;
    	return false;    	
    }
    
    /**
	 * This method checks if it is a draw 
	 * 
	 * @param	None
	 * 
	 * @return	true: If  game is draw
	 * 			false: If game is still on
	 * 
	 */
    public boolean isItaDraw()
    {		
    	/*
    	 * This loop goes from column = 0 to last column for only row = 0
    	 */
		for(int column = 0; column < MAX_COLUMNS; column++)
		{	
			//If there is any place to drop any game piece then return false 
			if(connect4FieldBoard[0][column].equals("o"))
				return false;
		}
		 
		System.out.println("It's a Draw!! ");
    	return true;
    }
    
    public String toString()
    {
    	printConnect4FieldBoard();
    	return "";
    }
    
	
    
    public int calculateNextMove()
    {    	
    	boolean alreadyDropped = false;
    	
    	// Check if user is winning from left horizontal side of board.
    	if(leftHorizontalWinCount >= 1)
    	{
    		// Try to fit Computer's game piece at the right hand side of the 
    		//board if there is a place
    		lastMoveColumn = lastMoveColumn + 1;
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
    			   connect4FieldBoard[lastMoveRow][lastMoveColumn].equals("o"))
    			alreadyDropped = true; 
    		
    		// We do not found the place to fit the game piece so restore the
    		//lastMoveColum pointer  
    		else
    			lastMoveColumn = lastMoveColumn - 1;
    	}
    	
    	// Check if user is winning from right horizontal side of board.
    	if(rightHorizontalWinCount >= 1 && alreadyDropped == false)
    	{
    		// Try to fit Computer's game piece at the left hand side of the 
    		//board if there is a place
    		lastMoveColumn = lastMoveColumn - 1;
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
    			   connect4FieldBoard[lastMoveRow][lastMoveColumn].equals("o"))
    			alreadyDropped = true;    			    		    		
    		else
    			// We do not found the place to fit the game piece so restore 
    			//the lastMoveColum pointer
    			lastMoveColumn = lastMoveColumn + 1;
    	}
    	
    	//Check if pattern for user is like *** and lastMoveColumn is at middle
    	if(rightHorizontalWinCount + leftHorizontalWinCount == 2 && 
    			alreadyDropped == false)
    	{
    		// check if piece can be dropped in right side of the pattern
    		lastMoveColumn = lastMoveColumn + rightHorizontalWinCount + 1;
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
    			   connect4FieldBoard[lastMoveRow][lastMoveColumn].equals("o"))		
    			alreadyDropped = true;    			    		    		
    		else
    			// We do not found the place to fit the game piece so restore
    			//the lastMoveColum pointer
    			lastMoveColumn = lastMoveColumn - rightHorizontalWinCount - 1;
    		
    		// check if piece can be dropped in left side of the pattern
    		lastMoveColumn = lastMoveColumn - rightHorizontalWinCount - 1;
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
    			   connect4FieldBoard[lastMoveRow][lastMoveColumn].equals("o"))
    			alreadyDropped = true;    			    		    		
    		else
    			// We do not found the place to fit the game piece so restore 
    			//the lastMoveColum pointer
    			lastMoveColumn = lastMoveColumn + rightHorizontalWinCount + 1;
    		
    	}
    	
    	// User is winning using vertical column. So just add Computer's game 
    	//piece at top of users game piece
    	if(verticalColumnWinCount >= 2 && alreadyDropped == false)
    	{
    		// Check if we can drop the game piece
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn))
    		{
	    		lastMoveColumn = lastMoveColumn + 0;
	    		alreadyDropped = true;
    		}
    	}
    	
    	// Check if user is winning from right up diagonal side of board.
    	if(diagonalUpRightWinCount >= 1 && alreadyDropped == false)
    	{
    		// Try to fit Computer's game piece at the left hand side of the 
    		//board if there is a place
    		lastMoveColumn = lastMoveColumn - diagonalUpRightWinCount;
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
    				connect4FieldBoard[lastMoveRow+diagonalUpRightWinCount]
    						[lastMoveColumn].equals("o"))    		
    			alreadyDropped = true;    			    		    		
    		else
    			// We do not found the place to fit the game piece so restore 
    			//the lastMoveColum pointer
    			lastMoveColumn = lastMoveColumn + diagonalUpRightWinCount;
    	}
    	// Check if user is winning from down left diagonal side of board.
    	else if(diagonalDownLeftWinCount >= 1 && alreadyDropped == false)
    	{
    		// Try to fit Computer's game piece at the down left hand side of 
    		//the board if there is a place
    		lastMoveColumn = lastMoveColumn - diagonalDownLeftWinCount - 1;
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
    				connect4FieldBoard[lastMoveRow+diagonalDownLeftWinCount+1]
    						[lastMoveColumn].equals("o"))    		
    			alreadyDropped = true;    			    		    		
    		else
    		{
    			// We do not found the place to fit the game piece so restore 
    			//the lastMoveColum pointer
    			lastMoveColumn = lastMoveColumn + diagonalDownLeftWinCount + 1;
    		 
    			// Now try the other side of the board to see if we can fit 
    			//Computers game Piece
	    		lastMoveColumn = lastMoveColumn + 1;
	    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
	    				connect4FieldBoard[lastMoveRow - 1]
	    						[lastMoveColumn].equals("o"))
	    			alreadyDropped = true;
	    		else
	    			// We do not found the place to fit the game piece so 
	    			//restore the lastMoveColum pointer
	    			lastMoveColumn = lastMoveColumn - 1;
    		}
    	}
    	
    	else if(diagonalDownRightWinCount >= 1 && alreadyDropped == false)
    	{
    		lastMoveColumn = lastMoveColumn - diagonalDownRightWinCount;
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
    				connect4FieldBoard[lastMoveRow - 1]
    						[lastMoveColumn].equals("o"))
    			alreadyDropped = true;
    		else
    			lastMoveColumn = lastMoveColumn + diagonalDownRightWinCount;
    	}
    	
    	else if(diagonalUpLeftWinCount >= 1 && alreadyDropped == false)
    	{
    		lastMoveColumn = lastMoveColumn + diagonalUpLeftWinCount;
    		if(checkIfPiecedCanBeDroppedIn(lastMoveColumn) && 
    				connect4FieldBoard[lastMoveRow + 1]
    						[lastMoveColumn].equals("o"))
    			alreadyDropped = true;
    		else
    			lastMoveColumn = lastMoveColumn - diagonalUpLeftWinCount;
    	}
    	// User is not winning so computer will try to win.
    	if(alreadyDropped == false)
    	{
        	for(int row = MAX_ROWS-1; row >= 0; row--)
        	{
        		for(int column = 0; column < MAX_COLUMNS - 1; column++ )
        		{
        			// Find a place in the board and drop the game piece
	        		if(connect4FieldBoard[row][column].equals("o"))
	        		{	        				        			
	        			lastMoveColumn = column;
	        			break;
	        		}
        		}
        	}    		
    	}
    	return lastMoveColumn;
    }

	@Override
	public void init(PlayerInterface playerA, PlayerInterface playerB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playTheGame() {
		// TODO Auto-generated method stub
		
	}

}
