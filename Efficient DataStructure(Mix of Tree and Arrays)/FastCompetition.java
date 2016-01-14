/* 
 * FastCompetition.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This program creates a generic storage class to store elements in most 
 * efficient way so that retrieval and removal of elements is fast
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */

public class FastCompetition<E> implements Competition<E>{
	private Node<E> startNode = null; // Root Node
	private Node<E> currentNode = null;	  // Current node
	private int size = 0;		  // size of the Dynamic list
	private Object sortedArray[];	 // Stores the sorted list
	private int arrayIndex = 0;		 // Current Index of array
	private final int MAX_STORAGE; // Defines the maximum storage

	// Default constructor. We are initializing the max storage to some number
	// here.
	public FastCompetition()
	{
		MAX_STORAGE = 100000;
	}
	
	// Parameterized constructor. Initial max storage with the args 
	// passed by the user
	public FastCompetition(int maxSize)
	{
		MAX_STORAGE = maxSize;
	}
	
	/**
   	* Adds the elements passed to the dynamic storage.   
   	*
   	* @param    e    generic type that needs to be stored
   	* 
   	* @return   	 True if element is successfully added
   	*/	
	@Override
	public boolean add(E e) {
		
		// First check we are within limit of maximum storage
		if(size < MAX_STORAGE)
		{
			// Check if this is the first element added i.e list is not empty
			if (startNode == null)
			{
				startNode = new Node<E>(e);
				currentNode = startNode;
				size = size + 1;
			}
			else
			{
				// We are storing elements in a tree like structure.
				Node<E> temp = new Node<E>(e);
				currentNode = startNode;
				while(true)
				{	
					// If the new element added is greater than the root node
					// then place this element on the right side of the tree.
					if(currentNode.value.toString().
							compareTo(temp.value.toString()) < 0)
					{
						if(currentNode.right == null)
						{
							currentNode.right = temp;
							currentNode = currentNode.right;
							size = size + 1;							
							break;
						}
						currentNode = currentNode.right;
					}

					// If the new element added is less than the root node
					// then place this element on the left side of the tree.
					else if(currentNode.value.toString().
							compareTo(temp.value.toString()) > 0)
					{
						if(currentNode.left == null)
						{
							currentNode.left = temp;
							currentNode = currentNode.left;
							size = size + 1;							
							break;
						}
						currentNode = currentNode.left;
					}
				}
			}
		}
		else
		{
			System.out.println("You have exceeded the Max buffer size");
			return false;
		}
		
		// return true as we have successfully added the element
		return true;
	}
	
	/**
   	* Checks whether a particular element is present in dynamic storage.   
   	*
   	* @param    o    object type that needs to be checked for presence
   	* 
   	* @return   	 True if element is present in the storage
   	*/
	@Override
	public boolean contains(Object o) {
		
		String temp = o.toString();
		int result = 0;
		
		currentNode = startNode;
			
		// loops through the tree to find if object is found or not
		while(currentNode != null)
		{			
			// Compares the incoming object with the elements in the tree
			result = temp.compareTo(currentNode.value.toString());
			if(result == 0)	
			{	
				// We found the element in the tree so return true
				return true;
			}
			else if(result < 0)
			{
				// The Object we are looking for is less than the current
				// current comparing element so move left of the binary tree
				currentNode = currentNode.left;
			}
			else
			{				
				// The Object we are looking for is greater than the current
				// current comparing element so move right of the binary tree
				currentNode = currentNode.right;
			}								
		}
				
		// We do not found the object that is being searched so return false.
		return false;		
	}

