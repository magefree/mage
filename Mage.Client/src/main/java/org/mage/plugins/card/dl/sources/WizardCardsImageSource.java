package org.mage.plugins.card.dl.sources;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mage.plugins.card.images.CardDownloadData;

/**
 *
 * @author North
 */
public class WizardCardsImageSource implements CardImageSource {

    private static CardImageSource instance;
    private static Map<String, String> setsAliases;
    private final Map<String, Map<String, String>> sets;

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new WizardCardsImageSource();
        }
        return instance;
    }

    @Override
    public String getSourceName() {
        return "WOTC Gatherer";
    }

    public WizardCardsImageSource() {
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
        setsAliases.put("ALA", "Shards of Alara");
        setsAliases.put("ALL", "Alliances");
        setsAliases.put("APC", "Apocalypse");
        setsAliases.put("ARB", "Alara Reborn");
        setsAliases.put("ARC", "Archenemy");
        setsAliases.put("ARN", "Arabian Nights");
        setsAliases.put("ATH", "Anthologies");
        setsAliases.put("ATQ", "Antiquities");
        setsAliases.put("AVR", "Avacyn Restored");
        setsAliases.put("BNG", "Born of the Gods");
        setsAliases.put("BOK", "Betrayers of Kamigawa");
        setsAliases.put("BRB", "Battle Royale Box Set");
        setsAliases.put("BTD", "Beatdown Box Set");
        setsAliases.put("C13", "Commander 2013 Edition");
        setsAliases.put("C14", "Commander 2014 Edition");
        setsAliases.put("CHK", "Champions of Kamigawa");
        setsAliases.put("CHR", "Chronicles");
        setsAliases.put("CMD", "Magic: The Gathering-Commander");
        setsAliases.put("CNS", "Magic: The Gathering-Conspiracy");
        setsAliases.put("CON", "Conflux");
        setsAliases.put("CSP", "Coldsnap");
        setsAliases.put("DD2", "Duel Decks: Jace vs. Chandra");
        setsAliases.put("DD3", "Duel Decks Anthology, Divine vs. Demonic^Duel Decks Anthology, Elves vs. Goblins^Duel Decks Anthology, Garruk vs. Liliana^Duel Decks Anthology, Jace vs. Chandra");
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
        setsAliases.put("DGM", "Dragon's Maze");
        setsAliases.put("DIS", "Dissension");
        setsAliases.put("DKA", "Dark Ascension");
        setsAliases.put("DKM", "Deckmasters");
        setsAliases.put("DRK", "The Dark");
        setsAliases.put("DST", "Darksteel");
        setsAliases.put("DTK", "Dragons of Tarkir");
        setsAliases.put("EVE", "Eventide");
        setsAliases.put("EVG", "Duel Decks: Elves vs. Goblins");
        setsAliases.put("EXO", "Exodus");
        setsAliases.put("FEM", "Fallen Empires");
        setsAliases.put("FNMP", "Friday Night Magic");
        setsAliases.put("FRF", "Fate Reforged");
        setsAliases.put("FUT", "Future Sight");
        setsAliases.put("FVD", "From the Vault: Dragons");
        setsAliases.put("FVE", "From the Vault: Exiled");
        setsAliases.put("FVR", "From the Vault: Relics");
        setsAliases.put("GPT", "Guildpact");
        setsAliases.put("GPX", "Grand Prix");
        setsAliases.put("GRC", "WPN Gateway");
        setsAliases.put("GTC", "Gatecrash");
        setsAliases.put("HML", "Homelands");
        setsAliases.put("HOP", "Planechase");
        setsAliases.put("ICE", "Ice Age");
        setsAliases.put("INV", "Invasion");
        setsAliases.put("ISD", "Innistrad");
        setsAliases.put("JOU", "Journey into Nyx");
        setsAliases.put("JR", "Judge Promo");
        setsAliases.put("JUD", "Judgment");
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
        setsAliases.put("MMB", "Modern Masters 2015");
        setsAliases.put("MMQ", "Mercadian Masques");
        setsAliases.put("MOR", "Morningtide");
        setsAliases.put("MPRP", "Magic Player Rewards");
        setsAliases.put("MRD", "Mirrodin");
        setsAliases.put("NMS", "Nemesis");
        setsAliases.put("NPH", "New Phyrexia");
        setsAliases.put("ODY", "Odyssey");
        setsAliases.put("ONS", "Onslaught");
        setsAliases.put("ORI", "Magic Origins");
        setsAliases.put("PC2", "Planechase 2012 Edition");
        setsAliases.put("PCY", "Prophecy");
        setsAliases.put("PD2", "Premium Deck Series: Fire and Lightning");
        setsAliases.put("PDS", "Premium Deck Series: Slivers");
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
        setsAliases.put("SOK", "Saviors of Kamigawa");
        setsAliases.put("SOM", "Scars of Mirrodin");
        setsAliases.put("STH", "Stronghold");
        setsAliases.put("THS", "Theros");
        setsAliases.put("TMP", "Tempest");
        setsAliases.put("TOR", "Torment");
        setsAliases.put("TSB", "Time Spiral 'Timeshifted'");
        setsAliases.put("TSP", "Time Spiral");
        setsAliases.put("UDS", "Urza's Destiny");
        setsAliases.put("UGL", "Unglued");
        setsAliases.put("ULG", "Urza's Legacy");
        setsAliases.put("UNH", "Unhinged");
        setsAliases.put("USG", "Urza's Saga");
        setsAliases.put("VG1", "Vanguard Set 1");
        setsAliases.put("VG2", "Vanguard Set 2");
        setsAliases.put("VG3", "Vanguard Set 3");
        setsAliases.put("VG4", "Vanguard Set 4");
        setsAliases.put("VGO", "MTGO Vanguard");
        setsAliases.put("VIS", "Visions");
        setsAliases.put("VMA", "Vintage Masters");
        setsAliases.put("WMCQ", "World Magic Cup Qualifier");
        setsAliases.put("WTH", "Weatherlight");
        setsAliases.put("WWK", "Worldwake");
        setsAliases.put("ZEN", "Zendikar");
    }

    private Map<String, String> getSetLinks(String cardSet) {
        Map<String, String> setLinks = new HashMap<>();
        try {
            String setNames = setsAliases.get(cardSet);
            for (String setName : setNames.split("\\^")) {
                String URLSetName = URLEncoder.encode(setName, "UTF-8");
                String urlDocument;
                urlDocument = "http://gatherer.wizards.com/Pages/Search/Default.aspx?output=spoiler&method=visual&action=advanced&set=+[%22" + URLSetName + "%22]";
                Document doc = Jsoup.connect(urlDocument).get();
                Elements cardsImages = doc.select("img[src^=../../Handlers/]");
                for (int i = 0; i < cardsImages.size(); i++) {
                    String cardName = normalizeName(cardsImages.get(i).attr("alt"));
                    if (cardName != null && !cardName.isEmpty()) {
                        if (cardName.equals("Forest") || cardName.equals("Swamp") || cardName.equals("Mountain") || cardName.equals("Island") || cardName.equals("Plains")) {
                            int landNumber = 1;
                            while (setLinks.get((cardName + landNumber).toLowerCase()) != null) {
                                landNumber++;
                            }
                            cardName += landNumber;
                        }
                        setLinks.put(cardName.toLowerCase(), cardsImages.get(i).attr("src").substring(5));
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Exception when parsing the wizards page: " + ex.getMessage());
        }
        return setLinks;
    }

    private String normalizeName(String name) {
        return name.replace("\u2014", "-").replace("\u2019", "'")
                .replace("\u00C6", "AE").replace("\u00E6", "ae")
                .replace("\u00C1", "A").replace("\u00E1", "a")
                .replace("\u00C2", "A").replace("\u00E2", "a")
                .replace("\u00D6", "O").replace("\u00F6", "o")
                .replace("\u00DB", "U").replace("\u00FB", "u")
                .replace("\u00DC", "U").replace("\u00FC", "u")
                .replace("\u00E9", "e").replace("&", "//")
                .replace("Hintreland Scourge", "Hinterland Scourge");
    }

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        Integer collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        if (card.isFlippedSide()) { //doesn't support rotated images
            return null;
        }
        String setNames = setsAliases.get(cardSet);
        if (setNames != null) {
            Map<String, String> setLinks = sets.get(cardSet);
            if (setLinks == null) {
                setLinks = getSetLinks(cardSet);
                sets.put(cardSet, setLinks);
            }
            String link = setLinks.get(card.getDownloadName().toLowerCase());
            if (link == null) {
                if (setLinks.size() >= collectorId) {
                    link = setLinks.get(Integer.toString(collectorId - 1));
                } else {
                    link = setLinks.get(Integer.toString(collectorId - 21));
                    if (link != null) {
                        link = link.replace(Integer.toString(collectorId - 20), (Integer.toString(collectorId - 20) + "a"));
                    }
                }
            }
            if (link != null && !link.startsWith("http://")) {
                link = "http://gatherer.wizards.com" + link;
            }
            return link;
        }
        return null;
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) {
        return null;
    }

    @Override
    public Float getAverageSize() {
        return 60.0f;
    }
}
