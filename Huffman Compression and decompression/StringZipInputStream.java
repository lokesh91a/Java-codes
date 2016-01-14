/* 
 * StringZipInputStream.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This is a Program handles the file compression algorithm
 * and view
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */

public class StringZipInputStream	{

	private FileInputStream inputStream;
	private int current;
	private int bitMask;
	private byte currentByte;
	private byte numBitsWritten;
	private BufferedWriter output = null;
	private int p=1;
	private String check;
	private String code = "";
	private LLOfAllCharacters LLObject;
	private BufferedInputStream input;
	private Node ref1, ref2, rootNode, currentNode;
	String line;
	private BufferedReader readOriginalFile;
	private FileInputStream fis;
	private String zipCode = "";
	private boolean streamOpened = false;
	private File originalFile;
	
	
	/**
   	* Creates a new input stream with a default buffer size.
   	*
   	* @param    out: Handle to file input stream
   	* 
   	* @return	None
   	*/
	public StringZipInputStream(FileInputStream out) throws IOException	{
				
		inputStream = out;
		this.LLObject = new LLOfAllCharacters();
		
		fis = new FileInputStream("original.txt");
		readOriginalFile = new BufferedReader(new InputStreamReader(fis));
		while ((line = readOriginalFile.readLine()) != null) 
		{
			//initial.search("newline");
			for(int chars=0; chars<line.length();chars++)
			{
				LLObject.search(Character.toString(line.charAt(chars)));
			}
		}
		
		LLObject.sort(LLObject.startNode);
		
		ref1 = LLObject.startNode;
		ref2 = ref1.right;
		currentNode = ref2.right;
		rootNode = makeTree(ref1, ref2, currentNode);
		
		
		traverseTreeToGetCode(rootNode, "");
		
		
		
	}	

	/**
   	* Reads data into a string. the method will block until some input can be read;
   	* otherwise, no bytes are read and null is returned.
   	* @param    	None
   	* 
   	* @return	None
   	*/
	public String read() throws IOException
	{	
		BufferedBitReader(inputStream);
		p = readBit();
		if(p==-1)
			return null;
		code = code+Integer.toString(p);
		check = LLObject.searchNode(code);
		if(!check.equals("not found"))
		{
			code="";
			return check;
		}
		return "";
	}
	
	/**
   	* This function created huffman tree
   	*
   	* @param    ref1, ref2, currentNode
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
	
	/**
   	* This method traverse tree to get huffman codes
   	*
   	* @param    rootNode, data
   	* 
   	* @return	None
   	*/	
	public void traverseTreeToGetCode(Node rootNode,String data)
	{
		if (rootNode == null)
		{			
			return;
		}
		zipCode = zipCode + data;
		rootNode.byteCode = zipCode;
		traverseTreeToGetCode(rootNode.left,"1");
		zipCode = rootNode.byteCode;
		traverseTreeToGetCode(rootNode.right,"0");
	}
	
	
	/**
   	* Closes this input stream and releases any system resources 
   	* associated with the stream.
   	* @param    None
   	* 
   	* @return	None
   	*/
	public void close() throws IOException
	{
		originalFile = new File("original.txt");
		originalFile.delete();
		
		input.close();
	}
	
	/**
   	* This function reads bits
   	*
   	* @param    out: Handle to file input stream
   	* 
   	* @return	None
   	*/
	public void BufferedBitReader(FileInputStream out) throws IOException
	{
		if(!streamOpened)
		{
			input = new BufferedInputStream(out);
			streamOpened = true;
		}
    }
	
	/**
   	* Creates a new output stream with a default buffer size.   
   	*
   	* @param    None
   	* 
   	* @return	None
   	*/
	public int readBit() throws IOException
	{
		int returnBit; // Hold the bit to return
        if(bitMask==0)
        {
        	current = input.read();
        	//afterCurrent = input.read();
        }
        if(current==-1)
        	return -1;
        //System.out.println(current);
       if(bitMask==0)
        	bitMask = 128;
        
       if ((bitMask & current) == 0)
            returnBit = 0;
       else
           returnBit = 1;

    //    next--;                 // One fewer bit to return
       bitMask = bitMask >> 1; // Shift to mask next bit
       return returnBit;
   }
	
} 
