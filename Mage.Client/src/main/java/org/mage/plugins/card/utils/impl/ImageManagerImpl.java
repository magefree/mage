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

import mage.client.util.gui.BufferedImageBuilder;
import org.mage.plugins.card.utils.ImageManager;
import org.mage.plugins.card.utils.Transparency;

public class ImageManagerImpl implements ImageManager {

	private static ImageManagerImpl fInstance = new ImageManagerImpl();
	
	public static ImageManagerImpl getInstance() {
		return fInstance;
	}
	
	@Override
	public BufferedImage getSicknessImage() {
		if (imageSickness == null) {
			Image image = getImageFromResourceTransparent("/sickness.png", Color.WHITE, new Rectangle(296, 265));
			Toolkit tk = Toolkit.getDefaultToolkit();
			image = tk.createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(0, 0, 200, 285)));
			imageSickness = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
		}
		return imageSickness;
	}

	@Override
	public BufferedImage getDayImage() {
		if (imageDay == null) {
			Image image = getImageFromResourceTransparent("/card/day.png", Color.WHITE, new Rectangle(20, 20));
			imageDay = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
		}
		return imageDay;
	}

	@Override
	public BufferedImage getNightImage() {
		if (imageNight == null) {
			Image image = getImageFromResourceTransparent("/card/night.png", Color.WHITE, new Rectangle(20, 20));
			imageNight = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
		}
		return imageNight;
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

	private static BufferedImage imageSickness = null;
	private static BufferedImage imageDay = null;
	private static BufferedImage imageNight = null;
}
