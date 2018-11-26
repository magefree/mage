package org.mage.plugins.card.dl.sources;

import mage.client.dialog.PreferencesDialog;
import org.mage.plugins.card.images.CardDownloadData;

import java.util.*;

/**
 * @author Quercitron, JayDi85
 */
public enum ScryfallImageSource implements CardImageSource {

    instance;

    private final Set<String> supportedSets;
    private final Map<String, String> languageAliases;

    ScryfallImageSource() {
        // https://scryfall.com/docs/api/languages
        languageAliases = new HashMap<>();
        languageAliases.put("en", "en");
        languageAliases.put("es", "es");
        languageAliases.put("jp", "ja");
        languageAliases.put("it", "it");
        languageAliases.put("fr", "fr");
        languageAliases.put("cn", "zhs"); // Simplified Chinese
        languageAliases.put("de", "de");
        languageAliases.put("ko", "ko");
        languageAliases.put("pt", "pt");
        languageAliases.put("ru", "ru");

        supportedSets = new LinkedHashSet<>();
        // supportedSets.add("PTC"); //
        supportedSets.add("LEA");
        supportedSets.add("LEB");
        supportedSets.add("2ED");
        supportedSets.add("ARN");
        supportedSets.add("ATQ");
        supportedSets.add("3ED");
        supportedSets.add("LEG");
        supportedSets.add("DRK");
        supportedSets.add("FEM");
        supportedSets.add("4ED");
        supportedSets.add("ICE");
        supportedSets.add("CHR");
        supportedSets.add("HML");
        supportedSets.add("ALL");
        supportedSets.add("MIR");
        supportedSets.add("VIS");
        supportedSets.add("5ED");
        supportedSets.add("POR");
        supportedSets.add("WTH");
        supportedSets.add("TMP");
        supportedSets.add("STH");
        supportedSets.add("EXO");
        supportedSets.add("P02");
        supportedSets.add("UGL");
        supportedSets.add("USG");
        supportedSets.add("DD3DVD");
        supportedSets.add("DD3EVG");
        supportedSets.add("DD3GVL");
        supportedSets.add("DD3JVC");

        supportedSets.add("ULG");
        supportedSets.add("6ED");
        supportedSets.add("UDS");
        supportedSets.add("PTK");
        supportedSets.add("S99");
        supportedSets.add("MMQ");
        // supportedSets.add("BRB");Battle Royale Box Set
        supportedSets.add("NEM");
        supportedSets.add("S00");
        supportedSets.add("PCY");
        supportedSets.add("INV");
        // supportedSets.add("BTD"); // Beatdown Boxset
        supportedSets.add("PLS");
        supportedSets.add("7ED");
        supportedSets.add("APC");
        supportedSets.add("ODY");
        // supportedSets.add("DKM"); // Deckmasters 2001
        supportedSets.add("TOR");
        supportedSets.add("JUD");
        supportedSets.add("ONS");
        supportedSets.add("LGN");
        supportedSets.add("SCG");
        supportedSets.add("8ED");
        supportedSets.add("MRD");
        supportedSets.add("DST");
        supportedSets.add("5DN");
        supportedSets.add("CHK");
        supportedSets.add("UNH");
        supportedSets.add("BOK");
        supportedSets.add("SOK");
        supportedSets.add("9ED");
        supportedSets.add("RAV");
        supportedSets.add("GPT");
        supportedSets.add("DIS");
        supportedSets.add("CSP");
        supportedSets.add("TSP");
        supportedSets.add("TSB");
        supportedSets.add("PLC");
        supportedSets.add("FUT");
        supportedSets.add("10E");
        supportedSets.add("MED");
        supportedSets.add("LRW");
        supportedSets.add("EVG");
        supportedSets.add("MOR");
        supportedSets.add("SHM");
        supportedSets.add("EVE");
        supportedSets.add("DRB");
        supportedSets.add("ME2");
        supportedSets.add("ALA");
        supportedSets.add("DD2");
        supportedSets.add("CON");
        supportedSets.add("DDC");
        supportedSets.add("ARB");
        supportedSets.add("M10");
        // supportedSets.add("TD0"); // Magic Online Deck Series
        supportedSets.add("V09");
        supportedSets.add("HOP");
        supportedSets.add("ME3");
        supportedSets.add("ZEN");
        supportedSets.add("DDD");
        supportedSets.add("H09");
        supportedSets.add("WWK");
        supportedSets.add("DDE");
        supportedSets.add("ROE");
        // duels of the planewalkers:
        supportedSets.add("DPA");
        supportedSets.add("DPAP");
        //
        supportedSets.add("ARC");
        supportedSets.add("M11");
        supportedSets.add("V10");
        supportedSets.add("DDF");
        supportedSets.add("SOM");
        // supportedSets.add("TD0"); // Commander Theme Decks
        supportedSets.add("PD2");
        supportedSets.add("ME4");
        supportedSets.add("MBS");
        supportedSets.add("DDG");
        supportedSets.add("NPH");
        supportedSets.add("CMD");
        supportedSets.add("M12");
        supportedSets.add("V11");
        supportedSets.add("DDH");
        supportedSets.add("ISD");
        supportedSets.add("PD3");
        supportedSets.add("DKA");
        supportedSets.add("DDI");
        supportedSets.add("AVR");
        supportedSets.add("PC2");
        supportedSets.add("M13");
        supportedSets.add("V12");
        supportedSets.add("DDJ");
        supportedSets.add("RTR");
        supportedSets.add("CM1");
        // supportedSets.add("TD2"); // Duel Decks: Mirrodin Pure vs. New Phyrexia
        supportedSets.add("GTC");
        supportedSets.add("DDK");
        supportedSets.add("DGM");
        supportedSets.add("MMA");
        supportedSets.add("M14");
        supportedSets.add("V13");
        supportedSets.add("DDL");
        supportedSets.add("THS");
        supportedSets.add("C13");
        supportedSets.add("BNG");
        supportedSets.add("DDM");
        supportedSets.add("JOU");
        // supportedSets.add("MD1"); // Modern Event Deck
        supportedSets.add("CNS");
        supportedSets.add("VMA");
        supportedSets.add("M15");
        supportedSets.add("V14");
        supportedSets.add("DDN");
        supportedSets.add("KTK");
        supportedSets.add("C14");
        // supportedSets.add("DD3"); // Duel Decks Anthology
        supportedSets.add("FRF");
        supportedSets.add("DDO");
        supportedSets.add("DTK");
        supportedSets.add("TPR");
        supportedSets.add("MM2");
        supportedSets.add("ORI");
        supportedSets.add("V15");
        supportedSets.add("DDP");
        supportedSets.add("BFZ");
        supportedSets.add("EXP");
        supportedSets.add("C15");
        // supportedSets.add("PZ1"); // Legendary Cube
        supportedSets.add("OGW");
        supportedSets.add("DDQ");
        supportedSets.add("W16");
        supportedSets.add("SOI");
        supportedSets.add("EMA");
        supportedSets.add("EMN");
        supportedSets.add("V16");
        supportedSets.add("CN2");
        supportedSets.add("DDR");
        supportedSets.add("KLD");
        supportedSets.add("MPS");
        // supportedSets.add("PZ2");
        supportedSets.add("C16");
        supportedSets.add("PCA");
        supportedSets.add("AER");
        supportedSets.add("MM3");
        supportedSets.add("DDS");
        supportedSets.add("W17");
        supportedSets.add("AKH");
        supportedSets.add("CMA");
        supportedSets.add("E01");
        supportedSets.add("HOU");
        supportedSets.add("C17");
        supportedSets.add("XLN");
        supportedSets.add("DDT");
        supportedSets.add("IMA");
        supportedSets.add("E02");
        supportedSets.add("V17");
        supportedSets.add("UST");
        supportedSets.add("DDU");
        supportedSets.add("RIX");
        supportedSets.add("WMCQ");
        supportedSets.add("PPRO");
        supportedSets.add("A25");
        supportedSets.add("DOM");
        supportedSets.add("BBD");
        supportedSets.add("C18");
        supportedSets.add("CM2");
        supportedSets.add("M19");
        supportedSets.add("GS1");
        supportedSets.add("GRN");
        supportedSets.add("GK1");
        supportedSets.add("GNT");
        supportedSets.add("UMA");
        supportedSets.add("PUMA");
        //
        supportedSets.add("EURO");
        supportedSets.add("GPX");
        supportedSets.add("ATH");
        supportedSets.add("GRC");
        supportedSets.add("ANA");
    }

