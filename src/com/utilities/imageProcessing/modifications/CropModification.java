package com.utilities.imageProcessing.modifications;

import java.awt.image.BufferedImage;

public class CropModification extends ImageModifier {
	private int x;
	private int y;
	private int w;
	private int h;
	
	public CropModification(int x1, int y1, int x2, int y2) {
		this.x = x1;
		this.y = y1;
		this.w = Math.abs(x2 - this.x);
		this.h = Math.abs(y2 - this.y);
	}

	@Override
	public BufferedImage applyModificationToImage(BufferedImage image) {
		return image.getSubimage(this.x, this.y, this.w, this.h);
	}

}