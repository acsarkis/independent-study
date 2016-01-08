package com.mvc.model.classifiers.naiveBayes;

import java.util.HashMap;
import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.EagerClassifier;
import com.mvc.view.subviews.UserInterfaceConstants;
import com.utilities.ConfusionMatrixCalculator;

public class NaiveBayes implements EagerClassifier {
	private final String MAP = "MAP";
	private final String MLE = "MLE";
	private String algorithm = MAP;
	private final String naiveBayesParameterPrefix = "Type: ";
	private final String[] selectableOptions = {this.naiveBayesParameterPrefix + MAP, this.naiveBayesParameterPrefix + MLE};
	private int totalExamples;
	private LinkedList<Example> trainingSet;
	private HashMap<Integer, Integer> uniqueFeatureCountMap;
	private HashMap<Integer, BayesKey> featureCounts;
	private HashMap<Integer, Integer> priorCounts;
	
	public NaiveBayes() {
		this.trainingSet = new LinkedList<Example>();
		this.runRequiredInitialization();
	}
	
	public NaiveBayes(LinkedList<Example> trainingSet) {
		this.trainingSet = trainingSet;
		this.runRequiredInitialization();
		this.runTraining();
	}
	
	private void runRequiredInitialization() {
		this.featureCounts = new HashMap<Integer, BayesKey>();
		this.priorCounts = new HashMap<Integer, Integer>();
		// stores the k value for the number of possible outcomes of a feature and the labels
		this.uniqueFeatureCountMap = new HashMap<Integer, Integer>();
	}

	@Override
	public void runTraining() {
		this.totalExamples = this.trainingSet.size();
		this.extractPriorCounts();
		this.extractUniqueFeatureCounts();
		this.extractFeatureCount();
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
			double currentProbability = 0.0;
			if(this.algorithm.equals(this.MAP)) {
				currentProbability = this.calculateMapForFeature(i, feature, classification);
			} else {
				// do MLE
				currentProbability = this.calculateMleForFeature(i, feature, classification);
			}
			probability *= currentProbability;
		}
		return probability;
	}
	
	/* Calculates the desired probability  without the laplace smoothing*/
	private double calculateMleForFeature(final int featNum, final int featureValue, final int classification) {
		// count of classification && featureValue
		double numerator = (double) this.featureCounts.get(featNum).getClassCount(featureValue, classification);
			
		// Prior
		double denominator = (double)this.priorCounts.get(classification);
			
		System.out.println(numerator + " " + denominator);
		return numerator / denominator;
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
		for(Example example : testSet) {
			int maxLabel = -1;
			double maxLiklihood = Integer.MIN_VALUE;
			for(int classification : this.priorCounts.keySet()) {
				double currentPrediction = this.makePrediction(example, classification);
				if(currentPrediction > maxLiklihood) {
					maxLiklihood = currentPrediction;
					maxLabel = classification;
				}
			}
			// put my prediction into the test set so I can get the confusion matrix later
			example.setPrediction(maxLabel);
			System.out.println(maxLabel + " " + maxLiklihood);
		}
	}

	@Override
	public void runTesting(LinkedList<Example> testSet) {
		this.runPredictor(testSet);
	}

	@Override
	public void setParameter(String parameter) { 
		parameter.replace(this.naiveBayesParameterPrefix, UserInterfaceConstants.NULL_STRING);
		this.algorithm = parameter;
		//System.out.println(parameter);
	}

	@Override
	public void runTraining(LinkedList<Example> trainingSet) {
		this.trainingSet = trainingSet;
		this.runTraining();
	}

	@Override
	public String[] getParameterOptions() {
		return this.selectableOptions;
	}

	@Override
	public void wipeValues() {
		this.runRequiredInitialization();
	}

}
