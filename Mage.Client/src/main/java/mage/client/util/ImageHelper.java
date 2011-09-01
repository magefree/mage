/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.client.util;

import static mage.constants.Constants.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import mage.cards.CardDimensions;
import mage.view.CardView;
import org.mage.card.arcane.UI;

import com.mortennobel.imagescaling.ResampleOp;
import java.awt.Rectangle;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ImageHelper {
	protected static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	protected static HashMap<String, BufferedImage> backgrounds = new HashMap<String, BufferedImage>();

	public static BufferedImage loadImage(String ref, int width, int height) {
		BufferedImage image = loadImage(ref);
		if (image != null)
			return scaleImage(image, width, height);
		return null;
	}

	/**
	 *
	 * @param ref - image name
	 * @param height - height after scaling
	 * @return a scaled image that preserves the original aspect ratio, with a specified height
	 */
	public static BufferedImage loadImage(String ref, int height) {
		BufferedImage image = loadImage(ref);
		if (image != null)
			return scaleImage(image, height);
		return null;
	}

	public static BufferedImage loadImage(String ref) {
		if (!images.containsKey(ref)) {
			try {
				images.put(ref, ImageIO.read(ImageHelper.class.getResourceAsStream(ref)));
			} catch (Exception e) {
				return null;
			}
		}
		return images.get(ref);
	}

	public static BufferedImage getBackground(CardView card, String backgroundName) {
		if (backgrounds.containsKey(backgroundName)) {
			return backgrounds.get(backgroundName);
		}
		
		BufferedImage background = new BufferedImage(FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
		backgrounds.put(backgroundName, background);
		return background;
	}


	public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
		BufferedImage scaledImage = image;
		int w = image.getWidth();
		int h = image.getHeight();
		do {
			w /= 2;
			h /= 2;
			if (w < width || h < height) {
				w = width;
				h = height;
			}
			BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics2D = newImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(scaledImage, 0, 0, w, h, null);
			graphics2D.dispose();
			scaledImage = newImage;
		} while (w != width || h != height);
		return scaledImage;
	}

	public static BufferedImage scaleImage(BufferedImage image, int height) {
		double ratio = height / (double)image.getHeight();
		int width = (int) (image.getWidth() * ratio);
		return scaleImage(image, width, height);
	}

	public static MemoryImageSource rotate(Image image, CardDimensions dimensions) {
		int buffer[] = new int[dimensions.frameWidth * dimensions.frameHeight];
		int rotate[] = new int[dimensions.frameHeight * dimensions.frameWidth];
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, dimensions.frameWidth, dimensions.frameHeight, buffer, 0, dimensions.frameWidth);
		try {
			grabber.grabPixels();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		for(int y = 0; y < dimensions.frameHeight; y++) {
			for(int x = 0; x < dimensions.frameWidth; x++) {
				rotate[((dimensions.frameWidth - x - 1) *dimensions.frameHeight)+y] = buffer[(y*dimensions.frameWidth)+x];
			}
		}

		return new MemoryImageSource(dimensions.frameHeight, dimensions.frameWidth, rotate, 0, dimensions.frameHeight);

	}

	public static void drawCosts(List<String> costs, Graphics2D g, int xOffset, int yOffset, ImageObserver o) {
		if (costs.size() > 0) {
			int costLeft = xOffset;
			for (int i = costs.size() - 1; i >= 0; i--) {
				String symbol = costs.get(i);
				g.drawString(symbol, costLeft, yOffset + SYMBOL_MAX_SPACE);
				costLeft -= SYMBOL_MAX_SPACE + 4;
			}
		}
	}

	/**
	 * Returns an image scaled to the size appropriate for the card picture
	 * panel
	 */
	public static BufferedImage getResizedImage(BufferedImage original, int width, int height) {
		ResampleOp resampleOp = new ResampleOp(width, height);
		BufferedImage image = resampleOp.filter(original, null);
		return image;
	}

	/**
	 * Returns an image scaled to fit width
	 * panel
	 */
    public static BufferedImage getResizedImage(BufferedImage original, int width) {
        if (width != original.getWidth()) {
            double ratio = width / (double) original.getWidth();
            int height = (int) (original.getHeight() * ratio);
            return getResizedImage(original, width, height);
        } else {
            return original;
        }
    }

	/**
	 * Returns an image scaled to the needed size
	 */
    public static BufferedImage getResizedImage(BufferedImage original, Rectangle sizeNeed) {
		ResampleOp resampleOp = new ResampleOp(sizeNeed.width, sizeNeed.height);
		BufferedImage image = resampleOp.filter(original, null);
		return image;
	}

	/**
	 * Get image using relative path in resources.
	 * @param path
	 * @return
	 */
	public static Image getImageFromResources(String path) {
		InputStream stream;
		stream = UI.class.getResourceAsStream(path);
		if (stream == null) {
			throw new IllegalArgumentException("Couldn't find image in resources: " + path);
		}

		try {
			BufferedImage image = ImageIO.read(stream);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
