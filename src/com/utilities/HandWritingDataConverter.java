package com.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

public class HandWritingDataConverter {
	private BufferedReader bufferedReader;
	private String line;
	private int numFeatures;
	private boolean numFeaturesInit = false;
	
	public LinkedList< LinkedList<Integer> > readinHandWritingData(URL url) {
		LinkedList< LinkedList<Integer> > output = new LinkedList< LinkedList<Integer>>();
		
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			while((this.line = this.bufferedReader.readLine()) != null) {
				String[] currentRow = line.split(FileConstants.CSV_SPLIT_BY);
				if(! this.numFeaturesInit) {
					this.numFeatures = currentRow.length;
				}
				if(currentRow.length == this.numFeatures) {
					output.addLast(this.processHandWritingData(currentRow));
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (this.bufferedReader != null) {
				try {
					this.bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return output;
	}
	
	private LinkedList<Integer> processHandWritingData(String[] row) {
		LinkedList<Integer> returnList = new LinkedList<Integer>();
		for(int i = 0; i < row.length - 10; i++) {
			returnList.addLast(Integer.parseInt(row[i]));
		}
		int num = 0;
		for(int i = row.length - 10; i < row.length; i++) {
			if(row[i].equals("1")) {
				returnList.addLast(num);
				break;
			}
			num++;
		}
		
		return returnList;
	}
	
	public LinkedList<Integer> toIntegers(String[] currentRow) {
		LinkedList<Integer> llist = new LinkedList<Integer>();
		for(int i = 0; i < currentRow.length; i++) {
			llist.add(Integer.parseInt(currentRow[i]));
		}
		return llist;
	}
}
