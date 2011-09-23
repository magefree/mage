/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.plugins.card.dl.sources;

import java.util.HashMap;
import java.util.Map;
import org.mage.plugins.card.utils.CardImageUtils;

/**
 *
 * @author North
 */
public class MtgatheringRuImageSource implements CardImageSource {

    private static CardImageSource hqInstance;
    private static CardImageSource mqInstance;
    private static CardImageSource lqInstance;
    private static final Map setsAliases;

    static {
        setsAliases = new HashMap();
        setsAliases.put("M11", "magic2011");
    }
    private String quality;

    public static CardImageSource getHqInstance() {
        if (hqInstance == null) {
            hqInstance = new MtgatheringRuImageSource("hq");
        }
        return hqInstance;
    }

    public static CardImageSource getMqInstance() {
        if (mqInstance == null) {
            mqInstance = new MtgatheringRuImageSource("md");
        }
        return mqInstance;
    }

    public static CardImageSource getLqInstance() {
        if (lqInstance == null) {
            lqInstance = new MtgatheringRuImageSource("lq");
        }
        return lqInstance;
    }

    public MtgatheringRuImageSource(String quality) {
        this.quality = quality;
    }

    @Override
    public String generateURL(Integer collectorId, String cardName, String cardSet, boolean twoFacedCard, boolean secondSide) throws Exception {
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        if (setsAliases.get(cardSet) != null) {
            String set = CardImageUtils.updateSet(cardSet, true);
            String url = "http://mtgathering.ru/scans/en/";
            url += set.toLowerCase() + "/" + quality + "/" + collectorId + ".jpg";
            return url;
        }
        return null;
    }

    @Override
    public String generateTokenUrl(String name, String set) {
        return null;
    }

    @Override
    public Float getAverageSize() {
        if(quality.equalsIgnoreCase("hq"))
            return 80.0f;
        if(quality.equalsIgnoreCase("md"))
            return 30.0f;
        if(quality.equalsIgnoreCase("lq"))
            return 9.0f;
        return 0f;
    }
}
