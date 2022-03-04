package org.mage.plugins.card.dl.sources;

import org.tritonus.share.ArraySet;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author JayDi85
 */
public class ScryfallImageSupportCards {

    static final Pattern REGEXP_DIRECT_KEY_SET_CODE_PATTERN = Pattern.compile("(\\w+)\\/", Pattern.MULTILINE);
    static final Pattern REGEXP_DIRECT_KEY_CARD_NAME_PATTERN = Pattern.compile("\\/(.+?)\\/", Pattern.MULTILINE);

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
            //add("PVAN"); // Vanguard Series
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
            //add("PR2"); // Magic Player Rewards 2002
            add("G02"); // Judge Gift Cards 2002
            add("F02"); // Friday Night Magic 2002
            add("TOR"); // Torment
            add("JUD"); // Judgment
            //add("PHJ"); // Hobby Japan Promos
            add("WC02"); // World Championship Decks 2002
            add("ONS"); // Onslaught
            //add("PMOA"); // Magic Online Avatars
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
            add("HHO"); // Happy Holidays
            add("G07"); // Judge Gift Cards 2007
            add("F07"); // Friday Night Magic 2007
            add("PLC"); // Planar Chaos
            add("FUT"); // Future Sight
            add("10E"); // Tenth Edition
            add("P10E"); // Tenth Edition Promos
            add("PSUM"); // Summer of Magic
            add("ME1"); // Masters Edition
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
            add("PALA"); // Shards of Alara Promos
            add("DD2"); // Duel Decks: Jace vs. Chandra
            add("PW09"); // Wizards Play Network 2009
            add("PDTP"); // Duels of the Planeswalkers 2009 Promos
            //add("PMPS09"); // Magic Premiere Shop 2009
            add("P09"); // Magic Player Rewards 2009
            add("G09"); // Judge Gift Cards 2009
            add("F09"); // Friday Night Magic 2009
            add("PBOOK"); // Miscellaneous Book Promos
            add("CON"); // Conflux
            add("DDC"); // Duel Decks: Divine vs. Demonic
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
            add("PW10"); // Wizards Play Network 2010
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
            add("PW11"); // Wizards Play Network 2011
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
            add("PW12"); // Wizards Play Network 2012
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
            //add("THP2"); // Born of the Gods Hero's Path
            add("BNG"); // Born of the Gods
            add("DDM"); // Duel Decks: Jace vs. Vraska
            add("PJOU"); // Journey into Nyx Promos
            add("JOU"); // Journey into Nyx
            add("MD1"); // Modern Event Deck 2014
            add("CNS"); // Conspiracy
            add("VMA"); // Vintage Masters
            add("PS14"); // San Diego Comic-Con 2014
            //add("PPC1"); // M15 Prerelease Challenge
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
            add("PDP15"); // Duels of the Planeswalkers 2015 Promos
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
            add("HTR16"); // 2016 Heroes of the Realm
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
            add("XANA"); // Arena New Player Experience Extras
            add("OANA"); // Arena New Player Experience Cards
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
            add("PANA"); // MTG Arena Promos
            add("UND"); // Unsanctioned
            add("FMB1"); // Mystery Booster Retail Edition Foils
            add("HA2"); // Historic Anthology 2
            add("SLD"); // Secret Lair Drop
            add("PMEI"); // Magazine Inserts
            add("SLU"); // Secret Lair: Ultimate Edition
            add("SS3"); // Signature Spellbook: Chandra
            add("HA3"); // Historic Anthology 3
            // add("TD0"); // Commander Theme Decks
            // add("TD2"); // Duel Decks: Mirrodin Pure vs. New Phyrexia
            // add("MD1"); // Modern Event Deck
            // add("DD3"); // Duel Decks Anthology
            // add("PZ1"); // Legendary Cube
            add("IKO"); // Ikoria: Lair of Behemoths
            add("C20"); // Commander 2020 Edition
            add("M21"); // Core Set 2021
            add("JMP"); // Jumpstart
            add("2XM"); // Double Masters
            add("AKR"); // Amonkhet Remastered
            add("ZNR"); // Zendikar Rising
            add("ZNC"); // Zendikar Rising Commander
            add("ZNE"); // Zendikar Rising Expeditions
            add("KLR"); // Kaladesh Remastered
            add("CMR"); // Commander Legends
            add("CC1"); // Commander Collection: Green
            add("KHM"); // Kaldheim
            add("KHC"); // Kaldheim Commander
            add("TSR"); // Time Spiral Remastered
            add("STX"); // Strixhaven: School of Mages
            add("STA"); // Strixhaven Mystical Archive
            add("HA4"); // Historic Anthology 4
            add("HA5"); // Historic Anthology 5
            add("C21"); // Commander 2021 Edition
            add("MH2"); // Modern Horizons 2
            add("H1R"); // Modern Horizons 1 Timeshifts
            add("AFR"); // Adventures in the Forgotten Realms
            add("AFC"); // Forgotten Realms Commander
            add("J21"); // Jumpstart: Historic Horizons
            add("MID"); // Innistrad: Midnight Hunt
            add("MIC"); // Midnight Hunt Commander
            add("VOW"); // Innistrad: Crimson Vow
            add("VOC"); // Crimson Vow Commander
            add("Y22"); // Alchemy: Innistrad
            add("DBL"); // Innistrad: Double Feature
            add("NEO"); // Kamigawa: Neon Dynasty
            add("NEC"); // Neon Dynasty Commander
            add("SNC"); // Streets of New Capenna
            add("SLX"); // Universes Within
        }
    };

    private static final Map<String, String> directDownloadLinks = new HashMap<String, String>() {
        {
            // xmage card -> api or image link
            // WARNING, try use api links as much as possible (it supports build-in translation)
            //
            // example:
            //   api link: https://api.scryfall.com/cards/trix/6/
            //   image link: https://c1.scryfall.com/file/scryfall-cards/large/back/d/5/d5dfd236-b1da-4552-b94f-ebf6bb9dafdf.jpg
            //
            // key for one card:
            //   set/card_name
            //
            // key for same name cards (alternative images):
            //   set/card_name/card_number_1
            //   set/card_name/card_number_2
            //
            // Cards with non-ASCII collector numbers must use direct download (cause xmage uses different card number)
            // Verify checks must check and show missing data from that list

            // 10E
            put("10E/Air Elemental/64*", "https://api.scryfall.com/cards/10e/64★/");
            put("10E/Anaba Bodyguard/187*", "https://api.scryfall.com/cards/10e/187★/");
            put("10E/Ancestor's Chosen/1*", "https://api.scryfall.com/cards/10e/1★/");
            put("10E/Angel of Mercy/2*", "https://api.scryfall.com/cards/10e/2★/");
            put("10E/Angelic Blessing/3*", "https://api.scryfall.com/cards/10e/3★/");
            put("10E/Angelic Wall/5*", "https://api.scryfall.com/cards/10e/5★/");
            put("10E/Arcane Teachings/188*", "https://api.scryfall.com/cards/10e/188★/");
            put("10E/Ascendant Evincar/127*", "https://api.scryfall.com/cards/10e/127★/");
            put("10E/Avatar of Might/251*", "https://api.scryfall.com/cards/10e/251★/");
            put("10E/Aven Cloudchaser/7*", "https://api.scryfall.com/cards/10e/7★/");
            put("10E/Aven Fisher/68*", "https://api.scryfall.com/cards/10e/68★/");
            put("10E/Aven Windreader/69*", "https://api.scryfall.com/cards/10e/69★/");
            put("10E/Benalish Knight/11*", "https://api.scryfall.com/cards/10e/11★/");
            put("10E/Birds of Paradise/252*", "https://api.scryfall.com/cards/10e/252★/");
            put("10E/Blanchwood Armor/253*", "https://api.scryfall.com/cards/10e/253★/");
            put("10E/Bog Wraith/130*", "https://api.scryfall.com/cards/10e/130★/");
            put("10E/Canopy Spider/254*", "https://api.scryfall.com/cards/10e/254★/");
            put("10E/Cloud Elemental/74*", "https://api.scryfall.com/cards/10e/74★/");
            put("10E/Cloud Sprite/75*", "https://api.scryfall.com/cards/10e/75★/");
            put("10E/Coat of Arms/316*", "https://api.scryfall.com/cards/10e/316★/");
            put("10E/Colossus of Sardia/317*", "https://api.scryfall.com/cards/10e/317★/");
            put("10E/Contaminated Bond/132*", "https://api.scryfall.com/cards/10e/132★/");
            put("10E/Dehydration/78*", "https://api.scryfall.com/cards/10e/78★/");
            put("10E/Dragon Roost/197*", "https://api.scryfall.com/cards/10e/197★/");
            put("10E/Drudge Skeletons/139*", "https://api.scryfall.com/cards/10e/139★/");
            put("10E/Dusk Imp/140*", "https://api.scryfall.com/cards/10e/140★/");
            put("10E/Elvish Champion/261*", "https://api.scryfall.com/cards/10e/261★/");
            put("10E/Faerie Conclave/351*", "https://api.scryfall.com/cards/10e/351★/");
            put("10E/Fear/142*", "https://api.scryfall.com/cards/10e/142★/");
            put("10E/Field Marshal/15*", "https://api.scryfall.com/cards/10e/15★/");
            put("10E/Firebreathing/200*", "https://api.scryfall.com/cards/10e/200★/");
            put("10E/Fog Elemental/85*", "https://api.scryfall.com/cards/10e/85★/");
            put("10E/Furnace Whelp/205*", "https://api.scryfall.com/cards/10e/205★/");
            put("10E/Ghitu Encampment/353*", "https://api.scryfall.com/cards/10e/353★/");
            put("10E/Giant Spider/267*", "https://api.scryfall.com/cards/10e/267★/");
            put("10E/Goblin King/207*", "https://api.scryfall.com/cards/10e/207★/");
            put("10E/Goblin Sky Raider/210*", "https://api.scryfall.com/cards/10e/210★/");
            put("10E/Heart of Light/19*", "https://api.scryfall.com/cards/10e/19★/");
            put("10E/Holy Strength/22*", "https://api.scryfall.com/cards/10e/22★/");
            put("10E/Hypnotic Specter/151*", "https://api.scryfall.com/cards/10e/151★/");
            put("10E/Kamahl, Pit Fighter/214*", "https://api.scryfall.com/cards/10e/214★/");
            put("10E/Leonin Scimitar/331*", "https://api.scryfall.com/cards/10e/331★/");
            put("10E/Lightning Elemental/217*", "https://api.scryfall.com/cards/10e/217★/");
            put("10E/Lord of the Pit/154*", "https://api.scryfall.com/cards/10e/154★/");
            put("10E/Loxodon Warhammer/332*", "https://api.scryfall.com/cards/10e/332★/");
            put("10E/Lure/276*", "https://api.scryfall.com/cards/10e/276★/");
            put("10E/Mahamoti Djinn/90*", "https://api.scryfall.com/cards/10e/90★/");
            put("10E/Mantis Engine/333*", "https://api.scryfall.com/cards/10e/333★/");
            put("10E/March of the Machines/91*", "https://api.scryfall.com/cards/10e/91★/");
            put("10E/Might Weaver/278*", "https://api.scryfall.com/cards/10e/278★/");
            //put("10E/Mind Bend/93*", "https://api.scryfall.com/cards/10e/93★/"); // not implemented
            put("10E/Mirri, Cat Warrior/279*", "https://api.scryfall.com/cards/10e/279★/");
            put("10E/Mobilization/29*", "https://api.scryfall.com/cards/10e/29★/");
            put("10E/Molimo, Maro-Sorcerer/280*", "https://api.scryfall.com/cards/10e/280★/");
            put("10E/Mortivore/161*", "https://api.scryfall.com/cards/10e/161★/");
            put("10E/Nekrataal/163*", "https://api.scryfall.com/cards/10e/163★/");
            put("10E/Nightmare/164*", "https://api.scryfall.com/cards/10e/164★/");
            put("10E/Nomad Mythmaker/30*", "https://api.scryfall.com/cards/10e/30★/");
            put("10E/Ornithopter/336*", "https://api.scryfall.com/cards/10e/336★/");
            put("10E/Overgrowth/283*", "https://api.scryfall.com/cards/10e/283★/");
            put("10E/Overrun/284*", "https://api.scryfall.com/cards/10e/284★/");
            put("10E/Pacifism/31*", "https://api.scryfall.com/cards/10e/31★/");
            put("10E/Paladin en-Vec/32*", "https://api.scryfall.com/cards/10e/32★/");
            put("10E/Pariah/33*", "https://api.scryfall.com/cards/10e/33★/");
            put("10E/Persuasion/95*", "https://api.scryfall.com/cards/10e/95★/");
            put("10E/Pincher Beetles/285*", "https://api.scryfall.com/cards/10e/285★/");
            put("10E/Plague Beetle/168*", "https://api.scryfall.com/cards/10e/168★/");
            put("10E/Platinum Angel/339*", "https://api.scryfall.com/cards/10e/339★/");
            put("10E/Primal Rage/286*", "https://api.scryfall.com/cards/10e/286★/");
            put("10E/Rage Weaver/223*", "https://api.scryfall.com/cards/10e/223★/");
            put("10E/Raging Goblin/224*", "https://api.scryfall.com/cards/10e/224★/");
            put("10E/Razormane Masticore/340*", "https://api.scryfall.com/cards/10e/340★/");
            put("10E/Regeneration/290*", "https://api.scryfall.com/cards/10e/290★/");
            put("10E/Reya Dawnbringer/35*", "https://api.scryfall.com/cards/10e/35★/");
            put("10E/Rhox/291*", "https://api.scryfall.com/cards/10e/291★/");
            put("10E/Robe of Mirrors/101*", "https://api.scryfall.com/cards/10e/101★/");
            put("10E/Rock Badger/226*", "https://api.scryfall.com/cards/10e/226★/");
            put("10E/Rootwater Commando/102*", "https://api.scryfall.com/cards/10e/102★/");
            put("10E/Rushwood Dryad/294*", "https://api.scryfall.com/cards/10e/294★/");
            put("10E/Sage Owl/104*", "https://api.scryfall.com/cards/10e/104★/");
            put("10E/Scalpelexis/105*", "https://api.scryfall.com/cards/10e/105★/");
            put("10E/Sengir Vampire/176*", "https://api.scryfall.com/cards/10e/176★/");
            put("10E/Serra Angel/39*", "https://api.scryfall.com/cards/10e/39★/");
            put("10E/Serra's Embrace/40*", "https://api.scryfall.com/cards/10e/40★/");
            put("10E/Severed Legion/177*", "https://api.scryfall.com/cards/10e/177★/");
            put("10E/Shimmering Wings/107*", "https://api.scryfall.com/cards/10e/107★/");
            put("10E/Shivan Dragon/230*", "https://api.scryfall.com/cards/10e/230★/");
            put("10E/Shivan Hellkite/231*", "https://api.scryfall.com/cards/10e/231★/");
            put("10E/Sky Weaver/109*", "https://api.scryfall.com/cards/10e/109★/");
            put("10E/Skyhunter Patrol/41*", "https://api.scryfall.com/cards/10e/41★/");
            put("10E/Skyhunter Prowler/42*", "https://api.scryfall.com/cards/10e/42★/");
            put("10E/Skyhunter Skirmisher/43*", "https://api.scryfall.com/cards/10e/43★/");
            put("10E/Snapping Drake/110*", "https://api.scryfall.com/cards/10e/110★/");
            put("10E/Spark Elemental/237*", "https://api.scryfall.com/cards/10e/237★/");
            put("10E/Spawning Pool/358*", "https://api.scryfall.com/cards/10e/358★/");
            put("10E/Spiketail Hatchling/111*", "https://api.scryfall.com/cards/10e/111★/");
            put("10E/Spirit Link/45*", "https://api.scryfall.com/cards/10e/45★/");
            put("10E/Stampeding Wildebeests/300*", "https://api.scryfall.com/cards/10e/300★/");
            put("10E/Steadfast Guard/48*", "https://api.scryfall.com/cards/10e/48★/");
            put("10E/Suntail Hawk/50*", "https://api.scryfall.com/cards/10e/50★/");
            put("10E/Tangle Spider/303*", "https://api.scryfall.com/cards/10e/303★/");
            put("10E/The Hive/324*", "https://api.scryfall.com/cards/10e/324★/");
            put("10E/Thieving Magpie/115*", "https://api.scryfall.com/cards/10e/115★/");
            put("10E/Threaten/242*", "https://api.scryfall.com/cards/10e/242★/");
            put("10E/Thundering Giant/243*", "https://api.scryfall.com/cards/10e/243★/");
            put("10E/Time Stop/117*", "https://api.scryfall.com/cards/10e/117★/");
            put("10E/Treetop Bracers/304*", "https://api.scryfall.com/cards/10e/304★/");
            put("10E/Treetop Village/361*", "https://api.scryfall.com/cards/10e/361★/");
            put("10E/Troll Ascetic/305*", "https://api.scryfall.com/cards/10e/305★/");
            put("10E/True Believer/53*", "https://api.scryfall.com/cards/10e/53★/");
            put("10E/Tundra Wolves/54*", "https://api.scryfall.com/cards/10e/54★/");
            put("10E/Uncontrollable Anger/244*", "https://api.scryfall.com/cards/10e/244★/");
            put("10E/Unholy Strength/185*", "https://api.scryfall.com/cards/10e/185★/");
            put("10E/Upwelling/306*", "https://api.scryfall.com/cards/10e/306★/");
            put("10E/Vampire Bats/186*", "https://api.scryfall.com/cards/10e/186★/");
            put("10E/Viashino Sandscout/246*", "https://api.scryfall.com/cards/10e/246★/");
            put("10E/Voice of All/56*", "https://api.scryfall.com/cards/10e/56★/");
            put("10E/Wall of Air/124*", "https://api.scryfall.com/cards/10e/124★/");
            put("10E/Wall of Fire/247*", "https://api.scryfall.com/cards/10e/247★/");
            put("10E/Wall of Swords/57*", "https://api.scryfall.com/cards/10e/57★/");
            put("10E/Wall of Wood/309*", "https://api.scryfall.com/cards/10e/309★/");
            put("10E/Whispersilk Cloak/345*", "https://api.scryfall.com/cards/10e/345★/");
            put("10E/Wild Griffin/59*", "https://api.scryfall.com/cards/10e/59★/");
            put("10E/Windborn Muse/60*", "https://api.scryfall.com/cards/10e/60★/");
            put("10E/Youthful Knight/62*", "https://api.scryfall.com/cards/10e/62★/");
            // 4ED
            put("4ED/El-Hajjaj/134+", "https://api.scryfall.com/cards/4ed/134†/");
            // 5ED
            put("5ED/Game of Chaos/232+", "https://api.scryfall.com/cards/5ed/232†/");
            put("5ED/Inferno/243+", "https://api.scryfall.com/cards/5ed/243†/");
            put("5ED/Ironclaw Curse/244+", "https://api.scryfall.com/cards/5ed/244†/");
            put("5ED/Manabarbs/250+", "https://api.scryfall.com/cards/5ed/250†/");
            put("5ED/Shivan Dragon/267+", "https://api.scryfall.com/cards/5ed/267†/");
            // AER
            put("AER/Alley Strangler/52+", "https://api.scryfall.com/cards/aer/52†/");
            put("AER/Dawnfeather Eagle/14+", "https://api.scryfall.com/cards/aer/14†/");
            put("AER/Wrangle/101+", "https://api.scryfall.com/cards/aer/101†/");
            // ARN
            put("ARN/Army of Allah/2+", "https://api.scryfall.com/cards/arn/2†/");
            put("ARN/Bird Maiden/37+", "https://api.scryfall.com/cards/arn/37†/");
            put("ARN/Erg Raiders/25+", "https://api.scryfall.com/cards/arn/25†/");
            put("ARN/Fishliver Oil/13+", "https://api.scryfall.com/cards/arn/13†/");
            put("ARN/Giant Tortoise/15+", "https://api.scryfall.com/cards/arn/15†/");
            put("ARN/Hasran Ogress/27+", "https://api.scryfall.com/cards/arn/27†/");
            put("ARN/Moorish Cavalry/7+", "https://api.scryfall.com/cards/arn/7†/");
            put("ARN/Nafs Asp/52+", "https://api.scryfall.com/cards/arn/52†/");
            put("ARN/Oubliette/31+", "https://api.scryfall.com/cards/arn/31†/");
            put("ARN/Piety/8+", "https://api.scryfall.com/cards/arn/8†/");
            put("ARN/Rukh Egg/43+", "https://api.scryfall.com/cards/arn/43†/");
            put("ARN/Stone-Throwing Devils/33+", "https://api.scryfall.com/cards/arn/33†/");
            put("ARN/War Elephant/11+", "https://api.scryfall.com/cards/arn/11†/");
            put("ARN/Wyluli Wolf/55+", "https://api.scryfall.com/cards/arn/55†/");
            // ATQ
            put("ATQ/Tawnos's Weaponry/70+", "https://api.scryfall.com/cards/atq/70†/");
            // DD2
            put("DD2/Chandra Nalaar/34*", "https://api.scryfall.com/cards/dd2/34★/");
            put("DD2/Jace Beleren/1*", "https://api.scryfall.com/cards/dd2/1★/");
            // DKM
            put("DKM/Icy Manipulator/36*", "https://api.scryfall.com/cards/dkm/36★/");
            put("DKM/Incinerate/14*", "https://api.scryfall.com/cards/dkm/14★/");
            // DRK
            put("DRK/Fountain of Youth/103+", "https://api.scryfall.com/cards/drk/103†/");
            put("DRK/Gaea's Touch/77+", "https://api.scryfall.com/cards/drk/77†/");
            put("DRK/Runesword/107+", "https://api.scryfall.com/cards/drk/107†/");
            // J14
            put("J14/Plains/1*", "https://api.scryfall.com/cards/j14/1★/");
            put("J14/Island/2*", "https://api.scryfall.com/cards/j14/2★/");
            put("J14/Swamp/3*", "https://api.scryfall.com/cards/j14/3★/");
            put("J14/Mountain/4*", "https://api.scryfall.com/cards/j14/4★/");
            put("J14/Forest/5*", "https://api.scryfall.com/cards/j14/5★/");
            // KLD
            put("KLD/Arborback Stomper/142+", "https://api.scryfall.com/cards/kld/142†/");
            put("KLD/Brazen Scourge/107+", "https://api.scryfall.com/cards/kld/107†/");
            put("KLD/Terrain Elemental/272+", "https://api.scryfall.com/cards/kld/272†/");
            put("KLD/Wind Drake/70+", "https://api.scryfall.com/cards/kld/70†/");
            // M20
            put("M20/Corpse Knight/206+", "https://api.scryfall.com/cards/m20/206†/");
            // MIR
            put("MIR/Reality Ripple/87+", "https://api.scryfall.com/cards/mir/87†/");
            // OGW
            put("OGW/Captain's Claws/162+", "https://api.scryfall.com/cards/ogw/162†/");
            // ODY
            put("ODY/Cephalid Looter/72+", "https://api.scryfall.com/cards/ody/72†/");
            put("ODY/Seafloor Debris/325+", "https://api.scryfall.com/cards/ody/325†/");
            // PAL99
            put("PAL99/Island/3+", "https://api.scryfall.com/cards/pal99/3†/");
            // PALA
            put("PALA/Ajani Vengeant/154*", "https://api.scryfall.com/cards/pala/154★/");
            // PAVR
            put("PAVR/Moonsilver Spear/217*", "https://api.scryfall.com/cards/pavr/217★/");
            put("PAVR/Restoration Angel/32*", "https://api.scryfall.com/cards/pavr/32★/");
            // PBNG
            put("PBNG/Arbiter of the Ideal/31*", "https://api.scryfall.com/cards/pbng/31★/");
            put("PBNG/Eater of Hope/66*", "https://api.scryfall.com/cards/pbng/66★/");
            put("PBNG/Forgestoker Dragon/98*", "https://api.scryfall.com/cards/pbng/98★/");
            put("PBNG/Nessian Wilds Ravager/129*", "https://api.scryfall.com/cards/pbng/129★/");
            put("PBNG/Silent Sentinel/26*", "https://api.scryfall.com/cards/pbng/26★/");
            put("PBNG/Tromokratis/55*", "https://api.scryfall.com/cards/pbng/55★/");
            // PDGM
            put("PDGM/Breaking // Entering/124*", "https://api.scryfall.com/cards/pdgm/124★/");
            put("PDGM/Maze's End/152*", "https://api.scryfall.com/cards/pdgm/152★/");
            put("PDGM/Plains/157*", "https://api.scryfall.com/cards/pdgm/157★/");
            // PDKA
            put("PDKA/Archdemon of Greed/71*", "https://api.scryfall.com/cards/pdka/71★/");
            put("PDKA/Mondronen Shaman/98*", "https://api.scryfall.com/cards/pdka/98★/");
            put("PDKA/Ravenous Demon/71*", "https://api.scryfall.com/cards/pdka/71★/");
            put("PDKA/Tovolar's Magehunter/98*", "https://api.scryfall.com/cards/pdka/98★/");
            // PGTC
            put("PGTC/Consuming Aberration/152*", "https://api.scryfall.com/cards/pgtc/152★/");
            put("PGTC/Fathom Mage/162*", "https://api.scryfall.com/cards/pgtc/162★/");
            put("PGTC/Foundry Champion/165*", "https://api.scryfall.com/cards/pgtc/165★/");
            put("PGTC/Rubblehulk/191*", "https://api.scryfall.com/cards/pgtc/191★/");
            put("PGTC/Skarrg Goliath/133*", "https://api.scryfall.com/cards/pgtc/133★/");
            put("PGTC/Treasury Thrull/201*", "https://api.scryfall.com/cards/pgtc/201★/");
            // PLS
            put("PLS/Ertai, the Corrupted/107*", "https://api.scryfall.com/cards/pls/107★/");
            put("PLS/Skyship Weatherlight/133*", "https://api.scryfall.com/cards/pls/133★/");
            put("PLS/Tahngarth, Talruum Hero/74*", "https://api.scryfall.com/cards/pls/74★/");
            // POR
            put("POR/Anaconda/158+", "https://api.scryfall.com/cards/por/158†/");
            put("POR/Blaze/118+", "https://api.scryfall.com/cards/por/118†/");
            put("POR/Elite Cat Warrior/163+", "https://api.scryfall.com/cards/por/163†/");
            put("POR/Hand of Death/96+", "https://api.scryfall.com/cards/por/96†/");
            put("POR/Monstrous Growth/173+", "https://api.scryfall.com/cards/por/173†/");
            put("POR/Raging Goblin/145+", "https://api.scryfall.com/cards/por/145†/");
            put("POR/Warrior's Charge/38+", "https://api.scryfall.com/cards/por/38†/");
            // PISD
            put("PISD/Howlpack Alpha/193*", "https://api.scryfall.com/cards/pisd/193★/");
            put("PISD/Ludevic's Abomination/64*", "https://api.scryfall.com/cards/pisd/64★/");
            put("PISD/Ludevic's Test Subject/64*", "https://api.scryfall.com/cards/pisd/64★/");
            put("PISD/Mayor of Avabruck/193*", "https://api.scryfall.com/cards/pisd/193★/");
            // PJOU
            put("PJOU/Dawnbringer Charioteers/6*", "https://api.scryfall.com/cards/pjou/6★/");
            put("PJOU/Dictate of the Twin Gods/93*", "https://api.scryfall.com/cards/pjou/93★/");
            put("PJOU/Doomwake Giant/66*", "https://api.scryfall.com/cards/pjou/66★/");
            put("PJOU/Heroes' Bane/126*", "https://api.scryfall.com/cards/pjou/126★/");
            put("PJOU/Scourge of Fleets/51*", "https://api.scryfall.com/cards/pjou/51★/");
            put("PJOU/Spawn of Thraxes/112*", "https://api.scryfall.com/cards/pjou/112★/");
            // PM10
            put("PM10/Ant Queen/166*", "https://api.scryfall.com/cards/pm10/166★/");
            put("PM10/Honor of the Pure/16*", "https://api.scryfall.com/cards/pm10/16★/");
            put("PM10/Vampire Nocturnus/118*", "https://api.scryfall.com/cards/pm10/118★/");
            // PM11
            put("PM11/Ancient Hellkite/122*", "https://api.scryfall.com/cards/pm11/122★/");
            put("PM11/Birds of Paradise/165*", "https://api.scryfall.com/cards/pm11/165★/");
            put("PM11/Sun Titan/35*", "https://api.scryfall.com/cards/pm11/35★/");
            // PM12
            put("PM12/Bloodlord of Vaasgoth/82*", "https://api.scryfall.com/cards/pm12/82★/");
            put("PM12/Chandra's Phoenix/126*", "https://api.scryfall.com/cards/pm12/126★/");
            put("PM12/Garruk's Horde/176*", "https://api.scryfall.com/cards/pm12/176★/");
            // PM13
            put("PM13/Cathedral of War/221*", "https://api.scryfall.com/cards/pm13/221★/");
            put("PM13/Magmaquake/140*", "https://api.scryfall.com/cards/pm13/140★/");
            put("PM13/Mwonvuli Beast Tracker/177*", "https://api.scryfall.com/cards/pm13/177★/");
            put("PM13/Staff of Nin/217*", "https://api.scryfall.com/cards/pm13/217★/");
            put("PM13/Xathrid Gorgon/118*", "https://api.scryfall.com/cards/pm13/118★/");
            // PM14
            put("PM14/Colossal Whale/48*", "https://api.scryfall.com/cards/pm14/48★/");
            put("PM14/Goblin Diplomats/141*", "https://api.scryfall.com/cards/pm14/141★/");
            put("PM14/Hive Stirrings/21*", "https://api.scryfall.com/cards/pm14/21★/");
            put("PM14/Megantic Sliver/185*", "https://api.scryfall.com/cards/pm14/185★/");
            put("PM14/Ratchet Bomb/215*", "https://api.scryfall.com/cards/pm14/215★/");
            // PMBS
            put("PMBS/Glissa, the Traitor/96*", "https://api.scryfall.com/cards/pmbs/96★/");
            put("PMBS/Hero of Bladehold/8*", "https://api.scryfall.com/cards/pmbs/8★/");
            put("PMBS/Thopter Assembly/140*", "https://api.scryfall.com/cards/pmbs/140★/");
            // PNPH
            put("PNPH/Phyrexian Metamorph/42*", "https://api.scryfall.com/cards/pnph/42★/");
            put("PNPH/Sheoldred, Whispering One/73*", "https://api.scryfall.com/cards/pnph/73★/");
            // PRES
            put("PRES/Goblin Chieftain/141*", "https://api.scryfall.com/cards/pres/141★/");
            put("PRES/Loam Lion/13*", "https://api.scryfall.com/cards/pres/13★/");
            put("PRES/Oran-Rief, the Vastwood/221*", "https://api.scryfall.com/cards/pres/221★/");
            // PROE
            put("PROE/Emrakul, the Aeons Torn/4*", "https://api.scryfall.com/cards/proe/4★/");
            put("PROE/Lord of Shatterskull Pass/156*", "https://api.scryfall.com/cards/proe/156★/");
            // PRTR
            put("PRTR/Archon of the Triumvirate/142*", "https://api.scryfall.com/cards/prtr/142★/");
            put("PRTR/Carnival Hellsteed/147*", "https://api.scryfall.com/cards/prtr/147★/");
            put("PRTR/Corpsejack Menace/152*", "https://api.scryfall.com/cards/prtr/152★/");
            put("PRTR/Deadbridge Goliath/120*", "https://api.scryfall.com/cards/prtr/120★/");
            put("PRTR/Grove of the Guardian/240*", "https://api.scryfall.com/cards/prtr/240★/");
            put("PRTR/Hypersonic Dragon/170*", "https://api.scryfall.com/cards/prtr/170★/");
            // PSDC
            put("PSDC/Ajani, Caller of the Pride/1*", "https://api.scryfall.com/cards/psdc/1★/");
            put("PSDC/Chandra, Pyromaster/132*", "https://api.scryfall.com/cards/psdc/132★/");
            put("PSDC/Garruk, Caller of Beasts/172*", "https://api.scryfall.com/cards/psdc/172★/");
            put("PSDC/Jace, Memory Adept/60*", "https://api.scryfall.com/cards/psdc/60★/");
            put("PSDC/Liliana of the Dark Realms/102*", "https://api.scryfall.com/cards/psdc/102★/");
            // PSOI
            put("PSOI/Tamiyo's Journal/265s+", "https://api.scryfall.com/cards/psoi/265s†/");
            // PSOM
            put("PSOM/Steel Hellkite/205*", "https://api.scryfall.com/cards/psom/205★/");
            put("PSOM/Wurmcoil Engine/223*", "https://api.scryfall.com/cards/psom/223★/");
            // PTHS
            put("PTHS/Abhorrent Overlord/75*", "https://api.scryfall.com/cards/pths/75★/");
            put("PTHS/Anthousa, Setessan Hero/149*", "https://api.scryfall.com/cards/pths/149★/");
            put("PTHS/Bident of Thassa/42*", "https://api.scryfall.com/cards/pths/42★/");
            put("PTHS/Celestial Archon/3*", "https://api.scryfall.com/cards/pths/3★/");
            put("PTHS/Ember Swallower/120*", "https://api.scryfall.com/cards/pths/120★/");
            put("PTHS/Shipbreaker Kraken/63*", "https://api.scryfall.com/cards/pths/63★/");
            // PWAR
            put("PWAR/Ajani, the Greathearted/184s*", "https://api.scryfall.com/cards/pwar/184s★/");
            put("PWAR/Angrath, Captain of Chaos/227s*", "https://api.scryfall.com/cards/pwar/227s★/");
            put("PWAR/Arlinn, Voice of the Pack/150s*", "https://api.scryfall.com/cards/pwar/150s★/");
            put("PWAR/Ashiok, Dream Render/228s*", "https://api.scryfall.com/cards/pwar/228s★/");
            put("PWAR/Chandra, Fire Artisan/119s*", "https://api.scryfall.com/cards/pwar/119s★/");
            put("PWAR/Davriel, Rogue Shadowmage/83s*", "https://api.scryfall.com/cards/pwar/83s★/");
            put("PWAR/Domri, Anarch of Bolas/191s*", "https://api.scryfall.com/cards/pwar/191s★/");
            put("PWAR/Dovin, Hand of Control/229s*", "https://api.scryfall.com/cards/pwar/229s★/");
            put("PWAR/Gideon Blackblade/13s*", "https://api.scryfall.com/cards/pwar/13s★/");
            put("PWAR/Huatli, the Sun's Heart/230s*", "https://api.scryfall.com/cards/pwar/230s★/");
            put("PWAR/Jace, Wielder of Mysteries/54s*", "https://api.scryfall.com/cards/pwar/54s★/");
            put("PWAR/Jaya, Venerated Firemage/135s*", "https://api.scryfall.com/cards/pwar/135s★/");
            put("PWAR/Jiang Yanggu, Wildcrafter/164s*", "https://api.scryfall.com/cards/pwar/164s★/");
            put("PWAR/Karn, the Great Creator/1s*", "https://api.scryfall.com/cards/pwar/1s★/");
            put("PWAR/Kasmina, Enigmatic Mentor/56s*", "https://api.scryfall.com/cards/pwar/56s★/");
            put("PWAR/Kaya, Bane of the Dead/231s*", "https://api.scryfall.com/cards/pwar/231s★/");
            put("PWAR/Kiora, Behemoth Beckoner/232s*", "https://api.scryfall.com/cards/pwar/232s★/");
            put("PWAR/Liliana, Dreadhorde General/97s*", "https://api.scryfall.com/cards/pwar/97s★/");
            put("PWAR/Nahiri, Storm of Stone/233s*", "https://api.scryfall.com/cards/pwar/233s★/");
            put("PWAR/Narset, Parter of Veils/61s*", "https://api.scryfall.com/cards/pwar/61s★/");
            put("PWAR/Nicol Bolas, Dragon-God/207s*", "https://api.scryfall.com/cards/pwar/207s★/");
            put("PWAR/Nissa, Who Shakes the World/169s*", "https://api.scryfall.com/cards/pwar/169s★/");
            put("PWAR/Ob Nixilis, the Hate-Twisted/100s*", "https://api.scryfall.com/cards/pwar/100s★/");
            put("PWAR/Ral, Storm Conduit/211s*", "https://api.scryfall.com/cards/pwar/211s★/");
            put("PWAR/Saheeli, Sublime Artificer/234s*", "https://api.scryfall.com/cards/pwar/234s★/");
            put("PWAR/Samut, Tyrant Smasher/235s*", "https://api.scryfall.com/cards/pwar/235s★/");
            put("PWAR/Sarkhan the Masterless/143s*", "https://api.scryfall.com/cards/pwar/143s★/");
            put("PWAR/Sorin, Vengeful Bloodlord/217s*", "https://api.scryfall.com/cards/pwar/217s★/");
            put("PWAR/Tamiyo, Collector of Tales/220s*", "https://api.scryfall.com/cards/pwar/220s★/");
            put("PWAR/Teferi, Time Raveler/221s*", "https://api.scryfall.com/cards/pwar/221s★/");
            put("PWAR/Teyo, the Shieldmage/32s*", "https://api.scryfall.com/cards/pwar/32s★/");
            put("PWAR/The Wanderer/37s*", "https://api.scryfall.com/cards/pwar/37s★/");
            put("PWAR/Tibalt, Rakish Instigator/146s*", "https://api.scryfall.com/cards/pwar/146s★/");
            put("PWAR/Ugin, the Ineffable/2s*", "https://api.scryfall.com/cards/pwar/2s★/");
            put("PWAR/Vivien, Champion of the Wilds/180s*", "https://api.scryfall.com/cards/pwar/180s★/");
            put("PWAR/Vraska, Swarm's Eminence/236s*", "https://api.scryfall.com/cards/pwar/236s★/");
            // PWWK
            put("PWWK/Comet Storm/76*", "https://api.scryfall.com/cards/pwwk/76★/");
            put("PWWK/Joraga Warcaller/106*", "https://api.scryfall.com/cards/pwwk/106★/");
            put("PWWK/Ruthless Cullblade/65*", "https://api.scryfall.com/cards/pwwk/65★/");
            // PZEN
            put("PZEN/Rampaging Baloths/178*", "https://api.scryfall.com/cards/pzen/178★/");
            put("PZEN/Valakut, the Molten Pinnacle/228*", "https://api.scryfall.com/cards/pzen/228★/");
            // SHM
            put("SHM/Reflecting Pool/278*", "https://api.scryfall.com/cards/shm/278★/");
            // SOI
            put("SOI/Tamiyo's Journal/265+a", "https://api.scryfall.com/cards/soi/265†a/");
            put("SOI/Tamiyo's Journal/265+b", "https://api.scryfall.com/cards/soi/265†b/");
            put("SOI/Tamiyo's Journal/265+c", "https://api.scryfall.com/cards/soi/265†c/");
            put("SOI/Tamiyo's Journal/265+d", "https://api.scryfall.com/cards/soi/265†d/");
            put("SOI/Tamiyo's Journal/265+e", "https://api.scryfall.com/cards/soi/265†e/");
            // THB
            put("THB/Temple of Abandon/347*", "https://api.scryfall.com/cards/thb/347★/");
            // UNH
            put("UNH/Aesthetic Consultation/48*", "https://api.scryfall.com/cards/unh/48★/");
            put("UNH/AWOL/2*", "https://api.scryfall.com/cards/unh/2★/");
            put("UNH/Bad Ass/49*", "https://api.scryfall.com/cards/unh/49★/");
            put("UNH/Blast from the Past/72*", "https://api.scryfall.com/cards/unh/72★/");
            put("UNH/Cardpecker/4*", "https://api.scryfall.com/cards/unh/4★/");
            put("UNH/Emcee/9*", "https://api.scryfall.com/cards/unh/9★/");
            put("UNH/Farewell to Arms/56*", "https://api.scryfall.com/cards/unh/56★/");
            put("UNH/Frazzled Editor/77*", "https://api.scryfall.com/cards/unh/77★/");
            put("UNH/Gleemax/121*", "https://api.scryfall.com/cards/unh/121★/");
            put("UNH/Goblin Mime/78*", "https://api.scryfall.com/cards/unh/78★/");
            put("UNH/Greater Morphling/34*", "https://api.scryfall.com/cards/unh/34★/");
            put("UNH/Keeper of the Sacred Word/101*", "https://api.scryfall.com/cards/unh/101★/");
            put("UNH/Laughing Hyena/103*", "https://api.scryfall.com/cards/unh/103★/");
            put("UNH/Letter Bomb/122*", "https://api.scryfall.com/cards/unh/122★/");
            put("UNH/Mana Screw/123*", "https://api.scryfall.com/cards/unh/123★/");
            put("UNH/Monkey Monkey Monkey/104*", "https://api.scryfall.com/cards/unh/104★/");
            put("UNH/Mons's Goblin Waiters/82*", "https://api.scryfall.com/cards/unh/82★/");
            put("UNH/Mox Lotus/124*", "https://api.scryfall.com/cards/unh/124★/");
            put("UNH/My First Tome/125*", "https://api.scryfall.com/cards/unh/125★/");
            put("UNH/Old Fogey/106*", "https://api.scryfall.com/cards/unh/106★/");
            put("UNH/Question Elemental?/43*", "https://api.scryfall.com/cards/unh/43★/");
            put("UNH/Richard Garfield, Ph.D./44*", "https://api.scryfall.com/cards/unh/44★/");
            put("UNH/Shoe Tree/109*", "https://api.scryfall.com/cards/unh/109★/");
            put("UNH/Standing Army/20*", "https://api.scryfall.com/cards/unh/20★/");
            put("UNH/Time Machine/128*", "https://api.scryfall.com/cards/unh/128★/");
            put("UNH/Touch and Go/90*", "https://api.scryfall.com/cards/unh/90★/");
            put("UNH/When Fluffy Bunnies Attack/67*", "https://api.scryfall.com/cards/unh/67★/");
            // WAR
            put("WAR/Ajani, the Greathearted/184*", "https://api.scryfall.com/cards/war/184★/");
            put("WAR/Angrath, Captain of Chaos/227*", "https://api.scryfall.com/cards/war/227★/");
            put("WAR/Arlinn, Voice of the Pack/150*", "https://api.scryfall.com/cards/war/150★/");
            put("WAR/Ashiok, Dream Render/228*", "https://api.scryfall.com/cards/war/228★/");
            put("WAR/Chandra, Fire Artisan/119*", "https://api.scryfall.com/cards/war/119★/");
            put("WAR/Davriel, Rogue Shadowmage/83*", "https://api.scryfall.com/cards/war/83★/");
            put("WAR/Domri, Anarch of Bolas/191*", "https://api.scryfall.com/cards/war/191★/");
            put("WAR/Dovin, Hand of Control/229*", "https://api.scryfall.com/cards/war/229★/");
            put("WAR/Gideon Blackblade/13*", "https://api.scryfall.com/cards/war/13★/");
            put("WAR/Huatli, the Sun's Heart/230*", "https://api.scryfall.com/cards/war/230★/");
            put("WAR/Jace, Wielder of Mysteries/54*", "https://api.scryfall.com/cards/war/54★/");
            put("WAR/Jaya, Venerated Firemage/135*", "https://api.scryfall.com/cards/war/135★/");
            put("WAR/Jiang Yanggu, Wildcrafter/164*", "https://api.scryfall.com/cards/war/164★/");
            put("WAR/Karn, the Great Creator/1*", "https://api.scryfall.com/cards/war/1★/");
            put("WAR/Kasmina, Enigmatic Mentor/56*", "https://api.scryfall.com/cards/war/56★/");
            put("WAR/Kaya, Bane of the Dead/231*", "https://api.scryfall.com/cards/war/231★/");
            put("WAR/Kiora, Behemoth Beckoner/232*", "https://api.scryfall.com/cards/war/232★/");
            put("WAR/Liliana, Dreadhorde General/97*", "https://api.scryfall.com/cards/war/97★/");
            put("WAR/Nahiri, Storm of Stone/233*", "https://api.scryfall.com/cards/war/233★/");
            put("WAR/Narset, Parter of Veils/61*", "https://api.scryfall.com/cards/war/61★/");
            put("WAR/Nicol Bolas, Dragon-God/207*", "https://api.scryfall.com/cards/war/207★/");
            put("WAR/Nissa, Who Shakes the World/169*", "https://api.scryfall.com/cards/war/169★/");
            put("WAR/Ob Nixilis, the Hate-Twisted/100*", "https://api.scryfall.com/cards/war/100★/");
            put("WAR/Ral, Storm Conduit/211*", "https://api.scryfall.com/cards/war/211★/");
            put("WAR/Saheeli, Sublime Artificer/234*", "https://api.scryfall.com/cards/war/234★/");
            put("WAR/Samut, Tyrant Smasher/235*", "https://api.scryfall.com/cards/war/235★/");
            put("WAR/Sarkhan the Masterless/143*", "https://api.scryfall.com/cards/war/143★/");
            put("WAR/Sorin, Vengeful Bloodlord/217*", "https://api.scryfall.com/cards/war/217★/");
            put("WAR/Tamiyo, Collector of Tales/220*", "https://api.scryfall.com/cards/war/220★/");
            put("WAR/Teferi, Time Raveler/221*", "https://api.scryfall.com/cards/war/221★/");
            put("WAR/Teyo, the Shieldmage/32*", "https://api.scryfall.com/cards/war/32★/");
            put("WAR/The Wanderer/37*", "https://api.scryfall.com/cards/war/37★/");
            put("WAR/Tibalt, Rakish Instigator/146*", "https://api.scryfall.com/cards/war/146★/");
            put("WAR/Ugin, the Ineffable/2*", "https://api.scryfall.com/cards/war/2★/");
            put("WAR/Vivien, Champion of the Wilds/180*", "https://api.scryfall.com/cards/war/180★/");
            put("WAR/Vraska, Swarm's Eminence/236*", "https://api.scryfall.com/cards/war/236★/");
            // SLD
            // TODO: update direct image links in 2022 for HQ images
            put("SLD/Zndrsplt, Eye of Wisdom/379", "https://api.scryfall.com/cards/sld/379/");
            put("SLD/Zndrsplt, Eye of Wisdom/379b", "https://c1.scryfall.com/file/scryfall-cards/large/back/d/5/d5dfd236-b1da-4552-b94f-ebf6bb9dafdf.jpg");
            put("SLD/Krark's Thumb/383", "https://api.scryfall.com/cards/sld/383/");
            put("SLD/Krark's Thumb/383b", "https://c1.scryfall.com/file/scryfall-cards/large/back/9/f/9f63277b-e139-46c8-b9e3-0cfb647f44cc.jpg");
            put("SLD/Okaun, Eye of Chaos/380", "https://api.scryfall.com/cards/sld/380/");
            put("SLD/Okaun, Eye of Chaos/380b", "https://c1.scryfall.com/file/scryfall-cards/large/back/9/4/94eea6e3-20bc-4dab-90ba-3113c120fb90.jpg");
            put("SLD/Propaganda/381", "https://api.scryfall.com/cards/sld/381/");
            put("SLD/Propaganda/381b", "https://c1.scryfall.com/file/scryfall-cards/large/back/3/e/3e3f0bcd-0796-494d-bf51-94b33c1671e9.jpg");
            put("SLD/Stitch in Time/382", "https://api.scryfall.com/cards/sld/382/");
            put("SLD/Stitch in Time/382b", "https://c1.scryfall.com/file/scryfall-cards/large/back/0/8/087c3a0d-c710-4451-989e-596b55352184.jpg");
        }
    };

    public static String findScryfallSetCode(String xmageCode) {
        return xmageCode.toLowerCase(Locale.ENGLISH);
    }

    public static Set<String> getSupportedSets() {
        return supportedSets;
    }

    public static String findDirectDownloadKey(String setCode, String cardName, String cardNumber) {
        // set/card/number
        String linkCode1 = setCode + "/" + cardName + "/" + cardNumber;
        if (directDownloadLinks.containsKey(linkCode1)) {
            return linkCode1;
        }

        // set/card
        String linkCode2 = setCode + "/" + cardName;
        if (directDownloadLinks.containsKey(linkCode2)) {
            return linkCode2;
        }

        // default
        return null;
    }

    public static String findDirectDownloadLink(String setCode, String cardName, String cardNumber) {
        String key = findDirectDownloadKey(setCode, cardName, cardNumber);
        return directDownloadLinks.get(key);
    }

    public static String extractSetCodeFromDirectKey(String key) {
        // from: 8ED/Giant Octopus
        // to: 8ED
        Matcher matcher = REGEXP_DIRECT_KEY_SET_CODE_PATTERN.matcher(key);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static String extractCardNameFromDirectKey(String key) {
        // from: 8ED/Giant Octopus/
        // to: Giant Octopus
        Matcher matcher = REGEXP_DIRECT_KEY_CARD_NAME_PATTERN.matcher(key + "/"); // add / for regexp workaround
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static boolean isApiLink(String link) {
        return !link.endsWith(".jpg") && !link.endsWith(".png");
    }

    public static Map<String, String> getDirectDownloadLinks() {
        return directDownloadLinks;
    }
}
