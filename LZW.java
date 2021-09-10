import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LZW {
	
	public static String compress(String uncompressed) {
        // Build the dictionary.
        int maximumSize = 512;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++)//load ascii table 
            dictionary.put("" + (char)i, i);
        String output = "";
        
        String current = "";
        for (char next : uncompressed.toCharArray()) {
            String combined = current + next;
            if (dictionary.containsKey(combined))//checks if combined is already in dictionary
                current = combined;//if yes, then combined characters become the current and continues to find similar characters 
            else {
                output += Integer.toBinaryString(dictionary.get(current)); 
                if (dictionary.size()<maximumSize)
                	dictionary.put(combined, maximumSize++);
                current = "" + next;
            }
        }
        if (!current.equals(""))
            output+= Integer.toBinaryString(dictionary.get(current));
        return output;
    }
	
	
	public static void main(String[] args) throws IOException {
		String filename = "lzw-file1.txt";
		FileOutputStream fileOut = new FileOutputStream(new File("binaryOutput"));
        DataOutputStream dataOut = new DataOutputStream(fileOut);
        BufferedReader reader = null;
        String line = "";
        String input = "";
        String inputBinary="";
        
        try {
            reader = new BufferedReader(new FileReader(filename));//initialized reader
            while((line = reader.readLine()) != null){//the bufferedreader reads each line of lzw-file, passes it onto the "compress" method that performs LZW compression, then the writer writes that line into the file.
          	  input += line;//writer writes the line of converted binary values to the file
              }
          
          dataOut.writeBytes(compress(input));
          inputBinary = "";
          char[] inputChars = input.toCharArray();
          for (char c : inputChars) {
              inputBinary += Integer.toBinaryString(c);
          }
          
        }finally {
        	reader.close();
        	dataOut.close();
        	fileOut.close();
        	System.out.println("Done.");
        	System.out.println("Input: " + inputBinary);
        	System.out.println("Output: " + compress(input));
        }
    }
}
