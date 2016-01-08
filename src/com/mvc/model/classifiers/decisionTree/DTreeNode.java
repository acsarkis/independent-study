package com.mvc.model.classifiers.decisionTree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import com.example.Example;

public class DTreeNode {
	private LinkedList<Example> examples;
	private LinkedList<Example> parentExamples;
	private LinkedList<Integer> featureIndexes;
	private final String splitAlgorithm = DecisionTreeConstants.INFORMATION_GAIN;
	private int maxDepth;
	
	private DTreeNode trueBranch = null;
	private DTreeNode falseBranch = null;
	
	private int branchFeatureIndex = -1;
	private int branchFeatureValue = -1;
	private int prediction = -1;

	public DTreeNode(LinkedList<Example> examples, LinkedList<Example> parentExamples, LinkedList<Integer> featureIndexes, int maxDepth) {
		this.examples = examples;
		this.parentExamples = parentExamples;
		this.featureIndexes = featureIndexes;
		this.maxDepth = maxDepth;
		
		if(examples.size() < 1) {
			// If examples is empty, then return the plurality value of the parent's exmaples
			this.prediction = this.calcPluralityValue(this.parentExamples);
		} else if(this.countClassifications(examples).size() == 1) {
			// If all attributes have the same classification, then predict that class
			this.prediction = examples.getLast().getClassification();
		} else if(featureIndexes.size() < 1 || maxDepth < 1) {
			// If there are no more features to split on, return plurality value of examples
			this.prediction = this.calcPluralityValue(this.examples);
		} else {
			this.useSplittingAlgorithm();
			
			LinkedList<Integer> availableFeatures = this.removeFeatureIndex(this.featureIndexes, this.branchFeatureIndex);
			
			LinkedList<Example> trueSplit = this.splitExamples(this.examples, true);
			this.trueBranch = new DTreeNode(trueSplit, this.examples, availableFeatures, maxDepth - 1);
			
			LinkedList<Example> falseSplit = this.splitExamples(this.examples, false);
			this.falseBranch = new DTreeNode(falseSplit, this.examples, availableFeatures, maxDepth - 1);
		}
	}
	
	public LinkedList<Example> runTesting(LinkedList<Example> testSet) {
		if(this.trueBranch != null && this.falseBranch != null) {
			LinkedList<Example> trueSplit = this.splitExamples(testSet, true);
			LinkedList<Example> trueBranchPredictions = this.trueBranch.runTesting(trueSplit);
			
			LinkedList<Example> falseSplit = this.splitExamples(testSet, false);
			LinkedList<Example> falseBranchPredictions = this.falseBranch.runTesting(falseSplit);
			
			trueBranchPredictions.addAll(falseBranchPredictions);
			
			return trueBranchPredictions;
		} else {
			// I am a leaf!
			for(Example example : testSet) {
				example.setPrediction(this.prediction);
				//System.out.println(this.prediction + " " + example.getClassification());
			}
		}
		return testSet;
	}
	
	private LinkedList<Integer> removeFeatureIndex(LinkedList<Integer> featureIndexes, Integer removeValue) {
		LinkedList<Integer> newList = new LinkedList<Integer>();
		
		for(Integer integer : featureIndexes) {
			if(integer != removeValue) {
				newList.add(integer);
			}
		}
		
		return newList;
	}
	
	private LinkedList<Example> splitExamples(LinkedList<Example> set, boolean truthValue) {
		LinkedList<Example> returnSplit = new LinkedList<Example>();
		if(set == null) {
			return returnSplit;
		}
		
		for(Example example : set) {
			if((example.getFeatureAt(this.branchFeatureIndex) == branchFeatureValue) == truthValue) {
				returnSplit.add(example);
			}
		}
		
		return returnSplit;
	}
	
	private void useSplittingAlgorithm() {
		if(this.splitAlgorithm.equals(DecisionTreeConstants.INFORMATION_GAIN)) {
			this.useInformationGain();
		}
		// could add different splitting algorithms here
	}
	
