package connect4FieldMVC;

/* 
 * MainClass.java
 * 
 * Revisions: 
 *     $1$ 
 */


/**
 * This is the Main Class
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * Class Connect4FieldModel implements the interface Connect4FieldInterface and
 * hence implements all its methods and it contains all the logic of MVC
 */

public class MainClass
{
	public static void main(String args[])
	{
		//Object of Connect4FieldModel is created
		Connect4FieldModel connect4FieldObj = new Connect4FieldModel();
		
		//Object of View is created
		View viewObj = new View();
		
		//Object of Controller is created and object of connect4FieldModel and
		//view is passes as an parameter
		Controller controllerObj = new Controller(connect4FieldObj, viewObj);
		
		//Play Method in controller is provoked which starts the game, end the
		//game, controls the game. Contacts model and view
		controllerObj.play();
	}
}
