/* 
 * CalculatorController.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * This is a Program handles the file compression algorithm
 * and view
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */
public class StringZipOutputStream  {		
		private	Node rootNode;	// Root Node of Tree
		private	Node ref1;		// Used to create huffman tree
		private Node ref2;		// Used to create huffman tree
		private Node currentNode; // current pointing Node
		
		// Stores the handle of the compressed file
		private FileOutputStream outputCompress; 
		private File originalFile; // Keeps the handle of the original file
		private BufferedWriter outputOriginal; // Used to write to temp file
		private LLOfAllCharacters LLObject;	   // Stores the characters read
		private BufferedReader readOriginalFile; // Used to read original files
		private FileInputStream fis;	// File Input stream reader
		private String line;			// Used to store line read
		private String zipCode = "";	// Used to store generated huffman code
		private byte currentByte;       // The byte that is being filled
	    private static byte numBitsWritten; // Used for packing byte data 
	    private BufferedOutputStream output; // Handle for writing output
		
		/**
	   	* Creates a new output stream with a default buffer size.   
	   	*
	   	* @param    out: Handle to file output stream
	   	* 
	   	* @return	None
	   	*/
		public StringZipOutputStream(FileOutputStream out) throws IOException
		{
			// Stores the Original file handle for later use
			originalFile = new File("original.txt");
			
			// Creates the buffered writer to save the incoming stream
			outputOriginal = new BufferedWriter(new FileWriter(originalFile));
			
			// Initialise the linked list used to store characters read
			LLObject =  new LLOfAllCharacters();
			
			// Save the handle of compressed file name
			outputCompress = out;
			
			// Initialize read line variable 
			line = null;
		}
		
		/**
	   	* Writes aStrign compressed output stream.    
	   	* This method will block until all data is written.
	   	* @param    aString: The incoming string to be written
	   	* 
	   	* @return	None
	   	*/
		public void write(String aString) throws IOException
		{
			
			outputOriginal.write(aString);
		}
		
		/**
	   	* Writes remaining data to the output stream and closes    
	   	* the underlying stream.
	   	*
	   	* @param    None
	   	* 
	   	* @return	None
	   	*/
		public void close() throws IOException
		{
			// Close the stream for the temporary file written	
			outputOriginal.close();
			
			// Now start reading the temporary file written
			fis = new FileInputStream("original.txt");
			readOriginalFile = new BufferedReader(new InputStreamReader(fis));
			
			// Start generating linkedlist of each character read and
			// also remembers the frequency of each repetitive character
			while ((line = readOriginalFile.readLine()) != null) 
			{				
				for(int chars=0; chars<line.length();chars++)
				{
					LLObject.search(Character.toString(line.charAt(chars)));
				}
			}
			
			// Now sort the linkedlist generated. Sorting is required for 
			// huffman coding
			LLObject.sort(LLObject.startNode);
			
			ref1 = LLObject.startNode;
			ref2 = ref1.right;
			currentNode = ref2.right;
			
			// Start making the huffman tree
			rootNode = makeTree(ref1, ref2, currentNode);
						
			// Now just traverse the huffman tree to get the huffman code
			traverseTreeToGetCode(rootNode, "");			
			
			// Now write to a file
			writeToFile(rootNode, LLObject.startNode);
			
			
				
		}

