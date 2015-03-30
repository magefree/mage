package mage.client.util.sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.constants.SetType;

/**
 * Utility class for constructed formats (expansions and other editions).
 *
 * @author nantuko
 */
public class ConstructedFormats {

    private static final GregorianCalendar calendar = new GregorianCalendar();
    

    public static final String ALL = "- All Sets";
    public static final String STANDARD = "- Standard";
    public static final String EXTENDED = "- Extended";
    public static final String MODERN = "- Modern";

    private static final String[] constructedFormats = {
            ALL, STANDARD, EXTENDED, MODERN,
            "* Khans of Tarkir Block", "Khans of Tarkir", "Fate Reforged", "Dragons of Tarkir",
            "* Theros Block", "Theros", "Born of the Gods", "Journey into Nyx",
            "* Return to Ravnica Block", "Return to Ravnica", "Gatecrash", "Dragon's Maze",
            "* Innistrad Block", "Innistrad", "Dark Ascension", "Avacyn Restored",
            "* Scars of Mirrodin Block", "Scars of Mirrodin", "Mirrodin Besieged", "New Phyrexia", 
            "* Zendikar Block", "Zendikar", "Worldwake", "Rise of the Eldrazi",
            "* Shards of Alara Block", "Shards of Alara", "Conflux", "Alara Reborn",
            "* Shadowmoor Block", "Shadowmoor", "Eventide",
            "* Lorwyn Block", "Lorwyn", "Morningtide",
            "* Time Spiral Block", "Time Spiral", "Planar Chaos", "Future Sight",
            "* Ravnica Block", "Ravnica: City of Guilds", "Guildpact", "Dissension",
            "* Kamigawa Block", "Champions of Kamigawa", "Betrayers of Kamigawa", "Saviors of Kamigawa", 
            "* Mirrodin Block", "Mirrodin", "Darksteel", "Fifth Dawn",
            "* Onslaught Block", "Onslaught", "Legions", "Scourge", 
            "* Odyssey Block", "Odyssey", "Torment", "Judgment",
            "* Invasion Block", "Invasion", "Planeshift", "Apocalypse", 
            "* Masquerade Block", "Mercadian Masques", "Nemesis", "Prophecy",
            "* Urza Block", "Urza's Saga", "Urza's Legacy", "Urza's Destiny", 
            "* Tempest Block", "Tempest", "Stronghold", "Exodus",
            "* Mirage Block", "Mirage", "Visions", "Weatherlight", 
            "* Ice Age Block", "Ice Age", "Alliances", "Coldsnap", 
            "Homelands", "Fallen Empires", "The Dark", "Legends", "Antiquities", "Arabian Nights",
            "Magic Origins",
            "Magic 2015",
            "Magic 2014",
            "Magic 2013",
            "Magic 2012",
            "Magic 2011",
            "Magic 2010",
            "Tenth Edition",
            "Ninth Edition",
            "Eighth Edition",
            "Seventh Edition",
            "Sixth Edition",
            "Fifth Edition",
            "Fourth Edition",
            "Revised Edition", "Unlimited Edition", "Limited Edition Beta", "Limited Edition Alpha",
            "Vintage Masters",
            "Conspiracy",
            "Modern Masters 2015",
            "Modern Masters",
            "Archenemy",
            "Commander 2014 Edition",
            "Commander 2013 Edition",
            "Commander",
            "Planechase 2012",
            "Planechase",
            "Portal", "Portal Second Age", "Portal Three Kingdoms","Starter 1999","Starter 2000",

            "Duel Decks: Anthology, Divine vs. Demonic",
            "Duel Decks: Anthology, Elves vs. Goblins",
            "Duel Decks: Anthology, Garruk vs. Liliana",
            "Duel Decks: Anthology, Jace vs. Chandra",
            "Duel Decks: Ajani vs. Nicol Bolas",
            "Duel Decks: Divine vs. Demonic",
            "Duel Decks: Elspeth vs. Kiora",
            "Duel Decks: Elspeth vs. Tezzeret",
            "Duel Decks: Elves vs. Goblins",
            "Duel Decks: Garruk vs. Liliana",
            "Duel Decks: Heroes vs. Monsters",
            "Duel Decks: Izzet vs. Golgari",
            "Duel Decks: Jace vs. Chandra",
            "Duel Decks: Jace vs. Vraska",
            "Duel Decks: Knights vs. Dragons",
            "Duel Decks: Phyrexia vs. the Coalition",
            "Duel Decks: Sorin vs. Tibalt",
            "Duel Decks: Speed vs. Cunning",
            "Duel Decks: Venser vs. Koth",

            "Friday Night Magic",
            "Game Day",
            "Grand Prix",
            "Guru",
            "Judge Promo",
            "Launch Party",
            "Media Inserts",
            "Magic Player Rewards",
            "Prerelease Events",
            "Unhinged",
            "World Magic Cup Qualifier",
            "WPN Gateway",
    };
    
