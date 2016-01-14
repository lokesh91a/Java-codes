/* 
 * StorageFixed.java
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This is a fixed size linked list
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * Class StorageFixed implements the interface Storage and 
 * hence implements all its methods
 */

public class LLOfAllCharacters
{
	//To store the address of the last node
	Node endNode;
	
	//To store the address of the first node
	Node startNode;
	
	//A node to keep a track while inserting extra node
	Node indexNode;
	
	//This counts the no of nodes present in the linked list
	int noOfNodes = 0;
		
		// Appends the specified element to the end of this storage. Returns 
		// true, if the element could be added, else false
	/**
	 * This method creates a new node in the link list and sets its value as e.
	 * If new node is added successfully then it returns true, else false
	 * 
	 * @param	e  value of new node.
	 * 
	 * @return	true  if new node is added successfully
	 * 			false if new node cannot be added
	 */	
	public boolean	add(int frequency, String character)
	{
		/**
		 * If no of nodes are zero i.e if link list is empty then first node
		 * is created
		 */
		if(noOfNodes==0)
		{
			startNode = new Node(1, character);
			
			//end node is set as start node since there is only one node
			endNode = startNode;
			
			//no of nodes has been incremented
			noOfNodes++;
			
			//true is returned
			return true;
		}
		
		/**
		 * If number of nodes is less than 100 then again new node is created 
		 * and its reference is set as end node to keep a track of the last 
		 * node 
		 */
		else
		{
			
			//new node is created in the last
			endNode.right = new Node(1, character);
			
			//new node created is set as end node
			endNode = endNode.right;
			noOfNodes++;
			return true;
		}
	}
	
	public boolean search(String b)
	{
		indexNode = startNode;
		if(noOfNodes==0)
		{
			add(1, b);
		}
		
		else
		{
			while(indexNode!=null)
			{
				if(indexNode.character.equals(b))
				{
					indexNode.frequency++;
					return true;
				}
					
				else
					indexNode = indexNode.right;
			}
			
			add(1,b);
		}
		return false;
	}

	
		/**
	 * This method returns the value of the first node
	 * @param	None
	 * 
	 * @return	E  value of first node
	 */	
	public String firstElement()
	{
		return startNode.character;
	}
	
	/**
	 * This method returns the value of the node at a particular index
 	 * @param	index  targeted node
	 * 
	 * @return	E  value of targeted node
	 */	
	public String get(int index)
	{	
		//index node is set as first node
		indexNode=startNode;
		
		/**
		 * If number of nodes is 0 or index passed is greater than no. of nodes
		 * then a message is printed and null is returned
		 */
		if(noOfNodes==0||index>noOfNodes)
		{
			System.out.println("No nodes exist");
			return null;
		}
		
		//If index is the last node then its value is returned
		else if(index==noOfNodes)
		{
			return endNode.character;
		}
		
		//Value is returned at a particular index
		else
		{	
			//This loop executes till the time it reaches the particular index
			//and then returns the value of that node
			while(index!=1)
			{
				indexNode=indexNode.right;
				index--;
			}
					
			return indexNode.character;
		}
	}

	/**
	 * This method returns the value of the last node
	 * @param	None
	 * 
	 * @return	E  value of first node
	 */	
	public String lastElement()
	{
		return endNode.character;
	}
	
	public void sort(Node startingNode)
	{
		int tempint;
		String tempStr;
		Node indexNode = startNode;	
		for(int i=0; i< noOfNodes; i++)
		{
			indexNode = startNode;
			for(int j=i;j<noOfNodes-1;j++)
			{
				//if(j!=i)
				
				if(indexNode.frequency > indexNode.right.frequency)
				{
					tempint = indexNode.frequency;
					tempStr = indexNode.character;
					indexNode.frequency = indexNode.right.frequency;
					indexNode.character = indexNode.right.character;
					indexNode.right.frequency = tempint;
					indexNode.right.character = tempStr;					
				}
				indexNode = indexNode.right;
			}
		}
	}
	
	public String searchNode(String p)
	{
		Node indexNode = startNode;
		String toBeSearched = p;
		int n=0;
		
		while( n<noOfNodes)
		{
			if(toBeSearched.equals(indexNode.byteCode))
			{
				
				return indexNode.character;
			}
			else
				indexNode = indexNode.right;
				n++;
		}
		return "not found";
		
	}
	
	
}
