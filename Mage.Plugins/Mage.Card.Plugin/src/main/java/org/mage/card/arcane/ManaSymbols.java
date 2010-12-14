package org.mage.card.arcane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.mage.plugins.card.constants.Constants;
import org.mage.plugins.card.images.ImageCache;
import org.mage.plugins.card.utils.BufferedImageBuilder;

public class ManaSymbols {
	private static final Logger log = Logger.getLogger(ManaSymbols.class);
	static private final Map<String, Image> manaImages = new HashMap<String, Image>();
	static private final Map<String, Image> manaImagesOriginal = new HashMap<String, Image>();
	static private Pattern replaceSymbolsPattern = Pattern.compile("\\{([^}/]*)/?([^}]*)\\}");

	static public void loadImages () {
		String[] symbols = new String[] {"0", "1", "10", "11", "12", "15", "16", "2", "3", "4", "5", "6", "7", "8", "9", "B", "BG",
			"BR", "G", "GU", "GW", "R", "RG", "RW", "S", "T", "U", "UB", "UR", "W", "WB", "WU", "X", "Y", "Z", "slash"};
		for (String symbol : symbols) {
			File file = new File(Constants.RESOURCE_PATH_MANA_LARGE + "/" + symbol + ".jpg");
			BufferedImageBuilder builder = new BufferedImageBuilder();
			Rectangle r = new Rectangle(11, 11);
			try {
				Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
				BufferedImage resized = ImageCache.getResizedImage(builder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
				manaImages.put(symbol, resized);
			} catch (Exception e) {}
			file = new File(Constants.RESOURCE_PATH_MANA_MEDIUM + "/" + symbol + ".jpg");
			try {
				Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
				manaImagesOriginal.put(symbol, image);
			} catch (Exception e) {}
		}
	}

	static public Image getManaSymbolImage(String symbol) {
		return manaImagesOriginal.get(symbol);
	}
	
	static public void draw (Graphics g, String manaCost, int x, int y) {
		if (manaCost.length() == 0) return;
		manaCost = manaCost.replace("\\", "");
		manaCost = UI.getDisplayManaCost(manaCost);
		StringTokenizer tok = new StringTokenizer(manaCost, " ");
		while (tok.hasMoreTokens()) {
			String symbol = tok.nextToken().substring(0);
			Image image = manaImages.get(symbol);
			if (image == null) {
				//log.error("Symbol not recognized \"" + symbol + "\" in mana cost: " + manaCost);
				continue;
			}
			g.drawImage(image, x, y, null);
			x += symbol.length() > 2 ? 10 : 12; // slash.png is only 10 pixels wide.
		}
	}

	static public String getStringManaCost(List<String> manaCost) {
		StringBuilder sb = new StringBuilder();
		for (String s : manaCost) {
			sb.append(s);
		}
		return sb.toString().replace("{", "").replace("}", " ").trim();
	}
	
	static public int getWidth(String manaCost) {
		int width = 0;
		manaCost = manaCost.replace("\\", "");
		StringTokenizer tok = new StringTokenizer(manaCost, " ");
		while (tok.hasMoreTokens()) {
			String symbol = tok.nextToken().substring(0);
			width += symbol.length() > 2 ? 10 : 12; // slash.png is only 10 pixels wide.
		}
		return width;
	}

	static public synchronized String replaceSymbolsWithHTML (String value, boolean small) {
		if (small)
			return replaceSymbolsPattern.matcher(value).replaceAll("<img src='file:plugins/images/symbols/small/$1$2.jpg' width=11 height=11>");
		else {
			value = value.replace("{slash}", "<img src='file:plugins/images/symbols/medium/slash.jpg' width=10 height=13>");
			return replaceSymbolsPattern.matcher(value).replaceAll("<img src='file:plugins/images/symbols/medium/$1$2.jpg' width=13 height=13>");
		}
	}
}