    @Override
    public CardImageUrls generateURL(CardDownloadData card) throws Exception {

        String preferredLanguage = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_PREF_LANGUAGE, "en");
        String defaultCode = "en";
        String localizedCode = languageAliases.getOrDefault(preferredLanguage, defaultCode);
        // loc example: https://api.scryfall.com/cards/xln/121/ru?format=image

        // WARNING, some cards haven't direct images and uses random GUID:
        // As example: Raging Ravine - https://scryfall.com/card/uma/249/raging-ravine
        // https://img.scryfall.com/cards/large/front/5/4/54f41726-e0bb-4154-a2db-4b68b50f5032.jpg
        String baseUrl = null;
        String alternativeUrl = null;

        // direct links to images (non localization)
        if (baseUrl == null) {

            // set/card/number
            String linkCode1 = card.getSet() + "/" + card.getName() + "/" + card.getCollectorId();
            if (directDownloadLinks.containsKey(linkCode1)) {
                baseUrl = directDownloadLinks.get(linkCode1);
                alternativeUrl = null;
            }

            // set/card
            String linkCode2 = card.getSet() + "/" + card.getName();
            if (directDownloadLinks.containsKey(linkCode2)) {
                baseUrl = directDownloadLinks.get(linkCode2);
                alternativeUrl = null;
            }
        }

