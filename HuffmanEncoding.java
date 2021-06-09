import java.io.*;

/*   This program will compress or expand a file using Huffman Encoding. The program
**   uses HuffmanTree.java and PriorityQueue.java. The output is written to a file.  */

public class HuffmanEncoding
{
    public static void main(String[] args) throws IOException
    {
        // Create new HuffmanTree object
        HuffmanTree huffman = new HuffmanTree();
        
        // Read in name of frequency file and pass to HuffmanTree
        huffman.frequencyFileName = args[0];
        
        // Read in path of file to compress or expand
        String fileToCompressOrExpand = args[1];        
        
        // Read in indicator for compression or expansion
        int compressExpand = Integer.parseInt( args[2] );
        
        // Read in path of output file
        String outputFile = args[3];
        
        // If indicator is a 1 then compress file, if it is a 2 then expand file
        if ( compressExpand == 1 )
        {            
            huffman.compressInput( fileToCompressOrExpand, outputFile );
        }
        else if ( compressExpand == 2 )
        {
			huffman.expandCode( fileToCompressOrExpand, outputFile );
        }
        else
        {
        	// Error handling
        	System.out.println( "Invalid. Enter 1 to compress, 2 to expand. ");
        }
    }
}