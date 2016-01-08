package com.utilities.imageProcessing.featureExtractors;

import java.awt.image.BufferedImage;

import com.utilities.imageProcessing.ImageUtilities;

/* If the pixel is black, return a 1 */
public class PixelValueExtractor implements FeatureExtractor {
	private ImageUtilities imageUtilities = new ImageUtilities();
	private int x;
	private int y;
	
	public PixelValueExtractor(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public double extractFeature(BufferedImage image) {
		double num = 0;
		// arbitrarily picked red.
		if(this.imageUtilities.getRedFromInt(image.getRGB(this.x, this.y)) == 0) {
			num = 1;
		}
		
		return num;
	}
}
