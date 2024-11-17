/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    // Mapping chars to unique 2 bit sequences for compressing and expanding
    private static int[] charMap = new int['t' + 1];
    private static char[] reverseCharMap = new char[4];

    public static void initializeCharMap() {
        charMap['c'] = 0b01;
        charMap['C'] = 0b01;
        charMap['t'] = 0b10;
        charMap['T'] = 0b10;
        charMap['g'] = 0b11;
        charMap['G'] = 0b11;
    }
    public static void initializeReverseCharMap() {
        reverseCharMap[0b00] = 'A';
        reverseCharMap[0b01] = 'C';
        reverseCharMap[0b10] = 'T';
        reverseCharMap[0b11] = 'G';
    }

    // Compresses a given file via the two bit codes above
    public static void compress() {
        // Reads in entire string as opposed to reading in chunks (due to smaller size of test cases)
        String sequence = BinaryStdIn.readString();
        // First thing in file is always an integer indicating how many 2 bit sequences will be found
        int numBases = sequence.length();
        BinaryStdOut.write(numBases);
        // Then individually turns each char into a 2 bit code
        for (int i = 0; i < numBases; i++) {
            BinaryStdOut.write(charMap[sequence.charAt(i)], 2);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    // Reverses the compress method, takes 2 bit codes and expands it to chars
    public static void expand() {
        // Checks how many 2 bit sequences it will find in compressed file
        int len = BinaryStdIn.readInt();
        for (int i = 0; i < len; i++) {
            char c;
            // Reads in single 2 bit code
            int bits = BinaryStdIn.readChar(2);
            // Converts bits to corresponding char
            c = reverseCharMap[bits];
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        // Initializes both char / int maps using helper methods
        initializeCharMap();
        initializeReverseCharMap();
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}