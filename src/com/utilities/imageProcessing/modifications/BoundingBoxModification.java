package com.utilities.imageProcessing.modifications;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.utilities.imageProcessing.dataStructures.BoundingBox;
import com.utilities.imageProcessing.dataStructures.Vector2D;

public class BoundingBoxModification extends ImageModifier {
	// the color to create the bounding box around
	Color boundingColor;
	
	public BoundingBoxModification(Color boundingColor) {
		this.boundingColor = boundingColor;
	}

	@Override
	public BufferedImage applyModificationToImage(BufferedImage image) {
		Vector2D minCord = new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Vector2D maxCord = new Vector2D(Integer.MIN_VALUE, Integer.MIN_VALUE);
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				int color = image.getRGB(x, y);
				int  red = this.imageUtilities.getRedFromInt(color);
				int  green = this.imageUtilities.getGreenFromInt(color);
				int  blue = this.imageUtilities.getBlueFromInt(color);
				
				if(red == this.boundingColor.getRed() && blue == this.boundingColor.getBlue() && green == this.boundingColor.getGreen()) {
					Vector2D currentCoordinate = new Vector2D(x,y);
					if(currentCoordinate.getX() < minCord.getX()) {
						minCord.setX(currentCoordinate.getX());
					}
					if(currentCoordinate.getY() < minCord.getY()) {
						minCord.setY(currentCoordinate.getY());
					}
					if(currentCoordinate.getX() > maxCord.getX()) {
						maxCord.setX(currentCoordinate.getX());
					}
					if(currentCoordinate.getY() > maxCord.getY()) {
						maxCord.setY(currentCoordinate.getY());
					}
				}
			}
		}
		if(minCord.getX() != Integer.MAX_VALUE || minCord.getX() != Integer.MAX_VALUE|| maxCord.getX() != Integer.MIN_VALUE || maxCord.getY() != Integer.MIN_VALUE) {
			CropModification cropModification = new CropModification(minCord.getX(), minCord.getY(), maxCord.getX(), maxCord.getY());
			return cropModification.applyModificationToImage(image);
		}
		return image;
	}
}
