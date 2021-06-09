import java.io.*;

/*   Template for an object of type HuffmanTree. When used with PriorityQueue.java
**   a Huffman Encoding Tree can be built. Reads input from a frequency file and a 
**   file to be expanded or encoded. Calls methods to build a priority queue that 
**   prioritizes nodes based upon frequency; the lowest frequency is at the front of 
**   the queue. Generates desired output, which is text or Huffman code. Punctuation
**   and capitalization are ignored. */

public class HuffmanTree
{
    private PriorityQueue pq;  // Variable for the priority queue
    public String frequencyFileName; // Variable for file containing letters, frequencies
    public String fileToCompressOrExpand; // Variable for input file
    private String[] letterArray;  // Array to store letters from frequency file
    private int[] frequencyArray;  // Array to store frequencies from frequency file
    private String[][] huffmanCodeArray; // Array to store Huffman codes for each letter
    private PriorityQueue.Node mainRoot; // Variable for the main root of Huffman Tree
    private int huffLetters = 0; // Initialize variable for indexing huffmanCodeArray
    private int huffCode = 0; // Initialize variable for indexing huffmanCodeArray
    
    // Constructor for HuffmanTree
    public HuffmanTree(  )
    {
        //this.pq = pq;
        this.letterArray = new String [26]; // 26 letters in alphabet
        this.frequencyArray = new int [26];
        this.huffmanCodeArray = new String [2][26];
        //this.mainRoot = mainRoot;
    } 
    
    // Read in the file containing letters and their frequencies
    public void readFrequencyFile( String frequencyFileName ) throws IOException
    {
        File f = new File( frequencyFileName );
        if( !f.exists() )
            {
                System.out.println( "Does not exist. Program ending." );
                System.exit( 1 );
            }
            else
            {    
                System.out.println( "File will be read." );
            }
        
        // Wrap FileReader with a BufferedReader for efficiency
        BufferedReader input = new BufferedReader( new FileReader( f ) );
    		
    	// Variable to hold a line of data from file
		String record = " ";  

		int m = 0; // Initialize variable to index letterArray
		int n = 0; // Initialize variable to index frequencyArray

        while( ( record = input.readLine() ) != null)
        {
            StringBuilder contents = new StringBuilder();
            contents.append( record );
            String s = contents.toString();
            // This print statement prints the length of each line.  
            //System.out.println( s.length() );
                       
            String x = String.valueOf( s.charAt(0) );
            letterArray[m++] = x;                    
						
            int a = 0;
            
            // Formatting to read values from frequency file
            if ( s.length() == 6 )  // When frequency value is single digit
            {
                a = Character.getNumericValue( s.charAt(4) );
            }
            
            else if ( s.length() == 5 )  // Frequency value for Z at end of file
            {
                a = Character.getNumericValue( s.charAt(4) );
            }
            
            else if ( s.length() == 7 )  // When frequency value is double digit
            {
                int y = Character.getNumericValue(s.charAt(4) ); // First digit
                int z = Character.getNumericValue(s.charAt(5) ); // Second digit
                String yz = y + "" + z; // Concatenate the two digits
                a = Integer.parseInt(yz);
            }
            frequencyArray[n++] = a;
        }    	 	
    }
    
    // Method reads input file to be compressed and converts to Huffman Code
    public void readFileToBeCompressed( String fileToBeCompressed, String outputFile, 
    	String [][] huffmanCodeArray ) throws IOException
    {    
        File f = new File( fileToBeCompressed ); // Create new File object
        if( !f.exists() )
            {
                System.out.println( "Does not exist. Program ending." );
                System.exit( 1 );
            }
            else
            {    
                System.out.println( "File will be read." );
            }
        
        // Wrap FileReader with a BufferedReader for efficiency
        BufferedReader input = new BufferedReader( new FileReader( f ) );
    		
    	// Variable to hold a line of data from file
		String record = " ";  
				
		// Define and initialize variable to count number of lines processed
		int countLine = 0;
		
		// Create a new StringBuilder object 
		StringBuilder huffmanOutputCode = new StringBuilder();
		
		// Read input file
        while( ( record = input.readLine() ) != null)
        {
            StringBuilder contents = new StringBuilder();
            contents.append( record );
            String s = contents.toString();

			// Convert the letters in input file to Huffman Code
            for ( int i = 0; i < s.length(); i++ )
            {
                 String x = String.valueOf( s.charAt(i) );
                 for ( int j = 0; j < 26; j++ )
                 {
                     // Find respective letter
                     if ( x.equalsIgnoreCase( huffmanCodeArray[0][j]) )
                     {
                         // Write corresponding Huffman Code for that letter
                         huffmanOutputCode.append( huffmanCodeArray[1][j] );                         
                     }
                 }                 
            } 
            huffmanOutputCode.append(System.getProperty( "line.separator" ));
            huffmanOutputCode.append(System.getProperty( "line.separator" ));
            FileWriter fr = new FileWriter( outputFile );
            BufferedWriter output= new BufferedWriter( fr );
			output.write( huffmanOutputCode.toString() );			
			output.close(); 
        }           
    }
    