    private ConstructedFormats() {
    }

    public static String[] getTypes() {
        return constructedFormats;
    }

    public static String getDefault() {
        return constructedFormats[1];
    }

    public static List<String> getSetsByFormat(String format) {
        if (format.equals("* Khans of Tarkir Block")) {
            return Arrays.asList("KTK", "FRF", "DTK");
        }
        if (format.equals("Dragons of Tarkir")) {
            return Arrays.asList("DTK");
        }
        if (format.equals("Fate Reforged")) {
            return Arrays.asList("FRF");
        }
        if (format.equals("Khans of Tarkir")) {
            return Arrays.asList("KTK");
        }
        if (format.equals("* Theros Block")) {
            return Arrays.asList("THS", "BNG", "JOU");
        }
        if (format.equals("Journey into Nyx")) {
            return Arrays.asList("JOU");
        }
        if (format.equals("Born of the Gods")) {
            return Arrays.asList("BNG");
        }
        if (format.equals("Theros")) {
            return Arrays.asList("THS");
        }
        if (format.equals("Arabian Nights")) {
            return Arrays.asList("ARN");
        }
        if (format.equals("Antiquities")) {
            return Arrays.asList("ATQ");
        }
        if (format.equals("Legends")) {
            return Arrays.asList("LEG");
        }
        if (format.equals("The Dark")) {
            return Arrays.asList("DRK");
        }
        if (format.equals("Fallen Empires")) {
            return Arrays.asList("FEM");
        }
        if (format.equals("Homelands")) {
            return Arrays.asList("HML");
        }
        if (format.equals("* Ice Age Block")) {
            return Arrays.asList("ICE", "ALL", "CSP");
        }        
        if (format.equals("Ice Age")) {
            return Arrays.asList("ICE");
        }
        if (format.equals("Alliances")) {
            return Arrays.asList("ALL");
        }
        if (format.equals("Coldsnap")) {
            return Arrays.asList("CSP");
        }
        if (format.equals("* Mirage Block")) {
            return Arrays.asList("MIR", "VIS", "WTH");
        }        
        if (format.equals("Mirage")) {
            return Arrays.asList("MIR");
        }
        if (format.equals("Visions")) {
            return Arrays.asList("VIS");
        }
        if (format.equals("Weatherlight")) {
            return Arrays.asList("WTH");
        }
        if (format.equals("* Tempest Block")) {
            return Arrays.asList("TMP", "STH", "EXO");
        }
        if (format.equals("Tempest")) {
            return Arrays.asList("TMP");
        }
        if (format.equals("Stronghold")) {
            return Arrays.asList("STH");
        }
        if (format.equals("Exodus")) {
            return Arrays.asList("EXO");
        }
        if (format.equals("* Urza Block")) {
            return Arrays.asList("USG", "ULG", "UDS");
        }        
        if (format.equals("Urza's Saga")) {
            return Arrays.asList("USG");
        }
        if (format.equals("Urza's Legacy")) {
            return Arrays.asList("ULG");
        }
        if (format.equals("Urza's Destiny")) {
            return Arrays.asList("UDS");
        }
        if (format.equals("* Masquerade Block")) {
            return Arrays.asList("MMQ", "NMS", "PCY");
        }        
        if (format.equals("Mercadian Masques")) {
            return Arrays.asList("MMQ");
        }
        if (format.equals("Nemesis")) {
            return Arrays.asList("NMS");
        }
        if (format.equals("Prophecy")) {
            return Arrays.asList("PCY");
        }
        if (format.equals("* Invasion Block")) {
            return Arrays.asList("INV", "PLS", "APC");
        }        
        if (format.equals("Invasion")) {
            return Arrays.asList("INV");
        }
        if (format.equals("Planeshift")) {
            return Arrays.asList("PLS");
        }
        if (format.equals("Apocalypse")) {
            return Arrays.asList("APC");
        }
        if (format.equals("* Odyssey Block")) {
            return Arrays.asList("ODY", "TOR", "JUD");
        }        
        if (format.equals("Odyssey")) {
            return Arrays.asList("ODY");
        }
        if (format.equals("Torment")) {
            return Arrays.asList("TOR");
        }
        if (format.equals("Judgment")) {
            return Arrays.asList("JUD");
        }
        if (format.equals("* Onslaught Block")) {
            return Arrays.asList("ONS", "LGN", "SCG");
        }        
        if (format.equals("Onslaught")) {
            return Arrays.asList("ONS");
        }
        if (format.equals("Legions")) {
            return Arrays.asList("LGN");
        }
        if (format.equals("Scourge")) {
            return Arrays.asList("SCG");
        }
        if (format.equals("* Mirrodin Block")) {
            return Arrays.asList("MRD", "DST", "5DN");
        }
        if (format.equals("Mirrodin")) {
            return Arrays.asList("MRD");
        }
        if (format.equals("Darksteel")) {
            return Arrays.asList("DST");
        }
        if (format.equals("Fifth Dawn")) {
            return Arrays.asList("5DN");
        }
        if (format.equals("* Kamigawa Block")) {
            return Arrays.asList("CHK", "BOK", "SOK");
        }
        if (format.equals("Champions of Kamigawa")) {
            return Arrays.asList("CHK");
        }
        if (format.equals("Betrayers of Kamigawa")) {
            return Arrays.asList("BOK");
        }
        if (format.equals("Saviors of Kamigawa")) {
            return Arrays.asList("SOK");
        }        
        if (format.equals("* Ravnica Block")) {
            return Arrays.asList("RAV", "GPT", "DIS");
        }
        if (format.equals("Ravnica: City of Guilds")) {
            return Arrays.asList("RAV");
        }
        if (format.equals("Guildpact")) {
            return Arrays.asList("GPT");
        }
        if (format.equals("Dissension")) {
            return Arrays.asList("DIS");
        }        
        if (format.equals("* Time Spiral Block")) {
            return Arrays.asList("TSP", "TSB", "PLC", "FUT");
        }
        if (format.equals("Time Spiral")) {
            return Arrays.asList("TSP", "TSB");
        }
        if (format.equals("Planar Chaos")) {
            return Arrays.asList("PLC");
        }
        if (format.equals("Future Sight")) {
            return Arrays.asList("FUT");
        }
        if (format.equals("* Lorwyn Block")) {
            return Arrays.asList("LRW", "MOR");
        }
        if (format.equals("Lorwyn")) {
            return Arrays.asList("LRW");
        }
        if (format.equals("Morningtide")) {
            return Arrays.asList("MOR");
        }
        if (format.equals("* Shadowmoor Block")) {
            return Arrays.asList("SHM", "EVE");
        }
        if (format.equals("Shadowmoor")) {
            return Arrays.asList("SHM");
        }
        if (format.equals("Eventide")) {
            return Arrays.asList("EVE");
        }
        if (format.equals("* Shards of Alara Block")) {
            return Arrays.asList("ALA", "CON", "ARB");
        }
        if (format.equals("Alara Reborn")) {
            return Arrays.asList("ARB");
        }
        if (format.equals("Conflux")) {
            return Arrays.asList("CON");
        }
        if (format.equals("Shards of Alara")) {
            return Arrays.asList("ALA");
        }
        if (format.equals("* Zendikar Block")) {
            return Arrays.asList("ZEN", "WWK", "ROE");
        }
        if (format.equals("Zendikar")) {
            return Arrays.asList("ZEN");
        }
        if (format.equals("Worldwake")) {
            return Arrays.asList("WWK");
        }
        if (format.equals("Rise of the Eldrazi")) {
            return Arrays.asList("ROE");
        }
        if (format.equals("* Scars of Mirrodin Block")) {
            return Arrays.asList("SOM", "MBS", "NPH");
        }
        if (format.equals("Scars of Mirrodin")) {
            return Arrays.asList("SOM");
        }
        if (format.equals("Mirrodin Besieged")) {
            return Arrays.asList("MBS");
        }
        if (format.equals("New Phyrexia")) {
            return Arrays.asList("NPH");
        }
        if (format.equals("* Innistrad Block")) {
            return Arrays.asList("ISD", "DKA", "AVR");
        }
        if (format.equals("Innistrad")) {
            return Arrays.asList("ISD");
        }
        if (format.equals("Dark Ascension")) {
            return Arrays.asList("DKA");
        }
        if (format.equals("Avacyn Restored")) {
            return Arrays.asList("AVR");
        }        
        if (format.equals("* Return to Ravnica Block")) {
            return Arrays.asList("RTR", "GTC", "DGM");
        }
        if (format.equals("Return to Ravnica")) {
            return Arrays.asList("RTR");
        }                                                               
        if (format.equals("Gatecrash")) {
            return Arrays.asList("GTC");
        }
        if (format.equals("Dragon's Maze")) {
            return Arrays.asList("DGM");
        }
        if (format.equals("Limited Edition Alpha")) {
            return Arrays.asList("LEA");
        }
        if (format.equals("Limited Edition Beta")) {
            return Arrays.asList("LEB");
        }
        if (format.equals("Unlimited Edition")) {
            return Arrays.asList("2ED");
        }
        if (format.equals("Revised Edition")) {
            return Arrays.asList("3ED");
        }
        if (format.equals("Fourth Edition")) {
            return Arrays.asList("4ED");
        }
        if (format.equals("Fifth Edition")) {
            return Arrays.asList("5ED");
        }
        if (format.equals("Sixth Edition")) {
            return Arrays.asList("6ED");
        }
        if (format.equals("Seventh Edition")) {
            return Arrays.asList("7ED");
        }
        if (format.equals("Eighth Edition")) {
            return Arrays.asList("8ED");
        }
        
        if (format.equals("Ninth Edition")) {
            return Arrays.asList("9ED");
        }
        if (format.equals("Tenth Edition")) {
            return Arrays.asList("10E");
        }                                                               
      
        if (format.equals("Magic 2010")) {
            return Arrays.asList("M10");
        }        
        if (format.equals("Magic 2011")) {
            return Arrays.asList("M11");
        }
        if (format.equals("Magic 2012")) {
            return Arrays.asList("M12");
        }
        if (format.equals("Magic 2013")) {
            return Arrays.asList("M13");
        }
        if (format.equals("Magic 2014")) {
            return Arrays.asList("M14");
        }
        if (format.equals("Magic 2015")) {
            return Arrays.asList("M15");
        }
        if (format.equals("Magic Origins")) {
            return Arrays.asList("ORI");
        }
        if (format.equals("Archenemy")) {
            return Arrays.asList("ARC");
        }
        if (format.equals("Planechase")) {
            return Arrays.asList("HOP");
        }
        if (format.equals("Commander")) {
            return Arrays.asList("CMD");
        }
        if (format.equals("Commander 2013 Edition")) {
            return Arrays.asList("C13");
        }
        if (format.equals("Commander 2014 Edition")) {
            return Arrays.asList("C14");
        }
        if (format.equals("Planechase 2012")) {
            return Arrays.asList("PC2");
        }
        if (format.equals("Modern Masters")) {
            return Arrays.asList("MMA");
        }
        if (format.equals("Modern Masters 2015")) {
            return Arrays.asList("MMB");
        }
        if (format.equals("Conspiracy")) {
            return Arrays.asList("CNS");
        }
        if (format.equals("Vintage Masters")) {
            return Arrays.asList("VMA");
        }
        if (format.equals("Friday Night Magic")) {
            return Arrays.asList("FNMP");
        }
        if (format.equals("Game Day")) {
            return Arrays.asList("MGDC");
        }
        if (format.equals("Grand Prix")) {
            return Arrays.asList("GPX");
        }
        if (format.equals("Launch Party")) {
            return Arrays.asList("MLP");
        }
        if (format.equals("Magic Player Rewards")) {
            return Arrays.asList("MPRP");
        }
        if (format.equals("Media Inserts")) {
            return Arrays.asList("MBP");
        }
        if (format.equals("Prerelease Events")) {
            return Arrays.asList("PTC");
        }
        if (format.equals("World Magic Cup Qualifier")) {
            return Arrays.asList("WMCQ");
        }
        if (format.equals("WPN Gateway")) {
            return Arrays.asList("GRC");
        }
        if (format.equals("Guru")) {
            return Arrays.asList("GUR");
        }
        if (format.equals("Judge Promo")) {
            return Arrays.asList("JR");
        }
        if (format.equals("Unhinged")) {
            return Arrays.asList("UNH");
        }
        if (format.equals("Portal")) {
            return Arrays.asList("POR");
        }
        if (format.equals("Portal Second Age")) {
            return Arrays.asList("PO2");
        }
        if (format.equals("Portal Three Kingdoms")) {
            return Arrays.asList("PTK");
        }
        if (format.equals("Starter 1999")) {
            return Arrays.asList("S99");
        }
        if (format.equals("Starter 2000")) {
            return Arrays.asList("S00");
        }
        if (format.equals("Duel Decks: Anthology, Elves vs. Goblins")) {
            return Arrays.asList("DD3");
        }
        if (format.equals("Duel Decks: Anthology, Jace vs. Chandra")) {
            return Arrays.asList("DD3");
        }
        if (format.equals("Duel Decks: Anthology, Divine vs. Demonic")) {
            return Arrays.asList("DD3");
        }
        if (format.equals("Duel Decks: Anthology, Garruk vs. Liliana")) {
            return Arrays.asList("DD3");
        }
        if (format.equals("Duel Decks: Elves vs. Goblins")) {
            return Arrays.asList("EVG");
        }
        if (format.equals("Duel Decks: Jace vs. Chandra")) {
            return Arrays.asList("DD2");
        }
        if (format.equals("Duel Decks: Divine vs. Demonic")) {
            return Arrays.asList("DDC");
        }
        if (format.equals("Duel Decks: Garruk vs. Liliana")) {
            return Arrays.asList("DDD");
        }
        if (format.equals("Duel Decks: Phyrexia vs. the Coalition")) {
            return Arrays.asList("DDE");
        }
        if (format.equals("Duel Decks: Elspeth vs. Kiora")) {
            return Arrays.asList("DDO");
        }
        if (format.equals("Duel Decks: Elspeth vs. Tezzeret")) {
            return Arrays.asList("DDF");
        }
        if (format.equals("Duel Decks: Knights vs. Dragons")) {
            return Arrays.asList("DDG");
        }
        if (format.equals("Duel Decks: Ajani vs. Nicol Bolas")) {
            return Arrays.asList("DDH");
        }
        if (format.equals("Duel Decks: Venser vs. Koth")) {
            return Arrays.asList("DDI");
        }
        if (format.equals("Duel Decks: Izzet vs. Golgari")) {
            return Arrays.asList("DDJ");
        }
        if (format.equals("Duel Decks: Sorin vs. Tibalt")) {
            return Arrays.asList("DDK");
        }
        if (format.equals("Duel Decks: Heroes vs. Monsters")) {
            return Arrays.asList("DDL");
        }
        if (format.equals("Duel Decks: Jace vs. Vraska")) {
            return Arrays.asList("DDM");
        }
        if (format.equals("Duel Decks: Speed vs. Cunning")) {
            return Arrays.asList("DDN");
        }

        if (format.equals(STANDARD)) {
            return standard;
        }
        if (format.equals(EXTENDED)) {
            return extended;
        }
        if (format.equals(MODERN)) {
            return modern;
        }
        return all;
    }

