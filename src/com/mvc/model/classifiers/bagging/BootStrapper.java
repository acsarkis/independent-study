package com.mvc.model.classifiers.bagging;

import java.util.LinkedList;

import com.example.Example;

public abstract class BootStrapper {
	
	/* Partition the data using random chance (the way bootstrapping is typically done) */
	public LinkedList<LinkedList<Example>> randomlyPartitionData(LinkedList<Example> input, int numClassifiers) {
		LinkedList<LinkedList<Example>> output = new LinkedList<LinkedList<Example> >();
		for(int i = 0; i < numClassifiers; i++) {
			output.add(this.randomSample(input));
		}
		
		return output;
	}
	
	public LinkedList<Example> randomSample(LinkedList<Example> input) {
		LinkedList<Example> output = new LinkedList<Example>();
		for(int i = 0; i < input.size(); i++) {
			int randomIndex = (int) (Math.random() * input.size());
			Example currentExample = input.get(randomIndex);
			output.add(currentExample.clone());
		}
		return output;
	}
	
	/* Partitions the data without using random chance */
	public LinkedList<LinkedList<Example> > partitionData(LinkedList<Example> input, int numBins) {
		int bucketSize = input.size() / numBins;
		LinkedList<LinkedList<Example>> output = new LinkedList<LinkedList<Example> >();
		if(bucketSize == 0) {
			// given too large of a number of bins for the size of the input
			return output;
		}
		// partition the data into the number of bins asked for!
		int listIndex = 0;
		for(int iteration = 0; iteration < numBins; iteration++) {
			LinkedList<Example> currentPartition = new LinkedList<Example>();
			for(int i = 0; i < bucketSize; i++) {
				currentPartition.add(input.get(listIndex));
				listIndex++;
			}
			if(iteration == numBins - 1 && numBins % input.size() != 0) {
				for(int i = (input.size() - (numBins % input.size())) + 1; i < input.size(); i++) {
					currentPartition.add(input.get(i));
				}
			}
			output.add(currentPartition);
		}
		return output;
	}
}
