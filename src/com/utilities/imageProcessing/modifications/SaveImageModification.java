package com.utilities.imageProcessing.modifications;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.utilities.imageProcessing.ResourceConstants;

public class SaveImageModification extends ImageModifier {
	private final String name = "out";
	private Integer numberOut = 0;
	private final String extension = "png";
	private final String path;
	
	public SaveImageModification(String path) {
		this.path = path;
	}

	@Override
	public BufferedImage applyModificationToImage(BufferedImage image) {
		File file = new File(ResourceConstants.RESOURCES_FOLDER + ResourceConstants.PARSED_IMAGES_FOLDER + this.name + this.numberOut.toString() + "." + this.extension);
		try {
			ImageIO.write(image, this.extension, file);
			this.numberOut++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
