package org.mage.card.arcane;

import java.awt.*;
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
	static private final Map<String, Dimension> setImagesExist = new HashMap<String, Dimension>();
	static private Pattern replaceSymbolsPattern = Pattern.compile("\\{([^}/]*)/?([^}]*)\\}");

	private static final String[] sets = {"DIS", "GPT", "RAV", "MRD",
			"10E", "HOP", "ALA", "CFX", "ARB", "ZEN", "WWK", "ROE", "SOM", "M10", "M11", "M12",
			"MBS", "DDF", "DST", "LRW", "MOR", "SHM", "EVE", "APC", "NPH", "TMP", "CHK"};


	static public void loadImages() {
		String[] symbols = new String[]{"0", "1", "10", "11", "12", "15", "16", "2", "3", "4", "5", "6", "7", "8", "9", "B", "BG",
				"BR", "G", "GU", "GW", "R", "RG", "RW", "S", "T", "U", "UB", "UR", "W", "WB", "WU", 
				"WP", "UP", "BP", "RP", "GP", "X", "Y", "Z", "slash"};
		for (String symbol : symbols) {
			File file = new File(Constants.RESOURCE_PATH_MANA_MEDIUM + "/" + symbol + ".jpg");
			Rectangle r = new Rectangle(11, 11);
			try {
				Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
				BufferedImage resized = ImageCache.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
				manaImages.put(symbol, resized);
			} catch (Exception e) {
			}
			file = new File(Constants.RESOURCE_PATH_MANA_MEDIUM + "/" + symbol + ".jpg");
			try {
				Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
				manaImagesOriginal.put(symbol, image);
			} catch (Exception e) {
			}
		}
		File file;
		for (String set : sets) {
			file = new File(Constants.RESOURCE_PATH_SET_SMALL);
			if (!file.exists()) {
				break;
			}
			file = new File(Constants.RESOURCE_PATH_SET_SMALL + set + "-C.png");
			try {
				Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
				int width = image.getWidth(null);
				int height = image.getHeight(null);
				setImagesExist.put(set, new Dimension(width, height));
			} catch (Exception e) {
			}
		}
	}

	static public Image getManaSymbolImage(String symbol) {
		return manaImagesOriginal.get(symbol);
	}

	static public void draw(Graphics g, String manaCost, int x, int y) {
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

	static public synchronized String replaceSymbolsWithHTML(String value, boolean small) {
		if (small)
			return replaceSymbolsPattern.matcher(value).replaceAll("<img src='file:plugins/images/symbols/small/$1$2.jpg' alt='$1$2' width=11 height=11>");
		else {
			value = value.replace("{slash}", "<img src='file:plugins/images/symbols/medium/slash.jpg' alt='slash' width=10 height=13>");
			return replaceSymbolsPattern.matcher(value).replaceAll("<img src='file:plugins/images/symbols/medium/$1$2.jpg' alt='$1$2' width=13 height=13>");
		}
	}

	static public String replaceSetCodeWithHTML(String set, String rarity) {
		String _set = set;
		if (_set.equals("CON")) {
			_set = "CFX";
		}
		if (setImagesExist.containsKey(_set)) {
			Integer width = setImagesExist.get(_set).width;
			Integer height = setImagesExist.get(_set).height;
			return "<img src='file:plugins/images/sets/small/" + _set + "-" + rarity + ".png' alt='" + rarity + " ' width=" + width + " height=" + height + ">";
		} else {
			return set;
		}
	}
}
