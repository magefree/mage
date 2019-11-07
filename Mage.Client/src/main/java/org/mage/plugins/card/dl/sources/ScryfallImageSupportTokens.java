package org.mage.plugins.card.dl.sources;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JayDi85
 */
public class ScryfallImageSupportTokens {

    private static final Map<String, String> supportedSets = new HashMap<>();

    private static final Map<String, String> supportedCards = new HashMap<String, String>() {
        {
            // xmage token -> direct or api link:
            // examples:
            //   direct example: https://img.scryfall.com/cards/large/en/trix/6.jpg
            //   api example: https://api.scryfall.com/cards/trix/6/en?format=image
            //   api example: https://api.scryfall.com/cards/trix/6?format=image
            // api format is primary
            //
            // code form for one token:
            //   set/token_name
            //
            // code form for same name tokens (alternative images):
            //   set/token_name/1
            //   set/token_name/2

            // RIX
            put("RIX/City's Blessing", "https://api.scryfall.com/cards/trix/6/en?format=image"); // TODO: missing from tokens data
            put("RIX/Elemental/1", "https://api.scryfall.com/cards/trix/1/en?format=image");
            put("RIX/Elemental/2", "https://api.scryfall.com/cards/trix/2/en?format=image");
            put("RIX/Golem", "https://api.scryfall.com/cards/trix/4/en?format=image");
            put("RIX/Emblem Huatli, Radiant Champion", "https://api.scryfall.com/cards/trix/5/en?format=image");
            put("RIX/Saproling", "https://api.scryfall.com/cards/trix/3/en?format=image");

            // RNA
            put("RNA/Beast", "https://api.scryfall.com/cards/trna/8/en?format=image");
            put("RNA/Centaur", "https://api.scryfall.com/cards/trna/5/en?format=image");
            put("RNA/Emblem Domri, Chaos Bringer", "https://api.scryfall.com/cards/trna/13/en?format=image");
            put("RNA/Frog Lizard", "https://api.scryfall.com/cards/trna/6/en?format=image");
            put("RNA/Goblin", "https://api.scryfall.com/cards/trna/4/en?format=image");
            put("RNA/Human", "https://api.scryfall.com/cards/trna/1/en?format=image");
            put("RNA/Illusion", "https://api.scryfall.com/cards/trna/2/en?format=image");
            put("RNA/Ooze", "https://api.scryfall.com/cards/trna/7/en?format=image");
            put("RNA/Sphinx", "https://api.scryfall.com/cards/trna/9/en?format=image");
            put("RNA/Spirit", "https://api.scryfall.com/cards/trna/10/en?format=image");
            put("RNA/Thopter", "https://api.scryfall.com/cards/trna/11/en?format=image");
            put("RNA/Treasure", "https://api.scryfall.com/cards/trna/12/en?format=image");
            put("RNA/Zombie", "https://api.scryfall.com/cards/trna/3/en?format=image");
            
            //GRN
            put("GRN/Angel", "https://api.scryfall.com/cards/tgrn/1/en?format=image");
            put("GRN/Elf Knight", "https://api.scryfall.com/cards/tgrn/6/en?format=image");
            put("GRN/Goblin", "https://api.scryfall.com/cards/tgrn/4/en?format=image");
            put("GRN/Insect", "https://api.scryfall.com/cards/tgrn/5/en?format=image");
            put("GRN/Emblem Ral, Izzet Viceroy", "https://api.scryfall.com/cards/tgrn/7/en?format=image");
            put("GRN/Soldier", "https://api.scryfall.com/cards/tgrn/2/en?format=image");
            put("GRN/Emblem Vraska, Golgari Queen", "https://api.scryfall.com/cards/tgrn/8/en?format=image");
            
            //DOM
            put("DOM/Cleric", "https://api.scryfall.com/cards/tdom/4/en?format=image");
            put("DOM/Construct", "https://api.scryfall.com/cards/tdom/14/en?format=image");
            put("DOM/Demon", "https://api.scryfall.com/cards/tdom/7/en?format=image");
            put("DOM/Elemental", "https://api.scryfall.com/cards/tdom/8/en?format=image");
            put("DOM/Goblin", "https://api.scryfall.com/cards/tdom/9/en?format=image");
            put("DOM/Emblem Jaya Ballard", "https://api.scryfall.com/cards/tdom/15/en?format=image");
            put("DOM/Karox Bladewing", "https://api.scryfall.com/cards/tdom/10/en?format=image");
            put("DOM/Knight/1", "https://api.scryfall.com/cards/tdom/1/en?format=image");
            put("DOM/Knight/2", "https://api.scryfall.com/cards/tdom/2/en?format=image");
            put("DOM/Nightmare Horror", "https://api.scryfall.com/cards/tdom/6/en?format=image");
            put("DOM/Saproling/1", "https://api.scryfall.com/cards/tdom/11/en?format=image");
            put("DOM/Saproling/2", "https://api.scryfall.com/cards/tdom/12/en?format=image");
            put("DOM/Saproling/3", "https://api.scryfall.com/cards/tdom/13/en?format=image");
            put("DOM/Soldier", "https://api.scryfall.com/cards/tdom/3/en?format=image");
            put("DOM/Emblem Teferi, Hero of Dominaria", "https://api.scryfall.com/cards/tdom/16/en?format=image");
            put("DOM/Zombie Knight", "https://api.scryfall.com/cards/tdom/5/en?format=image");
            
            //XLN
            put("XLN/Dinosaur", "https://api.scryfall.com/cards/txln/5/en?format=image");
            put("XLN/Illusion", "https://api.scryfall.com/cards/txln/2/en?format=image");
            put("XLN/Merfolk", "https://api.scryfall.com/cards/txln/3/en?format=image");
            put("XLN/Pirate", "https://api.scryfall.com/cards/txln/4/en?format=image");
            put("XLN/Plant", "https://api.scryfall.com/cards/txln/6/en?format=image");
            put("XLN/Treasure/1", "https://api.scryfall.com/cards/txln/7/en?format=image");
            put("XLN/Treasure/2", "https://api.scryfall.com/cards/txln/8/en?format=image");
            put("XLN/Treasure/3", "https://api.scryfall.com/cards/txln/9/en?format=image");
            put("XLN/Treasure/4", "https://api.scryfall.com/cards/txln/10/en?format=image");
            put("XLN/Vampire", "https://api.scryfall.com/cards/txln/1/en?format=image");
            
            //HOU
            put("HOU/Horse", "https://api.scryfall.com/cards/thou/10/en?format=image");
            put("HOU/Insect", "https://api.scryfall.com/cards/thou/12/en?format=image");
            put("HOU/Snake", "https://api.scryfall.com/cards/thou/11/en?format=image");
            
            //AKH
            put("AKH/Beast", "https://api.scryfall.com/cards/takh/21/en?format=image");
            put("AKH/Cat", "https://api.scryfall.com/cards/takh/16/en?format=image");
            put("AKH/Drake", "https://api.scryfall.com/cards/takh/18/en?format=image");
            put("AKH/Emblem - Gideon", "https://api.scryfall.com/cards/takh/25/en?format=image");
            put("AKH/Hippo", "https://api.scryfall.com/cards/takh/22/en?format=image");
            put("AKH/Snake", "https://api.scryfall.com/cards/takh/23/en?format=image");
            put("AKH/Warrior", "https://api.scryfall.com/cards/takh/17/en?format=image");
            put("AKH/Wurm", "https://api.scryfall.com/cards/takh/24/en?format=image");
            put("AKH/Zombie", "https://api.scryfall.com/cards/takh/20/en?format=image");
            
            //AER
            put("AER/Etherium Cell", "https://api.scryfall.com/cards/taer/3/en?format=image");
            put("AER/Gremlin", "https://api.scryfall.com/cards/taer/1/en?format=image");
            put("AER/Ragavan", "https://api.scryfall.com/cards/taer/2/en?format=image");
            put("AER/Emblem Tezzeret the Schemer", "https://api.scryfall.com/cards/taer/4/en?format=image");
            
            //KLD
            put("KLD/Beast", "https://api.scryfall.com/cards/tkld/1/en?format=image");
            put("KLD/Emblem Chandra", "https://api.scryfall.com/cards/tkld/10/en?format=image");
            put("KLD/Construct/1", "https://api.scryfall.com/cards/tkld/2/en?format=image");
            put("KLD/Construct/2", "https://api.scryfall.com/cards/tkld/3/en?format=image");
            put("KLD/Emblem Dovin", "https://api.scryfall.com/cards/tkld/12/en?format=image");
            put("KLD/Emblem Nissa", "https://api.scryfall.com/cards/tkld/11/en?format=image");
            put("KLD/Servo/1", "https://api.scryfall.com/cards/tkld/4/en?format=image");
            put("KLD/Servo/2", "https://api.scryfall.com/cards/tkld/5/en?format=image");
            put("KLD/Servo/3", "https://api.scryfall.com/cards/tkld/6/en?format=image");
            put("KLD/Thopter/1", "https://api.scryfall.com/cards/tkld/7/en?format=image");
            put("KLD/Thopter/2", "https://api.scryfall.com/cards/tkld/8/en?format=image");
            put("KLD/Thopter/3", "https://api.scryfall.com/cards/tkld/9/en?format=image");
            
            //EMN
            put("EMN/Eldrazi Horror", "https://api.scryfall.com/cards/temn/1/en?format=image");
            put("EMN/Human", "https://api.scryfall.com/cards/temn/7/en?format=image");
            put("EMN/Human Wizard", "https://api.scryfall.com/cards/temn/2/en?format=image");
            put("EMN/Emblem Liliana", "https://api.scryfall.com/cards/temn/9/en?format=image");
            put("EMN/Spider", "https://api.scryfall.com/cards/temn/8/en?format=image");
            put("EMN/Emblem Tamiyo", "https://api.scryfall.com/cards/temn/10/en?format=image");
            put("EMN/Zombie/1", "https://api.scryfall.com/cards/temn/3/en?format=image");
            put("EMN/Zombie/2", "https://api.scryfall.com/cards/temn/4/en?format=image");
            put("EMN/Zombie/3", "https://api.scryfall.com/cards/temn/5/en?format=image");
            put("EMN/Zombie/4", "https://api.scryfall.com/cards/temn/6/en?format=image");
            
            //SOI
            put("SOI/Angel", "https://api.scryfall.com/cards/tsoi/1/en?format=image");
            put("SOI/Emblem Arlinn", "https://api.scryfall.com/cards/tsoi/18/en?format=image");
            put("SOI/Clue/1", "https://api.scryfall.com/cards/tsoi/11/en?format=image");
            put("SOI/Clue/2", "https://api.scryfall.com/cards/tsoi/12/en?format=image");
            put("SOI/Clue/3", "https://api.scryfall.com/cards/tsoi/13/en?format=image");
            put("SOI/Clue/4", "https://api.scryfall.com/cards/tsoi/14/en?format=image");
            put("SOI/Clue/5", "https://api.scryfall.com/cards/tsoi/15/en?format=image");
            put("SOI/Clue/6", "https://api.scryfall.com/cards/tsoi/16/en?format=image");
            put("SOI/Devil", "https://api.scryfall.com/cards/tsoi/6/en?format=image");
            put("SOI/Human Cleric", "https://api.scryfall.com/cards/tsoi/10/en?format=image");
            put("SOI/Human Soldier", "https://api.scryfall.com/cards/tsoi/2/en?format=image");
            put("SOI/Insect", "https://api.scryfall.com/cards/tsoi/7/en?format=image");
            put("SOI/Emblem Jace", "https://api.scryfall.com/cards/tsoi/17/en?format=image");
            put("SOI/Ooze", "https://api.scryfall.com/cards/tsoi/8/en?format=image");
            put("SOI/Spirit", "https://api.scryfall.com/cards/tsoi/3/en?format=image");
            put("SOI/Vampire Knight", "https://api.scryfall.com/cards/tsoi/4/en?format=image");
            put("SOI/Wolf", "https://api.scryfall.com/cards/tsoi/9/en?format=image");
            put("SOI/Zombie", "https://api.scryfall.com/cards/tsoi/5/en?format=image");
            
            //OGW
            put("OGW/Angel", "https://api.scryfall.com/cards/togw/7/en?format=image");
            put("OGW/Eldrazi Scion/1", "https://api.scryfall.com/cards/togw/1/en?format=image");
            put("OGW/Eldrazi Scion/2", "https://api.scryfall.com/cards/togw/2/en?format=image");
            put("OGW/Eldrazi Scion/3", "https://api.scryfall.com/cards/togw/3/en?format=image");
            put("OGW/Eldrazi Scion/4", "https://api.scryfall.com/cards/togw/4/en?format=image");
            put("OGW/Eldrazi Scion/5", "https://api.scryfall.com/cards/togw/5/en?format=image");
            put("OGW/Eldrazi Scion/6", "https://api.scryfall.com/cards/togw/6/en?format=image");
            put("OGW/Elemental/1", "https://api.scryfall.com/cards/togw/10/en?format=image");
            put("OGW/Elemental/2", "https://api.scryfall.com/cards/togw/9/en?format=image");
            put("OGW/Plant", "https://api.scryfall.com/cards/togw/11/en?format=image");
            put("OGW/Zombie", "https://api.scryfall.com/cards/togw/8/en?format=image");
            
            //BFZ
            put("BFZ/Dragon", "https://api.scryfall.com/cards/tbfz/8/en?format=image");
            put("BFZ/Eldrazi", "https://api.scryfall.com/cards/tbfz/1/en?format=image");
            put("BFZ/Eldrazi Scion/1", "https://api.scryfall.com/cards/tbfz/2/en?format=image");
            put("BFZ/Eldrazi Scion/2", "https://api.scryfall.com/cards/tbfz/3/en?format=image");
            put("BFZ/Eldrazi Scion/3", "https://api.scryfall.com/cards/tbfz/4/en?format=image");
            put("BFZ/Elemental/1", "https://api.scryfall.com/cards/tbfz/11/en?format=image");
            put("BFZ/Elemental/2", "https://api.scryfall.com/cards/tbfz/9/en?format=image");
            put("BFZ/Emblem Gideon", "https://api.scryfall.com/cards/tbfz/12/en?format=image");
            put("BFZ/Emblem Kiora", "https://api.scryfall.com/cards/tbfz/14/en?format=image");
            put("BFZ/Knight Ally", "https://api.scryfall.com/cards/tbfz/5/en?format=image");
            put("BFZ/Kor Ally", "https://api.scryfall.com/cards/tbfz/6/en?format=image");
            put("BFZ/Emblem Nixilis", "https://api.scryfall.com/cards/tbfz/13/en?format=image");
            put("BFZ/Octopus", "https://api.scryfall.com/cards/tbfz/7/en?format=image");
            put("BFZ/Plant", "https://api.scryfall.com/cards/tbfz/10/en?format=image");
            
            // WAR
            put("WAR/Angel", "https://api.scryfall.com/cards/twar/2/en?format=image");
            put("WAR/Assassin", "https://api.scryfall.com/cards/twar/6/en?format=image");
            put("WAR/Citizen", "https://api.scryfall.com/cards/twar/16/en?format=image");
            put("WAR/Devil", "https://api.scryfall.com/cards/twar/12/en?format=image");
            put("WAR/Dragon", "https://api.scryfall.com/cards/twar/13/en?format=image");
            put("WAR/Goblin", "https://api.scryfall.com/cards/twar/14/en?format=image");
            put("WAR/Emblem Nissa, Who Shakes the World", "https://api.scryfall.com/cards/twar/19/en?format=image");
            put("WAR/Servo", "https://api.scryfall.com/cards/twar/18/en?format=image");
            put("WAR/Soldier", "https://api.scryfall.com/cards/twar/3/en?format=image");
            put("WAR/Spirit", "https://api.scryfall.com/cards/twar/1/en?format=image");
            put("WAR/Voja, Friend to Elves", "https://api.scryfall.com/cards/twar/17/en?format=image");
            put("WAR/Wall", "https://api.scryfall.com/cards/twar/4/en?format=image");
            put("WAR/Wizard", "https://api.scryfall.com/cards/twar/5/en?format=image");
            put("WAR/Wolf", "https://api.scryfall.com/cards/twar/15/en?format=image");
            put("WAR/Zombie Army/1", "https://api.scryfall.com/cards/twar/10/en?format=image");
            put("WAR/Zombie Army/2", "https://api.scryfall.com/cards/twar/8/en?format=image");
            put("WAR/Zombie Army/3", "https://api.scryfall.com/cards/twar/9/en?format=image");
            put("WAR/Zombie Warrior", "https://api.scryfall.com/cards/twar/11/en?format=image");
            put("WAR/Zombie", "https://api.scryfall.com/cards/twar/7/en?format=image");

            // MH1
            put("MH1/Angel", "https://api.scryfall.com/cards/tmh1/2/en?format=image");
            put("MH1/Bear", "https://api.scryfall.com/cards/tmh1/11/en?format=image");
            put("MH1/Bird", "https://api.scryfall.com/cards/tmh1/3/en?format=image");
            put("MH1/Construct", "https://api.scryfall.com/cards/tmh1/17/en?format=image");
            put("MH1/Elemental/1", "https://api.scryfall.com/cards/tmh1/8/en?format=image");
            put("MH1/Elemental/2", "https://api.scryfall.com/cards/tmh1/9/en?format=image");
            put("MH1/Elephant", "https://api.scryfall.com/cards/tmh1/12/en?format=image");
            put("MH1/Goblin", "https://api.scryfall.com/cards/tmh1/10/en?format=image");
            put("MH1/Golem", "https://api.scryfall.com/cards/tmh1/18/en?format=image");
            put("MH1/Illusion", "https://api.scryfall.com/cards/tmh1/5/en?format=image");
            put("MH1/Marit Lage", "https://api.scryfall.com/cards/tmh1/6/en?format=image");
            put("MH1/Myr", "https://api.scryfall.com/cards/tmh1/19/en?format=image");
            put("MH1/Rhino", "https://api.scryfall.com/cards/tmh1/13/en?format=image");
            put("MH1/Emblem Serra the Benevolent", "https://api.scryfall.com/cards/tmh1/20/en?format=image");
            put("MH1/Shapeshifter", "https://api.scryfall.com/cards/tmh1/1/en?format=image");
            put("MH1/Soldier", "https://api.scryfall.com/cards/tmh1/4/en?format=image");
            put("MH1/Spider", "https://api.scryfall.com/cards/tmh1/14/en?format=image");
            put("MH1/Spirit", "https://api.scryfall.com/cards/tmh1/16/en?format=image");
            put("MH1/Squirrel", "https://api.scryfall.com/cards/tmh1/15/en?format=image");
            put("MH1/Emblem Wrenn and Six", "https://api.scryfall.com/cards/tmh1/21/en?format=image");
            put("MH1/Zombie", "https://api.scryfall.com/cards/tmh1/7/en?format=image");
            
            //M19
            put("M19/Emblem Ajani, Adversary of Tyrants", "https://api.scryfall.com/cards/tm19/15/en?format=image");
            put("M19/Angel", "https://api.scryfall.com/cards/tm19/1/en?format=image");
            put("M19/Avatar", "https://api.scryfall.com/cards/tm19/2/en?format=image");
            put("M19/Bat", "https://api.scryfall.com/cards/tm19/7/en?format=image");
            put("M19/Beast", "https://api.scryfall.com/cards/tm19/12/en?format=image");
            put("M19/Cat", "https://api.scryfall.com/cards/tm19/3/en?format=image");
            put("M19/Dragon/1", "https://api.scryfall.com/cards/tm19/9/en?format=image");
            put("M19/Dragon/2", "https://api.scryfall.com/cards/tm19/10/en?format=image");
            put("M19/Elf Warrior", "https://api.scryfall.com/cards/tm19/13/en?format=image");
            put("M19/Goblin", "https://api.scryfall.com/cards/tm19/11/en?format=image");
            put("M19/Knight", "https://api.scryfall.com/cards/tm19/4/en?format=image");
            put("M19/Ox", "https://api.scryfall.com/cards/tm19/5/en?format=image");
            put("M19/Soldier", "https://api.scryfall.com/cards/tm19/6/en?format=image");
            put("M19/Emblem Tezzeret, Artifice Master", "https://api.scryfall.com/cards/tm19/16/en?format=image");
            put("M19/Thopter", "https://api.scryfall.com/cards/tm19/14/en?format=image");
            put("M19/Emblem Vivien Reid", "https://api.scryfall.com/cards/tm19/17/en?format=image");
            put("M19/Zombie", "https://api.scryfall.com/cards/tm19/8/en?format=image");
            
            // M20
            put("M20/Ajani's Pridemate", "https://api.scryfall.com/cards/tm20/1/en?format=image");
            put("M20/Emblem Chandra, Awakened Inferno", "https://api.scryfall.com/cards/tm20/11/en?format=image");
            put("M20/Demon", "https://api.scryfall.com/cards/tm20/5/en?format=image");
            put("M20/Elemental Bird", "https://api.scryfall.com/cards/tm20/4/en?format=image");
            put("M20/Elemental", "https://api.scryfall.com/cards/tm20/7/en?format=image");
            put("M20/Golem", "https://api.scryfall.com/cards/tm20/9/en?format=image");
            put("M20/Emblem Mu Yanling, Sky Dancer", "https://api.scryfall.com/cards/tm20/12/en?format=image");
            put("M20/Soldier", "https://api.scryfall.com/cards/tm20/2/en?format=image");
            put("M20/Spirit", "https://api.scryfall.com/cards/tm20/3/en?format=image");
            put("M20/Treasure", "https://api.scryfall.com/cards/tm20/10/en?format=image");
            put("M20/Wolf", "https://api.scryfall.com/cards/tm20/8/en?format=image");
            put("M20/Zombie", "https://api.scryfall.com/cards/tm20/6/en?format=image");
            
            //C18
            put("C18/Angel", "https://api.scryfall.com/cards/tc18/3/en?format=image");
            put("C18/Cat Warrior", "https://api.scryfall.com/cards/tc18/15/en?format=image");
            put("C18/Cat", "https://api.scryfall.com/cards/tc18/5/en?format=image");
            put("C18/Clue", "https://api.scryfall.com/cards/tc18/19/en?format=image");
            put("C18/Construct/1", "https://api.scryfall.com/cards/tc18/20/en?format=image");
            put("C18/Construct/2", "https://api.scryfall.com/cards/tc18/21/en?format=image");
            put("C18/Dragon Egg", "https://api.scryfall.com/cards/tc18/10/en?format=image");
            put("C18/Dragon", "https://api.scryfall.com/cards/tc18/11/en?format=image");
            put("C18/Elemental", "https://api.scryfall.com/cards/tc18/16/en?format=image");
            put("C18/Horror", "https://api.scryfall.com/cards/tc18/22/en?format=image");
            put("C18/Manifest", "https://api.scryfall.com/cards/tc18/1/en?format=image");
            put("C18/Mask", "https://api.scryfall.com/cards/tc18/4/en?format=image");
            put("C18/Myr", "https://api.scryfall.com/cards/tc18/7/en?format=image");
            put("C18/Plant", "https://api.scryfall.com/cards/tc18/17/en?format=image");
            put("C18/Servo", "https://api.scryfall.com/cards/tc18/24/en?format=image");
            put("C18/Survivor", "https://api.scryfall.com/cards/tc18/12/en?format=image");
            put("C18/Thopter/1", "https://api.scryfall.com/cards/tc18/25/en?format=image");
            put("C18/Thopter/2", "https://api.scryfall.com/cards/tc18/26/en?format=image");
            put("C18/Thopter/3", "https://api.scryfall.com/cards/tc18/8/en?format=image");
            put("C18/Worm", "https://api.scryfall.com/cards/tc18/18/en?format=image");
            put("C18/Zombie", "https://api.scryfall.com/cards/tc18/9/en?format=image");
            
            //C19
            put("C19/Assassin", "https://api.scryfall.com/cards/tc19/9/en?format=image");
            put("C19/Beast/1", "https://api.scryfall.com/cards/tc19/13/en?format=image");
            put("C19/Beast/2", "https://api.scryfall.com/cards/tc19/14/en?format=image");
            put("C19/Bird/1", "https://api.scryfall.com/cards/tc19/2/en?format=image");
            put("C19/Bird/2", "https://api.scryfall.com/cards/tc19/1/en?format=image");
            put("C19/Centaur", "https://api.scryfall.com/cards/tc19/15/en?format=image");
            put("C19/Dragon", "https://api.scryfall.com/cards/tc19/12/en?format=image");
            put("C19/Drake", "https://api.scryfall.com/cards/tc19/8/en?format=image");
            put("C19/Egg", "https://api.scryfall.com/cards/tc19/16/en?format=image");
            put("C19/Eldrazi", "https://api.scryfall.com/cards/tc19/26/en?format=image");
            put("C19/Gargoyle", "https://api.scryfall.com/cards/tc19/22/en?format=image");
            put("C19/Horror", "https://api.scryfall.com/cards/tc19/23/en?format=image");
            put("C19/Human", "https://api.scryfall.com/cards/tc19/3/en?format=image");
            put("C19/Emblem Ob Nixilis Reignited", "https://api.scryfall.com/cards/tc19/29/en?format=image");
            put("C19/Pegasus", "https://api.scryfall.com/cards/tc19/4/en?format=image");
            put("C19/Plant", "https://api.scryfall.com/cards/tc19/17/en?format=image");
            put("C19/Rhino", "https://api.scryfall.com/cards/tc19/18/en?format=image");
            put("C19/Saproling", "https://api.scryfall.com/cards/tc19/19/en?format=image");
            put("C19/Sculpture", "https://api.scryfall.com/cards/tc19/24/en?format=image");
            put("C19/Snake", "https://api.scryfall.com/cards/tc19/20/en?format=image");
            put("C19/Spirit", "https://api.scryfall.com/cards/tc19/5/en?format=image");
            put("C19/Treasure", "https://api.scryfall.com/cards/tc19/25/en?format=image");
            put("C19/Wurm", "https://api.scryfall.com/cards/tc19/21/en?format=image");
            put("C19/Zombie/1", "https://api.scryfall.com/cards/tc19/11/en?format=image");
            put("C19/Zombie/2", "https://api.scryfall.com/cards/tc19/10/en?format=image");


            // generate supported sets
            supportedSets.clear();
            for (String cardName : this.keySet()) {
                String[] s = cardName.split("\\/");
                if (s.length > 1) {
                    supportedSets.putIfAbsent(s[0], s[0]);
                }
            }
        }
    };

    public static Map<String, String> getSupportedSets() {
        return supportedSets;
    }

    public static String findTokenLink(String setCode, String tokenName, Integer tokenNumber) {
        String search = setCode + "/" + tokenName + (!tokenNumber.equals(0) ? "/" + tokenNumber : "");
        return supportedCards.getOrDefault(search, null);
    }
}
