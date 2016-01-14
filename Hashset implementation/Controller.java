import java.io.IOException;
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
public class Controller implements Runnable
{
	//Object of model of Connect4Field(Back end Logic of game)
	private  Connect4FieldModel connect4FieldObj;
	
	//Object of view of Connect4Field(to display)
	private  View viewObj;	
	
	private int playerId; 
	
	private String playerName;	
	
	private static Object lock = new Object();
	
	private static int sequence = 0;	
	
	//Parameterized Constructor
	public Controller(Connect4FieldModel objConnect4Field, View objView, int playerId, String playerName) 
	{
		this.connect4FieldObj = objConnect4Field;
		this.viewObj = objView;
		// this.input = input;
		this.playerName = playerName;
		//this.namePlayer = input.next().toString();
		this.playerId = playerId;
	}
	
	public void run(){
						
		try {
			play();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	/**
	 * This method contains the logic of how the game is played
	 * 
	 * @param	None
	 * 
	 * @return	None
	 * @throws IOException 
	 */
	public void play() throws IOException 
	{					
		char gamePiece;		
		//New players are created here
		if(this.playerId == 0){
			gamePiece = '+';
		}
		else if (this.playerId == 1){
			gamePiece = '*';
		}
		else if(this.playerId == 2){
			gamePiece = '$';
		}
		else{
			gamePiece = '&';
		}
			
		//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		//message = bufferedReader.readLine();
		connect4FieldObj.playerArrayInterface[this.playerId] = new 
				Player(connect4FieldObj, this.playerName, 
						gamePiece);
		
		////This loop executes till the time game gets a draw or a win 
		do
    	{	
			synchronized(lock){
				if(this.playerId == sequence)
				{								
		    		viewObj.display(connect4FieldObj.printConnect4FieldBoard(),this.playerId);
		    		int column = viewObj.getInput(connect4FieldObj.playerArrayInterface[this.playerId].getName() 
		    				+" Enter Column number:",this.playerId);
		    		
		    		//Takes column number from player to drop game piece
		    		//int column = Integer.parseInt(bufferedReader.readLine());
		    		
		    		/*
		    		 * If game piece can be dropped then drop the game piece at that
		    		 * column
		    		 */
		    		if(connect4FieldObj.checkIfPiecedCanBeDroppedIn(column))
		    		{
		    			connect4FieldObj.dropPieces(column, connect4FieldObj.
		    					playerArrayInterface[this.playerId].getGamePiece());
		    		}	
		    		
		    		//Checks if last move wins by calling a method of Conect4FieldModel
		    		if(connect4FieldObj.didLastMoveWin())
		    		{
		    			//If wins then displays the board and a message by calling 
		    			//method of view and game gets over
		    			viewObj.display(connect4FieldObj.printConnect4FieldBoard(),this.playerId);
		    			viewObj.display(connect4FieldObj.playerArrayInterface[this.playerId].
		    					getName() +	" Won the Game!!",this.playerId);
		    			lock.notifyAll();		    			
		    			break;
		    		} 
		    		sequence++;
		    		if(sequence > 3)
		    			sequence = 0;
				}
				else{
					lock.notifyAll();
					try {
						lock.wait();
						//Checks if last move wins by calling a method of Conect4FieldModel
			    		if(connect4FieldObj.didLastMoveWin())
			    		{
			    			//If wins then displays the board and a message by calling 
			    			//method of view and game gets over
			    			viewObj.display(connect4FieldObj.printConnect4FieldBoard(),this.playerId);
			    			viewObj.display(connect4FieldObj.playerArrayInterface[sequence].
			    					getName() +	" Won the Game!!",this.playerId);
			    			connect4FieldObj.initConnect4FieldBoard();
			    			break;
			    		}
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}
			}
    	}	
		//Do loop will execute again if game is not a draw or a win
		while(!(connect4FieldObj.isItaDraw())); 
	
	}
}
