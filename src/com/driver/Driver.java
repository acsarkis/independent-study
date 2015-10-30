package com.driver;

import java.util.LinkedList;

import com.decisionTree.DecisionTree;
import com.example.Example;
import com.naiveBayes.NaiveBayes;
import com.utilities.ConfusionMatrixCalculator;
import com.utilities.FileConstants;
import com.utilities.HandWritingDataConverter;

public class Driver {
	private final ConfusionMatrixCalculator confusionMatrixCalculator;
	private final HandWritingDataConverter handWritingDataConverter;
	private LinkedList<LinkedList<Integer> > rawTrainSet;
	private LinkedList<Example> trainSet;
	
	private LinkedList<LinkedList<Integer> > rawTestSet;
	private LinkedList<Example> testSet;
	
	public Driver() {
		this.confusionMatrixCalculator = new ConfusionMatrixCalculator();
		this.handWritingDataConverter = new HandWritingDataConverter();
		/*this.extractHandwritingData();
		
		NaiveBayes naiveBayes = new NaiveBayes(this.trainSet);
		naiveBayes.runTesting(this.testSet);
		
		DecisionTree betterDecisionTree = new DecisionTree(this.trainSet);
		betterDecisionTree.runTesting(this.testSet);
		
		this.confusionMatrixCalculator.calculateConfusionMatrix(this.testSet);*/
		
		//BetterNeuralNetwork neuralNetwork = new BetterNeuralNetwork(this.trainSet);
	}
	
	private void extractHandwritingData() {
		String path = FileConstants.DATA_FOLDER + FileConstants.HANDWRINTING_BASE_NAME;
		this.rawTrainSet = this.handWritingDataConverter.readinHandWritingData(getClass().getResource(path + FileConstants.TRAIN_FILE));
		this.trainSet = this.parseFeatures(this.rawTrainSet);
		
		this.rawTestSet = this.handWritingDataConverter.readinHandWritingData(getClass().getResource(path + FileConstants.TEST_FILE));
		this.testSet = this.parseFeatures(this.rawTestSet);
	}
	
	private LinkedList<Example> parseFeatures(LinkedList<LinkedList<Integer> > dataset) {
		LinkedList<Example> output = new LinkedList<Example>();
		for(int i = 0; i < dataset.size(); i++) {
			Example currentFeature = new Example(dataset.get(i));
			output.add(currentFeature);
		}
		return output;
	}
}
