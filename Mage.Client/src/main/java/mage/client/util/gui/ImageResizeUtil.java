package mage.client.util.gui;

import com.mortennobel.imagescaling.ResampleOp;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author nantuko
 */
public class ImageResizeUtil {

    public static BufferedImage getResizedImage(BufferedImage original, Rectangle sizeNeed) {
		ResampleOp resampleOp = new ResampleOp(sizeNeed.width, sizeNeed.height);
		BufferedImage image = resampleOp.filter(original, null);
		return image;
	}
}
