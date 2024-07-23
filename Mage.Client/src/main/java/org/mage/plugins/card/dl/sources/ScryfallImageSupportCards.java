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
            add("PDCI"); // DCI Promos
            add("P06"); // Magic Player Rewards 2006
            add("G06"); // Judge Gift Cards 2006
            add("F06"); // Friday Night Magic 2006
            add("GPT"); // Guildpact
            add("DIS"); // Dissension
            add("CST"); // Coldsnap Theme Decks
            add("CSP"); // Coldsnap
            add("TSP"); // Time Spiral
            add("TSB"); // Time Spiral Timeshifted
            //add("PMPS07"); // Magic Premiere Shop 2007
            add("P07"); // Magic Player Rewards 2007
            add("HHO"); // Happy Holidays
            add("G07"); // Judge Gift Cards 2007
            add("F07"); // Friday Night Magic 2007
            add("PLC"); // Planar Chaos
            add("FUT"); // Future Sight
            add("10E"); // Tenth Edition
            add("P10E"); // Tenth Edition Promos
            add("ME1"); // Masters Edition
            add("LRW"); // Lorwyn
            add("DD1"); // Duel Decks: Elves vs. Goblins
            add("PSUS"); // Junior Super Series
            add("PJSE"); // Junior Series Europe
            add("PJAS"); // Junior APAC Series
            //add("PMPS08"); // Magic Premiere Shop 2008
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
            add("ALA"); // Shards of Alara
            add("PALA"); // Shards of Alara Promos
            add("DD2"); // Duel Decks: Jace vs. Chandra
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
            add("PIDW"); // IDW Comics Inserts
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
            add("WMC"); // World Magic Cup Qualifiers
            add("H17"); // HasCon 2017
            add("PHTR"); // 2016 Heroes of the Realm
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
            add("PH17"); // Heroes of the Realm 2017
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
            add("CMB1"); // Mystery Booster Playtest Cards 2019
            //add("MB1"); // Mystery Booster
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
            //add("FMB1"); // Mystery Booster Retail Edition Foils
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
            add("PH19"); // 2019 Heroes of the Realm
            add("2XM"); // Double Masters
            add("AKR"); // Amonkhet Remastered
            add("ANB"); // Arena Beginner Set
            add("ZNR"); // Zendikar Rising
            add("ZNC"); // Zendikar Rising Commander
            add("ZNE"); // Zendikar Rising Expeditions
            add("KLR"); // Kaladesh Remastered
            add("CMR"); // Commander Legends
            add("CC1"); // Commander Collection: Green
            add("PL21"); // Year of the Ox 2021
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
            add("PLG21"); // Love Your LGS 2021
            add("AFR"); // Adventures in the Forgotten Realms
            add("AFC"); // Forgotten Realms Commander
            add("J21"); // Jumpstart: Historic Horizons
            add("MID"); // Innistrad: Midnight Hunt
            add("MIC"); // Midnight Hunt Commander
            add("VOW"); // Innistrad: Crimson Vow
            add("VOC"); // Crimson Vow Commander
            add("YMID"); // Alchemy: Innistrad
            add("DBL"); // Innistrad: Double Feature
            add("CC2"); // Commander Collection: Black
            add("NEO"); // Kamigawa: Neon Dynasty
            add("NEC"); // Neon Dynasty Commander
            add("PL22"); // Year of the Tiger 2022
            add("SNC"); // Streets of New Capenna
            add("NCC"); // New Capenna Commander
            add("SLX"); // Universes Within
            add("CLB"); // Commander Legends: Battle for Baldur's Gate
            add("2X2"); // Double Masters 2022
            add("DMU"); // Dominaria United
            add("DMC"); // Dominaria United Commander
            add("YDMU"); // Alchemy: Dominaria
            add("40K"); // Warhammer 40,000
            add("UNF"); // Unfinity
            add("GN3"); // Game Night: Free-for-All
            add("BRO"); // The Brothers' War
            add("BRC"); // The Brothers' War Commander
            add("BRR"); // The Brothers' War Retro Artifacts
            add("BOT"); // Transformers
            add("J22"); // Jumpstart 2022
            add("SCD"); // Starter Commander Decks
            add("SLC"); // Secret Lair 30th Anniversary Countdown Kit
            add("DMR"); // Dominaria Remastered
            add("ONE"); // Phyrexia: All Will Be One
            add("ONC"); // Phyrexia: All Will Be One Commander
            add("PL23"); // Year of the Rabbit 2023
            add("SLP"); // Secret Lair Showdown
            add("MOM"); // March of the Machine
            add("MOC"); // March of the Machine Commander
            add("MAT"); // March of the Machine: The Aftermath
            add("MUL"); // Multiverse Legends
            add("30A"); // Thirtieth Anniversary Edition
            add("LTR"); // The Lord of the Rings: Tales of Middle-Earth
            add("LTC"); // Tales of Middle-Earth Commander
            add("CMM"); // Commander Masters
            add("WHO"); // Doctor Who
            add("WOE"); // Wilds of Eldraine
            add("WOT"); // Wilds of Eldraine: Enchanting Tales
            add("WOC"); // Wilds of Eldraine Commander
            add("LCI"); // The Lost Caverns of Ixalan
            add("LCC"); // The Lost Caverns of Ixalan Commander
            add("REX"); // Jurassic World Collection
            add("SPG"); // Special Guests
            add("RVR"); // Ravnica Remastered
            add("PIP"); // Fallout
            add("MKM"); // Murders at Karlov Manor
            add("MKC"); // Murders at Karlov Manor Commander
            add("CLU"); // Ravnica: Clue Edition
            add("OTJ"); // Outlaws of Thunder Junction
            add("OTC"); // Outlaws of Thunder Junction Commander
            add("OTP"); // Breaking News
            add("BIG"); // The Big Score
            add("MH3"); // Modern Horizons 3
            add("M3C"); // Modern Horizons 3 Commander
            add("ACR"); // Assassin's Creed
            add("BLB"); // Bloomburrow
            add("BLC"); // Bloomburrow Commander
            add("DSK"); // Duskmourn: House of Horror
            add("FDN"); // Foundations

            // Custom sets using Scryfall images - must provide a direct link for each card in directDownloadLinks
            add("CALC"); // Custom Alchemized versions of existing cards
        }
    };

    private static final Map<String, String> directDownloadLinks = new HashMap<String, String>() {
        {
            // xmage card -> api or image link
            // WARNING, try use api links as much as possible (it supports build-in translation)
            //
            // example:
            //   api link: https://api.scryfall.com/cards/trix/6/
            //   api direct link: https://api.scryfall.com/cards/rex/26/en?format=image&face=back
            //   api loc link: https://api.scryfall.com/cards/rex/26/?format=image - loc code will be inserted between "/?"
            //   image link: https://c1.scryfall.com/file/scryfall-cards/large/back/d/5/d5dfd236-b1da-4552-b94f-ebf6bb9dafdf.jpg
            //
            // key for one card:
            //   set/card_name
            //
            // key for same name cards (alternative images):
            //   set/card_name/card_number_1
            //   set/card_name/card_number_2
            //
            // double faced cards:
            //  front face image: format=image&face=front or simply format=image
            //  back face image: format=image&face=back
            //  example: https://api.scryfall.com/cards/rex/26/en?format=image&face=back
            //
            // Cards with non-ASCII collector numbers
            // - ending in "*" will replace with "★" for scryfall
            // - ending in "+" will replace with "†" for scryfall
            // - ending in "Ph" will replace with "Φ" for scryfall
            // - anything else must use direct download (cause xmage uses different card number)
            // Verify checks must check and show missing data from that list,
            // see test_checkMissingScryfallSettingsAndCardNumbers

            // SOI
            put("SOI/Tamiyo's Journal/265+a", "https://api.scryfall.com/cards/soi/265†a/");
            put("SOI/Tamiyo's Journal/265+b", "https://api.scryfall.com/cards/soi/265†b/");
            put("SOI/Tamiyo's Journal/265+c", "https://api.scryfall.com/cards/soi/265†c/");
            put("SOI/Tamiyo's Journal/265+d", "https://api.scryfall.com/cards/soi/265†d/");
            put("SOI/Tamiyo's Journal/265+e", "https://api.scryfall.com/cards/soi/265†e/");
            // SLD
            // fake double faced cards
            put("SLD/Adrix and Nev, Twincasters/1544b", "https://api.scryfall.com/cards/sld/1544/en?format=image&face=back");
            put("SLD/Ajani Goldmane/1453b", "https://api.scryfall.com/cards/sld/1453/en?format=image&face=back");
            put("SLD/Anointed Procession/1511b", "https://api.scryfall.com/cards/sld/1453/en?format=image&face=back");
            put("SLD/Blightsteel Colossus/1079b", "https://api.scryfall.com/cards/sld/1079/en?format=image&face=back");
            put("SLD/Chandra Nalaar/1456b", "https://api.scryfall.com/cards/sld/1456/en?format=image&face=back");
            put("SLD/Darksteel Colossus/1081b", "https://api.scryfall.com/cards/sld/1081/en?format=image&face=back");
            put("SLD/Death Baron/1458b", "https://api.scryfall.com/cards/sld/1458/en?format=image&face=back");
            put("SLD/Doubling Cube/1080b", "https://api.scryfall.com/cards/sld/1080/en?format=image&face=back");
            put("SLD/Etali, Primal Storm/1123b", "https://api.scryfall.com/cards/sld/1123/en?format=image&face=back");
            put("SLD/Garruk Wildspeaker/1457b", "https://api.scryfall.com/cards/sld/1457/en?format=image&face=back");
            put("SLD/Ghalta, Primal Hunger/1124b", "https://api.scryfall.com/cards/sld/1124/en?format=image&face=back");
            put("SLD/Grimgrin, Corpse-Born/1461b", "https://api.scryfall.com/cards/sld/1461/en?format=image&face=back");
            put("SLD/Jace Beleren/1454b", "https://api.scryfall.com/cards/sld/1454/en?format=image&face=back");
            put("SLD/Jetmir, Nexus of Revels/1509b", "https://api.scryfall.com/cards/sld/1509/en?format=image&face=back");
            put("SLD/Jinnie Fay, Jetmir's Second/1510b", "https://api.scryfall.com/cards/sld/1510/en?format=image&face=back");
            put("SLD/Krark's Thumb/383b", "https://api.scryfall.com/cards/sld/383/en?format=image&face=back");
            put("SLD/Krark, the Thumbless/1543b", "https://api.scryfall.com/cards/sld/1543/en?format=image&face=back");
            put("SLD/Liliana Vess/1455b", "https://api.scryfall.com/cards/sld/1455/en?format=image&face=back");
            put("SLD/Noxious Ghoul/1459b", "https://api.scryfall.com/cards/sld/1459/en?format=image&face=back");
            put("SLD/Okaun, Eye of Chaos/380b", "https://api.scryfall.com/cards/sld/380/en?format=image&face=back");
            put("SLD/Okaun, Eye of Chaos/380b*", "https://api.scryfall.com/cards/sld/380★/en?format=image&face=back");
            put("SLD/Propaganda/381b", "https://api.scryfall.com/cards/sld/381/en?format=image&face=back");
            put("SLD/Rin and Seri, Inseparable/1508b", "https://api.scryfall.com/cards/sld/1508/en?format=image&face=back");
            put("SLD/Sakashima of a Thousand Faces/1541b", "https://api.scryfall.com/cards/sld/1541/en?format=image&face=back");
            put("SLD/Sol Ring/1512b", "https://api.scryfall.com/cards/sld/1512/en?format=image&face=back");
            put("SLD/Stitch in Time/382b", "https://api.scryfall.com/cards/sld/382/en?format=image&face=back");
            put("SLD/Terror/750b", "https://api.scryfall.com/cards/sld/750/en?format=image&face=back");
            put("SLD/Ulamog, the Ceaseless Hunger/1122b", "https://api.scryfall.com/cards/sld/1122/en?format=image&face=back");
            put("SLD/Unholy Grotto/1462b", "https://api.scryfall.com/cards/sld/1462/en?format=image&face=back");
            put("SLD/Yargle, Glutton of Urborg/1542b", "https://api.scryfall.com/cards/sld/1542/en?format=image&face=back");
            put("SLD/Zndrsplt, Eye of Wisdom/379b", "https://api.scryfall.com/cards/sld/379/en?format=image&face=back");
            put("SLD/Zndrsplt, Eye of Wisdom/379b*", "https://api.scryfall.com/cards/sld/379★/en?format=image&face=back");
            put("SLD/Zombie Master/1460b", "https://api.scryfall.com/cards/sld/1460/en?format=image&face=back");
            // normal cards
            put("SLD/Viscera Seer/99999VS", "https://api.scryfall.com/cards/sld/VS/"); // see issue 11157

            // CALC - custom alchemy version of cards.
            put("CALC/C-Pillar of the Paruns", "https://api.scryfall.com/cards/dis/176/");

            // LTR - 0 number for tokens only
            put("LTR/The One Ring/001", "https://api.scryfall.com/cards/ltr/0/");

            // REX - double faced lands (xmage uses two diff lands for it)
            put("REX/Command Tower/26b", "https://api.scryfall.com/cards/rex/26/en?format=image&face=back");
            put("REX/Forest/25b", "https://api.scryfall.com/cards/rex/25/en?format=image&face=back");
            put("REX/Island/22b", "https://api.scryfall.com/cards/rex/22/en?format=image&face=back");
            put("REX/Mountain/24b", "https://api.scryfall.com/cards/rex/24/en?format=image&face=back");
            put("REX/Plains/21b", "https://api.scryfall.com/cards/rex/21/en?format=image&face=back");
            put("REX/Swamp/23b", "https://api.scryfall.com/cards/rex/23/en?format=image&face=back");
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

    public static String prepareCardNumber(String xmageCardNumber) {
        if (xmageCardNumber.endsWith("*")) {
            return xmageCardNumber.substring(0, xmageCardNumber.length() - 1) + "★";
        }
        if (xmageCardNumber.endsWith("+")) {
            return xmageCardNumber.substring(0, xmageCardNumber.length() - 1) + "†";
        }
        if (xmageCardNumber.endsWith("Ph")) {
            return xmageCardNumber.substring(0, xmageCardNumber.length() - 2) + "Φ";
        }
        return xmageCardNumber;
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
        return !link.endsWith(".jpg") && !link.endsWith(".png") && !link.contains("format=image");
    }

    public static Map<String, String> getDirectDownloadLinks() {
        return directDownloadLinks;
    }
}
