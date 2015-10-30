package com.naiveBayes;

import java.util.HashMap;
import java.util.LinkedList;

import com.example.Example;
import com.interfaces.EagerClassifier;
import com.utilities.ConfusionMatrixCalculator;

public class NaiveBayes implements EagerClassifier {
	private String algorithm;
	private final String MAP = "MAP";
	private final String MLE = "MLE";
	private final int totalExamples;
	private final LinkedList<Example> trainingSet;
	private HashMap<Integer, Integer> uniqueFeatureCountMap;
	private HashMap<Integer, BayesKey> featureCounts;
	private HashMap<Integer, Integer> priorCounts;
	
	public NaiveBayes(LinkedList<Example> trainingSet) {
		this.algorithm = this.MAP;
		this.trainingSet = trainingSet;
		this.totalExamples = this.trainingSet.size();
		this.featureCounts = new HashMap<Integer, BayesKey>();
		this.priorCounts = new HashMap<Integer, Integer>();
		// stores the k value for the number of possible outcomes of a feature and the labels
		this.uniqueFeatureCountMap = new HashMap<Integer, Integer>();
		this.runTraining();
	}

	@Override
	public void getParameters() {
		// If someone wanted to later, it would be easy to add MLE
		this.algorithm = this.MAP;
	}

	@Override
	public void runTraining() {
		this.extractPriorCounts();
		this.extractUniqueFeatureCounts();
		this.extractFeatureCount();
		for(Integer key : this.featureCounts.keySet()) {
			//System.out.println(this.featureCounts.get(key).toString());
		}
	}
	
	public void extractFeatureCount() {
		for(Example example : this.trainingSet) {
			for(int i = 0; i < example.getNumFeatures(); i++) {
				if(! this.featureCounts.containsKey(i)) {
					this.featureCounts.put(i, new BayesKey(i));
				}
				int feature = example.getFeatureAt(i);
				BayesKey counter = this.featureCounts.get(i);
				counter.addEntry(feature, example.getClassification());
			}
		}
	}
	
	public void extractUniqueFeatureCounts() {
		int numFeatures = this.trainingSet.getFirst().getNumFeatures();
		for(int i = 0; i < numFeatures; i++) {
			HashMap<Integer, Boolean> currentFeatureValues = new HashMap<Integer,Boolean>();
			for(Example example : this.trainingSet) {
				this.featureCounts.put(i, new BayesKey(i));
				currentFeatureValues.put(example.getFeatureAt(i), true);
			}
			this.uniqueFeatureCountMap.put(i, currentFeatureValues.size());
		}
	}
	
	public void extractPriorCounts() {
		for(Example example : this.trainingSet) {
			int count = 1;
			int key = example.getClassification();
			if(this.priorCounts.containsKey(key)) {
				count += this.priorCounts.get(key);
			}
			this.priorCounts.put(key, count);
		}
	}
	
	/* Makes the prediction for the current example */
	private double makePrediction(final Example example, final int classification) {
		double probability = 1.0 + (double) this.priorCounts.get(classification) / (double) this.totalExamples;
		for(int i = 0; i < example.getNumFeatures(); i++) {
			int feature = example.getFeatureAt(i);
			double currentProbability = this.calculateMapForFeature(i, feature, classification);
			probability *= currentProbability;
		}
		return probability;
	}
	
	/* Calculates the laplace smoothing and the desired probability */
	private double calculateMapForFeature(final int featNum, final int featureValue, final int classification) {
		// 1 + count of classification && featureValue
		double numerator = 1.0 + (double) this.featureCounts.get(featNum).getClassCount(featureValue, classification);
		
		// Prior + count
		double denominator = (double)this.priorCounts.get(classification) + (double) this.uniqueFeatureCountMap.get(featNum);
		
		return numerator / denominator;
	}
	
	/* Uses Laplace smoothing to calculate the probabilities and make a prediction */
	public void runPredictor(LinkedList<Example> testSet) {
		HashMap<Integer, Double> currentPredictionMap = new HashMap<Integer, Double>();
		for(Example example : testSet) {
			int maxLabel = -1;
			double maxLiklihood = Double.MIN_NORMAL;
			for(int classification : this.priorCounts.keySet()) {
				double currentPrediction = this.makePrediction(example, classification);
				if(currentPrediction > maxLiklihood) {
					maxLiklihood = currentPrediction;
					maxLabel = classification;
				}
			}
			// put my prediction into the test set so I can get the confusion matrix later
			example.setPrediction(maxLabel);
			//System.out.println(maxLabel + " " + maxLiklihood);
		}
	}

	@Override
	public void runTesting(LinkedList<Example> testSet) {
		if(this.algorithm.equals(this.MAP)) {
			this.runPredictor(testSet);
		}
	}

}
