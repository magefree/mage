package org.mage.plugins.card.dl.sources;

import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.util.CardLanguage;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.utils.CardImageUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author North
 */
public enum WizardCardsImageSource implements CardImageSource {

    instance;

    private static final Logger logger = Logger.getLogger(WizardCardsImageSource.class);

    private final Map<String, String> setsAliases;
    private final Map<CardLanguage, String> languageAliases;
    private final Map<String, Map<String, String>> sets;
    private final Set<String> supportedSets;
    private CardLanguage currentLanguage = CardLanguage.ENGLISH; // working language

    @Override
    public String getSourceName() {
        return "WOTC Gatherer";
    }

    WizardCardsImageSource() {

        languageAliases = new EnumMap<>(CardLanguage.class);
        languageAliases.put(CardLanguage.ENGLISH, "English");
        languageAliases.put(CardLanguage.SPANISH, "Spanish");
        languageAliases.put(CardLanguage.FRENCH, "French");
        languageAliases.put(CardLanguage.GERMAN, "German");
        languageAliases.put(CardLanguage.ITALIAN, "Italian");
        languageAliases.put(CardLanguage.PORTUGUESE, "Portuguese (Brazil)");
        languageAliases.put(CardLanguage.JAPANESE, "Japanese");
        languageAliases.put(CardLanguage.KOREAN, "Korean");
        languageAliases.put(CardLanguage.RUSSIAN, "Russian");
        languageAliases.put(CardLanguage.CHINES_SIMPLE, "Chinese Simplified");
        languageAliases.put(CardLanguage.CHINES_TRADITION, "Chinese Traditional ");

        supportedSets = new LinkedHashSet<>();
        // supportedSets.add("PTC"); // Prerelease Events
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
        //supportedSets.add("DPA");
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
        // supportedSets.add("PZ2"); // Treasure Chests
        supportedSets.add("C16");
        supportedSets.add("PCA");
        supportedSets.add("AER");
        supportedSets.add("MM3");
        supportedSets.add("DDS");
        supportedSets.add("W17");
        supportedSets.add("AKH");
        supportedSets.add("CMA");
        supportedSets.add("CM2"); // Commander Anthology, Vol. II
        supportedSets.add("E01");
        supportedSets.add("HOU");
        supportedSets.add("C17");
        supportedSets.add("XLN");
        supportedSets.add("DDT"); // Duel Decks: Merfolk vs. Goblins
        supportedSets.add("DDU"); // Duel Decks: Elves vs. Inventors
        supportedSets.add("IMA"); // Iconic Msters
        supportedSets.add("E02"); // Explorers of Ixalan
        supportedSets.add("V17"); // From the Vault: Transform
        supportedSets.add("UST"); // Unstable
        supportedSets.add("RIX"); // Rivals of Ixalan
        supportedSets.add("A25"); // Masters 25
        supportedSets.add("DOM"); // Dominaria
        supportedSets.add("BBD"); // Battlebond
        supportedSets.add("GS1"); // Global Series: Jiang Yanggu and Mu Yanling
        supportedSets.add("M19"); // Core 2019
        supportedSets.add("C18"); // Commander 2018
//      supportedSets.add("GRN"); // Guilds of Ravnica
//      supportedSets.add("RNA"); // Ravnica Allegiance

        sets = new HashMap<>();
        setsAliases = new HashMap<>();
        setsAliases.put("2ED", "Unlimited Edition");
        setsAliases.put("10E", "Tenth Edition");
        setsAliases.put("3ED", "Revised Edition");
        setsAliases.put("4ED", "Fourth Edition");
        setsAliases.put("5DN", "Fifth Dawn");
        setsAliases.put("5ED", "Fifth Edition");
        setsAliases.put("6ED", "Classic Sixth Edition");
        setsAliases.put("7ED", "Seventh Edition");
        setsAliases.put("8ED", "Eighth Edition");
        setsAliases.put("9ED", "Ninth Edition");
        setsAliases.put("AER", "Aether Revolt");
        setsAliases.put("AKH", "Amonkhet");
        setsAliases.put("ALA", "Shards of Alara");
        setsAliases.put("ALL", "Alliances");
        setsAliases.put("ANB", "Archenemy: Nicol Bolas");
        setsAliases.put("APC", "Apocalypse");
        setsAliases.put("ARB", "Alara Reborn");
        setsAliases.put("ARC", "Archenemy");
        setsAliases.put("ARN", "Arabian Nights");
        setsAliases.put("ATH", "Anthologies");
        setsAliases.put("ATQ", "Antiquities");
        setsAliases.put("AVR", "Avacyn Restored");
        setsAliases.put("BFZ", "Battle for Zendikar");
        setsAliases.put("BNG", "Born of the Gods");
        setsAliases.put("BOK", "Betrayers of Kamigawa");
        setsAliases.put("BRB", "Battle Royale Box Set");
        setsAliases.put("BTD", "Beatdown Box Set");
        setsAliases.put("C13", "Commander 2013 Edition");
        setsAliases.put("C14", "Commander 2014");
        setsAliases.put("C15", "Commander 2015");
        setsAliases.put("C16", "Commander 2016");
        setsAliases.put("C17", "Commander 2017");
        setsAliases.put("CMA", "Commander Anthology");
        setsAliases.put("CM2", "Commander Anthology 2018");
        setsAliases.put("CHK", "Champions of Kamigawa");
        setsAliases.put("CHR", "Chronicles");
        setsAliases.put("CMD", "Magic: The Gathering-Commander");
        setsAliases.put("CNS", "Magic: The Gathering—Conspiracy");
        setsAliases.put("CN2", "Conspiracy: Take the Crown");
        setsAliases.put("CON", "Conflux");
        setsAliases.put("CSP", "Coldsnap");
        setsAliases.put("DD2", "Duel Decks: Jace vs. Chandra");
        setsAliases.put("DD3DVD", "Duel Decks Anthology, Divine vs. Demonic");
        setsAliases.put("DD3EVG", "Duel Decks Anthology, Elves vs. Goblins");
        setsAliases.put("DD3GVL", "Duel Decks Anthology, Garruk vs. Liliana");
        setsAliases.put("DD3JVC", "Duel Decks Anthology, Jace vs. Chandra");
        setsAliases.put("DDC", "Duel Decks: Divine vs. Demonic");
        setsAliases.put("DDD", "Duel Decks: Garruk vs. Liliana");
        setsAliases.put("DDE", "Duel Decks: Phyrexia vs. the Coalition");
        setsAliases.put("DDF", "Duel Decks: Elspeth vs. Tezzeret");
        setsAliases.put("DDG", "Duel Decks: Knights vs. Dragons");
        setsAliases.put("DDH", "Duel Decks: Ajani vs. Nicol Bolas");
        setsAliases.put("DDI", "Duel Decks: Venser vs. Koth");
        setsAliases.put("DDJ", "Duel Decks: Izzet vs. Golgari");
        setsAliases.put("DDK", "Duel Decks: Sorin vs. Tibalt");
        setsAliases.put("DDL", "Duel Decks: Heroes vs. Monsters");
        setsAliases.put("DDM", "Duel Decks: Jace vs. Vraska");
        setsAliases.put("DDN", "Duel Decks: Speed vs. Cunning");
        setsAliases.put("DDO", "Duel Decks: Elspeth vs. Kiora");
        setsAliases.put("DDP", "Duel Decks: Zendikar vs. Eldrazi");
        setsAliases.put("DDQ", "Duel Decks: Blessed vs. Cursed");
        setsAliases.put("DDR", "Duel Decks: Nissa vs. Ob Nixilis");
        setsAliases.put("DDS", "Duel Decks: Mind vs. Might");
        setsAliases.put("DDT", "Duel Decks: Merfolk vs. Goblins");
        setsAliases.put("DDU", "Duel Decks: Elves vs. Inventors");
        setsAliases.put("DGM", "Dragon's Maze");
        setsAliases.put("DIS", "Dissension");
        setsAliases.put("DKA", "Dark Ascension");
        setsAliases.put("DKM", "Deckmasters");
        setsAliases.put("DRB", "From the Vault: Dragons");
        setsAliases.put("DRK", "The Dark");
        setsAliases.put("DST", "Darksteel");
        setsAliases.put("DTK", "Dragons of Tarkir");
        setsAliases.put("E01", "Archenemy: Nicol Bolas");
        setsAliases.put("EMN", "Eldritch Moon");
        setsAliases.put("EMA", "Eternal Masters");
        setsAliases.put("EVE", "Eventide");
        setsAliases.put("EVG", "Duel Decks: Elves vs. Goblins");
        setsAliases.put("EXO", "Exodus");
        setsAliases.put("FEM", "Fallen Empires");
//        setsAliases.put("FNMP", "Friday Night Magic");
        setsAliases.put("FRF", "Fate Reforged");
        setsAliases.put("FUT", "Future Sight");
        setsAliases.put("GPT", "Guildpact");
        setsAliases.put("GPX", "Grand Prix");
        setsAliases.put("GRC", "WPN Gateway");
        setsAliases.put("GTC", "Gatecrash");
        setsAliases.put("H09", "Premium Deck Series: Slivers");
        setsAliases.put("HML", "Homelands");
        setsAliases.put("HOP", "Planechase");
        setsAliases.put("HOU", "Hour of Devastation");
        setsAliases.put("ICE", "Ice Age");
        setsAliases.put("IMA", "Iconic Masters");
        setsAliases.put("INV", "Invasion");
        setsAliases.put("ISD", "Innistrad");
        setsAliases.put("JOU", "Journey into Nyx");
        setsAliases.put("JR", "Judge Promo");
        setsAliases.put("JUD", "Judgment");
        setsAliases.put("KLD", "Kaladesh");
        setsAliases.put("KTK", "Khans of Tarkir");
        setsAliases.put("LEA", "Limited Edition Alpha");
        setsAliases.put("LEB", "Limited Edition Beta");
        setsAliases.put("LEG", "Legends");
        setsAliases.put("LGN", "Legions");
        setsAliases.put("LRW", "Lorwyn");
        setsAliases.put("M10", "Magic 2010");
        setsAliases.put("M11", "Magic 2011");
        setsAliases.put("M12", "Magic 2012");
        setsAliases.put("M13", "Magic 2013");
        setsAliases.put("M14", "Magic 2014");
        setsAliases.put("M15", "Magic 2015");
        setsAliases.put("MBP", "Media Inserts");
        setsAliases.put("MBS", "Mirrodin Besieged");
        setsAliases.put("ME2", "Masters Edition II");
        setsAliases.put("ME3", "Masters Edition III");
        setsAliases.put("ME4", "Masters Edition IV");
        setsAliases.put("MED", "Masters Edition");
//        setsAliases.put("MGDC", "Game Day");
        setsAliases.put("MIR", "Mirage");
        setsAliases.put("MLP", "Launch Party");
        setsAliases.put("MMA", "Modern Masters");
        setsAliases.put("MM2", "Modern Masters 2015");
        setsAliases.put("MM3", "Modern Masters 2017");
        setsAliases.put("MMQ", "Mercadian Masques");
        setsAliases.put("MOR", "Morningtide");
        setsAliases.put("MPRP", "Magic Player Rewards");
        setsAliases.put("MPS", "Masterpiece Series");
        setsAliases.put("MRD", "Mirrodin");
        setsAliases.put("NEM", "Nemesis");
        setsAliases.put("NPH", "New Phyrexia");
        setsAliases.put("OGW", "Oath of the Gatewatch");
        setsAliases.put("ODY", "Odyssey");
        setsAliases.put("ONS", "Onslaught");
        setsAliases.put("ORI", "Magic Origins");
        setsAliases.put("PC2", "Planechase 2012 Edition");
        setsAliases.put("PCY", "Prophecy");
        setsAliases.put("PD2", "Premium Deck Series: Fire and Lightning");
        setsAliases.put("PLC", "Planar Chaos");
        setsAliases.put("PLS", "Planeshift");
        setsAliases.put("PO2", "Portal Second Age");
        setsAliases.put("POR", "Portal");
        setsAliases.put("PTC", "Prerelease Events");
        setsAliases.put("PTK", "Portal Three Kingdoms");
        setsAliases.put("RAV", "Ravnica: City of Guilds");
        setsAliases.put("ROE", "Rise of the Eldrazi");
        setsAliases.put("RTR", "Return to Ravnica");
        setsAliases.put("S00", "Starter 2000");
        setsAliases.put("S99", "Starter 1999");
        setsAliases.put("SCG", "Scourge");
        setsAliases.put("SHM", "Shadowmoor");
        setsAliases.put("SOI", "Shadows over Innistrad");
        setsAliases.put("SOK", "Saviors of Kamigawa");
        setsAliases.put("SOM", "Scars of Mirrodin");
        setsAliases.put("STH", "Stronghold");
        setsAliases.put("THS", "Theros");
        setsAliases.put("TMP", "Tempest");
        setsAliases.put("TOR", "Torment");
        setsAliases.put("TPR", "Tempest Remastered");
        setsAliases.put("TSB", "Time Spiral \"Timeshifted\"");
        setsAliases.put("TSP", "Time Spiral");
        setsAliases.put("UDS", "Urza's Destiny");
        setsAliases.put("UGL", "Unglued");
        setsAliases.put("ULG", "Urza's Legacy");
        setsAliases.put("UNH", "Unhinged");
        setsAliases.put("USG", "Urza's Saga");
        setsAliases.put("V09", "From the Vault: Exiled");
        setsAliases.put("V10", "From the Vault: Relics");
        setsAliases.put("V11", "From the Vault: Legends");
        setsAliases.put("V12", "From the Vault: Realms");
        setsAliases.put("V13", "From the Vault: Twenty");
        setsAliases.put("V14", "From the Vault: Annihilation (2014)");
        setsAliases.put("V15", "From the Vault: Angels (2015)");
        setsAliases.put("V16", "From the Vault: Lore (2016)");
        setsAliases.put("VG1", "Vanguard Set 1");
        setsAliases.put("VG2", "Vanguard Set 2");
        setsAliases.put("VG3", "Vanguard Set 3");
        setsAliases.put("VG4", "Vanguard Set 4");
        setsAliases.put("VGO", "MTGO Vanguard");
        setsAliases.put("VIS", "Visions");
        setsAliases.put("VMA", "Vintage Masters");
        setsAliases.put("W16", "Welcome Deck 2016");
        setsAliases.put("W17", "Welcome Deck 2017");
        setsAliases.put("WMCQ", "World Magic Cup Qualifier");
        setsAliases.put("WTH", "Weatherlight");
        setsAliases.put("WWK", "Worldwake");
        setsAliases.put("ZEN", "Zendikar");
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
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        return true;
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        String collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        if (card.isFlippedSide()) { //doesn't support rotated images
            return null;
        }

        Map<String, String> setLinks = sets.computeIfAbsent(cardSet, k -> getSetLinks(cardSet));
        if (setLinks == null || setLinks.isEmpty()) {
            return null;
        }
        String searchKey = card.getDownloadName().toLowerCase(Locale.ENGLISH).replace(" ", "").replace("&", "//");
        String link = setLinks.get(searchKey);
        if (link == null) {
            int length = collectorId.length();
            // Try to find card image with added letter (e.g. from Unstable)
            if (Character.isLetter(collectorId.charAt(length - 1))) {
                String key = searchKey + collectorId.charAt(length - 1);
                link = setLinks.get(key);
            }
            // Try to find image with added card number (e.g. basic lands)
            if (link == null) {
                String key = searchKey + collectorId;
                link = setLinks.get(key);
                if (link == null) {
                    int number = Integer.parseInt(collectorId.substring(0, length));
                    if (number > 0) {
                        List<String> l = new ArrayList<>(setLinks.values());
                        if (l.size() >= number) {
                            link = l.get(number - 1);
                        } else {
                            link = l.get(number - 21);
                            if (link != null) {
                                link = link.replace(Integer.toString(number - 20), (Integer.toString(number - 20) + 'a'));
                            }
                        }
                    }
                }
            }
        }
        if (link != null && !link.startsWith("https://")) {
            link = "https://gatherer.wizards.com" + link;
        }

        if (link != null) {
            return new CardImageUrls(link);
        } else {
            return null;
        }
    }