    // Method reads input file to be expanded and converts to text
    public void readFileToBeExpanded( String fileToBeExpanded, String outputFile, 
    	PriorityQueue.Node mainRoot ) throws IOException
    {
        // Create object of type FileInputStream for input file
        InputStream inputStream = new FileInputStream( fileToBeExpanded );
        // Create object of type FileOutputStream to write output file
        OutputStream outputStream = new FileOutputStream( outputFile );
        
        FileWriter fr = new FileWriter( outputFile );
        BufferedWriter output= new BufferedWriter( fr );
        
        int s; // Variable for input from file
        int i = 0;
		int [] holder = new int [250]; // Create array to hold input from file
        
        PriorityQueue.Node nextRoot = mainRoot; // Variable for traversing three
        while (( s = inputStream.read() ) != -1 )     
        {                                    
            if (nextRoot.rightChild == null && nextRoot.leftChild == null)
            {
            	System.out.print(nextRoot.letter);
            	output.write( nextRoot.letter.toString() );
            	
            	nextRoot = mainRoot;
            }
            	
            if ( s == 48 ) // 48 is ascii value for 0
            {
            	nextRoot = nextRoot.leftChild;
            }
            
            else if ( s == 49 ) // 49 is ascii value for 1
            {
            	nextRoot = nextRoot.rightChild;
            }         
        } 
        System.out.println();
        output.close();       
    }
    
    // Method generates priority queue of 26 nodes, one for each letter of frequency file		
	public PriorityQueue generatePriorityQueue( PriorityQueue pq )
	{
	    //PriorityQueue pq = new PriorityQueue( );
	    // Iterate through letters and frequencies, add corresponding values to each node
	    for (int i = 0; i <= 25; i++)
	    {
	        pq.insertNode( letterArray[i], frequencyArray[i] );
	    }
	    // Perform insertion sort to order frequencies in priority queue	    
	    pq.insertionSort( );
	    return pq;	    
	}    
    

    // Method recursively generates the Huffman codes for each letter of the alphabet
    private String[][] buildHuffmanCode( PriorityQueue.Node node, 
    	String s, String [][] huffmanCodeArray ) 
    {   
    	// If node is internal, call function again 
        if ( !isALeafNode( node ) ) 
        { 
            buildHuffmanCode( node.leftChild,  s + '0', huffmanCodeArray );
            buildHuffmanCode( node.rightChild, s + '1', huffmanCodeArray );
        }
        
        else 
        {            
            // Print codes to console
            //System.out.println( node.letter + " " + s );
			
			// If a leaf node, initialize variable for node's letter value
            String l = node.letter;            
            
            huffmanCodeArray[0][huffLetters++] = l; // add letter to array
            huffmanCodeArray[1][huffCode++] = s;    // add code generated for that letter
        }
        return huffmanCodeArray;
    }
        
    // Method accepts a node and determines whether it is a leaf node
    public static boolean isALeafNode( PriorityQueue.Node root )
    {
        // Returns true if both right and left child nodes are null
        if ( root.rightChild == null && root.leftChild == null  )
        {
            return true;
        }
        
        return false;        
    }
    
