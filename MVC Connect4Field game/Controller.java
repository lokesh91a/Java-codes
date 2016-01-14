package connect4FieldMVC;
import java.util.Scanner;



/* 
 * Controller.java
 * 
 * Revisions: 
 *     $1$ 
 */


/**
 * This is a controller of Connect4Field  game
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * Class Controller controls the whole game. It contacts to both model and
 * view 
 */
public class Controller
{
	//Object of model of Connect4Field(Back end Logic of game)
	private  Connect4FieldModel connect4FieldObj;
	
	//Object of view of Connect4Field(to display)
	private  View viewObj;
	
	//Parameterized Constructor
	public Controller(Connect4FieldModel objConnect4Field, View objView)
	{
		this.connect4FieldObj = objConnect4Field;
		this.viewObj = objView;
	}
	
	/**
	 * This method contains the logic of how the game is played
	 * 
	 * @param	None
	 * 
	 * @return	None
	 */
	public void play() 
	{			
		//Variable is defined to take input from user when needed
		Scanner input = new Scanner(System.in);
		
		//Method of view class is called to display a message 
		viewObj.display("Single Player or MultiPlayer: ");
		viewObj.display("Enter 1 for single palyer or Enter 2 for " +
				"MultiPlayer: ");
		
		//Input is taken from user about the number of players
		int numPlayers = input.nextInt();
		
		//Array for game piece
		char gamePieceArray[] = {'*','+'};
		
		//This loop is used to initialize the players with their name, 
		//game piece
		for (int index = 0; index < numPlayers; index++)
		{
			//Method of view class is called to display a message 
			viewObj.display("Enter the name of Player " + (index + 1));
			
			//New players are created here
			connect4FieldObj.playerArrayInterface[index] = new 
					Player(connect4FieldObj, input.next().toString(), 
							gamePieceArray[index]);
		}
		
		//If player is playing against a computer
		if(numPlayers == 1)
		{
			//Method of view class is called to display a message 			
			viewObj.display("You are playing against Computer now." );
			
			//New computer player is created with name as "Computer"
			connect4FieldObj.playerArrayInterface[1] = new 
					Player(connect4FieldObj, "Computer", 
							gamePieceArray[1]);
		}
		
		////This loop executes till the time game gets a draw or a win 
		do
    	{	
    		viewObj.display(connect4FieldObj.printConnect4FieldBoard());
    		viewObj.display(connect4FieldObj.playerArrayInterface[0].getName() 
    				+" Enter Column number:");
    		
    		//Takes column number from player to drop game piece
    		int column = Integer.parseInt(input.next().toString());
    		
    		/*
    		 * If game piece can be dropped then drop the game piece at that
    		 * column
    		 */
    		if(connect4FieldObj.checkIfPiecedCanBeDroppedIn(column))
    		{
    			connect4FieldObj.dropPieces(column, connect4FieldObj.
    					playerArrayInterface[0].getGamePiece());
    		}	
    		
    		//Checks if last move wins by calling a method of Conect4FieldModel
    		if(connect4FieldObj.didLastMoveWin())
    		{
    			//If wins then displays the board and a message by calling 
    			//method of view and game gets over
    			viewObj.display(connect4FieldObj.printConnect4FieldBoard());
    			viewObj.display(connect4FieldObj.playerArrayInterface[0].
    					getName() +	" Won the Game!!");
    			break;
    		}
    		
    		//If the other player is a computer
    		if(connect4FieldObj.playerArrayInterface[1].getName().
    				equals("Computer"))
    		{
    			//Next move is calculated by calling a method from model
    			int nextMove = connect4FieldObj.calculateNextMove();
    			
    			/*
        		 * If game piece can be dropped then drop the game piece at 
        		 * that column
        		 */
    			if(connect4FieldObj.checkIfPiecedCanBeDroppedIn(nextMove))
    			{
    				connect4FieldObj.dropPieces(nextMove, connect4FieldObj.
    						playerArrayInterface[1].getGamePiece());
    			}
    			
    			//Checks if last move wins by calling a method of 
    			//Conect4FieldModel
    			if(connect4FieldObj.didLastMoveWin())
    			{
    				//If wins then displays the board and a message by calling 
        			//method of view and game gets over
    				viewObj.display(connect4FieldObj.
    						printConnect4FieldBoard());
        			viewObj.display(connect4FieldObj.playerArrayInterface[1].
        					getName() + " Won the Game!!");
        			break;
    			}
    		}
    		
    		//Player 2 gets a move and same steps as of player 1 repeats
    		else
    		{   
    			viewObj.display(connect4FieldObj.printConnect4FieldBoard());
        		viewObj.display(connect4FieldObj.playerArrayInterface[1].
        				getName() +" Enter Column number:");
        		column = Integer.parseInt(input.next().toString());
        		
        		if(connect4FieldObj.checkIfPiecedCanBeDroppedIn(column))
        		{
        			connect4FieldObj.dropPieces(column, connect4FieldObj.
        					playerArrayInterface[1].getGamePiece());
        		}	
        		
        		if(connect4FieldObj.didLastMoveWin())
        		{
        			viewObj.display(connect4FieldObj.printConnect4FieldBoard());
        			viewObj.display(connect4FieldObj.playerArrayInterface[1].
        					getName() + " Won the Game!!");
        			break;
        		}
    		}   
    	}	
		//Do loop will execute again if game is not a draw or a win
		while(!(connect4FieldObj.isItaDraw())); 
	
	}
}
