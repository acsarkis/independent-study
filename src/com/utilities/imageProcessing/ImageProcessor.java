package com.utilities.imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import com.utilities.imageProcessing.featureExtractors.FeatureExtractor;
import com.utilities.imageProcessing.modifications.BoundingBoxModification;
import com.utilities.imageProcessing.modifications.CropModification;
import com.utilities.imageProcessing.modifications.ImageModifier;
import com.utilities.imageProcessing.modifications.ResizeModifiction;
import com.utilities.imageProcessing.modifications.ToBinaryImageModification;

public class ImageProcessor {
	
	public ArrayList<BufferedImage> preProcessImages(String imagesLocation, int desiredWidth, int desiredHeight) {
		ArrayList<BufferedImage> images = this.extractImagesFromFolder(imagesLocation);
		images = this.applyToArraylist(images, new ToBinaryImageModification());
		images = this.applyToChildDataset(images, new CropModification(
				ResourceConstants.CHILD_DATASET_CROP_X1,
				ResourceConstants.CHILD_DATASET_CROP_Y1,
				ResourceConstants.CHILD_DATASET_CROP_X2,
				ResourceConstants.CHILD_DATASET_CROP_Y2));
		images = this.applyToArraylist(images, new BoundingBoxModification(Color.black));
		images = this.applyToArraylist(images, new ResizeModifiction(desiredWidth, desiredHeight));
		
		return images;
	}
	
	private ArrayList<BufferedImage> extractImagesFromFolder(String path) {
		File[] directoryListing = new File(path).listFiles();
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		
		if(directoryListing != null) {
			for(File file : directoryListing) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(file);
					images.add(image);
				} catch (IOException e) { 	e.printStackTrace(); }
			}
		}
		return images;
	}
	
	public ArrayList<BufferedImage> applyToArraylist(ArrayList<BufferedImage> images, ImageModifier modifier) {
		ArrayList<BufferedImage> outputList = new ArrayList<BufferedImage>();
		for(BufferedImage image : images) {
			outputList.add(modifier.applyModificationToImage(image));
		}
		return outputList;
	}
	
	public LinkedList<Double> extractFeatureFromImages(ArrayList<BufferedImage> images, FeatureExtractor featureExtractor) {
		LinkedList<Double> outputList = new LinkedList<Double>();
		for(BufferedImage image : images) {
			outputList.add(featureExtractor.extractFeature(image));
		}
		return outputList;
	}
	
	public ArrayList<BufferedImage> applyToChildDataset(ArrayList<BufferedImage> images, ImageModifier modifier) {
		ArrayList<BufferedImage> outputList = new ArrayList<BufferedImage>();
		for(BufferedImage image : images) {
			if(image.getWidth() == 375 && image.getHeight() == 300) {
				outputList.add(modifier.applyModificationToImage(image));
			} else {
				outputList.add(image);
			}
		}
		return outputList;
	}
}