        // special card number like "103a" and "U123" already compatible
        if (baseUrl == null && card.isCollectorIdWithStr()) {
            // WARNING, after 2018 it's not compatible and some new sets have GUID files instead card numbers
            // TODO: replace card number links to API calls (need test with lands, alternative images and double faces), replace not working images by direct links
            if (card.getCollectorId().startsWith("U")) {
                // fix for Ultimate Box Topper (PUMA) -- need to use API
                // ignored and go to API call at the end
            } else {
                baseUrl = "https://img.scryfall.com/cards/large/" + localizedCode + "/" + formatSetName(card.getSet()) + "/"
                        + card.getCollectorId() + ".jpg";
                alternativeUrl = "https://img.scryfall.com/cards/large/" + defaultCode + "/" + formatSetName(card.getSet()) + "/"
                        + card.getCollectorId() + ".jpg";
            }
        }

        // double faced cards do not supports by API (need direct link for img)
        // example: https://img.scryfall.com/cards/large/en/xln/173b.jpg
        if (baseUrl == null && card.isTwoFacedCard()) {
            baseUrl = "https://img.scryfall.com/cards/large/" + localizedCode + "/" + formatSetName(card.getSet()) + "/"
                    + card.getCollectorId() + (card.isSecondSide() ? "b" : "a") + ".jpg";
            alternativeUrl = "https://img.scryfall.com/cards/large/" + defaultCode + "/" + formatSetName(card.getSet()) + "/"
                    + card.getCollectorId() + (card.isSecondSide() ? "b" : "a") + ".jpg";
        }

