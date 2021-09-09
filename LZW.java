import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LZW {
	
	public static List<Integer> compress(String uncompressed) {
        // Build the dictionary.
        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++)//load ascii table 
            dictionary.put("" + (char)i, i);
 
        String current = "";
        List<Integer> encodedValues = new ArrayList<Integer>();
        for (char next : uncompressed.toCharArray()) {
            String combined = current + next;
            if (dictionary.containsKey(combined))//checks if combined is already in dictionary
                current = combined;
            else {
                encodedValues.add(dictionary.get(current));
                // Add combined letters to the dictionary
                dictionary.put(combined, dictSize++);
                current = "" + next;
            }
        }
 
        // Output the ascii code for each character(s) in encodedValues.
        if (!current.equals(""))
            encodedValues.add(dictionary.get(current));
        return convertToBinary(encodedValues);
    }
	/*
	 * This method takes a list of ascii dictionary values and outputs them in binary.
	 */
	public static List<Integer> convertToBinary(List<Integer> list) {
		List<Integer> binaryValues= new ArrayList<Integer>();
		for (int i = 0; i<list.size(); i++) {
			int currentNum = list.get(i);
			String binaryStr = "";
				while (currentNum != 0) {
					binaryStr += currentNum%2;
					currentNum = currentNum/2;
				}
			int binaryNum = Integer.parseInt(binaryStr);
			binaryValues.add(binaryNum);
		}
		return binaryValues;
	}
	
	public static void main(String[] args) throws IOException {
		String filename = "lzw-file3.txt";
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(filename));
            System.out.println("---------Contents of the file-----------\n");
            while( (line = br.readLine()) != null){//the bufferedreader reads each line, stores it, and passes it onto the "compress" method that performs LZW compression.
            	List<Integer> compressed = compress(line);
                System.out.println(compressed);
            }
        }finally {
        	br.close();
        }
        
    }
	
}