    private Map<String, String> getSetLinks(String cardSet) {
        LinkedHashMap<String, String> setLinks = new LinkedHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            String setNames = setsAliases.get(cardSet);
            if (setNames == null) {
                setNames = Sets.getInstance().get(cardSet).getName();
            }

            for (String setName : setNames.split("\\^")) {
                // String URLSetName = URLEncoder.encode(setName, "UTF-8");
                String URLSetName = setName.replaceAll(" ", "%20");
                int page = 0;
                int firstMultiverseIdLastPage = 0;
                Pages:
                while (page < 999) {
                    String searchUrl = "https://gatherer.wizards.com/Pages/Search/Default.aspx?sort=cn+&page=" + page + "&action=advanced&output=spoiler&method=visual&set=+%5B%22" + URLSetName + "%22%5D";
                    logger.debug("URL: " + searchUrl);
                    Document doc = CardImageUtils.downloadHtmlDocument(searchUrl);
                    Elements cardsImages = doc.select("img[src^=../../Handlers/]");
                    if (cardsImages.isEmpty()) {
                        break;
                    }
                    for (int i = 0; i < cardsImages.size(); i++) {
                        Integer multiverseId = Integer.parseInt(cardsImages.get(i).attr("src").replaceAll("[^\\d]", ""));
                        if (i == 0) {
                            if (multiverseId == firstMultiverseIdLastPage) {
                                break Pages;
                            }
                            firstMultiverseIdLastPage = multiverseId;
                        }
                        String cardName = normalizeName(cardsImages.get(i).attr("alt"));
                        if (cardName != null && !cardName.isEmpty()) {
                            if (cardName.equals("Forest") || cardName.equals("Swamp") || cardName.equals("Mountain") || cardName.equals("Island")
                                    || cardName.equals("Plains") || cardName.equals("Wastes")) {
                                getLandVariations(setLinks, cardSet, multiverseId, cardName);
                            } else {
                                String numberChar = "";
                                int pos1 = cardName.indexOf('(');
                                if (pos1 > 0) {
                                    int pos2 = cardName.indexOf('(', pos1 + 1);
                                    if (pos2 > 0) {
                                        numberChar = cardName.substring(pos2 + 1, pos2 + 2);
                                        cardName = cardName.substring(0, pos1);
                                    }
                                }
                                Integer preferredMultiverseId = getLocalizedMultiverseId(getCurrentLanguage(), multiverseId);
                                setLinks.put(cardName.toLowerCase(Locale.ENGLISH) + numberChar, generateLink(preferredMultiverseId));
                            }
                        }
                    }
                    page++;
                }
            }
        } catch (IOException ex) {
            logger.error("Exception when parsing the wizards page: " + ex.getMessage());
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ie) {
            }
        }

        return setLinks;
    }

    private void getLandVariations(LinkedHashMap<String, String> setLinks, String cardSet, int multiverseId, String cardName) throws IOException, NumberFormatException {
        CardCriteria criteria = new CardCriteria();
        criteria.nameExact(cardName);
        criteria.setCodes(cardSet);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);

        String urlLandDocument = "https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=" + multiverseId;
        Document landDoc = CardImageUtils.downloadHtmlDocument(urlLandDocument);
        Elements variations = landDoc.select("a.variationlink");
        if (!variations.isEmpty()) {
            if (variations.size() > cards.size()) {
                logger.warn("More links for lands than cards in DB found for set: " + cardSet + " Name: " + cardName);
            }
            if (variations.size() < cards.size()) {
                logger.warn("Less links for lands than cards in DB found for set: " + cardSet + " Name: " + cardName);
            }
            int iteration = 0;
            for (Element variation : variations) {
                String colNumb = String.valueOf(iteration);
                if (cards.size() > iteration) {
                    CardInfo cardInfo = cards.get(iteration);
                    if (cardInfo != null) {
                        colNumb = cardInfo.getCardNumber();
                    }
                }
                Integer landMultiverseId = Integer.parseInt(variation.attr("href").replaceAll("[^\\d]", ""));
                setLinks.put((cardName).toLowerCase(Locale.ENGLISH) + colNumb, generateLink(landMultiverseId));
                iteration++;
            }
        } else {
            setLinks.put(cardName.toLowerCase(Locale.ENGLISH), generateLink(multiverseId));
        }
    }

    private static String generateLink(int landMultiverseId) {
        return "/Handlers/Image.ashx?multiverseid=" + landMultiverseId + "&type=card";
    }

    private int getLocalizedMultiverseId(CardLanguage preferredLanguage, Integer multiverseId) throws IOException {
        if (preferredLanguage.equals(CardLanguage.ENGLISH)) {
            return multiverseId;
        }

        String languageName = languageAliases.get(preferredLanguage);
        HashMap<String, Integer> localizedLanguageIds = getlocalizedMultiverseIds(multiverseId);
        if (localizedLanguageIds.containsKey(languageName)) {
            return localizedLanguageIds.get(languageName);
        } else {
            return multiverseId;
        }
    }

    private HashMap<String, Integer> getlocalizedMultiverseIds(Integer englishMultiverseId) throws IOException {
        String cardLanguagesUrl = "https://gatherer.wizards.com/Pages/Card/Languages.aspx?multiverseid=" + englishMultiverseId;
        Document cardLanguagesDoc = CardImageUtils.downloadHtmlDocument(cardLanguagesUrl);
        Elements languageTableRows = cardLanguagesDoc.select("tr.cardItem");
        HashMap<String, Integer> localizedIds = new HashMap<>();
        if (!languageTableRows.isEmpty()) {
            for (Element languageTableRow : languageTableRows) {
                Elements languageTableColumns = languageTableRow.select("td");
                Integer localizedId = Integer.parseInt(languageTableColumns.get(0).select("a").first().attr("href").replaceAll("[^\\d]", ""));
                String languageName = languageTableColumns.get(1).text().trim();
                localizedIds.put(languageName, localizedId);
            }
        }
        return localizedIds;
    }

    private String normalizeName(String name) {
        //Split card
        if (name.contains("//")) {
            if (name.indexOf('(') > 0) {
                name = name.substring(0, name.indexOf('(') - 1);
            }
        }
        //Special timeshifted name
        if (name.startsWith("XX")) {
            name = name.substring(name.indexOf('(') + 1, name.length() - 1);
        }
        return name.replace("\u2014", "-").replace("\u2019", "'")
                .replace("\u00C6", "AE").replace("\u00E6", "ae")
                .replace("\u00C3\u2020", "AE")
                .replace("\u00C1", "A").replace("\u00E1", "a")
                .replace("\u00C2", "A").replace("\u00E2", "a")
                .replace("\u00D6", "O").replace("\u00F6", "o")
                .replace("\u00DB", "U").replace("\u00FB", "u")
                .replace("\u00DC", "U").replace("\u00FC", "u")
                .replace("\u00E9", "e").replace("&", "//")
                .replace(" ", "")
                .replace("Hintreland Scourge", "Hinterland Scourge");

    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) {
        return null;
    }

    @Override
    public float getAverageSize() {
        return 60.0f;
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
    public boolean isLanguagesSupport() {
        return true;
    }

    @Override
    public void setCurrentLanguage(CardLanguage cardLanguage) {
        this.currentLanguage = cardLanguage;
    }

    @Override
    public CardLanguage getCurrentLanguage() {
        return currentLanguage;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }

    @Override
    public ArrayList<String> getSupportedSets() {
        ArrayList<String> supportedSetsCopy = new ArrayList<>();
        supportedSetsCopy.addAll(supportedSets);
        return supportedSetsCopy;
    }

}