    // Method builds Huffman Tree then calls method to generate codes for each letter
    public void buildHuffmanTreeForCompression( PriorityQueue pq )
    {
        // Start with a full priority queue that has 26 nodes, one for each letter of the 
        // alphabet. Process until only one node is left, which is the root of the tree.
        while ( pq.queueLength( ) > 1 )
        {
            // Create new objects of type PriorityQueue.Node as variables for popped nodes
            PriorityQueue.Node x = pq.new Node ( );
            PriorityQueue.Node y = pq.new Node ( );
            
            // Pop the two nodes with the lowest frequencies from head of priority queue
            x = pq.popQueue( );
            y = pq.popQueue( );
            
        	// Initialize int variables to hold frequency values of the popped nodes
            int frequencyX = x.frequency;
            int frequencyY = y.frequency;

			// Add the frequencies of the two popped nodes
            int totalFrequency = frequencyX + frequencyY;

			// Initialize String variables to hold the letter values of the popped nodes
            String letterX = x.letter;
            String letterY = y.letter;

			// Initialize String variable to hold concatenated letters of the popped nodes
            String combinedLetters = x.letter + y.letter;

			// Create new variable of type PriorityQueue.Node to hold new root node
            PriorityQueue.Node root = pq.new Node ( );
        	
        	// Store the concatenated letters and total frequency in a new root node
        	root.letter = combinedLetters;
        	root.frequency = totalFrequency;
        	
            // Logic for assigning left child node and right child node of the root
            // Node with lower frequency becomes left child
            if ( frequencyX < frequencyY )
            {            
                root.leftChild = x;
                root.rightChild = y;
            }
            else if ( frequencyY < frequencyX )
            {
                root.leftChild = y;
                root.rightChild = x;
            }
        	
        	// If the frequencies of the two nodes are equal, need to break tie
        	else if ( frequencyX == frequencyY )
        	{
        	    // Initialize variable to hold output of alphabetic comparator
        	    int compare = letterX.compareTo( letterY );
        	    
        	    // Node with shortest number of letters becomes left child
        	    if ( letterX.length() < letterY.length() )
        	    {
        	    	root.leftChild = x;
        	    	root.rightChild = y;
        	    }
        	    else if ( letterX.length() > letterY.length() )
        	    {
        	        root.leftChild = y;
        	        root.rightChild = x;
        	    }
        	    
        	    // If the nodes have equal number of letters, prioritize alphabetically
        	    else if ( letterX.length() == letterY.length() )
        	    {
        	    	// If letter of 'X' node comes before letter of 'Y' node, x goes left
        	    	if ( compare < 0 )
        	    	{
        	        	root.leftChild = x;
                		root.rightChild = y;
                	}
                	// If letter of 'Y' node comes before letter of 'X' node, y goes left
                	else if ( compare > 0 )
                	{
                    	root.leftChild = y;
                		root.rightChild = x;
                	}
                }
        	}
        	
        	// Insert the newly created root node into the priority queue
		    // Insert at head if it is null or lower frequency than head
		    if ( pq.head == null || root.frequency < pq.head.frequency )
		    {
		    	root.right = pq.head;
		    	pq.head = root;
		    	pq.counter++;
			}
			// Otherwise insert the node in the middle or rear, based on frequency 
        	else 
        	{
            	PriorityQueue.Node nodeWithinSortedQueue = pq.head;
            	while ( nodeWithinSortedQueue.right != null && 
            		nodeWithinSortedQueue.right.frequency <= root.frequency )
				{
                	nodeWithinSortedQueue = nodeWithinSortedQueue.right;
            	}
        		root.right = nodeWithinSortedQueue.right;
            	nodeWithinSortedQueue.right = root;
            	pq.counter++;
			}
			// Sort nodes to ensure lowest frequencies are at head of priority queue
			pq.insertionSort( );  
		}
		
		//PriorityQueue.Node mainRoot = pq.head;
		mainRoot = pq.head;
		// Call method to generate Huffman codes for each letter
		buildHuffmanCode(mainRoot, "", huffmanCodeArray );     
    }
    
