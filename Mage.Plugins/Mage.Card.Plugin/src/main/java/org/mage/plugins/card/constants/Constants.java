package org.mage.plugins.card.constants;

import java.awt.Rectangle;

public class Constants {
	public static final String RESOURCE_PATH = "/images";
	public static final String RESOURCE_PATH_MANA = resourcePath("mana");

	public static final Rectangle CARD_SIZE_FULL = new Rectangle(101, 149);
	
	/**
	 * Build resource path.
	 * 
	 * @param folder
	 * @return
	 */
	private static String resourcePath(String folder) {
		return RESOURCE_PATH + "/" + folder;
	}
}
