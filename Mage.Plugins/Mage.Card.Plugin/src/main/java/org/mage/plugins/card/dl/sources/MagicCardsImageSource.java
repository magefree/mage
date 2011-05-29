package org.mage.plugins.card.dl.sources;

import org.mage.plugins.card.utils.CardImageUtils;

/**
 *
 * @author North
 */
public class MagicCardsImageSource implements CardImageSource {

    private static CardImageSource instance = new MagicCardsImageSource();

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new MagicCardsImageSource();
        }
        return instance;
    }

    @Override
    public String generateURL(Integer collectorId, String cardSet) throws Exception {
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        String set = CardImageUtils.updateSet(cardSet, true);
        String url = "http://magiccards.info/scans/en/";
        url += set.toLowerCase() + "/" + collectorId + ".jpg";

        return url;
    }
}
