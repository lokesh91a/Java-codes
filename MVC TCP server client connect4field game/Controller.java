import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
 * Class Controller controls the whole game. It contacts to both model and view
 */
public class Controller implements Runnable {
	// Object of model of Connect4Field(Back end Logic of game)
	private Connect4FieldModel connect4FieldObj;

	// Object of view of Connect4Field(to display)
	private View viewObj;

	private Player[] playerObj;
	private int noOfPlayers;
	private PrintWriter[] out2, out4;
	private BufferedReader[] in2, in4;

	// Parameterized Constructor
	public Controller(Connect4FieldModel objConnect4Field, View objView,
			int noOfPlayers, Player[] playerObj) {
		this.connect4FieldObj = objConnect4Field;
		this.viewObj = objView;
		this.playerObj = playerObj;
		this.noOfPlayers = noOfPlayers;
		this.out2 = new PrintWriter[2];
		this.in2 = new BufferedReader[2];
		this.out4 = new PrintWriter[4];
		this.in4 = new BufferedReader[4];
	}

	public void run() {
		try {
			play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Game Over");
		}
	}

	/**
	 * This method contains the logic of how the game is played
	 * 
	 * @param None
	 * 
	 * @return None
	 * @throws IOException
	 */
	public void play() throws IOException {
		if (noOfPlayers == 2) {
			// This loop is used to initialize the players with their name,
			// game piece
			for (int index = 0; index < noOfPlayers; index++) {
				// New players are created here
				connect4FieldObj.playerArrayInterface2[index] = playerObj[index];
				out2[index] = new PrintWriter(
						connect4FieldObj.playerArrayInterface2[index]
								.getSocket().getOutputStream(), true);
				in2[index] = new BufferedReader(new InputStreamReader(
						connect4FieldObj.playerArrayInterface2[index]
								.getSocket().getInputStream()));
			}

			while (!(connect4FieldObj.isItaDraw())) {
				for (int index = 0; index < noOfPlayers; index++) {
					out2[index].println(connect4FieldObj
							.printConnect4FieldBoard()
							+ "\n"
							+ connect4FieldObj.playerArrayInterface2[0]
									.getName() + " Enter Column number:");
					int data = Integer.parseInt(in2[index].readLine());

					if (connect4FieldObj.checkIfPiecedCanBeDroppedIn(data)) {
						connect4FieldObj.dropPieces(data,
								connect4FieldObj.playerArrayInterface2[index]
										.getGamePiece());
					}

					// Checks if last move wins by calling a method of
					// Conect4FieldModel
					if (connect4FieldObj.didLastMoveWin()) {
						// If wins then displays the board and a message by
						// calling
						// method of view and game gets over
						for (int all = 0; all < noOfPlayers; all++) {
							out2[all].println(connect4FieldObj
									.printConnect4FieldBoard());
							out2[all]
									.println(connect4FieldObj.playerArrayInterface2[index]
											.getName() + " Won the Game!!");
						}
						connect4FieldObj.playerArrayInterface2[0].getSocket()
								.close();
						connect4FieldObj.playerArrayInterface2[1].getSocket()
								.close();
					}
				}
			}
		}
		if (noOfPlayers == 4) {
			// This loop is used to initialize the players with their name,
			// game piece
			for (int index = 0; index < noOfPlayers; index++) {
				// New players are created here
				connect4FieldObj.playerArrayInterface4[index] = playerObj[index];
				out4[index] = new PrintWriter(
						connect4FieldObj.playerArrayInterface4[index]
								.getSocket().getOutputStream(), true);
				in4[index] = new BufferedReader(new InputStreamReader(
						connect4FieldObj.playerArrayInterface4[index]
								.getSocket().getInputStream()));
			}

			while (!(connect4FieldObj.isItaDraw())) {
				for (int index = 0; index < noOfPlayers; index++) {
					out4[index].println(connect4FieldObj
							.printConnect4FieldBoard()
							+ "\n"
							+ connect4FieldObj.playerArrayInterface4[index]
									.getName() + " Enter Column number:");
					int data = Integer.parseInt(in4[index].readLine());

					if (connect4FieldObj.checkIfPiecedCanBeDroppedIn(data)) {
						connect4FieldObj.dropPieces(data,
								connect4FieldObj.playerArrayInterface4[index]
										.getGamePiece());
					}

					// Checks if last move wins by calling a method of
					// Conect4FieldModel
					if (connect4FieldObj.didLastMoveWin()) {
						// If wins then displays the board and a message by
						// calling
						// method of view and game gets over
						for (int all = 0; all < noOfPlayers; all++) {
							out4[all].println(connect4FieldObj
									.printConnect4FieldBoard());
							out4[all]
									.println(connect4FieldObj.playerArrayInterface4[all]
											.getName() + " Won the Game!!");
						}
						connect4FieldObj.playerArrayInterface2[0].getSocket()
								.close();
						connect4FieldObj.playerArrayInterface2[1].getSocket()
								.close();
						connect4FieldObj.playerArrayInterface2[2].getSocket()
								.close();
						connect4FieldObj.playerArrayInterface2[3].getSocket()
								.close();
					}

				}
			}
		}
	}
}
