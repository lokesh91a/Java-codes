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
class Node<E>
{
	Node<E> left;	// Points to the left Node 
	E value;		// Store the data 
	Node<E> right;	// Points to the right Node
	Node(){}		
	
	// Initializing the Node using constructor
	Node(E value)
	{
		this.left = null;
		this.value = value;
		this.right = null;
	}
}
