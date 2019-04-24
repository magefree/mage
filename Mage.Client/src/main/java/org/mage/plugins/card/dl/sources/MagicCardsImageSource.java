package org.mage.plugins.card.dl.sources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import mage.client.dialog.PreferencesDialog;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.utils.CardImageUtils;

/**
 * @author North
 */
public enum MagicCardsImageSource implements CardImageSource {

    instance;

    private static final Set<String> supportedSets = new LinkedHashSet<String>() {
        {
            // add("PTC"); // Prerelease Events
            add("LEA");
            add("LEB");
            add("2ED");
            add("ARN");
            add("ATQ");
            add("3ED");
            add("LEG");
            add("DRK");
            add("FEM");
            add("4ED");
            add("ICE");
            add("CHR");
            add("HML");
            add("ALL");
            add("MIR");
            add("VIS");
            add("5ED");
            add("POR");
            add("WTH");
            add("TMP");
            add("STH");
            add("EXO");
            add("P02");
            add("UGL");
            add("USG");
            add("DD3DVD");
            add("DD3EVG");
            add("DD3GVL");
            add("DD3JVC");

            add("ULG");
            add("6ED");
            add("UDS");
            add("PTK");
            add("S99");
            add("MMQ");
            // add("BRB");Battle Royale Box Set
            add("NEM");
            add("S00");
            add("PCY");
            add("INV");
            // add("BTD"); // Beatdown Boxset
            add("PLS");
            add("7ED");
            add("APC");
            add("ODY");
            // add("DKM"); // Deckmasters 2001
            add("TOR");
            add("JUD");
            add("ONS");
            add("LGN");
            add("SCG");
            add("8ED");
            add("MRD");
            add("DST");
            add("5DN");
            add("CHK");
            add("UNH");
            add("BOK");
            add("SOK");
            add("9ED");
            add("RAV");
            add("GPT");
            add("DIS");
            add("CSP");
            add("TSP");
            add("TSB");
            add("PLC");
            add("FUT");
            add("10E");
            add("MED");
            add("LRW");
            add("EVG");
            add("MOR");
            add("SHM");
            add("EVE");
            add("DRB");
            add("ME2");
            add("ALA");
            add("DD2");
            add("CON");
            add("DDC");
            add("ARB");
            add("M10");
            // add("TD0"); // Magic Online Deck Series
            add("V09");
            add("HOP");
            add("ME3");
            add("ZEN");
            add("DDD");
            add("H09");
            add("WWK");
            add("DDE");
            add("ROE");
            add("DPA");
            add("ARC");
            add("M11");
            add("V10");
            add("DDF");
            add("SOM");
            // add("TD0"); // Commander Theme Decks
            add("PD2");
            add("ME4");
            add("MBS");
            add("DDG");
            add("NPH");
            add("CMD");
            add("M12");
            add("V11");
            add("DDH");
            add("ISD");
            add("PD3");
            add("DKA");
            add("DDI");
            add("AVR");
            add("PC2");
            add("M13");
            add("V12");
            add("DDJ");
            add("RTR");
            add("CM1");
            // add("TD2"); // Duel Decks: Mirrodin Pure vs. New Phyrexia
            add("GTC");
            add("DDK");
            add("DGM");
            add("MMA");
            add("M14");
            add("V13");
            add("DDL");
            add("THS");
            add("C13");
            add("BNG");
            add("DDM");
            add("JOU");
            // add("MD1"); // Modern Event Deck
            add("CNS");
            add("VMA");
            add("M15");
            add("V14");
            add("DDN");
            add("KTK");
            add("C14");
            // add("DD3"); // Duel Decks Anthology
            add("FRF");
            add("DDO");
            add("DTK");
            add("TPR");
            add("MM2");
            add("ORI");
            add("V15");
            add("DDP");
            add("BFZ");
            add("EXP");
            add("C15");
            // add("PZ1"); // Legendary Cube
            add("OGW");
            add("DDQ");
            add("W16");
            add("SOI");
            add("EMA");
            add("EMN");
            add("V16");
            add("CN2");
            add("DDR");
            add("KLD");
            add("MPS");
            // add("PZ2"); // Treasure Chests
            add("C16");
            add("PCA");
            add("AER");
            add("MM3");
            add("DDS");
            add("W17");
            add("AKH");
            add("MPS");
            add("CMA");
            add("E01");
            add("HOU");
            add("C17");
            add("XLN");
            add("DDT");
            add("DDU");
            add("IMA");
            add("E02");
            add("V17");
            add("UST");
            add("RIX");
            add("A25");
            add("DOM");
//        add("CM2");
//        add("M19");
        }
    };

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
            put("DDT", "duel-decks-merfolk-vs-goblin");
            put("DDU", "duel-decks-elves-vs-inventors");
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
            put("W17", "welcome-deck-2017");
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

    @Override
    public String getNextHttpImageUrl() {
        return null;
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        return null;
    }

    @Override
    public CardImageUrls generateURL(CardDownloadData card) throws Exception {
        String collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        String set = CardImageUtils.updateSet(cardSet, true);

        String preferedLanguage = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_PREF_LANGUAGE, "en");

        StringBuilder url = new StringBuilder("http://magiccards.info/scans/").append(preferedLanguage).append('/');
        url.append(set.toLowerCase(Locale.ENGLISH)).append('/').append(collectorId);

        if (card.isTwoFacedCard()) {
            url.append(card.isSecondSide() ? "b" : "a");
        }
        if (card.isSplitCard()) {
            url.append('a');
        }
        if (card.isFlipCard()) {
            if (card.isFlippedSide()) { // download rotated by 180 degree image
                url.append('b');
            } else {
                url.append('a');
            }
        }
        url.append(".jpg");

        return new CardImageUrls(url.toString());
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) {
        String name = card.getName();
        // add type to name if it's not 0
        if (card.getType() > 0) {
            name = name + ' ' + card.getType();
        }
        name = name.replaceAll(" ", "-").replace(",", "").toLowerCase(Locale.ENGLISH);
        String set = "not-supported-set";
        if (setNameTokenReplacement.containsKey(card.getSet())) {
            set = setNameTokenReplacement.get(card.getSet());
        } else {
            set += '-' + card.getSet();
        }
        return new CardImageUrls("http://magiccards.info/extras/token/" + set + '/' + name + ".jpg");
    }

    @Override
    public float getAverageSize() {
        return 70.0f;
    }

    @Override
    public int getTotalImages() {
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return true;
    }

    @Override
    public ArrayList<String> getSupportedSets() {
        ArrayList<String> supportedSetsCopy = new ArrayList<>();
        supportedSetsCopy.addAll(supportedSets);
        return supportedSetsCopy;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }

}
