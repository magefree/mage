package org.mage.plugins.card.dl.sources;

import org.tritonus.share.ArraySet;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author JayDi85
 */
public class ScryfallImageSupportCards {

    private static final Map<String, String> xmageSetsToScryfall = new HashMap<String, String>() {
        {
            // xmage -> scryfall
            put("DD3GVL", "gvl");
            put("DD3JVC", "jvc");
            put("DD3DVD", "dvd");
            put("DD3EVG", "evg");
            put("MPS-AKH", "mp2");
            put("MBP", "pmei");
            put("WMCQ", "pwcq");
            put("EURO", "pelp");
            put("GPX", "pgpx");
            put("MED", "me1");
            put("MEDM", "med");
        }
    };

    private static final Set<String> supportedSets = new ArraySet<String>() {
        {
            // xmage set codes
            // add("PTC"); //
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
            // duels of the planewalkers:
            add("DPA");
            add("DPAP");
            //
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
            // add("PZ2");
            add("C16");
            add("PCA");
            add("AER");
            add("MM3");
            add("DDS");
            add("W17");
            add("AKH");
            add("CMA");
            add("E01");
            add("HOU");
            add("C17");
            add("XLN");
            add("DDT");
            add("IMA");
            add("E02");
            add("V17");
            add("UST");
            add("DDU");
            add("RIX");
            add("WMCQ");
            add("PPRO");
            add("A25");
            add("DOM");
            add("BBD");
            add("C18");
            add("CM2");
            add("M19");
            add("GS1");
            add("GRN");
            add("GK1");
            add("GNT");
            add("UMA");
            add("PUMA");
            add("RNA");
            add("MEDM");
            add("GK2");
            add("MH1");
            //
            add("EURO");
            add("GPX");
            add("ATH");
            add("GRC");
            add("ANA");
        }
    };

    private static final Map<String, String> directDownloadLinks = new HashMap<String, String>() {
        {
            // xmage card -> direct or api link:
            // examples:
            //   direct example: https://img.scryfall.com/cards/large/en/trix/6.jpg
            //   api example: https://api.scryfall.com/cards/trix/6/en?format=image
            //   api example: https://api.scryfall.com/cards/trix/6?format=image
            // api format is primary
            //
            // code form for one card:
            //   set/card_name
            //
            // code form for same name cards (alternative images):
            //   set/card_name/card_number
            //   set/card_name/card_number

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

            // TODO: remove after scryfall add lands to RNA (that's link must works: https://api.scryfall.com/cards/rna/262/en?format=image)
            put("RNA/Plains", "https://api.scryfall.com/cards/grn/260/en?format=image");
            put("RNA/Island", "https://api.scryfall.com/cards/grn/261/en?format=image");
            put("RNA/Swamp", "https://api.scryfall.com/cards/grn/262/en?format=image");
            put("RNA/Mountain", "https://api.scryfall.com/cards/grn/263/en?format=image");
            put("RNA/Forest", "https://api.scryfall.com/cards/grn/264/en?format=image");
        }
    };

    public static String findScryfallSetCode(String xmageCode) {
        return xmageSetsToScryfall.getOrDefault(xmageCode, xmageCode).toLowerCase(Locale.ENGLISH);
    }

    public static Set<String> getSupportedSets() {
        return supportedSets;
    }

    public static String findDirectDownloadLink(String setCode, String cardName, String cardNumber) {

        // set/card/number
        String linkCode1 = setCode + "/" + cardName + "/" + cardNumber;
        if (directDownloadLinks.containsKey(linkCode1)) {
            return directDownloadLinks.get(linkCode1);
        }

        // set/card
        String linkCode2 = setCode + "/" + cardName;
        if (directDownloadLinks.containsKey(linkCode2)) {
            return directDownloadLinks.get(linkCode2);
        }

        // default
        return null;
    }
}
