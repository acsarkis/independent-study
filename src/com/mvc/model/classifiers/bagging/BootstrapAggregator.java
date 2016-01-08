package com.mvc.model.classifiers.bagging;

import java.util.HashMap;
import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.EagerClassifier;

public class BootstrapAggregator {
	private LinkedList<EagerClassifier> classifiers;
	private HashMap<Integer, HashMap<Integer, Integer> > majorityVoteMap;
	
	public BootstrapAggregator() {
		this.classifiers = new LinkedList<EagerClassifier>();
		this.majorityVoteMap = new HashMap<Integer, HashMap<Integer, Integer> >();
	}
	
	public void addClassifier(EagerClassifier eagerClassifier) {
		this.classifiers.add(eagerClassifier);
	}
	
	public LinkedList<Example> getClassificationOnTestSet(LinkedList<Example> testSet) {
		this.majorityVoteMap = new HashMap<Integer, HashMap<Integer, Integer> >();
		
		for(EagerClassifier classifier : this.classifiers) {
			classifier.runTesting(testSet);
			this.updateVoteMap(testSet);
		}
		this.setMajorityVotes(testSet);
		
		return testSet;
	}
	
	private void setMajorityVotes(LinkedList<Example> testSet) {
		for(int i = 0; i < testSet.size(); i++) {
			HashMap<Integer, Integer> currentVotes = this.majorityVoteMap.get(i);
			testSet.get(i).setPrediction(this.getMajorityPrediction(currentVotes));
		}
	}
	
	private int getMajorityPrediction(HashMap<Integer, Integer> map) {
		int maxPrediction = -1;
		int maxVotes = Integer.MIN_VALUE;
		for(Integer key : map.keySet()) {
			if(map.get(key) > maxVotes) {
				maxPrediction = key;
				maxVotes = map.get(key);
			}
		}
		return maxPrediction;
	}
	
	private void updateVoteMap(LinkedList<Example> testSet) {
		for(int i = 0; i < testSet.size(); i++) {
			HashMap<Integer, Integer> currentMap;
			if(this.majorityVoteMap.containsKey(i)) {
				currentMap = this.majorityVoteMap.get(i);
			} else {
				currentMap = new HashMap<Integer, Integer>();
			}
			
			int count = 1;
			if(currentMap.containsKey(testSet.get(i).getPrediction())) {
				count += currentMap.get(testSet.get(i).getPrediction());
			}
			currentMap.put(testSet.get(i).getPrediction(), count);
			this.majorityVoteMap.put(i, currentMap);
		}
	}
}
