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

    private static final Map<String, String> xmageSetsToScryfall = ImmutableMap.<String, String>builder().
            put("MBP", "pmei").
            build();


    private static final Set<String> supportedSets = new ArraySet<String>() {
        {
            // Sorted by release date, as listed on Scryfall
            // Commented sets are not available on XMage, most likely because they are non-English sets.

            add("LEA"); // Limited Edition Alpha
            add("LEB"); // Limited Edition Beta
            add("2ED"); // Unlimited Edition
            //add("CEI"); // Intl. Collectors’ Edition
            //add("CED"); // Collectors’ Edition
            add("ARN"); // Arabian Nights
            add("ATQ"); // Antiquities
            //add("FBB"); // Foreign Black Border
            add("3ED"); // Revised Edition
            add("LEG"); // Legends
            add("SUM"); // Summer Magic / Edgar
            add("PDRC"); // Dragon Con
            add("DRK"); // The Dark
            add("FEM"); // Fallen Empires
            add("PLGM"); // DCI Legend Membership
            //add("4BB"); // Fourth Edition Foreign Black Border
            add("PHPR"); // HarperPrism Book Promos
            add("4ED"); // Fourth Edition
            add("ICE"); // Ice Age
            add("CHR"); // Chronicles
            //add("RIN"); // Rinascimento
            //add("REN"); // Renaissance
            add("HML"); // Homelands
            add("PTC"); // Pro Tour Collector Set
            add("ALL"); // Alliances
            add("RQS"); // Rivals Quick Start Set
            add("PARL"); // Arena League 1996
            //add("PRED"); // Redemption Program
            add("MIR"); // Mirage
            add("MGB"); // Multiverse Gift Box
            add("ITP"); // Introductory Two-Player Set
            add("VIS"); // Visions
            add("5ED"); // Fifth Edition
            add("PVAN"); // Vanguard Series
            add("PPOD"); // Portal Demo Game
            add("POR"); // Portal
            add("WTH"); // Weatherlight
            add("WC97"); // World Championship Decks 1997
            add("TMP"); // Tempest
            add("JGP"); // Judge Gift Cards 1998
            add("STH"); // Stronghold
            add("P02"); // Portal Second Age
            add("EXO"); // Exodus
            add("UGL"); // Unglued
            add("WC98"); // World Championship Decks 1998
            add("PALP"); // Asia Pacific Land Program
            add("USG"); // Urza's Saga
            add("ATH"); // Anthologies
            add("PAL99"); // Arena League 1999
            add("G99"); // Judge Gift Cards 1999
            add("ULG"); // Urza's Legacy
            add("6ED"); // Classic Sixth Edition
            add("PTK"); // Portal Three Kingdoms
            add("UDS"); // Urza's Destiny
            add("S99"); // Starter 1999
            add("PGRU"); // Guru
            add("WC99"); // World Championship Decks 1999
            add("PWOS"); // Wizards of the Coast Online Store
            add("MMQ"); // Mercadian Masques
            add("BRB"); // Battle Royale Box Set
            add("PAL00"); // Arena League 2000
            add("G00"); // Judge Gift Cards 2000
            add("FNM"); // Friday Night Magic 2000
            add("PELP"); // European Land Program
            add("NEM"); // Nemesis
            add("S00"); // Starter 2000
            add("PCY"); // Prophecy
            add("WC00"); // World Championship Decks 2000
            add("BTD"); // Beatdown Box Set
            add("INV"); // Invasion
            add("PAL01"); // Arena League 2001
            add("MPR"); // Magic Player Rewards 2001
            add("G01"); // Judge Gift Cards 2001
            add("F01"); // Friday Night Magic 2001
            add("PLS"); // Planeshift
            add("7ED"); // Seventh Edition
            add("APC"); // Apocalypse
            add("WC01"); // World Championship Decks 2001
            add("ODY"); // Odyssey
            add("DKM"); // Deckmasters
            add("PAL02"); // Arena League 2002
            add("PR2"); // Magic Player Rewards 2002
            add("G02"); // Judge Gift Cards 2002
            add("F02"); // Friday Night Magic 2002
            add("TOR"); // Torment
            add("JUD"); // Judgment
            //add("PHJ"); // Hobby Japan Promos
            add("WC02"); // World Championship Decks 2002
            add("ONS"); // Onslaught
            add("PMOA"); // Magic Online Avatars
            add("PAL03"); // Arena League 2003
            add("P03"); // Magic Player Rewards 2003
            add("G03"); // Judge Gift Cards 2003
            add("F03"); // Friday Night Magic 2003
            add("LGN"); // Legions
            add("SCG"); // Scourge
            add("8ED"); // Eighth Edition
            add("WC03"); // World Championship Decks 2003
            add("MRD"); // Mirrodin
            add("PAL04"); // Arena League 2004
            add("P04"); // Magic Player Rewards 2004
            add("G04"); // Judge Gift Cards 2004
            add("F04"); // Friday Night Magic 2004
            add("DST"); // Darksteel
            add("5DN"); // Fifth Dawn
            add("WC04"); // World Championship Decks 2004
            add("CHK"); // Champions of Kamigawa
            add("UNH"); // Unhinged
            //add("PMPS"); // Magic Premiere Shop 2005
            add("PAL05"); // Arena League 2005
            add("P05"); // Magic Player Rewards 2005
            add("G05"); // Judge Gift Cards 2005
            add("F05"); // Friday Night Magic 2005
            add("BOK"); // Betrayers of Kamigawa
            add("SOK"); // Saviors of Kamigawa
            add("9ED"); // Ninth Edition
            //add("PSAL"); // Salvat 2005
            add("RAV"); // Ravnica: City of Guilds
            add("P2HG"); // Two-Headed Giant Tournament
            add("PAL06"); // Arena League 2006
            //add("PMPS06"); // Magic Premiere Shop 2006
            add("PHUK"); // Hachette UK
            add("PGTW"); // Gateway 2006
            add("P06"); // Magic Player Rewards 2006
            add("G06"); // Judge Gift Cards 2006
            add("F06"); // Friday Night Magic 2006
            add("GPT"); // Guildpact
            add("DIS"); // Dissension
            add("CST"); // Coldsnap Theme Decks
            add("CSP"); // Coldsnap
            add("TSP"); // Time Spiral
            add("TSB"); // Time Spiral Timeshifted
            add("PG07"); // Gateway 2007
            //add("PMPS07"); // Magic Premiere Shop 2007
            add("P07"); // Magic Player Rewards 2007
            add("G07"); // Judge Gift Cards 2007
            add("F07"); // Friday Night Magic 2007
            add("PLC"); // Planar Chaos
            add("FUT"); // Future Sight
            add("10E"); // Tenth Edition
            add("P10E"); // Tenth Edition Promos
            add("PSUM"); // Summer of Magic
            add("ME1"); // Masters Edition
            add("PREL"); // Release Events
            add("LRW"); // Lorwyn
            add("DD1"); // Duel Decks: Elves vs. Goblins
            add("PSUS"); // Junior Super Series
            add("PJSE"); // Junior Series Europe
            add("PJAS"); // Junior APAC Series
            //add("PMPS08"); // Magic Premiere Shop 2008
            add("PG08"); // Gateway 2008
            add("P08"); // Magic Player Rewards 2008
            add("G08"); // Judge Gift Cards 2008
            add("F08"); // Friday Night Magic 2008
            add("MOR"); // Morningtide
            add("PCMP"); // Champs and States
            add("P15A"); // 15th Anniversary Cards
            add("SHM"); // Shadowmoor
            //add("PJJT"); // Japan Junior Tournament
            add("EVE"); // Eventide
            add("DRB"); // From the Vault: Dragons
            add("ME2"); // Masters Edition II
            add("PWPN"); // Wizards Play Network 2008
            add("ALA"); // Shards of Alara
            add("DD2"); // Duel Decks: Jace vs. Chandra
            add("PWP09"); // Wizards Play Network 2009
            add("PDTP"); // Duels of the Planeswalkers 2009 Promos
            //add("PMPS09"); // Magic Premiere Shop 2009
            add("P09"); // Magic Player Rewards 2009
            add("G09"); // Judge Gift Cards 2009
            add("F09"); // Friday Night Magic 2009
            add("PBOK"); // Miscellaneous Book Promos
            add("CON"); // Conflux
            add("DDC"); // Duel Decks: Divine vs. Demonic
            add("PPRE"); // Prerelease Events
            add("ARB"); // Alara Reborn
            add("PM10"); // Magic 2010 Promos
            add("M10"); // Magic 2010
            add("V09"); // From the Vault: Exiled
            add("HOP"); // Planechase
            add("ME3"); // Masters Edition III
            add("PZEN"); // Zendikar Promos
            add("ZEN"); // Zendikar
            add("DDD"); // Duel Decks: Garruk vs. Liliana
            add("H09"); // Premium Deck Series: Slivers
            add("PDP10"); // Duels of the Planeswalkers 2010 Promos
            add("PWP10"); // Wizards Play Network 2010
            //add("PMPS10"); // Magic Premiere Shop 2010
            add("P10"); // Magic Player Rewards 2010
            add("G10"); // Judge Gift Cards 2010
            add("F10"); // Friday Night Magic 2010
            add("WWK"); // Worldwake
            add("DDE"); // Duel Decks: Phyrexia vs. the Coalition
            add("ROE"); // Rise of the Eldrazi
            add("DPA"); // Duels of the Planeswalkers
            add("ARC"); // Archenemy
            add("PM11"); // Magic 2011 Promos
            add("M11"); // Magic 2011
            add("PWWK"); // Worldwake Promos
            add("PROE"); // Rise of the Eldrazi Promos
            add("V10"); // From the Vault: Relics
            add("DDF"); // Duel Decks: Elspeth vs. Tezzeret
            add("PSOM"); // Scars of Mirrodin Promos
            add("SOM"); // Scars of Mirrodin
            add("TD0"); // Magic Online Theme Decks
            add("PD2"); // Premium Deck Series: Fire and Lightning
            //add("PMPS11"); // Magic Premiere Shop 2011
            add("PDP11"); // Duels of the Planeswalkers 2011 Promos
            add("PWP11"); // Wizards Play Network 2011
            //add("PS11"); // Salvat 2011
            add("P11"); // Magic Player Rewards 2011
            add("G11"); // Judge Gift Cards 2011
            add("F11"); // Friday Night Magic 2011
            add("ME4"); // Masters Edition IV
            add("PMBS"); // Mirrodin Besieged Promos
            add("MBS"); // Mirrodin Besieged
            add("DDG"); // Duel Decks: Knights vs. Dragons
            add("PNPH"); // New Phyrexia Promos
            add("NPH"); // New Phyrexia
            add("TD2"); // Duel Decks: Mirrodin Pure vs. New Phyrexia
            add("PCMD"); // Commander 2011 Launch Party
            add("CMD"); // Commander 2011
            add("PM12"); // Magic 2012 Promos
            add("M12"); // Magic 2012
            add("V11"); // From the Vault: Legends
            add("DDH"); // Duel Decks: Ajani vs. Nicol Bolas
            add("PISD"); // Innistrad Promos
            add("ISD"); // Innistrad
            add("PD3"); // Premium Deck Series: Graveborn
            add("PIDW"); // IDW Comics 2012
            add("PWP12"); // Wizards Play Network 2012
            add("PDP12"); // Duels of the Planeswalkers 2012 Promos
            add("J12"); // Judge Gift Cards 2012
            add("F12"); // Friday Night Magic 2012
            add("PDKA"); // Dark Ascension Promos
            add("DKA"); // Dark Ascension
            add("DDI"); // Duel Decks: Venser vs. Koth
            add("PHEL"); // Open the Helvault
            add("PAVR"); // Avacyn Restored Promos
            add("AVR"); // Avacyn Restored
            add("PC2"); // Planechase 2012
            add("PM13"); // Magic 2013 Promos
            add("M13"); // Magic 2013
            add("V12"); // From the Vault: Realms
            add("DDJ"); // Duel Decks: Izzet vs. Golgari
            add("RTR"); // Return to Ravnica
            add("PRTR"); // Return to Ravnica Promos
            add("CM1"); // Commander's Arsenal
            add("PDP13"); // Duels of the Planeswalkers 2013 Promos
            add("PI13"); // IDW Comics 2013
            add("J13"); // Judge Gift Cards 2013
            add("F13"); // Friday Night Magic 2013
            add("PGTC"); // Gatecrash Promos
            add("GTC"); // Gatecrash
            add("DDK"); // Duel Decks: Sorin vs. Tibalt
            add("PDGM"); // Dragon's Maze Promos
            add("DGM"); // Dragon's Maze
            add("MMA"); // Modern Masters
            add("PM14"); // Magic 2014 Promos
            add("PSDC"); // San Diego Comic-Con 2013
            add("M14"); // Magic 2014
            add("V13"); // From the Vault: Twenty
            add("DDL"); // Duel Decks: Heroes vs. Monsters
            add("PTHS"); // Theros Promos
            add("THS"); // Theros
            add("C13"); // Commander 2013
            add("PDP14"); // Duels of the Planeswalkers 2014 Promos
            add("PI14"); // IDW Comics 2014
            add("J14"); // Judge Gift Cards 2014
            add("F14"); // Friday Night Magic 2014
            add("PBNG"); // Born of the Gods Promos
            add("THP2"); // Born of the Gods Hero's Path
            add("BNG"); // Born of the Gods
            add("DDM"); // Duel Decks: Jace vs. Vraska
            add("PJOU"); // Journey into Nyx Promos
            add("JOU"); // Journey into Nyx
            add("MD1"); // Modern Event Deck 2014
            add("PLPA"); // Launch Parties
            add("CNS"); // Conspiracy
            add("VMA"); // Vintage Masters
            add("PS14"); // San Diego Comic-Con 2014
            add("PPC1"); // M15 Prerelease Challenge
            add("PM15"); // Magic 2015 Promos
            add("M15"); // Magic 2015
            add("CP1"); // Magic 2015 Clash Pack
            add("V14"); // From the Vault: Annihilation
            add("DDN"); // Duel Decks: Speed vs. Cunning
            add("KTK"); // Khans of Tarkir
            add("PKTK"); // Khans of Tarkir Promos
            add("C14"); // Commander 2014
            //add("PCEL"); // Celebration Cards
            add("JVC"); // Duel Decks Anthology: Jace vs. Chandra
            add("GVL"); // Duel Decks Anthology: Garruk vs. Liliana
            add("EVG"); // Duel Decks Anthology: Elves vs. Goblins
            add("DVD"); // Duel Decks Anthology: Divine vs. Demonic
            add("J15"); // Judge Gift Cards 2015
            add("F15"); // Friday Night Magic 2015
            add("UGIN"); // Ugin's Fate
            add("PURL"); // URL/Convention Promos
            add("CP2"); // Fate Reforged Clash Pack
            add("FRF"); // Fate Reforged
            add("PFRF"); // Fate Reforged Promos
            add("DDO"); // Duel Decks: Elspeth vs. Kiora
            add("DTK"); // Dragons of Tarkir
            add("PDTK"); // Dragons of Tarkir Promos
            add("PTKDF"); // Tarkir Dragonfury
            add("TPR"); // Tempest Remastered
            add("MM2"); // Modern Masters 2015
            add("PS15"); // San Diego Comic-Con 2015
            add("CP3"); // Magic Origins Clash Pack
            add("PORI"); // Magic Origins Promos
            add("ORI"); // Magic Origins
            add("V15"); // From the Vault: Angels
            add("DDP"); // Duel Decks: Zendikar vs. Eldrazi
            add("BFZ"); // Battle for Zendikar
            add("PSS1"); // BFZ Standard Series
            add("EXP"); // Zendikar Expeditions
            add("PBFZ"); // Battle for Zendikar Promos
            add("C15"); // Commander 2015
            add("PZ1"); // Legendary Cube Prize Pack
            add("J16"); // Judge Gift Cards 2016
            add("F16"); // Friday Night Magic 2016
            add("OGW"); // Oath of the Gatewatch
            add("POGW"); // Oath of the Gatewatch Promos
            add("DDQ"); // Duel Decks: Blessed vs. Cursed
            add("SOI"); // Shadows over Innistrad
            add("W16"); // Welcome Deck 2016
            add("PSOI"); // Shadows over Innistrad Promos
            add("EMA"); // Eternal Masters
            add("PEMN"); // Eldritch Moon Promos
            add("EMN"); // Eldritch Moon
            add("V16"); // From the Vault: Lore
            add("CN2"); // Conspiracy: Take the Crown
            add("DDR"); // Duel Decks: Nissa vs. Ob Nixilis
            add("PKLD"); // Kaladesh Promos
            add("KLD"); // Kaladesh
            add("MPS"); // Kaladesh Inventions
            add("PS16"); // San Diego Comic-Con 2016
            add("C16"); // Commander 2016
            add("PCA"); // Planechase Anthology
            add("J17"); // Judge Gift Cards 2017
            add("F17"); // Friday Night Magic 2017
            add("AER"); // Aether Revolt
            add("PAER"); // Aether Revolt Promos
            add("MM3"); // Modern Masters 2017
            add("DDS"); // Duel Decks: Mind vs. Might
            add("W17"); // Welcome Deck 2017
            add("PAKH"); // Amonkhet Promos
            add("AKH"); // Amonkhet
            add("MP2"); // Amonkhet Invocations
            add("CMA"); // Commander Anthology
            add("E01"); // Archenemy: Nicol Bolas
            add("PHOU"); // Hour of Devastation Promos
            add("HOU"); // Hour of Devastation
            add("PS17"); // San Diego Comic-Con 2017
            add("C17"); // Commander 2017
            add("PWCQ"); // World Magic Cup Qualifiers
            add("H17"); // HasCon 2017
            add("HTR"); // 2016 Heroes of the Realm
            add("PXLN"); // Ixalan Promos
            add("XLN"); // Ixalan
            add("PSS2"); // XLN Standard Showdown
            add("G17"); // 2017 Gift Pack
            add("DDT"); // Duel Decks: Merfolk vs. Goblins
            add("PUST"); // Unstable Promos
            add("IMA"); // Iconic Masters
            add("V17"); // From the Vault: Transform
            add("PXTC"); // XLN Treasure Chest
            add("E02"); // Explorers of Ixalan
            add("UST"); // Unstable
            add("J18"); // Judge Gift Cards 2018
            add("PRIX"); // Rivals of Ixalan Promos
            add("RIX"); // Rivals of Ixalan
            add("PNAT"); // Nationals Promos
            add("A25"); // Masters 25
            add("DDU"); // Duel Decks: Elves vs. Inventors
            add("PDOM"); // Dominaria Promos
            add("DOM"); // Dominaria
            add("CM2"); // Commander Anthology Volume II
            add("BBD"); // Battlebond
            add("PBBD"); // Battlebond Promos
            add("PGPX"); // Grand Prix Promos
            add("SS1"); // Signature Spellbook: Jace
            add("GS1"); // Global Series Jiang Yanggu & Mu Yanling
            add("PM19"); // Core Set 2019 Promos
            add("PSS3"); // M19 Standard Showdown
            add("M19"); // Core Set 2019
            add("ANA"); // Arena New Player Experience
            add("PS18"); // San Diego Comic-Con 2018
            //add("HTR17"); // Heroes of the Realm 2017
            add("C18"); // Commander 2018
            add("PGRN"); // Guilds of Ravnica Promos
            add("PRWK"); // GRN Ravnica Weekend
            add("GRN"); // Guilds of Ravnica
            add("GK1"); // GRN Guild Kit
            add("GNT"); // Game Night
            add("G18"); // M19 Gift Pack
            add("PZ2"); // Treasure Chest
            add("PUMA"); // Ultimate Box Topper
            add("UMA"); // Ultimate Masters
            add("PF19"); // MagicFest 2019
            add("PRNA"); // Ravnica Allegiance Promos
            add("RNA"); // Ravnica Allegiance
            add("GK2"); // RNA Guild Kit
            add("PRW2"); // RNA Ravnica Weekend
            add("J19"); // Judge Gift Cards 2019
            add("PRM"); // Magic Online Promos
            add("MED"); // Mythic Edition
            add("WAR"); // War of the Spark
            add("PWAR"); // War of the Spark Promos
            add("PMH1"); // Modern Horizons Promos
            add("MH1"); // Modern Horizons
            add("SS2"); // Signature Spellbook: Gideon
            add("PRES"); // Resale Promos
            add("PPP1"); // M20 Promo Packs
            add("PM20"); // Core Set 2020 Promos
            add("M20"); // Core Set 2020
            add("PS19"); // San Diego Comic-Con 2019
            //add("HTR18"); // Heroes of the Realm 2018
            add("C19"); // Commander 2019
            add("PELD"); // Throne of Eldraine Promos
            add("ELD"); // Throne of Eldraine
            //add("PTG"); // Ponies: The Galloping
            //add("CMB1"); // Mystery Booster Playtest Cards
            add("MB1"); // Mystery Booster
            add("GN2"); // Game Night 2019
            add("HA1"); // Historic Anthology 1
            //add("HHO"); // Happy Holidays
            add("OVNT"); // Vintage Championship
            add("OLGC"); // Legacy Championship
            add("PPRO"); // Pro Tour Promos
            add("PF20"); // MagicFest 2020
            add("J20"); // Judge Gift Cards 2020
            add("PTHB"); // Theros Beyond Death Promos
            add("THB"); // Theros Beyond Death
            add("PWOR"); // World Championship Promos
            //add("PANA"); // MTG Arena Promos
            add("PSLD"); // Secret Lair Drop Promos
            add("UND"); // Unsanctioned
            add("FMB1"); // Mystery Booster Retail Edition Foils
            add("HA2"); // Historic Anthology 2
            add("SLD"); // Secret Lair Drop
            add("PMEI"); // Magazine Inserts
            add("SLU"); // Secret Lair: Ultimate Edition
            //add("SS3"); // Signature Spellbook: Chandra

            // TODO: DuelsOfThePlaneswalkersPromos
            add("DPAP");
            add("GRC");
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
