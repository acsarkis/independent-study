package com.driver;

import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.classifiers.bagging.RandomForest;
import com.mvc.model.classifiers.decisionTree.DecisionTree;
import com.mvc.model.classifiers.naiveBayes.NaiveBayes;
import com.mvc.model.classifiers.neuralNetwork.NeuralNetwork;
import com.utilities.ConfusionMatrixCalculator;
import com.utilities.fileProcessing.CsvFileReader;
import com.utilities.fileProcessing.FileConstants;
import com.utilities.fileProcessing.HandWritingDataConverter;
import com.utilities.imageProcessing.ResourceConstants;
import com.utilities.imageProcessing.ImageDataExtractor;

public class HeadlessModeDriver {
	private final ConfusionMatrixCalculator confusionMatrixCalculator;
	private final HandWritingDataConverter handWritingDataConverter;
	private final CsvFileReader csvFileReader;
	private LinkedList<LinkedList<Integer> > rawTrainSet;
	private LinkedList<Example> trainSet;
	
	private LinkedList<LinkedList<Integer> > rawTestSet;
	private LinkedList<Example> testSet;
	
	/* Use this as your sandbox for headless mode */
	public HeadlessModeDriver() {
		this.csvFileReader = new CsvFileReader();
		this.confusionMatrixCalculator = new ConfusionMatrixCalculator();
		this.handWritingDataConverter = new HandWritingDataConverter();
		
		String imagesFrom = ResourceConstants.RESOURCES_FOLDER + ResourceConstants.IMAGES_TO_PARSE_FOLDER;
		String spreadsheetTo = ResourceConstants.RESOURCES_FOLDER + ResourceConstants.SPREADSHEET_OUTPUT_FOLDER;
		
		ImageDataExtractor spreadsheetBuilder = new ImageDataExtractor(imagesFrom, spreadsheetTo);		
		
		/*this.extractRegularData(FileConstants.BINARY_ZOO_DATASET);
		//this.extractHandwritingData();
		
		NeuralNetwork neuralNetwork = new NeuralNetwork(this.testSet);
		
		/*
		RandomForest randomForest = new RandomForest(this.trainSet);
		randomForest.runTesting(this.testSet);
		this.confusionMatrixCalculator.calculateConfusionMatrix(this.testSet);*/
		
		/*NaiveBayes naiveBayes = new NaiveBayes(this.trainSet);
		naiveBayes.runTesting(this.testSet);
		
		DecisionTree betterDecisionTree = new DecisionTree(this.trainSet);
		betterDecisionTree.runTesting(this.testSet);
		
		this.confusionMatrixCalculator.calculateConfusionMatrix(this.testSet);*/
		
		//BetterNeuralNetwork neuralNetwork = new BetterNeuralNetwork(this.trainSet);
	}
	
	private void extractRegularData(String dataset) {
		String path = FileConstants.DATA_FOLDER + dataset;
		
		this.rawTrainSet = this.csvFileReader.readCsvFile(getClass().getResource(path + FileConstants.TRAIN_FILE));
		this.trainSet = this.parseFeatures(this.rawTrainSet);
		
		this.rawTestSet = this.csvFileReader.readCsvFile(getClass().getResource(path + FileConstants.TEST_FILE));
		this.testSet = this.parseFeatures(this.rawTestSet);
	}
	
	private void extractHandwritingData() {
		String path = FileConstants.DATA_FOLDER + FileConstants.HANDWRITING_DATASET;
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