		/**
	   	* Writes the compressed data to the file   
	   	*
	   	* @param    rootNode, startNode: RootNode of tree and start node of lst
	   	* 
	   	* @return	None
	   	*/
		public void writeToFile(Node rootNode, Node startNode) throws NumberFormatException, IOException{
			Node tempNode = startNode;
			
			// Get the buffered writer
			BufferedBitWriter(outputCompress);
			FileInputStream fis = new FileInputStream("original.txt");
			//Construct BufferedReader from InputstreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			// Start writing the byte codes to create compressed file
			String line = null;
			while ((line = br.readLine()) != null) 
			{				
				for(int chars=0; chars<line.length();chars++)
				{
					tempNode = startNode;
					while(tempNode != null){
						if(tempNode.character.equals(Character.toString(line.charAt(chars)))){
							for(int i = 0; i < tempNode.byteCode.length(); i++){
								String temp = Character.toString(tempNode.byteCode.charAt(i));
								int tempinteger = Integer.parseInt(temp);
								writeBit(tempinteger);
							}							
							break;
						}
						tempNode = tempNode.right;
					}
				}
				
				// Just verify that if we are left with any bits
				if (numBitsWritten > 0) { 
					// Write bits to compressed file if there are any
		            output.write(currentByte);
		            numBitsWritten = 0;
		            currentByte = 0;
		        }
		        
			}
			br.close();			        
			output.close();
		}
		
		/**
	   	* Creates the buffered writer   
	   	*
	   	* @param    pathName: File output stream path
	   	* 
	   	* @return	None
	   	*/
		public void BufferedBitWriter(FileOutputStream pathName) throws FileNotFoundException {
	        currentByte = 0;
	        numBitsWritten = 0;
	        output = new BufferedOutputStream(pathName);
	    }
		
		/**
	   	* This method packs the data into a byte   
	   	*
	   	* @param    bit: each encoded bit
	   	* 
	   	* @return	None
	   	*/
	    public void writeBit(int bit) throws IOException {
	    	
	    	// Just verify if the bits are valid
	        if (bit < 0 || bit > 1)
	            throw new IllegalArgumentException("Argument to writeBit: bit = " + bit);
	       
	        numBitsWritten++;
	        currentByte |= bit << (8 - numBitsWritten);
	        if (numBitsWritten == 8) 
	        { 
	        	// We got full byte so write it to the file
	            output.write(currentByte);
	            numBitsWritten = 0;
	            currentByte = 0;
	        }
	    
	    }
		
		/**
	   	* This method traverse the huffman tree to get codes   
	   	*
	   	* @param    rootNode, data: Root node of tree and the codes
	   	* 
	   	* @return	None
	   	*/
		public void traverseTreeToGetCode(Node rootNode,String data)
		{
			// If we reached the bottom of the tree stop recursion
			if (rootNode == null)
			{			
				return;
			}
			
			// Traverse to the tree in preorder to get all the codes
			zipCode = zipCode + data;
			rootNode.byteCode = zipCode;
			
			// When we are moving left generate code 1
			traverseTreeToGetCode(rootNode.left,"1");
			zipCode = rootNode.byteCode;
			
			// When we are moving right generate code 0
			traverseTreeToGetCode(rootNode.right,"0");
		}
		
		/**
	   	* This method contains the code to generate the huffman tree   
	   	*
	   	* @param    ref1, ref2, currentNode: References of the tree
	   	* 
	   	* @return	None
	   	*/
		public Node makeTree(Node ref1, Node ref2, Node currentNode)
		{
			if(currentNode==null)
			{
				ref1 = new Node(ref1,ref1.frequency+ref2.frequency,"21",ref2);
				return ref1;
			}
				
			else if(currentNode.right==null)
			{
				ref1 = new Node(ref1,ref1.frequency+ref2.frequency,"21",ref2);
				ref1 = new Node(ref1,ref1.frequency+currentNode.frequency,"21",currentNode);
				return ref1;
			}
			
			else
			{
				ref1 = new Node(ref1,ref1.frequency+ref2.frequency,"21",ref2);
				if(ref1.frequency<=currentNode.frequency||ref1.frequency<=currentNode.right.frequency)
				{
					ref2 = currentNode;
					currentNode = currentNode.right;
					ref1 = makeTree(ref1, ref2, currentNode);
				}
				
				else if(ref1.frequency>currentNode.right.frequency)
				{
					ref2 = new Node(currentNode, currentNode.frequency+currentNode.right.frequency,"12",currentNode.right);
					currentNode = currentNode.right.right;
					ref1 = makeTree(ref1, ref2, currentNode);
				}
			}
			return ref1;

		}
}