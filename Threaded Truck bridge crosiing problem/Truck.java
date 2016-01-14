/* 
 * Truck.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */

import java.util.Random;

/**
 * This is a Truck class which is used to create Truck Object 
 *  
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */
public class Truck {

	private int weight;  // To Store weight of Truck
	private String side; // To Store from which side the truck is coming
	
	/**
   	* Constructor to initialize the Truck object   
   	*
   	* @param    side    From which side of bridge truck is coming
   	* 
   	* @return   None
   	*/
	public Truck(String side){
		this.side = side;
		
		// Each truck is initialized which random weight from 100lb to 200klb
		Random random = new Random();
		this.weight = random.nextInt(99900) + 100;
	}
	
	/**
   	* Returns the incoming location of the truck on bridge   
   	*
   	* @param    None
   	* 
   	* @return   side
   	*/
	public String getSide(){
		return side;
	}
	
	/**
   	* Returns the weight of the current truck object   
   	*
   	* @param    None
   	* 
   	* @return   weight
   	*/
	public int getWeight(){
		return weight;
	}
}
