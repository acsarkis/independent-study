package com.utilities.imageProcessing.modifications;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ToBinaryImageModification extends ImageModifier {
	private final int defaultColorCutoff = 255 / 2;
	
	public ToBinaryImageModification() { }

	@Override
	public BufferedImage applyModificationToImage(BufferedImage image) {
		BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				Color currentColor = new Color(image.getRGB(x, y));
				int averageValue = this.imageUtilities.getMinColor(currentColor);
				
				if(averageValue < this.defaultColorCutoff) {
					currentColor = Color.black;
				} else {
					currentColor = Color.white;
				}
				
				outputImage.setRGB(x, y, currentColor.getRGB());
			}
		}
		return outputImage;
	}
}
