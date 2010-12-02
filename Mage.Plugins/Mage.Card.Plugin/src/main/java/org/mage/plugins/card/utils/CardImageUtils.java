package org.mage.plugins.card.utils;

import java.io.File;
import java.util.HashMap;

import mage.cards.Card;
import mage.game.permanent.PermanentToken;

import org.mage.plugins.card.CardUrl;
import org.mage.plugins.card.constants.Constants;
import org.mage.plugins.card.images.CardInfo;
import org.mage.plugins.card.properties.SettingsManager;

public class CardImageUtils {

	private static HashMap<CardUrl, String> pathCache = new HashMap<CardUrl, String>();
	
	/**
	 * Get path to image for specific card.
	 * 
	 * @param c
	 *            card to get path for
	 * @return String if image exists, else null
	 */
	public static String getImagePath(CardInfo c) {
		String filePath;
        String suffix = ".jpg";
	    String cardname = c.name;
	    String set = c.set;
		
	    if (cardname != null) { cardname = cardname.replace("'", ""); }
		CardUrl card = new CardUrl(cardname, set, c.collectorId, c.isToken);
		
		File file = null;
		if (c.isToken) {
			if (pathCache.containsKey(card)) {
				return pathCache.get(card);
			}
			filePath = getTokenImagePath(card);
			file = new File(filePath);
			
			if (!file.exists()) {
				filePath = searchForCardImage(card);
				file = new File(filePath);
			}
			
			if (file.exists()) {
				pathCache.put(card, filePath);
			}
		} else {
			filePath = getImagePath(card, false);
			file = new File(filePath);
			
			if (!file.exists()) {
				filePath = getImagePath(card, true);
				file = new File(filePath);
			}
		}

		/**
		 * try current directory
		 */
		if (file == null || !file.exists()) {
			filePath = cleanString(c.name) + suffix;
			file = new File(filePath);
		}

		if (file.exists()) {
			return filePath;
		} else {
			return null;
		}
	}
	
	private static boolean isToken(Card c) {
		return c != null && c instanceof PermanentToken;
	}
	
	private static String getTokenImagePath(CardUrl card) {
		String filename = getImagePath(card, false);
		CardUrl c = new CardUrl(card.name, card.set, 0, card.token);
		
		File file = new File(filename);
		if (!file.exists()) {
			c.name = card.name + " 1";
			filename = getImagePath(c, false);
			file = new File(filename);
			if (!file.exists()) {
				c.name = card.name + " 2";
				filename = getImagePath(c, false);
				file = new File(filename);
			}
		}
		
		return filename;
	}
	
	private static String searchForCardImage(CardUrl card) {
		File file = null;
		String path = "";
		CardUrl c = new CardUrl(card.name, card.set, 0, card.token);
		boolean found = false; // search only in older sets
		
		for (String set : SettingsManager.getIntance().getTokenLookupOrder()) {
			if (found || card.set.isEmpty()) { // start looking for image only if we have found card.set in the list (as this list is ordered)
				c.set = set;
				path = getTokenImagePath(c);
				file = new File(path);
				if (file.exists()) {
					pathCache.put(card, path);
					return path;
				}
			}
			//if (set.equals(card.set)) found = true;
		}
		return "";
	}

	public static String cleanString(String in) {
		in = in.trim();
		StringBuffer out = new StringBuffer();
		char c;
		for (int i = 0; i < in.length(); i++) {
			c = in.charAt(i);
			if (c == ' ' || c == '-')
				out.append('_');
			else if (Character.isLetterOrDigit(c)) {
				out.append(c);
			}
		}

		return out.toString().toLowerCase();
	}
	
	public static String generateURL(Integer collectorId, String cardSet) throws Exception {
		if (collectorId == null || cardSet == null) {
			throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
		}
		String set = updateSet(cardSet,true);
		String url = "http://magiccards.info/scans/en/";
		url += set.toLowerCase() + "/" + collectorId + ".jpg";
		
		return url;
	}
	
	private static String updateSet(String cardSet, boolean forUrl) {
		String set = cardSet.toLowerCase();
		if (set.equals("con")) {
			set = "cfx";
		}
		if (forUrl) {
			set = SettingsManager.getIntance().getSetNameReplacement(set);
		}
		return set;
	}
	
	public static String getImageDir(CardUrl card) {
		if (card.set == null) {
			return "";
		}
		String set = updateSet(card.set,false).toUpperCase();
		if (card.token) {
			return Constants.IO.imageBaseDir + File.separator + "TOK" + File.separator + set;
		} else {
			return Constants.IO.imageBaseDir + set;
		}
	}
	
	public static String getImagePath(CardUrl card, boolean withCollector) {
		if (withCollector) {
			return getImageDir(card) + File.separator + card.name + "." + card.collector + ".full.jpg";
		} else {
			return getImageDir(card) + File.separator + card.name + ".full.jpg";
		}
	}
}
