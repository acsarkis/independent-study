package com.utilities.fileProcessing;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

public class CsvFileReader {
	public CsvFileReader() { }
	
	/*  Resource1: http://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 *  Resource2: http://stackoverflow.com/questions/12103371/how-to-use-getclass-getresource-method
	 */
	public LinkedList<LinkedList<Integer> > readCsvFile(URL url) {
		LinkedList<LinkedList<Integer> > output = new LinkedList<LinkedList<Integer> >();
		BufferedReader br = null;
		String line = "";
		
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			while((line = br.readLine()) != null) {
				String[] currentRow = line.split(FileConstants.CSV_SPLIT_BY);
				LinkedList<Integer> currRowInts = this.toIntegers(currentRow);
				output.add(currRowInts);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return output;
	}
	
	public LinkedList<Integer> toIntegers(String[] currentRow) {
		LinkedList<Integer> llist = new LinkedList<Integer>();
		for(int i = 0; i < currentRow.length; i++) {
			llist.add(Integer.parseInt(currentRow[i]));
		}
		return llist;
	}
}
