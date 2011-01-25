package org.mage.plugins.card.utils.impl;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.net.URL;

import javax.imageio.ImageIO;

import org.mage.plugins.card.utils.BufferedImageBuilder;
import org.mage.plugins.card.utils.ImageManager;
import org.mage.plugins.card.utils.Transparency;

public class ImageManagerImpl implements ImageManager {

	private static ImageManagerImpl fInstance = new ImageManagerImpl();
	
	public static ImageManagerImpl getInstance() {
		return fInstance;
	}
	
	@Override
	public Image getSicknessImage() {
		if (imageSickness == null) {
			Image image = getImageFromResourceTransparent("/sickness.png", Color.WHITE, new Rectangle(296, 265));
			Toolkit tk = Toolkit.getDefaultToolkit();
			image = tk.createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(0, 0, 200, 285)));
			imageSickness = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
		}
		return imageSickness;
	}

	protected static Image getImageFromResourceTransparent(String path, Color mask, Rectangle rec) {
        BufferedImage image = null;
        Image imageCardTransparent = null;
        Image resized = null;

        URL imageURL = ImageManager.class.getResource(path);

        try {
            image = ImageIO.read(imageURL);
            imageCardTransparent = Transparency.makeColorTransparent(image, mask);

            resized = imageCardTransparent.getScaledInstance(rec.width, rec.height, java.awt.Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resized;
    }

	private static Image imageSickness = null;
}
