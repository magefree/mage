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
            put("10E", "tenth-edition");
            put("AER", "aether-revolt");
            put("AKH", "amonkhet");
            put("ALA", "shards-of-alara");
            put("ANB", "archenemy-nicol-bolas");
            put("APAC", "asia-pacific-land-program");
            put("APC", "player-rewards-2001");
            put("ARB", "alara-reborn");
            put("ARC", "archenemy");
            put("ARENA", "arena-league");
            put("AVR", "avacyn-restored");
            put("BFZ", "battle-for-zendikar");
            put("BNG", "born-of-the-gods");
            put("C13", "commander-2013-edition");
            put("C14", "commander-2014");
            put("C15", "commander-2015");
            put("C16", "commander-2016");
            put("CLASH", "clash-pack");
            put("CMA", "commander-anthology");
            put("CMA", "commanders-arsenal");
            put("CMD", "commander");
            put("CN2", "conspiracy-take-the-crown");
            put("CNS", "conspiracy");
            put("CON", "conflux");
            put("CP", "champs");
            put("CSP", "coldsnap");
            put("DD2", "duel-decks-jace-vs-chandra");
            put("DD3DVD", "duel-decks-anthology-divine-vs-demonic");
            put("DD3EVG", "duel-decks-anthology-elves-vs-goblins");
            put("DD3GVL", "duel-decks-anthology-garruk-vs-liliana");
            put("DD3JVC", "duel-decks-anthology-jace-vs-chandra");
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
            put("DDQ", "duel-decks-blessed-vs-cursed");
            put("DDR", "duel-decks-nissa-vs-ob-nixilis");
            put("DDS", "duel-decks-mind-vs-might");
            put("DGM", "dragons-maze");
            put("DKA", "dark-ascension");
            put("DRB", "from-the-vault-dragons");
            put("DTK", "dragons-of-tarkir");
            put("EMA", "eternal-masters");
            put("EMN", "eldritch-moon");
            put("EURO", "european-land-program");
            put("EVE", "eventide");
            put("EVG", "duel-decks-elves-vs-goblins");
            put("EXP", "zendikar-expeditions");
            put("FNMP", "friday-night-magic");
            put("FRF", "fate-reforged");
            put("GPX", "grand-prix");
            put("GRC", "wpngateway");
            put("GTC", "gatecrash");
            put("HOP", "planechase");
            put("HOU", "hour-of-devastation");
            put("INV", "player-rewards-2001");
            put("ISD", "innistrad");
            put("JOU", "journey-into-nyx");
            put("JR", "judge-gift-program");
            put("KLD", "kaladesh");
            put("KTK", "khans-of-tarkir");
            put("LRW", "lorwyn");
            put("M10", "magic-2010");
            put("M11", "magic-2011");
            put("M12", "magic-2012");
            put("M13", "magic-2013");
            put("M14", "magic-2014");
            put("M15", "magic-2015");
            put("MBP", "media-inserts");
            put("MBS", "mirrodin-besieged");
            put("MGDC", "magic-game-day-cards");
            put("MLP", "launch-party");
            put("MM2", "modern-masters-2015");
            put("MM3", "modern-masters-2017");
            put("MMA", "modern-masters");
            put("MOR", "morningtide");
            put("MPRP", "magic-player-rewards");
            put("MPS", "masterpiece-series");
            put("NPH", "new-phyrexia");
            put("ODY", "player-rewards-2002");
            put("OGW", "oath-of-the-gatewatch");
            put("ORG", "oath-of-the-gatewatch");
            put("ORI", "magic-origins");
            put("PC2", "planechase-2012-edition");
            put("PO2", "portal-second-age");
            put("PLS", "player-rewards-2001");
            put("POR", "portal");
            put("PTC", "prerelease-events");
            put("PTK", "portal-three-kingdoms");
            put("ROE", "rise-of-the-eldrazi");
            put("RTR", "return-to-ravnica");
            put("SHM", "shadowmoor");
            put("SOI", "shadows-over-innistrad");
            put("SOM", "scars-of-mirrodin");
            put("SUS", "super-series");
            put("THS", "theros");
            put("TPR", "tempest-remastered");
            put("UGIN", "ugins-fate");
            put("V09", "from-the-vault-exiled");
            put("V10", "from-the-vault-relics");
            put("V11", "from-the-vault-legends");
            put("V12", "from-the-vault-realms");
            put("V13", "from-the-vault-twenty");
            put("V14", "from-the-vault-annihilation");
            put("V15", "from-the-vault-angels");
            put("V16", "from-the-vault-lore");
            put("VMA", "vintage-masters");
            put("W16", "welcome-deck-2016");
            put("WMCQ", "world-magic-cup-qualifier");
            put("WWK", "worldwake");
            put("ZEN", "zendikar");
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
    public String getNextHttpImageUrl() {
        return null;
    }
    
    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        return null;
    }

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        String collectorId = card.getCollectorId();
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
        String name = card.getName();
        // add type to name if it's not 0
        if (card.getType() > 0) {
            name = name + " " + card.getType();
        }
        name = name.replaceAll(" ", "-").replace(",", "").toLowerCase();
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
    
    @Override
    public Integer getTotalImages() {
        return -1;
    }
}
