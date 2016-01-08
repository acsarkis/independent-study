package com.mvc.controller;

import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.ClassifierMetricsModel;
import com.mvc.model.EagerClassifier;
import com.mvc.model.classifiers.nullClassifier.NullClassifier;
import com.mvc.view.subviews.UserInterfaceConstants;
import com.utilities.fileProcessing.CsvFileReader;
import com.utilities.fileProcessing.FileConstants;
import com.utilities.fileProcessing.HandWritingDataConverter;

public class ClassifierController {
	private final HandWritingDataConverter handWritingDataConverter;
	private final CsvFileReader csvFileReader;
	private ClassifierMetricsModel classifierMetrics;
	
	private String currentSet = UserInterfaceConstants.NULL_STRING;
	private LinkedList<LinkedList<Integer> > rawTrainSet;
	private LinkedList<Example> trainSet;
	
	private LinkedList<LinkedList<Integer> > rawTestSet;
	private LinkedList<Example> testSet;
	
	public ClassifierController(ClassifierMetricsModel classifierMetrics) { 
		this.classifierMetrics = classifierMetrics;
		this.handWritingDataConverter = new HandWritingDataConverter();
		this.csvFileReader = new CsvFileReader();
	}
	
	public void runClassificationTask(String classifier, String parameter, String dataset) {
		EagerClassifier realClassifier = UserInterfaceConstants.STRING_CLASSIFIER_MAPPING.get(classifier);
		realClassifier.wipeValues();
		realClassifier.setParameter(parameter);
		this.classifierMetrics.wipeValues();
		
		if(! dataset.equals(UserInterfaceConstants.NULL_STRING)) {
			this.handleDataset(dataset);
			this.handleClassifier(classifier, parameter);
		}
	}
	
	private void handleClassifier(String classifier, String parameter) {
		EagerClassifier selectedClassifier = UserInterfaceConstants.STRING_CLASSIFIER_MAPPING.get(classifier);
		selectedClassifier.setParameter(parameter);
		selectedClassifier.runTraining(this.trainSet);
		selectedClassifier.runTesting(this.testSet);
		this.classifierMetrics.calculateConfusionMatrix(this.testSet);
	}
	
	private void handleDataset(String dataset) {
		if(dataset.equals(FileConstants.HANDWRITING_DATASET)) {
			this.extractHandwritingData();
		} else {
			this.extractRegularData(dataset);
		}
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
