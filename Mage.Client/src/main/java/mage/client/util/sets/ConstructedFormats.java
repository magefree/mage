package mage.client.util.sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardRepository;

/**
 * Utility class for constructed formats (expansions and other editions).
 *
 * @author nantuko
 */
public class ConstructedFormats {

    private static final String[] constructedFormats = {"- All Sets", "- Standard", "- Extended", "- Modern",
                                                        "* Return to Ravnica Block", "Dragon's Maze", "Gatecrash","Return to Ravnica", 
                                                        "Magic 2013", "Planechase 2012",
                                                        "* Innistrad Block", "Avacyn Restored", "Dark Ascension", "Innistrad", 
                                                        "Magic 2012", "Commander",
                                                        "* Scars of Mirrodin Block", "New Phyrexia", "Mirrodin Besieged", "Scars of Mirrodin", "Magic 2011",
                                                        "* Zendikar Block", "Rise of the Eldrazi", "Worldwake", "Zendikar", 
                                                        "Magic 2010", "Planechase",
                                                        "* Shards of Alara Block", "Alara Reborn", "Conflux", "Shards of Alara",
                                                        "* Shadowmoor Block", "Shadowmoor", "Eventide",
                                                        "* Lorwyn Block", "Lorwyn", "Morningtide",
                                                        "* Time Spiral Block", "Future Sight", "Planar Chaos", "Time Spiral", "Tenth Edition",
                                                        "* Ravnica Block", "Dissension", "Guildpact", "Ravnica: City of Guilds",
                                                        "* Kamigawa Block", "Saviors of Kamigawa", "Betrayers of Kamigawa", "Champions of Kamigawa","Ninth Edition",
                                                        "* Mirrodin Block", "Fifth Dawn", "Darksteel", "Mirrodin",
                                                        "* Onslaught Block", "Scourge", "Legions", "Onslaught","Eighth Edition",
                                                        "* Odyssey Block", "Judgment", "Torment", "Odyssey",
                                                        "* Invasion Block", "Apocalypse", "Planeshift", "Invasion","Seventh Edition",
                                                        "* Masquerade Block", "Prophecy", "Nemesis", "Mercadian Masques",
                                                        "* Urza Block", "Urza's Destiny", "Urza's Legacy", "Urza's Saga", "Sixth Edition",
                                                        "* Tempest Block", "Exodus", "Stronghold", "Tempest",
                                                        "* Mirage Block", "Weatherlight", "Visions", "Mirage", "Fifth Edition",
                                                        "* Ice Age Block", "Coldsnap", "Alliances", "Ice Age", "Fourth Edition",
                                                        "Homelands","Fallen Empires","The Dark","Legends","Antiquities", "Arabian Nights",
                                                        "Revised Edition", "Unlimited Edition", "Limited Edition Beta", "Limited Edition Alpha",
                                                        "Guru",
                                                        "Duel Decks: Elspeth vs. Tezzeret"
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
            return Arrays.asList("ALA");
        }
        if (format.equals("Conflux")) {
            return Arrays.asList("CON");
        }
        if (format.equals("Shards of Alara")) {
            return Arrays.asList("ARB");
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
        if (format.equals("Planechase")) {
            return Arrays.asList("HOP");
        }
        if (format.equals("Commander")) {
            return Arrays.asList("CMD");
        }
        if (format.equals("Planechase 2012")) {
            return Arrays.asList("PC2");
        }

        if (format.equals("Guru")) {
            return Arrays.asList("GUR");
        }
        if (format.equals("Duel Decks: Elspeth vs. Tezzeret")) {
            return Arrays.asList("DDF");
        }
        
        if (format.equals("- Standard")) {
            return standard;
        }
        if (format.equals("- Extended")) {
            return extended;
        }
        if (format.equals("- Modern")) {
            return modern;
        }
        return all;
    }

    private static void buildLists() {
        for (String setCode : CardRepository.instance.getSetCodes()) {
            ExpansionSet set = Sets.findSet(setCode);

            if (set.getReleaseDate().after(standardDate)) {
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

    private static final List<String> standard = new ArrayList<String>();
    private static final Date standardDate = new GregorianCalendar(2011, 9, 29).getTime();

    private static final List<String> extended = new ArrayList<String>();
    private static final Date extendedDate = new GregorianCalendar(2009, 8, 20).getTime();

    private static final List<String> modern = new ArrayList<String>();
    private static final Date modernDate = new GregorianCalendar(2003, 7, 20).getTime();

    // for all sets just return empty list
    private static final List<String> all = new ArrayList<String>();

    static {
        buildLists();
    }
}
