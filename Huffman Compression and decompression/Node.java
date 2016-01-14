/* 
 * Node.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This is a Node class for generating dynamic data storage 
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */
class Node
{
	Node left;	// Points to the left Node 
	String character;		// Store the data
	int frequency;
	Node right;	// Points to the right Node
	String byteCode = "";
	Node(){}		
	
	// Initializing the Node using constructor
	Node(int frequency,String character)
	{
		this.left = null;
		this.byteCode = "";
		this.character = character;
		this.frequency = frequency;
		this.right = null;
	}
	
	/**
	 * Parameterized Constructor
	 * 
	 * @param left			left pointer 
	 * @param frequency		no. of occurences
	 * @param character		which character
	 * @param right			right pointer
	 */
	Node(Node left, int frequency,String character, Node right)
	{
		this.left = left;
		this.byteCode = "";
		this.frequency = frequency;
		this.character = character;
		this.right = right;
	}
}
