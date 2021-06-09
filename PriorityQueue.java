/*   Template for an object of type PriorityQueue using a linked implementation. A nested
**   Node class is included. Nodes are prioritized by their frequency value. The node
**   with the lowest frequency is at the head of the queue, and the node with the highest
**   frequency is at the end of the queue. Contains a method to order nodes using 
**   insertion sort. When used with HuffmanTree.java, can be used to construct a Huffman 
**   EncodingTree. */

public class PriorityQueue
{
    public int counter; // Variable to keep track of the size of queue
    public Node head;   // Variable to denote head (or front) of the queue
    public Node rear;   // Variable to denote rear (or end) of the queue
    
    // Nested class used as a template for an object of type Node.
    public class Node 
    {
        public String letter;   // Variable to hold letter from frequency file
        public int frequency;   // Variable to hold frequency of respective letter
        public Node left;       // Reference to left pointer (for use with queue)
        public Node right;      // Reference to right pointer (for use with queue)
        public Node leftChild;  // Reference to left child (for use when binary tree)
        public Node rightChild; // Reference to right child (for use when binary tree)
        
        // Constructor for an 'empty' Node
        public Node ( )
        {
            this.letter = null;
            this.frequency = 0;
            this.left = null;
            this.right = null;
            this.leftChild = null;
            this.rightChild = null;
        }
        
        // Constructor for a Node with a given letter and frequency
        public Node ( String letter, int frequency )
        {
            this.letter = letter;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
            this.leftChild = null;
            this.rightChild = null;
        }
    }
        
    // Constructor for PriorityQueue object
    public PriorityQueue( )
    {
        this.head = null; // Variable to reference the front of the queue
        this.rear = null; // Variable to reference the end of the queue
        this.counter = 0; // Variable to hold letter from frequency file
    }
    
    // Method returns true if the priority queue is empty
    public boolean isQueueEmpty( )
    {
        if ( head == null )
        {
            return true;
        }        
        return false;
    }
    
    // Method returns an integer that denotes the number of nodes in the queue
    public int queueLength( )
    {
        return counter;
    }        

	// Method inserts new node into beginning of queue if empty, otherwise at rear 
    public void insertNode( String letter, int frequency )
    {
        Node newNode = new Node ( letter, frequency );
                
        if ( head == null )
        {
            head = newNode;
            rear = head;
        }
        
        else
        {
            rear.right = newNode;
            rear = newNode;
        }
             
        counter++; // Increment counter to keep track of queue length
    }
    
    // Method performs insertion sort to order a queue 
    public void insertionSort( )
    {
        Node sortedQueue = null; //new queue that is sorted
        Node nodeToBeInserted = head; //head of unsorted queue
         
        while ( nodeToBeInserted != null )
        {
            // Save a reference to the next node for next iteration
            Node nextNodeToBeInserted = nodeToBeInserted.right;
            
            // If new sorted queue is empty or if node's frequency is less than head
            // node, then add node to front of queue
            if ( sortedQueue == null || nodeToBeInserted.frequency<sortedQueue.frequency )
            {
                nodeToBeInserted.right = sortedQueue;
                sortedQueue = nodeToBeInserted;
            }
            
            // If new sorted queue contains nodes already, find location for the node
            // being sorted based upon its frequency
            else
            {
                Node nodeWithinSortedQueue = sortedQueue;
                
                // Iterate through queue
                while ( nodeWithinSortedQueue.right != null && 
                	nodeWithinSortedQueue.right.frequency <= nodeToBeInserted.frequency )
                {
                    nodeWithinSortedQueue = nodeWithinSortedQueue.right;
                }
                nodeToBeInserted.right = nodeWithinSortedQueue.right;
                nodeWithinSortedQueue.right = nodeToBeInserted;
            }
                        
            nodeToBeInserted = nextNodeToBeInserted;
        }
        head = sortedQueue;   
    }
    
    // Method removes one node from the front of the queue and decrements the counter
    public Node popQueue( )
    {
        // Error handling if queue is empty
        //if ( isQueueEmpty() )
        //{
        //    System.out.println( "Error: This queue is empty." );
        //}
        
        Node temp = head;
        head = head.right;
        counter--;
        return temp;
    }
    
	// Method prints the queue to the console, starting at the head 
    public void printQueue( )
    {
        while( head != null )
        {
            System.out.println( head.letter + " " + head.frequency );
            head = head.right;
        }
    }
}