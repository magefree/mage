package org.mage.plugins.card.dl.sources;

import java.util.HashMap;
import java.util.Map;
import org.mage.plugins.card.utils.CardImageUtils;

/**
 *
 * @author North
 */
public class MagicCardsImageSource implements CardImageSource {

    private static CardImageSource instance = new MagicCardsImageSource();
    private static final Map<String, String> setNameReplacement = new HashMap<String, String>() {

        {
            put("DDH", "duel-decks-ajani-vs-nicol-bolas");
            put("M12", "magic-2012");
            put("NPH", "new-phyrexia");
            put("MBS", "mirrodin-besieged");
            put("SOM", "scars-of-mirrodin");
            put("M11", "magic-2011");
            put("ROE", "rise-of-the-eldrazi");
            put("PVC", "duel-decks-phyrexia-vs-the-coalition");
            put("WWK", "worldwake");
            put("ZEN", "zendikar");
            put("HOP", "planechase");
            put("M10", "magic-2010");
            put("GVL", "duel-decks-garruk-vs-liliana");
            put("ARB", "alara-reborn");
            put("DVD", "duel-decks-divine-vs-demonic");
            put("CON", "conflux");
            put("JVC", "duel-decks-jace-vs-chandra");
            put("ALA", "shards-of-alara");
            put("EVE", "eventide");
            put("SHM", "shadowmoor");
            put("EVG", "duel-decks-elves-vs-goblins");
            put("MOR", "morningtide");
            put("LRW", "lorwyn");
            put("10E", "tenth-edition");
            put("CSP", "coldsnap");
            put("CHK", "player-rewards-2004");
        }
        private static final long serialVersionUID = 1L;
    };

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new MagicCardsImageSource();
        }
        return instance;
    }

    @Override
    public String generateURL(Integer collectorId, String cardName, String cardSet, boolean twoFacedCard, boolean secondSide) throws Exception {
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        String set = CardImageUtils.updateSet(cardSet, true);
        StringBuilder url = new StringBuilder("http://magiccards.info/scans/en/");
        url.append(set.toLowerCase()).append("/").append(collectorId);

        if (cardSet.equals("ISD")) {
            throw new Exception("ISD is not downloadable from magiccards.info. The ISD images have too bad quality.");
        }

        if (twoFacedCard) {
            url.append(secondSide ? "b" : "a");
        }
        url.append(".jpg");

        return url.toString();
    }

    @Override
    public String generateTokenUrl(String name, String set) {
        String _name = name.replaceAll(" ", "-").toLowerCase();
        String _set = "not-supported-set";
        if (setNameReplacement.containsKey(set)) {
            _set = setNameReplacement.get(set);
        } else {
            _set += "-" + set;
        }
        String url = "http://magiccards.info/extras/token/" + _set + "/" + _name + ".jpg";
        return url;

    }

    @Override
    public Float getAverageSize() {
        return 70.0f;
    }
}
