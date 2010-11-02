package org.mage.card.constants;

public class Constants {
	public static final String RESOURCE_PATH = "/images";
	public static final String RESOURCE_PATH_MANA = resourcePath("mana");

	
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