	/**
   	* This function removes the element from the dynamic data structure   
   	*
   	* @param    o    object type that needs to be removed
   	* 
   	* @return   	 True if element is removed successfully
   	*/	
	@Override
	public boolean remove(Object o) {
		
		// If start reference is not null, this means we have elements in tree
		if(startNode != null)
		{
			currentNode = startNode;
			
			String temp = o.toString();			
			Node<E> endParent = currentNode;
			
			// loop through the tree to find the element to be removed
			while(temp.compareTo(currentNode.value.toString()) != 0)
			{			
				endParent = currentNode;
				 if(temp.compareTo(currentNode.value.toString()) < 0)
				{
					if(currentNode.left == null)
					{	
						break;
					}
					currentNode = currentNode.left;
				}
				else if(temp.compareTo(currentNode.value.toString()) > 0)
				{
					if(currentNode.right == null)
					{			
						 break;
					}
					currentNode = currentNode.right;
				}
			}
			while(currentNode != null)
			{	
				// If start Node is same as currentNode, this means remove
				// current root node
				if(startNode == currentNode)
				{
					// Make one temporary root node and act as we are deleting
					// normal node other than root node
					endParent = new Node<E>();
					endParent.left = currentNode;
				}
				
				// Check if the node we want to delete has both left and right
				// Nodes
				if(currentNode.left != null && currentNode.right != null)
				{									
					Node<E> focusParent = currentNode.right;
					Node<E> focusRight = null;
					
					// If the current node that is to  be deleted has a right
					// node but right nodes left is not present. In this case
					// we are copying the value of its child node to the node
					// to be deleted and then adjusting links
					if(currentNode.right.left == null){	
						currentNode.value = focusParent.value;
						currentNode.right = focusParent.right;
						focusParent.right = null;
						size = size - 1;
						return true;
					}
					
					// If current node's right node has a left node, move to 
					// the extreme left node
					focusRight = currentNode.right.left;
					while(focusRight.left != null)
					{
						focusParent = focusRight;
					
						focusRight = focusRight.left;
					}
						// Now copy the value of extreme left node to the node
						// that is to be deleted and then adjust links
						currentNode.value = focusRight.value;
						focusParent.left = focusRight.right;
						focusRight.right = null;
						size = size - 1;
						return true;
				}						
				
				// Check if current Node is right of its parent node
				else if(endParent.right == currentNode)
				{
					// If currentNode's left tree is not present, then 
					// make  parent node's right pointing to right tree and 
					// break the link of the node to be deleted					
					if(currentNode.left == null){
						endParent.right = currentNode.right;
						currentNode.right = null;
						size = size - 1;
						return true;
					}
					// Else if no right tree is present do the same as above
					// make  parent node's right pointing to left tree and 
					// break the link of the node to be deleted
					else{
						endParent.right = currentNode.left;
						currentNode.left = null;
						size = size - 1;
						return true;
					}
				}
					
				// Check if current Node is left of its parent node
				else if(endParent.left == currentNode)
				{
					// If currentNode's left tree is not present, then 
					// make  parent node's left pointing to right tree and 
					// break the link of the node to be deleted
					if(currentNode.left == null){
						endParent.left = currentNode.right;
						currentNode.right = null;
						
					}
					
					// Else if no right tree is present do the same as above
					// make  parent node's left pointing to left tree and 
					// break the link of the node to be deleted
					else{
						endParent.left = currentNode.left;
						currentNode.left = null;
						
					}	
					// We are finally adjusting the links here and making the
					// dummy node pointing to null after the delete operation
					// is performed
					if(startNode == currentNode) {
						startNode = endParent.left;
						endParent = null;
						}
					size = size - 1;
					return true;
				}															
			}
					
			return false;
		}
		else
			return false;		
	}

	/**
   	* Returns the element from a particular index   
   	*
   	* @param    index	element present at index    
   	* 
   	* @return   E	    Returns elements from index passed
   	*/	
	@SuppressWarnings("unchecked")
	@Override
	public E elementAt(int index) {	
		
		return (E) sortedArray[index];		
	}

	/**
   	* Sorts the elements stored in the dynamic structure   
   	*
   	* @param    None	    
   	* 
   	* @return   Null	    
   	*/	
	@Override
	public Competition<E> sort() {
		arrayIndex = 0;
		sortedArray = new Object[size];
		inOrderSearch(startNode);		
		return this;
	}

	/**
   	* Search tree in indorder format so that we get the sorted list   
   	*
   	* @param    index	element present at index    
   	* 
   	* @return   E	    Returns elements from index passed
   	*/	
	public void inOrderSearch(Node<E> start)
	{		
		if (start == null)
			return;
		
		inOrderSearch(start.left);

		createSortedList(start.value);
		
		inOrderSearch(start.right);
	}
	
	/**
   	* This function adds sorted elements in the array  
   	*
   	* @param    value	element that needs to be added in the sorted list    
   	* 
   	* @return   None	
   	*/	
	public void createSortedList(E value)
	{			
		// Add sorted elements to array
		sortedArray[arrayIndex] = value;
		arrayIndex++;
	}
	
	/**
   	* This function returns the size of the dynamic storage  
   	*
   	* @param    None	    
   	* 
   	* @return   Returns size of the the dynamic storage	
   	*/		
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}
}