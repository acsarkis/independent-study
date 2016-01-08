package com.utilities.imageProcessing.featureExtractors;

import java.awt.image.BufferedImage;

import com.utilities.imageProcessing.ImageUtilities;
import com.utilities.imageProcessing.dataStructures.Vector2D;
import com.utilities.imageProcessing.modifications.ToBinaryImageModification;

public class ImageSegmentExtractor implements FeatureExtractor {
	private int[][] segmentedImage;
	private BufferedImage image;
	private ImageUtilities imageUtilities = new ImageUtilities();
	
	private void findSegments() {
		Vector2D pixel1;
		
		for(int x = 0; x < this.image.getWidth(); x++) {
			for(int y = 0; y < this.image.getHeight(); y++) {
				pixel1 = new Vector2D(x,y);
				
				int range = 5;
				
				for(int xC = x + -range; xC < x + range; xC++) {
					for(int yC = y + -range; yC < y + range; yC++) {
						if(x == xC && y == yC) {
							continue;
						}
						this.tryToMergeGroups(pixel1, new Vector2D(xC,yC));
					}
				}
			}
		}
	}
	
	private void tryToMergeGroups(Vector2D pixel1, Vector2D pixel2) {
		if(this.imageUtilities.isValidPos(pixel1, this.image.getWidth(), this.image.getHeight()) && 
		   this.imageUtilities.isValidPos(pixel2, this.image.getWidth(), this.image.getHeight())) {
			int color1 = this.imageUtilities.getBlueFromInt(this.image.getRGB(pixel1.getX(), pixel1.getY()));
			int color2 = this.imageUtilities.getBlueFromInt(this.image.getRGB(pixel2.getX(), pixel2.getY()));
			if(color1 == color2) {
				this.mergePixelGroups(pixel1, pixel2);
			}
		}
	}
	
	private void outputFinalGroups() {
		System.out.println();
		for(int x = 0; x < this.image.getWidth(); x++) {
			for(int y = 0; y < this.image.getHeight(); y++) {
				System.out.printf("%3d ",this.segmentedImage[y][x]);
			}
			System.out.println();
		}
	}
	
	private void initializeGroups(int numGroups) {
		int i = 0;
		for(int x = 0; x < this.image.getWidth(); x++) {
			for(int y = 0; y < this.image.getHeight(); y++) {
				if(this.imageUtilities.getRedFromInt(this.image.getRGB(x, y)) == 255) {
					segmentedImage[x][y] = -1;
				} else {
					segmentedImage[x][y] = i;
				}
				i++;
			}
		}
	}
	
	private void mergePixelGroups(Vector2D pixel1, Vector2D pixel2) {
		int newValue = this.segmentedImage[pixel1.getX()][pixel1.getY()];
		int prevValue = this.segmentedImage[pixel2.getX()][pixel2.getY()];
		
		for(int x = 0; x < this.segmentedImage.length; x++) {
			for(int y = 0; y < this.segmentedImage.length; y++) {
				if(this.segmentedImage[x][y] == prevValue) {
					this.segmentedImage[x][y] = newValue;
				}
			}
		}
		this.segmentedImage[pixel2.getX()][pixel2.getY()] = this.segmentedImage[pixel1.getX()][pixel1.getY()];
	}
	
	@Override
	public double extractFeature(BufferedImage image) {
		ToBinaryImageModification toBinaryModification = new ToBinaryImageModification();
		//this.image = image;
		this.image = toBinaryModification.applyModificationToImage(image);
		this.segmentedImage = new int[image.getWidth()][image.getHeight()];
		this.initializeGroups(image.getWidth() * image.getHeight());
		this.findSegments();
		this.outputFinalGroups();
		return 0;
	}
}
