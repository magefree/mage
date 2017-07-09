package org.mage.plugins.card.dl.sources;

import org.mage.plugins.card.images.CardDownloadData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Quercitron
 *
 */
public enum ScryfallImageSource  implements CardImageSource {

    instance;

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        return "https://api.scryfall.com/cards/" + formatSetName(card.getSet()) + "/" + card.getCollectorId() + "?format=image";
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) throws Exception {
        return null;
    }

    @Override
    public String getNextHttpImageUrl() {
        return null;
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        return null;
    }

    @Override
    public String getSourceName() {
        return "scryfall.com";
    }

    @Override
    public float getAverageSize() {
        return 240;
    }

    @Override
    public int getTotalImages() {
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return false;
    }

    @Override
    public void doPause(String httpImageUrl) {

    }

    private String formatSetName(String setName) {
        if (setNameReplacement.containsKey(setName)) {
            setName = setNameReplacement.get(setName);
        }
        return setName.toLowerCase();
    }

    private static final Map<String, String> setNameReplacement = new HashMap<String, String>() {
        {
            put("DD3GVL", "gvl");
            put("DD3JVC", "jvc");
            put("DD3DVD", "dvd");
            put("DD3EVG", "evg");
            put("MPS-AKH", "mp2");
            put("MBP", "pmei");
        }
    };
}
