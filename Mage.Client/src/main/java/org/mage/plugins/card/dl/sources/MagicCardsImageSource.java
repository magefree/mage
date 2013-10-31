package org.mage.plugins.card.dl.sources;

import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.utils.CardImageUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author North
 */
public class MagicCardsImageSource implements CardImageSource {

    private static CardImageSource instance = new MagicCardsImageSource();
    private static final Map<String, String> setNameReplacement = new HashMap<String, String>() {

        {
            put("C13", "commander-2013-edition");
            put("THS", "theros");
            put("M14", "magic-2014");
            put("DDL", "duel-decks-heroes-vs-monsters");
            put("MMA", "modern-masters");
            put("DGM", "dragons-maze");
            put("GTC", "gatecrash");
            put("RTR", "return-to-ravnica");
            put("M13", "magic-2013");
            put("AVR", "avacyn-restored");
            put("DDI", "duel-decks-venser-vs-koth");
            put("DKA", "dark-ascension");
            put("ISD", "innistrad");
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
            put("CMD", "commander");
            put("PC2", "planechase-2012-edition");
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
    public String generateURL(CardDownloadData card) throws Exception {
        Integer collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        String set = CardImageUtils.updateSet(cardSet, true);
        StringBuilder url = new StringBuilder("http://magiccards.info/scans/en/");
        url.append(set.toLowerCase()).append("/").append(collectorId);

        if (card.isTwoFacedCard()) {
            url.append(card.isSecondSide() ? "b" : "a");
        }
        if (card.isSplitCard()) {
            url.append("a");
        }
        if (card.isFlipCard()) {
            if (card.isFlippedSide()) { // download rotated by 180 degree image
                url.append("b");
            } else {
                url.append("a");
            }
        }
        url.append(".jpg");

        return url.toString();
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) {
        String name = card.getName().replaceAll(" ", "-").replace(",", "").toLowerCase();
        String set = "not-supported-set";
        if (setNameReplacement.containsKey(card.getSet())) {
            set = setNameReplacement.get(card.getSet());
        } else {
            set += "-" + card.getSet();
        }
        return "http://magiccards.info/extras/token/" + set + "/" + name + ".jpg";
    }

    @Override
    public Float getAverageSize() {
        return 70.0f;
    }
}