        // basic cards by api call (redirect to img link)
        // example: https://api.scryfall.com/cards/xln/121/en?format=image
        if (baseUrl == null) {
            baseUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet()) + "/"
                    + card.getCollectorId() + "/" + localizedCode + "?format=image";
            alternativeUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet()) + "/"
                    + card.getCollectorId() + "/" + defaultCode + "?format=image";
        }

        return new CardImageUrls(baseUrl, alternativeUrl);
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws Exception {
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
        return 90;
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
        return setName.toLowerCase(Locale.ENGLISH);
    }

    private static final Map<String, String> setNameReplacement = new HashMap<String, String>() {
        {
            put("DD3GVL", "gvl");
            put("DD3JVC", "jvc");
            put("DD3DVD", "dvd");
            put("DD3EVG", "evg");
            put("MPS-AKH", "mp2");
            put("MBP", "pmei");
            put("WMCQ", "pwcq");
            put("EURO", "pelp");
            put("GPX", "pgpx");
        }
    };

    private static final Map<String, String> directDownloadLinks = new HashMap<String, String>() {
        {
            // direct links to download images for special cards

            // Duels of the Planeswalkers Promos -- xmage uses one set (DPAP), but scryfall store it by years
            // 2009 - https://scryfall.com/sets/pdtp
            put("DPAP/Garruk Wildspeaker", "https://img.scryfall.com/cards/large/en/pdtp/1.jpg");
            // 2010 - https://scryfall.com/sets/pdp10
            put("DPAP/Liliana Vess", "https://img.scryfall.com/cards/large/en/pdp10/1.jpg");
            put("DPAP/Nissa Revane", "https://img.scryfall.com/cards/large/en/pdp10/2.jpg");
            // 2011 - https://scryfall.com/sets/pdp11
            put("DPAP/Frost Titan", "https://img.scryfall.com/cards/large/en/pdp11/1.jpg");
            put("DPAP/Grave Titan", "https://img.scryfall.com/cards/large/en/pdp11/2.jpg");
            put("DPAP/Inferno Titan", "https://img.scryfall.com/cards/large/en/pdp11/3.jpg");
            // 2012 - https://scryfall.com/sets/pdp12
            put("DPAP/Primordial Hydra", "https://img.scryfall.com/cards/large/en/pdp12/1.jpg");
            put("DPAP/Serra Avatar", "https://img.scryfall.com/cards/large/en/pdp12/2.jpg");
            put("DPAP/Vampire Nocturnus", "https://img.scryfall.com/cards/large/en/pdp12/3.jpg");
            // 2013 - https://scryfall.com/sets/pdp13
            put("DPAP/Bonescythe Sliver", "https://img.scryfall.com/cards/large/en/pdp13/1.jpg");
            put("DPAP/Ogre Battledriver", "https://img.scryfall.com/cards/large/en/pdp13/2.jpg");
            put("DPAP/Scavenging Ooze", "https://img.scryfall.com/cards/large/en/pdp13/3.jpg");
            // 2014 - https://scryfall.com/sets/pdp14
            put("DPAP/Soul of Ravnica", "https://img.scryfall.com/cards/large/en/pdp14/1.jpg");
            put("DPAP/Soul of Zendikar", "https://img.scryfall.com/cards/large/en/pdp14/2.jpg");

            // Gateway Promos -- xmage uses one set (GRC), but scryfall store it by years
            // 2006 - https://scryfall.com/sets/pgtw
            put("GRC/Fiery Temper", "https://img.scryfall.com/cards/large/en/pgtw/3.jpg");
            put("GRC/Icatian Javelineers", "https://img.scryfall.com/cards/large/en/pgtw/2.jpg");
            put("GRC/Wood Elves", "https://img.scryfall.com/cards/large/en/pgtw/1.jpg");
            // 2007 - https://scryfall.com/sets/pg07
            put("GRC/Boomerang", "https://img.scryfall.com/cards/large/en/pg07/4.jpg");
            put("GRC/Calciderm", "https://img.scryfall.com/cards/large/en/pg07/5.jpg");
            put("GRC/Dauntless Dourbark", "https://img.scryfall.com/cards/large/en/pg07/12.jpg");
            put("GRC/Llanowar Elves", "https://img.scryfall.com/cards/large/en/pg07/9.jpg");
            put("GRC/Mind Stone", "https://img.scryfall.com/cards/large/en/pg07/11.jpg");
            put("GRC/Mogg Fanatic", "https://img.scryfall.com/cards/large/en/pg07/10.jpg");
            put("GRC/Reckless Wurm", "https://img.scryfall.com/cards/large/en/pg07/6.jpg");
            put("GRC/Yixlid Jailer", "https://img.scryfall.com/cards/large/en/pg07/7.jpg");
            put("GRC/Zoetic Cavern", "https://img.scryfall.com/cards/large/en/pg07/8.jpg");
            // 2008a - https://scryfall.com/sets/pg08
            put("GRC/Boggart Ram-Gang", "https://img.scryfall.com/cards/large/en/pg08/17.jpg");
            put("GRC/Cenn's Tactician", "https://img.scryfall.com/cards/large/en/pg08/14.jpg");
            put("GRC/Duergar Hedge-Mage", "https://img.scryfall.com/cards/large/en/pg08/19.jpg");
            put("GRC/Gravedigger", "https://img.scryfall.com/cards/large/en/pg08/16.jpg");
            put("GRC/Lava Axe", "https://img.scryfall.com/cards/large/en/pg08/13.jpg");
            put("GRC/Oona's Blackguard", "https://img.scryfall.com/cards/large/en/pg08/15.jpg");
            put("GRC/Selkie Hedge-Mage", "https://img.scryfall.com/cards/large/en/pg08/20.jpg");
            put("GRC/Wilt-Leaf Cavaliers", "https://img.scryfall.com/cards/large/en/pg08/18.jpg");

            // Wizards Play Network Promos -- xmage uses one set (GRC), but scryfall store it by years
            // 2008b - https://scryfall.com/sets/pwpn
            put("GRC/Sprouting Thrinax", "https://img.scryfall.com/cards/large/en/pwpn/21.jpg");
            put("GRC/Woolly Thoctar", "https://img.scryfall.com/cards/large/en/pwpn/22.jpg");
            // 2009 - https://scryfall.com/sets/pwp09
            put("GRC/Hellspark Elemental", "https://img.scryfall.com/cards/large/en/pwp09/25.jpg");
            put("GRC/Kor Duelist", "https://img.scryfall.com/cards/large/en/pwp09/32.jpg");
            put("GRC/Marisi's Twinclaws", "https://img.scryfall.com/cards/large/en/pwp09/26.jpg");
            put("GRC/Mind Control", "https://img.scryfall.com/cards/large/en/pwp09/30.jpg");
            put("GRC/Path to Exile", "https://img.scryfall.com/cards/large/en/pwp09/24.jpg");
            put("GRC/Rise from the Grave", "https://img.scryfall.com/cards/large/en/pwp09/31.jpg");
            put("GRC/Slave of Bolas", "https://img.scryfall.com/cards/large/en/pwp09/27.jpg");
            put("GRC/Vampire Nighthawk", "https://img.scryfall.com/cards/large/en/pwp09/33.jpg");
            // 2010 - https://scryfall.com/sets/pwp10
            put("GRC/Kor Firewalker", "https://img.scryfall.com/cards/large/en/pwp10/36.jpg");
            put("GRC/Leatherback Baloth", "https://img.scryfall.com/cards/large/en/pwp10/37.jpg");
            put("GRC/Syphon Mind", "https://img.scryfall.com/cards/large/en/pwp10/40.jpg");
            put("GRC/Pathrazer of Ulamog", "https://img.scryfall.com/cards/large/en/pwp10/46.jpg");
            put("GRC/Curse of Wizardry", "https://img.scryfall.com/cards/large/en/pwp10/47.jpg");
            put("GRC/Fling/50", "https://img.scryfall.com/cards/large/en/pwp10/50.jpg"); // same card but different year
            put("GRC/Sylvan Ranger/51", "https://img.scryfall.com/cards/large/en/pwp10/51.jpg"); // same card but different year
            put("GRC/Plague Stinger", "https://img.scryfall.com/cards/large/en/pwp10/59.jpg");
            put("GRC/Golem's Heart", "https://img.scryfall.com/cards/large/en/pwp10/60.jpg");
            put("GRC/Skinrender", "https://img.scryfall.com/cards/large/en/pwp10/63.jpg");
            // 2011 - https://scryfall.com/sets/pwp11
            put("GRC/Auramancer", "https://img.scryfall.com/cards/large/en/pwp11/77.jpg");
            put("GRC/Bloodcrazed Neonate", "https://img.scryfall.com/cards/large/en/pwp11/83.jpg");
            put("GRC/Boneyard Wurm", "https://img.scryfall.com/cards/large/en/pwp11/84.jpg");
            put("GRC/Circle of Flame", "https://img.scryfall.com/cards/large/en/pwp11/78.jpg");
            put("GRC/Curse of the Bloody Tome", "https://img.scryfall.com/cards/large/en/pwp11/80.jpg");
            put("GRC/Fling/69", "https://img.scryfall.com/cards/large/en/pwp11/69.jpg"); // same card but different year
            put("GRC/Master's Call", "https://img.scryfall.com/cards/large/en/pwp11/64.jpg");
            put("GRC/Maul Splicer", "https://img.scryfall.com/cards/large/en/pwp11/72.jpg");
            put("GRC/Plague Myr", "https://img.scryfall.com/cards/large/en/pwp11/65.jpg");
            put("GRC/Shrine of Burning Rage", "https://img.scryfall.com/cards/large/en/pwp11/73.jpg");
            put("GRC/Signal Pest", "https://img.scryfall.com/cards/large/en/pwp11/66.jpg");
            put("GRC/Sylvan Ranger/70", "https://img.scryfall.com/cards/large/en/pwp11/70.jpg"); // same card but different year
            put("GRC/Tormented Soul", "https://img.scryfall.com/cards/large/en/pwp11/76.jpg");
            put("GRC/Vault Skirge", "https://img.scryfall.com/cards/large/en/pwp11/71.jpg");
            // 2012 - https://scryfall.com/sets/pwp12
            put("GRC/Curse of Thirst", "https://img.scryfall.com/cards/large/en/pwp12/81.jpg");
            put("GRC/Gather the Townsfolk", "https://img.scryfall.com/cards/large/en/pwp12/79.jpg");
            put("GRC/Nearheath Stalker", "https://img.scryfall.com/cards/large/en/pwp12/82.jpg");

            // TODO: remove Grand Prix fix after scryfall fix image's link (that's link must be work: https://img.scryfall.com/cards/large/en/pgpx/2016b.jpg )
            put("GPX/Sword of Feast and Famine", "https://img.scryfall.com/cards/large/en/pgpx/1%E2%98%85.jpg");
        }
    };

    @Override
    public ArrayList<String> getSupportedSets() {
        ArrayList<String> supportedSetsCopy = new ArrayList<>();
        supportedSetsCopy.addAll(supportedSets);
        return supportedSetsCopy;
    }
}