    private static void buildLists() {
        GregorianCalendar cutoff;
        // month is zero based so January = 0
        if (calendar.get(Calendar.MONTH) > 8) {
            cutoff = new GregorianCalendar(calendar.get(Calendar.YEAR) - 1, Calendar.SEPTEMBER, 1);
        }
        else {
            cutoff = new GregorianCalendar(calendar.get(Calendar.YEAR) - 2, Calendar.SEPTEMBER, 1);
        }
        
        for (ExpansionInfo set : ExpansionRepository.instance.getAll()) {
            if (set.getType().equals(SetType.CORE) || set.getType().equals(SetType.EXPANSION)) {
                if (set.getReleaseDate().after(cutoff.getTime())) {
                    standard.add(set.getCode());
                }
                if (set.getReleaseDate().after(extendedDate)) {
                    extended.add(set.getCode());
                }
                if (set.getReleaseDate().after(modernDate)) {
                    modern.add(set.getCode());
                }
            }
        }
    }

    private static final List<String> standard = new ArrayList<>();
    
    private static final List<String> extended = new ArrayList<>();
    private static final Date extendedDate = new GregorianCalendar(2009, 8, 20).getTime();

    private static final List<String> modern = new ArrayList<>();
    private static final Date modernDate = new GregorianCalendar(2003, 7, 20).getTime();

    // for all sets just return empty list
    private static final List<String> all = new ArrayList<>();

    static {
        buildLists();
    }
}
