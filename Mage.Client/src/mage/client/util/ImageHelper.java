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
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.StackAbilityView;
import static mage.client.util.Constants.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ImageHelper {
	protected static HashMap<String, Image> images = new HashMap<String, Image>();
	protected static HashMap<String, BufferedImage> backgrounds = new HashMap<String, BufferedImage>();

	public static Image loadImage(String ref, int width, int height) {
		Image image = loadImage(ref);
		if (image != null)
			return ScaleImage(image, width, height);
		return null;
	}

	public static Image loadImage(String ref) {
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

	public static BufferedImage getBackground(CardView card) {
		// card background should be the same for all cards with the same name/art
		String cardName = card.getName()+card.getArt();
		if (backgrounds.containsKey(cardName)) {
			return backgrounds.get(cardName);
		}
		
		BufferedImage background = new BufferedImage(FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) background.getGraphics();
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
		g.drawImage(getFrame(card), 0, 0, Color.WHITE, null);
		if (card.getArt() != null && !card.getArt().equals("")) {
			Image art = loadImage(Config.cardArtResourcePath + card.getArt(), ART_MAX_WIDTH, ART_MAX_HEIGHT);
			g.drawImage(art, CONTENT_MAX_XOFFSET, ART_MAX_YOFFSET, null);
		}

		if (card.getCardTypes() != null && (card.getCardTypes().contains(CardType.CREATURE) || card.getCardTypes().contains(CardType.PLANESWALKER))) {
			g.drawImage(Frames.PowBoxLeft, POWBOX_MAX_LEFT, POWBOX_MAX_TOP, null);
			g.drawImage(Frames.PowBoxMid, POWBOX_MAX_LEFT + 7, POWBOX_MAX_TOP, null);
			g.drawImage(Frames.PowBoxRight, POWBOX_MAX_LEFT + 38, POWBOX_MAX_TOP, null);
		}

	    g.dispose();

		backgrounds.put(cardName, background);
		return background;
	}

	protected static Image getFrame(CardView card) {
		if (card instanceof StackAbilityView || card instanceof AbilityView) {
			return Frames.Effect;
		}

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

	protected static Image getLandFrame(CardView card) {
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

	public static Image ScaleImage(Image image, int width, int height) {
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	public static MemoryImageSource rotate(Image image) {
		int buffer[] = new int[FRAME_WIDTH * FRAME_HEIGHT];
		int rotate[] = new int[FRAME_HEIGHT * FRAME_WIDTH];
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, buffer, 0, FRAME_WIDTH);
		try {
			grabber.grabPixels();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		for(int y = 0; y < FRAME_HEIGHT; y++) {
			for(int x = 0; x < FRAME_WIDTH; x++) {
				rotate[((FRAME_WIDTH - x - 1) *FRAME_HEIGHT)+y] = buffer[(y*FRAME_WIDTH)+x];
			}
		}

		return new MemoryImageSource(FRAME_HEIGHT, FRAME_WIDTH, rotate, 0, FRAME_HEIGHT);

	}

	public static void DrawCosts(List<String> costs, Graphics2D g, int xOffset, int yOffset, ImageObserver o) {
		if (costs.size() > 0) {
			int costLeft = xOffset;
			for (int i = costs.size() - 1; i >= 0; i--) {
				String symbol = costs.get(i);
				Image image = Symbols.getSymbol(symbol);
				if (image != null) {
					g.drawImage(image, costLeft, yOffset, o);
					costLeft -= SYMBOL_SPACE;
				}
				else {
					g.drawString(symbol, costLeft, yOffset + SYMBOL_SPACE);
					costLeft -= SYMBOL_SPACE + 4;
				}
			}
		}
	}

}
