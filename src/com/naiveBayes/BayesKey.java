package com.naiveBayes;

import java.util.HashMap;


/** This stores the counts that would be needed to calculate MLE.  NaiveBayes.java has the MAP counts **/
public class BayesKey {
	private final int featIndex;
	private HashMap<Integer, HashMap<Integer, Integer>> classificationMap;
	
	public BayesKey(int featIndex) {
		this.featIndex = featIndex;
		// maps are <FeatureValue, Count of that and the positive/negative label>
		this.classificationMap  = new HashMap<Integer, HashMap<Integer, Integer>>();
	}
	
	public void addEntry(int featureValue, int classification) {
		// increment the count for #(feature && classification)
		this.incrementClassificationsMap(classification, featureValue);
	}
	
	public void incrementClassificationsMap(int index, int key) {
		int count = 1;
		if(this.classificationMap.containsKey(index)) {
			if(this.classificationMap.get(index).containsKey(key)) {
				count += this.classificationMap.get(index).get(key);
			}
			this.classificationMap.get(index).put(key, count);
		} else {
			this.classificationMap.put(index, new HashMap<Integer, Integer>());
			this.classificationMap.get(index).put(key, count);
		}
	}
	
	public void incrementMap(HashMap<Integer, Integer> map, int key) {
		int count = 1;
		if(map.containsKey(key)) {
			count += map.get(key);
		}
		map.put(key, count);
	}
	
	public int getClassCount(int featureValue, int classNum) {
		if(this.classificationMap.containsKey(classNum) && this.classificationMap.get(classNum).containsKey(featureValue)) {
			return this.classificationMap.get(classNum).get(featureValue);
		}
		
		return 0;
	}
	
	@Override
	public String toString() {
		String returnString = "FeatIndex: " + this.featIndex + "\n";
		returnString += "Positive Counts: \n";
		returnString += "Negative Counts: \n";
		returnString += "\n";
		return returnString;
	}
	
	public String getHashString(HashMap<Integer, Integer> hashMap) {
		String returnString = "";
		
		for(Integer key : hashMap.keySet()) {
			returnString += "Value: " + key.toString() + " Count: " + hashMap.get(key).toString() + "\n";
		}
		
		return returnString;
	}
}
