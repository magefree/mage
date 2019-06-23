package org.mage.plugins.card.dl.sources;

import com.google.common.collect.ImmutableMap;
import org.tritonus.share.ArraySet;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author JayDi85
 */
public class ScryfallImageSupportCards {

    private static final Map<String, String> xmageSetsToScryfall = ImmutableMap.<String, String>builder().put("DD3GVL", "gvl").
            put("DD3JVC", "jvc").
            put("DD3DVD", "dvd").
            put("DD3EVG", "evg").
            put("MPS-AKH", "mp2").
            put("MBP", "pmei").
            put("WMCQ", "pwcq").
            put("EURO", "pelp").
            put("GPX", "pgpx").
            put("MED", "me1").
            put("MEDM", "med").build();

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
            add("WAR");
            add("M20");
            //
            add("EURO");
            add("GPX");
            add("ATH");
            add("GRC");
            add("ANA");
            add("G18");
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
            put("DPAP/Garruk Wildspeaker", "https://api.scryfall.com/cards/pdtp/1/en?format=image");
            // 2010 - https://scryfall.com/sets/pdp10
            put("DPAP/Liliana Vess", "https://api.scryfall.com/cards/pdp10/1/en?format=image");
            put("DPAP/Nissa Revane", "https://api.scryfall.com/cards/pdp10/2/en?format=image");
            // 2011 - https://scryfall.com/sets/pdp11
            put("DPAP/Frost Titan", "https://api.scryfall.com/cards/pdp11/1/en?format=image");
            put("DPAP/Grave Titan", "https://api.scryfall.com/cards/pdp11/2/en?format=image");
            put("DPAP/Inferno Titan", "https://api.scryfall.com/cards/pdp11/3/en?format=image");
            // 2012 - https://scryfall.com/sets/pdp12
            put("DPAP/Primordial Hydra", "https://api.scryfall.com/cards/pdp12/1/en?format=image");
            put("DPAP/Serra Avatar", "https://api.scryfall.com/cards/pdp12/2/en?format=image");
            put("DPAP/Vampire Nocturnus", "https://api.scryfall.com/cards/pdp12/3/en?format=image");
            // 2013 - https://scryfall.com/sets/pdp13
            put("DPAP/Bonescythe Sliver", "https://api.scryfall.com/cards/pdp13/1/en?format=image");
            put("DPAP/Ogre Battledriver", "https://api.scryfall.com/cards/pdp13/2/en?format=image");
            put("DPAP/Scavenging Ooze", "https://api.scryfall.com/cards/pdp13/3/en?format=image");
            // 2014 - https://scryfall.com/sets/pdp14
            put("DPAP/Soul of Ravnica", "https://api.scryfall.com/cards/pdp14/1/en?format=image");
            put("DPAP/Soul of Zendikar", "https://api.scryfall.com/cards/pdp14/2/en?format=image");

            // Gateway Promos -- xmage uses one set (GRC), but scryfall store it by years
            // 2006 - https://scryfall.com/sets/pgtw
            put("GRC/Fiery Temper", "https://api.scryfall.com/cards/pgtw/3/en?format=image");
            put("GRC/Icatian Javelineers", "https://api.scryfall.com/cards/pgtw/2/en?format=image");
            put("GRC/Wood Elves", "https://api.scryfall.com/cards/pgtw/1/en?format=image");
            // 2007 - https://scryfall.com/sets/pg07
            put("GRC/Boomerang", "https://api.scryfall.com/cards/pg07/4/en?format=image");
            put("GRC/Calciderm", "https://api.scryfall.com/cards/pg07/5/en?format=image");
            put("GRC/Dauntless Dourbark", "https://api.scryfall.com/cards/pg07/12/en?format=image");
            put("GRC/Llanowar Elves", "https://api.scryfall.com/cards/pg07/9/en?format=image");
            put("GRC/Mind Stone", "https://api.scryfall.com/cards/pg07/11/en?format=image");
            put("GRC/Mogg Fanatic", "https://api.scryfall.com/cards/pg07/10/en?format=image");
            put("GRC/Reckless Wurm", "https://api.scryfall.com/cards/pg07/6/en?format=image");
            put("GRC/Yixlid Jailer", "https://api.scryfall.com/cards/pg07/7/en?format=image");
            put("GRC/Zoetic Cavern", "https://api.scryfall.com/cards/pg07/8/en?format=image");
            // 2008a - https://scryfall.com/sets/pg08
            put("GRC/Boggart Ram-Gang", "https://api.scryfall.com/cards/pg08/17/en?format=image");
            put("GRC/Cenn's Tactician", "https://api.scryfall.com/cards/pg08/14/en?format=image");
            put("GRC/Duergar Hedge-Mage", "https://api.scryfall.com/cards/pg08/19/en?format=image");
            put("GRC/Gravedigger", "https://api.scryfall.com/cards/pg08/16/en?format=image");
            put("GRC/Lava Axe", "https://api.scryfall.com/cards/pg08/13/en?format=image");
            put("GRC/Oona's Blackguard", "https://api.scryfall.com/cards/pg08/15/en?format=image");
            put("GRC/Selkie Hedge-Mage", "https://api.scryfall.com/cards/pg08/20/en?format=image");
            put("GRC/Wilt-Leaf Cavaliers", "https://api.scryfall.com/cards/pg08/18/en?format=image");