    // Method builds a Huffman Tree and returns it as object of type PriorityQueue.Node 
    public PriorityQueue.Node buildHuffmanTreeForExpansion( PriorityQueue pq )
    {
        // Start with a full priority queue that has 26 nodes, one for each letter of the 
        // alphabet. Process until only one node is left, which is the root of the tree.
        while ( pq.queueLength( ) > 1 )
        {
            // Create new objects of type PriorityQueue.Node as variables for popped nodes
            PriorityQueue.Node x = pq.new Node ( );
            PriorityQueue.Node y = pq.new Node ( );
            
            // Pop the two nodes with the lowest frequencies from head of priority queue
            x = pq.popQueue( );
            y = pq.popQueue( );
            
        	// Initialize int variables to hold frequency values of the popped nodes
            int frequencyX = x.frequency;
            int frequencyY = y.frequency;

			// Add the frequencies of the two popped nodes
            int totalFrequency = frequencyX + frequencyY;

			// Initialize String variables to hold the letter values of the popped nodes
            String letterX = x.letter;
            String letterY = y.letter;

			// Initialize String variable to hold concatenated letters of the popped nodes
            String combinedLetters = x.letter + y.letter;

			// Create new variable of type PriorityQueue.Node to hold new root node
            PriorityQueue.Node root = pq.new Node ( );
        	
        	// Store the concatenated letters and total frequency in a new root node
        	root.letter = combinedLetters;
        	root.frequency = totalFrequency;
        	
        	// Logic for assigning left child node and right child node of the root
            // Node with lower frequency becomes left child
            if ( frequencyX < frequencyY )
            {            
                root.leftChild = x;
                root.rightChild = y;
            }
            else if ( frequencyY < frequencyX )
            {
                root.leftChild = y;
                root.rightChild = x;
            }
        	
        	// If the frequencies of the two nodes are equal, need to break tie
        	else if ( frequencyX == frequencyY )
        	{
        	    // Initialize variable to hold output of alphabetic comparator
        	    int compare = letterX.compareTo(letterY);
        	    
        	    // Node with shortest number of letters becomes left child
        	    if ( letterX.length() < letterY.length() )
        	    {
        	    	root.leftChild = x;
        	    	root.rightChild = y;
        	    }
        	    else if ( letterX.length() > letterY.length() )
        	    {
        	        root.leftChild = y;
        	        root.rightChild = x;
        	    }
        	    
        	    // If the nodes have equal number of letters, prioritize alphabetically
        	    else if ( letterX.length() == letterY.length() )
        	    {
        	    	// If letter of 'X' node comes before letter of 'Y' node, x goes left
        	    	if ( compare < 0 )
        	    	{
        	        	root.leftChild = x;
                		root.rightChild = y;
                	}
                	// If letter of 'Y' node comes before letter of 'X' node, y goes left
                	else if ( compare > 0 )
                	{
                    	root.leftChild = y;
                		root.rightChild = x;
                	}
                }
        	}
        	
        	// Insert the newly created root node into the priority queue
		    // Insert at head if it is null or lower frequency than head
		    if ( pq.head == null || root.frequency < pq.head.frequency )
		    {
		    	root.right = pq.head;
		    	pq.head = root;
		    	pq.counter++;
			}
        	
        	// Otherwise insert the node in the middle or rear, based on frequency  
        	else 
        	{
            	PriorityQueue.Node nodeWithinSortedQueue = pq.head;
            	while ( nodeWithinSortedQueue.right != null && 
            		nodeWithinSortedQueue.right.frequency <= root.frequency )
				{
                	nodeWithinSortedQueue = nodeWithinSortedQueue.right;
            	}
        		root.right = nodeWithinSortedQueue.right;
            	nodeWithinSortedQueue.right = root;
            	pq.counter++;
			}
			// Sort nodes to ensure lowest frequencies are at head of priority queue
			pq.insertionSort( );  
		}
		mainRoot = pq.head;		
		return mainRoot;    
    }
    
    // Method recursively traverses the Huffman tree in preorder and prints to console
    public void preOrderTreeTraversal( PriorityQueue.Node mainRoot )
    {
        if ( mainRoot == null )
        {
            return;
        }
        System.out.print( mainRoot.letter + ": " + mainRoot.frequency + ", " );
        preOrderTreeTraversal( mainRoot.leftChild );
        preOrderTreeTraversal( mainRoot.rightChild );
    }

    // Method converts a file to be compressed and outputs compressed code to a file
    public void compressInput( String fileToCompressOrExpand, String outputFile ) 
    	throws IOException
    {
        // Method accepts frequency file to build Huffman Tree
        readFrequencyFile( frequencyFileName );
        // Create new PriorityQueue object
        PriorityQueue pq = new PriorityQueue( );
        // Method generates a priority queue
        generatePriorityQueue( pq );        
        // Method builds the Huffman Tree
        buildHuffmanTreeForCompression( pq );
        // Call method to print Huffman Tree in pre-order
        //preOrderTreeTraversal( mainRoot );    
        // Method reads in clear text file and outputs compressed file
        readFileToBeCompressed ( fileToCompressOrExpand, outputFile, huffmanCodeArray );
    }
    
    // Method converts compressed file into text
    public void expandCode( String fileToCompressOrExpand, String outputFile ) 
    	throws IOException
    {
        // Method accepts frequency file to build Huffman Tree
        readFrequencyFile( frequencyFileName );
        // Create new PriorityQueue object
        PriorityQueue pq = new PriorityQueue( );
        // Method generates a priority queue
        generatePriorityQueue( pq );        
        // Create new PriorityQueue.Node object to hold root node of Huffman Tree
        PriorityQueue.Node mainRoot = pq.new Node ( ); 
        // Method builds the Huffman Tree and saves root node as mainRoot    
        mainRoot = buildHuffmanTreeForExpansion( pq ); 
        // Method reads in compressed file and outputs expanded file
        readFileToBeExpanded ( fileToCompressOrExpand, outputFile, mainRoot );
    }
}