	private void useInformationGain() {
		HashMap<Integer, Integer> featureCounts = new HashMap<Integer, Integer>();
		HashSet<Integer> uniqueClassifications = this.getUniqueClassifications();
		int maxFeatureIndex = 0;
		int maxPrediction = 0;
		double maxScore = 0;
		int maxFeatureValue = 0;
		
		for(int currentClassification : uniqueClassifications) {
			for(int i = 0; i < this.featureIndexes.size(); i++) {
				featureCounts = this.getPossibleFeatureValues(i);
				for(int featureVal : featureCounts.keySet()) {
					double currentScore = this.oneVsManyPredictor(featureVal, featureIndexes.get(i), currentClassification);
					if(currentScore > maxScore) {
						// feature's index
						maxFeatureIndex = featureIndexes.get(i);
						// feature's value 
						maxPrediction = currentClassification;
						// Information gain value
						maxScore = currentScore;
						// the predicted classification
						maxFeatureValue = featureVal;
					}
				}
			}
		}
		System.out.println("Split on feature: " + maxFeatureIndex + " Split on it being: " + maxFeatureValue +  " Predict: " + maxPrediction);
		this.branchFeatureIndex = maxFeatureIndex;
		this.branchFeatureValue = maxFeatureValue;
	}
	
	private double oneVsManyPredictor(int featureValue, int featIndex, int classificationValue) {
		//System.out.println(featureValue + " " + featIndex + " " + classificationValue);
		int posGivenTrait = 0;
		int negGivenTrait = 0;
		int posGivenNoTrait = 0;
		int negGivenNoTrait = 0;
		
		for(Example currentExample : this.examples) {
			if(currentExample.getFeatureAt(featIndex) == featureValue) {
				if(currentExample.tryClassification(classificationValue)) {
					posGivenTrait++;
				} else {
					negGivenTrait++;
				}
			} else {
				if(currentExample.tryClassification(classificationValue)) {
					posGivenNoTrait++;
				} else {
					negGivenNoTrait++;
				}
			}
		}
		//int total = posGivenTrait + negGivenTrait + posGivenNoTrait + negGivenNoTrait;
		//System.out.println(posGivenTrait + " " + negGivenTrait + " " + posGivenNoTrait + " " + negGivenNoTrait + " " + total);
		return calculateGain(posGivenTrait, negGivenTrait, posGivenNoTrait, negGivenNoTrait);
	}
	
	private double calculateGain(int posGivenTrait, int negGivenTrait, int posGivenNoTrait, int negGivenNoTrait) {
		int total = posGivenTrait + negGivenTrait + posGivenNoTrait + negGivenNoTrait;
		int totalGivenTrait = posGivenTrait + negGivenTrait;
		int totalGivenNoTrait = negGivenNoTrait + posGivenNoTrait;
		
		double entropy1 = this.divide(posGivenTrait, total);
		double entropy2 = this.divide(posGivenNoTrait, total);
		
		double result = 1.0 - ((this.divide(totalGivenTrait, total) * this.calcEntropy(entropy1)) + 
				(this.divide(totalGivenNoTrait, total) * this.calcEntropy(entropy2)));
		
		return result;
	}
	
	private double divide(int numer, int denom) {
		if(denom == 0) {
			return 0.0;
		}
		double result = (double) numer / (double) denom;
		return result;
	}
	
	private double calcEntropy(double input) {
		return -1.0 * (input * this.log2(input) + (1.0 - input) * this.log2(1.0 - input));
	}
	
	private double log2(double input) {
		if(input == 0) {
			return 0;
		}
		return Math.log(input) / Math.log(2);
	}
	
	private HashMap<Integer, Integer> getPossibleFeatureValues(final int featureIndex) {
		HashMap<Integer, Integer> featureCounts = new HashMap<Integer, Integer>();
		for(int i = 0; i < this.examples.size(); i++) {
			int currentFeatureValue = this.examples.get(i).getFeatureAt(featureIndex);
			int count = 1;
			if(featureCounts.containsKey(currentFeatureValue)) {
				count += featureCounts.get(currentFeatureValue);
			}
			featureCounts.put(currentFeatureValue, count);
		}
		return featureCounts;
	}
	
	private HashSet<Integer> getUniqueClassifications() {
		HashSet<Integer> classifications = new HashSet<Integer>();
		for(Example example : this.examples) {
			classifications.add(example.getClassification());
		}
		return classifications;
	}
	
	private int calcPluralityValue(LinkedList<Example> set) {
		return this.getMaxValueFromHash(this.countClassifications(set));
	}
	
	private HashMap<Integer, Integer> countClassifications(LinkedList<Example> set) {
		HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for(Example example : set) {
			int classification = example.getClassification();
			int count = 1;
			if(counts.containsKey(classification)) {
				count += counts.get(classification);
			}
			counts.put(classification, count);
		}
		
		return counts;
	}
	
	private int getMaxValueFromHash(HashMap<Integer, Integer> map) {
		int maxValue = Integer.MIN_VALUE;
		int maxKey = -1;
		for(Integer key : map.keySet()) {
			int currentValue = map.get(key);
			if(currentValue > maxValue) {
				maxValue = currentValue;
				maxKey = key;
			}
		}
		return maxKey;
	}
}