            // Wizards Play Network Promos -- xmage uses one set (GRC), but scryfall store it by years
            // 2008b - https://scryfall.com/sets/pwpn
            put("GRC/Sprouting Thrinax", "https://api.scryfall.com/cards/pwpn/21/en?format=image");
            put("GRC/Woolly Thoctar", "https://api.scryfall.com/cards/pwpn/22/en?format=image");
            // 2009 - https://scryfall.com/sets/pwp09
            put("GRC/Hellspark Elemental", "https://api.scryfall.com/cards/pwp09/25/en?format=image");
            put("GRC/Kor Duelist", "https://api.scryfall.com/cards/pwp09/32/en?format=image");
            put("GRC/Marisi's Twinclaws", "https://api.scryfall.com/cards/pwp09/26/en?format=image");
            put("GRC/Mind Control", "https://api.scryfall.com/cards/pwp09/30/en?format=image");
            put("GRC/Path to Exile", "https://api.scryfall.com/cards/pwp09/24/en?format=image");
            put("GRC/Rise from the Grave", "https://api.scryfall.com/cards/pwp09/31/en?format=image");
            put("GRC/Slave of Bolas", "https://api.scryfall.com/cards/pwp09/27/en?format=image");
            put("GRC/Vampire Nighthawk", "https://api.scryfall.com/cards/pwp09/33/en?format=image");
            // 2010 - https://scryfall.com/sets/pwp10
            put("GRC/Kor Firewalker", "https://api.scryfall.com/cards/pwp10/36/en?format=image");
            put("GRC/Leatherback Baloth", "https://api.scryfall.com/cards/pwp10/37/en?format=image");
            put("GRC/Syphon Mind", "https://api.scryfall.com/cards/pwp10/40/en?format=image");
            put("GRC/Pathrazer of Ulamog", "https://api.scryfall.com/cards/pwp10/46/en?format=image");
            put("GRC/Curse of Wizardry", "https://api.scryfall.com/cards/pwp10/47/en?format=image");
            put("GRC/Fling/50", "https://api.scryfall.com/cards/pwp10/50/en?format=image"); // same card but different year
            put("GRC/Sylvan Ranger/51", "https://api.scryfall.com/cards/pwp10/51/en?format=image"); // same card but different year
            put("GRC/Plague Stinger", "https://api.scryfall.com/cards/pwp10/59/en?format=image");
            put("GRC/Golem's Heart", "https://api.scryfall.com/cards/pwp10/60/en?format=image");
            put("GRC/Skinrender", "https://api.scryfall.com/cards/pwp10/63/en?format=image");
            // 2011 - https://scryfall.com/sets/pwp11
            put("GRC/Auramancer", "https://api.scryfall.com/cards/pwp11/77/en?format=image");
            put("GRC/Bloodcrazed Neonate", "https://api.scryfall.com/cards/pwp11/83/en?format=image");
            put("GRC/Boneyard Wurm", "https://api.scryfall.com/cards/pwp11/84/en?format=image");
            put("GRC/Circle of Flame", "https://api.scryfall.com/cards/pwp11/78/en?format=image");
            put("GRC/Curse of the Bloody Tome", "https://api.scryfall.com/cards/pwp11/80/en?format=image");
            put("GRC/Fling/69", "https://api.scryfall.com/cards/pwp11/69/en?format=image"); // same card but different year
            put("GRC/Master's Call", "https://api.scryfall.com/cards/pwp11/64/en?format=image");
            put("GRC/Maul Splicer", "https://api.scryfall.com/cards/pwp11/72/en?format=image");
            put("GRC/Plague Myr", "https://api.scryfall.com/cards/pwp11/65/en?format=image");
            put("GRC/Shrine of Burning Rage", "https://api.scryfall.com/cards/pwp11/73/en?format=image");
            put("GRC/Signal Pest", "https://api.scryfall.com/cards/pwp11/66/en?format=image");
            put("GRC/Sylvan Ranger/70", "https://api.scryfall.com/cards/pwp11/70/en?format=image"); // same card but different year
            put("GRC/Tormented Soul", "https://api.scryfall.com/cards/pwp11/76/en?format=image");
            put("GRC/Vault Skirge", "https://api.scryfall.com/cards/pwp11/71/en?format=image");
            // 2012 - https://scryfall.com/sets/pwp12
            put("GRC/Curse of Thirst", "https://api.scryfall.com/cards/pwp12/81/en?format=image");
            put("GRC/Gather the Townsfolk", "https://api.scryfall.com/cards/pwp12/79/en?format=image");
            put("GRC/Nearheath Stalker", "https://api.scryfall.com/cards/pwp12/82/en?format=image");

            // Spined Wurm print in Starter 2000 is actually from Magazine Inserts
            put("S00/Spined Wurm", "https://api.scryfall.com/cards/pmei/11/en?format=image");
            // Most of the other S00 cards are from 6ED
            // We'll download lands manually because we have multiple arts and XMage has totally different ID's
            put("S00/Forest/49", "https://api.scryfall.com/cards/6ed/347/en?format=image");
            put("S00/Forest/50", "https://api.scryfall.com/cards/6ed/348/en?format=image");
            put("S00/Island/51", "https://api.scryfall.com/cards/6ed/335/en?format=image");
            put("S00/Island/52", "https://api.scryfall.com/cards/6ed/336/en?format=image");
            put("S00/Mountain/53", "https://api.scryfall.com/cards/6ed/343/en?format=image");
            put("S00/Mountain/54", "https://api.scryfall.com/cards/6ed/344/en?format=image");
            put("S00/Plains/55", "https://api.scryfall.com/cards/6ed/331/en?format=image");
            put("S00/Plains/56", "https://api.scryfall.com/cards/6ed/332/en?format=image");
            put("S00/Swamp/57", "https://api.scryfall.com/cards/6ed/339/en?format=image");
            put("S00/Swamp/58", "https://api.scryfall.com/cards/6ed/340/en?format=image");
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
