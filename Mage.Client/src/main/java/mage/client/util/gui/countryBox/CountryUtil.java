/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util.gui.countryBox;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public class CountryUtil {

    private static final Logger logger = Logger.getLogger(CountryUtil.class);
    private static final Map<String, ImageIcon> flagIconCache = new HashMap<>();
    private static final Map<String, String> countryMap = new HashMap<>();

    public static ImageIcon getCountryFlagIcon(String countryCode) {
        ImageIcon flagIcon = flagIconCache.get(countryCode);
        if (flagIcon == null) {
            URL url = CountryUtil.class.getResource("/flags/" + countryCode + (countryCode.endsWith(".png") ? "" : ".png"));
            if (url != null) {
                flagIcon = new javax.swing.ImageIcon(url);
            }
            if (flagIcon == null || flagIcon.getImage() == null) {
                logger.warn("Country flag resource not found: " + countryCode);
                flagIconCache.put(countryCode, flagIcon);
            } else {
                flagIconCache.put(countryCode, flagIcon);
            }
        }
        return flagIcon;
    }

    public static String getCountryName(String countryCode) {
        if (countryMap.isEmpty()) {
            for (int i = 0; i <= CountryComboBox.countryList.length - 1; i++) {
                countryMap.put(CountryComboBox.countryList[i][1], CountryComboBox.countryList[i][0]);
            }
        }
        return countryMap.get(countryCode);
    }
}
