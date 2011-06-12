package org.mage.plugins.card.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

import org.mage.plugins.card.constants.Constants;

public class SettingsManager {

	private static SettingsManager settingsManager = null;

	public static SettingsManager getIntance() {
		if (settingsManager == null) {
			synchronized (SettingsManager.class) {
				if (settingsManager == null) settingsManager = new SettingsManager();
			}
		}
		return settingsManager;
	}

	private SettingsManager() {
		loadImageProperties();
	}
	
	public void reloadImageProperties() {
		loadImageProperties();
	}
	
	private void loadImageProperties() {
		imageUrlProperties = new Properties();
		try {
			InputStream is = SettingsManager.class.getClassLoader().getResourceAsStream(Constants.IO.IMAGE_PROPERTIES_FILE);
			if (is == null)
				throw new RuntimeException("Couldn't load " + Constants.IO.IMAGE_PROPERTIES_FILE);
			imageUrlProperties.load(is);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public String getSetNameReplacement(String setName) {
		String result = setName;
		if (imageUrlProperties != null) {
			result = imageUrlProperties.getProperty(setName, setName);
		}
		return result;	
	}
	
	public HashSet<String> getIgnoreUrls() {
		HashSet<String> ignoreUrls = new HashSet<String>();
		if (imageUrlProperties != null) {
			String result = imageUrlProperties.getProperty("ignore.urls");
			if (result != null) {
				String[] ignore = result.split(",");
                ignoreUrls.addAll(Arrays.asList(ignore));
			}
		}
		return ignoreUrls;
	}
	
	public ArrayList<String> getTokenLookupOrder() {
		ArrayList<String> order = new ArrayList<String>();
		if (imageUrlProperties != null) {
			String result = imageUrlProperties.getProperty("token.lookup.order");
			if (result != null) {
				String[] sets = result.split(",");
                order.addAll(Arrays.asList(sets));
			}
		}
		return order;
	}

	private Properties imageUrlProperties;
}
