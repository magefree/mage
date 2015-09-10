package org.mage.plugins.card.dl.sources;

import java.util.HashMap;
import java.util.Map;
import mage.client.dialog.PreferencesDialog;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.utils.CardImageUtils;

/**
 *
 * @author North
 */
public class MagicCardsImageSource implements CardImageSource {

    private static CardImageSource instance = new MagicCardsImageSource();

    private static final Map<String, String> setNameTokenReplacement = new HashMap<String, String>() {
        {
            put("BFZ", "battle-for-zendikar");
            put("FVL", "from-the-vault-legends");
            put("FVD", "from-the-vault-dragons");
            put("FVE", "from-the-vault-exiled");
            put("FVR", "from-the-vault-relics");
            put("V12", "from-the-vault-realms");
            put("V13", "from-the-vault-twenty");
            put("V14", "from-the-vault-annihilation");
            put("V15", "from-the-vault-angels");
            put("CLASH", "clash-pack");
            put("TPR", "tempest-remastered");
            put("ORI", "magic-origins");
            put("MM2", "modern-masters-2015");
            put("PTC", "prerelease-events");
            put("DTK", "dragons-of-tarkir");
            put("GRC", "wpngateway");
            put("MBP", "media-inserts");
            put("MLP", "launch-party");
            put("WMCQ", "world-magic-cup-qualifier");
            put("GPX", "grand-prix");
            put("JR", "judge-gift-program");
            put("MGDC", "magic-game-day-cards");
            put("FNMP", "friday-night-magic");
            put("FRF", "fate-reforged");
            put("C14", "commander-2014");
            put("KTK", "khans-of-tarkir");
            put("VMA", "vintage-masters");
            put("CNS", "conspiracy");
            put("JOU", "journey-into-nyx");
            put("BNG", "born-of-the-gods");
            put("C13", "commander-2013-edition");
            put("THS", "theros");
            put("MMA", "modern-masters");
            put("DGM", "dragons-maze");
            put("GTC", "gatecrash");
            put("RTR", "return-to-ravnica");
            put("AVR", "avacyn-restored");
            put("DKA", "dark-ascension");
            put("ISD", "innistrad");
            put("NPH", "new-phyrexia");
            put("MBS", "mirrodin-besieged");
            put("SOM", "scars-of-mirrodin");
            put("ROE", "rise-of-the-eldrazi");
            put("WWK", "worldwake");
            put("ZEN", "zendikar");
            put("HOP", "planechase");
            put("CMD", "commander");
            put("ARC", "archenemy");
            put("PC2", "planechase-2012-edition");
            put("ARB", "alara-reborn");
            put("CON", "conflux");
            put("ALA", "shards-of-alara");
            put("EVE", "eventide");
            put("SHM", "shadowmoor");
            put("MOR", "morningtide");
            put("LRW", "lorwyn");
            put("10E", "tenth-edition");
            put("CSP", "coldsnap");
            put("MPRP", "magic-player-rewards");
            put("POR", "portal");
            put("PO2", "portal-second-age");
            put("PTK", "portal-three-kingdoms");
            put("M15", "magic-2015");
            put("M14", "magic-2014");
            put("M13", "magic-2013");
            put("M12", "magic-2012");
            put("M11", "magic-2011");
            put("M10", "magic-2010");
            put("EVG", "duel-decks-elves-vs-goblins");
            put("DD2", "duel-decks-jace-vs-chandra");
            put("DD3A", "duel-decks-anthology-divine-vs-demonic");
            put("DD3B", "duel-decks-anthology-elves-vs-goblins");
            put("DD3C", "duel-decks-anthology-garruk-vs-liliana");
            put("DD3D", "duel-decks-anthology-jace-vs-chandra");
            put("DDC", "duel-decks-divine-vs-demonic");
            put("DDD", "duel-decks-garruk-vs-liliana");
            put("DDE", "duel-decks-phyrexia-vs-the-coalition");
            put("DDF", "duel-decks-elspeth-vs-tezzeret");
            put("DDG", "duel-decks-knights-vs-dragons");
            put("DDH", "duel-decks-ajani-vs-nicol-bolas");
            put("DDI", "duel-decks-venser-vs-koth");
            put("DDJ", "duel-decks-izzet-vs-golgari");
            put("DDK", "duel-decks-sorin-vs-tibalt");
            put("DDL", "duel-decks-heroes-vs-monsters");
            put("DDM", "duel-decks-jace-vs-vraska");
            put("DDN", "duel-decks-speed-vs-cunning");
            put("DDO", "duel-decks-elspeth-vs-kiora");
            put("DDP", "duel-decks-zendikar-vs-eldrazi");
        }
        private static final long serialVersionUID = 1L;
    };

    @Override
    public String getSourceName() {
        return "magiccards.info";
    }

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

        String preferedLanguage = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_PREF_LANGUAGE, "en");

        StringBuilder url = new StringBuilder("http://magiccards.info/scans/").append(preferedLanguage).append("/");
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
        if (setNameTokenReplacement.containsKey(card.getSet())) {
            set = setNameTokenReplacement.get(card.getSet());
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
