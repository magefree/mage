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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.client.cards.CardDimensions;
import mage.sets.Sets;
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.StackAbilityView;
import static mage.client.util.Constants.*;

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
				if (Config.useResource)
					images.put(ref, ImageIO.read(ImageHelper.class.getResourceAsStream(ref)));
				else
					images.put(ref, ImageIO.read(new File(ref)));
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
		Graphics2D g = (Graphics2D) background.getGraphics();
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
		if (card instanceof StackAbilityView || card instanceof AbilityView) {
			g.drawImage(Frames.Effect, 0, 0, Color.WHITE, null);
		}
		else {
			g.drawImage(getFrame(card), 0, 0, Color.WHITE, null);
			if (card.getArt() != null && !card.getArt().equals("")) {
				BufferedImage art = loadImage(Config.cardArtResourcePath + card.getArt(), ART_MAX_WIDTH, ART_MAX_HEIGHT);
				g.drawImage(art, CONTENT_MAX_XOFFSET, ART_MAX_YOFFSET, null);
			}

			if (card.getExpansionSetCode() != null && card.getExpansionSetCode().length() > 0 && card.getRarity() != null && card.getRarity() != Rarity.NA) {
				try {
					String symbolCode = Sets.getInstance().get(card.getExpansionSetCode()).getSymbolCode();
					if (symbolCode != null && symbolCode.length() > 0) {
						StringBuilder sb = new StringBuilder();
						sb.append(Config.setIconsResourcePath).append("graphic_").append(symbolCode).append("_").append(card.getRarity().getSymbolCode()).append(".png");
						BufferedImage icon = loadImage(sb.toString(), ICON_MAX_HEIGHT);
						if (icon != null)
							g.drawImage(icon, ICON_MAX_XOFFSET - icon.getWidth(), ICON_MAX_YOFFSET, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} catch (Error er) {
					er.printStackTrace();
				}
			}

			if (card.getCardTypes() != null && (card.getCardTypes().contains(CardType.CREATURE) || card.getCardTypes().contains(CardType.PLANESWALKER))) {
				g.drawImage(Frames.PowBoxLeft, POWBOX_MAX_LEFT, POWBOX_MAX_TOP, null);
				g.drawImage(Frames.PowBoxMid, POWBOX_MAX_LEFT + 7, POWBOX_MAX_TOP, null);
				g.drawImage(Frames.PowBoxRight, POWBOX_MAX_LEFT + 38, POWBOX_MAX_TOP, null);
			}
		}

	    g.dispose();

		backgrounds.put(backgroundName, background);
		return background;
	}

	protected static BufferedImage getFrame(CardView card) {

		if (card.getCardTypes().contains(CardType.LAND)) {
			return getLandFrame(card);
		}
		else {
			if (card.getColor().isColorless()) {
				return Frames.Grey;
			} else if (card.getColor().isMulticolored()) {
				if (card.getColor().getColorCount() > 2)
					return Frames.Gold;
				if (card.getColor().isBlack() && card.getColor().isRed()) {
					if (Frames.BlackRed != null)
						return Frames.BlackRed;
				}
				else if (card.getColor().isBlack() && card.getColor().isGreen()) {
					if (Frames.BlackGreen != null)
						return Frames.BlackGreen;
				}
				else if (card.getColor().isBlack() && card.getColor().isBlue()) {
					if (Frames.BlueBlack != null)
						return Frames.BlueBlack;
				}
				else if (card.getColor().isRed() && card.getColor().isBlue()) {
					if (Frames.BlueRed != null)
						return Frames.BlueRed;
				}
				else if (card.getColor().isGreen() && card.getColor().isBlue()) {
					if (Frames.GreenBlue != null)
						return Frames.GreenBlue;
				}
				else if (card.getColor().isGreen() && card.getColor().isWhite()) {
					if (Frames.GreenWhite != null)
						return Frames.GreenWhite;
				}
				else if (card.getColor().isRed() && card.getColor().isGreen()) {
					if (Frames.RedGreen != null)
						return Frames.RedGreen;
				}
				else if (card.getColor().isRed() && card.getColor().isWhite()) {
					if (Frames.RedWhite != null)
						return Frames.RedWhite;
				}
				else if (card.getColor().isWhite() && card.getColor().isBlack()) {
					if (Frames.WhiteBlack != null)
						return Frames.WhiteBlack;
				}
				else if (card.getColor().isWhite() && card.getColor().isBlue()) {
					if (Frames.WhiteBlue != null)
						return Frames.WhiteBlue;
				}
				return Frames.Gold;
			} else {
				if (card.getColor().isBlack()) {
					return Frames.Black;
				} else if (card.getColor().isBlue()) {
					return Frames.Blue;
				} else if (card.getColor().isRed()) {
					return Frames.Red;
				} else if (card.getColor().isGreen()) {
					return Frames.Green;
				} else if (card.getColor().isWhite()) {
					return Frames.White;
				}
			}
		}
		return Frames.Grey;
	}

	protected static BufferedImage getLandFrame(CardView card) {
		if (card.getSuperTypes().contains("Basic")) {
			if (card.getSubTypes().contains("Forest")) {
				return Frames.Forest;
			}
			else if (card.getSubTypes().contains("Island")) {
				return Frames.Island;
			}
			else if (card.getSubTypes().contains("Mountain")) {
				return Frames.Mountain;
			}
			else if (card.getSubTypes().contains("Plains")) {
				return Frames.Plains;
			}
			else if (card.getSubTypes().contains("Swamp")) {
				return Frames.Swamp;
			}
		}
		return Frames.Land;
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
				Image image = Symbols.getSymbol(symbol);
				if (image != null) {
					g.drawImage(image, costLeft, yOffset, o);
					costLeft -= SYMBOL_MAX_SPACE;
				}
				else {
					g.drawString(symbol, costLeft, yOffset + SYMBOL_MAX_SPACE);
					costLeft -= SYMBOL_MAX_SPACE + 4;
				}
			}
		}
	}

}
