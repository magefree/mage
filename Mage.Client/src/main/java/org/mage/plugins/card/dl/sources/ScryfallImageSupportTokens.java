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
            put("GRN/Bird Illusion", "https://api.scryfall.com/cards/tgrn/3/en?format=image");
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

            //AKH - tokens
            put("AKH/Beast", "https://api.scryfall.com/cards/takh/21/en?format=image");
            put("AKH/Cat", "https://api.scryfall.com/cards/takh/16/en?format=image");
            put("AKH/Drake", "https://api.scryfall.com/cards/takh/18/en?format=image");
            put("AKH/Emblem Gideon", "https://api.scryfall.com/cards/takh/25/en?format=image");
            put("AKH/Hippo", "https://api.scryfall.com/cards/takh/22/en?format=image");
            put("AKH/Insect", "https://api.scryfall.com/cards/takh/19/en?format=image");
            put("AKH/Snake", "https://api.scryfall.com/cards/takh/23/en?format=image");
            put("AKH/Warrior", "https://api.scryfall.com/cards/takh/17/en?format=image");
            put("AKH/Wurm", "https://api.scryfall.com/cards/takh/24/en?format=image");
            put("AKH/Zombie", "https://api.scryfall.com/cards/takh/20/en?format=image");
            //AKH - embalm ability (token from card)
            put("AKH/Angel of Sanctions", "https://api.scryfall.com/cards/takh/1/en?format=image");
            put("AKH/Anointer Priest", "https://api.scryfall.com/cards/takh/2/en?format=image");
            put("AKH/Aven Initiate", "https://api.scryfall.com/cards/takh/3/en?format=image");
            put("AKH/Aven Wind Guide", "https://api.scryfall.com/cards/takh/4/en?format=image");
            put("AKH/Glyph Keeper", "https://api.scryfall.com/cards/takh/5/en?format=image");
            put("AKH/Heart-Piercer Manticore", "https://api.scryfall.com/cards/takh/6/en?format=image");
            put("AKH/Honored Hydra", "https://api.scryfall.com/cards/takh/7/en?format=image");
            put("AKH/Labyrinth Guardian", "https://api.scryfall.com/cards/takh/8/en?format=image");
            put("AKH/Oketra's Attendant", "https://api.scryfall.com/cards/takh/9/en?format=image");
            put("AKH/Sacred Cat", "https://api.scryfall.com/cards/takh/10/en?format=image");
            put("AKH/Tah-Crop Skirmisher", "https://api.scryfall.com/cards/takh/11/en?format=image");
            put("AKH/Temmet, Vizier of Naktamun", "https://api.scryfall.com/cards/takh/12/en?format=image");
            put("AKH/Trueheart Duelist", "https://api.scryfall.com/cards/takh/13/en?format=image");
            put("AKH/Unwavering Initiate", "https://api.scryfall.com/cards/takh/14/en?format=image");
            put("AKH/Vizier of Many Faces", "https://api.scryfall.com/cards/takh/15/en?format=image");

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

            // M19
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

            // C18
            put ("C18/Angel", "https://api.scryfall.com/cards/tc18/3/en?format=image");
            put ("C18/Beast/1", "https://api.scryfall.com/cards/tc18/13/en?format=image");
            put ("C18/Beast/2", "https://api.scryfall.com/cards/tc18/14/en?format=image");
            put ("C18/Cat", "https://api.scryfall.com/cards/tc18/5/en?format=image");
            put ("C18/Cat Warrior", "https://api.scryfall.com/cards/tc18/15/en?format=image");
            put ("C18/Clue", "https://api.scryfall.com/cards/tc18/19/en?format=image");
            put ("C18/Construct/1", "https://api.scryfall.com/cards/tc18/20/en?format=image");
            put ("C18/Construct/2", "https://api.scryfall.com/cards/tc18/21/en?format=image");
            put ("C18/Dragon", "https://api.scryfall.com/cards/tc18/11/en?format=image");
            put ("C18/Dragon Egg", "https://api.scryfall.com/cards/tc18/10/en?format=image");
            put ("C18/Elemental", "https://api.scryfall.com/cards/tc18/16/en?format=image");
            put ("C18/Phyrexian Horror", "https://api.scryfall.com/cards/tc18/22/en?format=image");
            put ("C18/Mask", "https://api.scryfall.com/cards/tc18/4/en?format=image");
            put ("C18/Myr", "https://api.scryfall.com/cards/tc18/23/en?format=image");
            put ("C18/Phyrexian Myr", "https://api.scryfall.com/cards/tc18/7/en?format=image");
            put ("C18/Plant", "https://api.scryfall.com/cards/tc18/17/en?format=image");
            put ("C18/Servo", "https://api.scryfall.com/cards/tc18/24/en?format=image");
            put ("C18/Shapeshifter", "https://api.scryfall.com/cards/tc18/2/en?format=image");
            put ("C18/Soldier", "https://api.scryfall.com/cards/tc18/6/en?format=image");
            put ("C18/Survivor", "https://api.scryfall.com/cards/tc18/12/en?format=image");
            put ("C18/Thopter/1", "https://api.scryfall.com/cards/tc18/25/en?format=image");
            put ("C18/Thopter/2", "https://api.scryfall.com/cards/tc18/26/en?format=image");
            put ("C18/Thopter/3", "https://api.scryfall.com/cards/tc18/8/en?format=image");
            put ("C18/Worm", "https://api.scryfall.com/cards/tc18/18/en?format=image");
            put ("C18/Zombie", "https://api.scryfall.com/cards/tc18/9/en?format=image");

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
            put("C19/Phyrexian Horror", "https://api.scryfall.com/cards/tc19/23/en?format=image");
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
            put("C19/Zombie/1", "https://api.scryfall.com/cards/tc19/10/en?format=image");
            put("C19/Zombie/2", "https://api.scryfall.com/cards/tc19/11/en?format=image");

            // ELD
            put("ELD/Bear", "https://api.scryfall.com/cards/teld/8/en?format=image");
            put("ELD/Boar", "https://api.scryfall.com/cards/teld/9/en?format=image");
            put("ELD/Dwarf", "https://api.scryfall.com/cards/teld/7/en?format=image");
            put("ELD/Faerie", "https://api.scryfall.com/cards/teld/5/en?format=image");
            put("ELD/Food/1", "https://api.scryfall.com/cards/teld/15/en?format=image");
            put("ELD/Food/2", "https://api.scryfall.com/cards/teld/16/en?format=image");
            put("ELD/Food/3", "https://api.scryfall.com/cards/teld/17/en?format=image");
            put("ELD/Food/4", "https://api.scryfall.com/cards/teld/18/en?format=image");
            put("ELD/Emblem Garruk, Cursed Huntsman", "https://api.scryfall.com/cards/teld/19/en?format=image");
            put("ELD/Giant", "https://api.scryfall.com/cards/teld/10/en?format=image");
            put("ELD/Goat", "https://api.scryfall.com/cards/teld/1/en?format=image");
            put("ELD/Human Cleric", "https://api.scryfall.com/cards/teld/11/en?format=image");
            put("ELD/Human Rogue", "https://api.scryfall.com/cards/teld/12/en?format=image");
            put("ELD/Human Warrior", "https://api.scryfall.com/cards/teld/13/en?format=image");
            put("ELD/Human", "https://api.scryfall.com/cards/teld/2/en?format=image");
            put("ELD/Knight", "https://api.scryfall.com/cards/teld/3/en?format=image");
            put("ELD/Mouse", "https://api.scryfall.com/cards/teld/4/en?format=image");
            put("ELD/Rat", "https://api.scryfall.com/cards/teld/6/en?format=image");
            put("ELD/Wolf", "https://api.scryfall.com/cards/teld/14/en?format=image");

            // THB
            put("THB/Elemental", "https://api.scryfall.com/cards/tthb/8/en?format=image");
            put("THB/Goat", "https://api.scryfall.com/cards/tthb/1/en?format=image");
            put("THB/Gold", "https://api.scryfall.com/cards/tthb/13/en?format=image");
            put("THB/Human Soldier", "https://api.scryfall.com/cards/tthb/2/en?format=image");
            put("THB/Kraken", "https://api.scryfall.com/cards/tthb/4/en?format=image");
            put("THB/Nightmare", "https://api.scryfall.com/cards/tthb/12/en?format=image");
            put("THB/Pegasus", "https://api.scryfall.com/cards/tthb/3/en?format=image");
            put("THB/Reflection", "https://api.scryfall.com/cards/tthb/5/en?format=image");
            put("THB/Satyr", "https://api.scryfall.com/cards/tthb/9/en?format=image");
            put("THB/Spider", "https://api.scryfall.com/cards/tthb/10/en?format=image");
            put("THB/Tentacle", "https://api.scryfall.com/cards/tthb/6/en?format=image");
            put("THB/Wall", "https://api.scryfall.com/cards/tthb/14/en?format=image");
            put("THB/Wolf", "https://api.scryfall.com/cards/tthb/11/en?format=image");
            put("THB/Zombie", "https://api.scryfall.com/cards/tthb/7/en?format=image");

            // IKO
            put("IKO/Emblem Narset Of The Ancient Way", "https://api.scryfall.com/cards/tiko/12/en?format=image");
            put("IKO/Beast", "https://api.scryfall.com/cards/tiko/10/en?format=image");
            put("IKO/Cat Bird", "https://api.scryfall.com/cards/tiko/2/en?format=image");
            put("IKO/Cat", "https://api.scryfall.com/cards/tiko/1/en?format=image");
            put("IKO/Dinosaur Beast", "https://api.scryfall.com/cards/tiko/11/en?format=image");
            put("IKO/Dinosaur", "https://api.scryfall.com/cards/tiko/8/en?format=image");
            put("IKO/Feather", "https://api.scryfall.com/cards/tiko/9/en?format=image");
            put("IKO/Human Soldier/1", "https://api.scryfall.com/cards/tiko/3/en?format=image");
            put("IKO/Human Soldier/2", "https://api.scryfall.com/cards/tiko/4/en?format=image");
            put("IKO/Human Soldier/3", "https://api.scryfall.com/cards/tiko/5/en?format=image");
            put("IKO/Kraken", "https://api.scryfall.com/cards/tiko/6/en?format=image");
            put("IKO/Shark", "https://api.scryfall.com/cards/tiko/7/en?format=image");

            // PCA
            put("PCA/Angel", "https://api.scryfall.com/cards/tpca/5/en?format=image");
            put("PCA/Beast", "https://api.scryfall.com/cards/tpca/13/en?format=image");
            put("PCA/Boar", "https://api.scryfall.com/cards/tpca/14/en?format=image");
            put("PCA/Dragon", "https://api.scryfall.com/cards/tpca/10/en?format=image");
            put("PCA/Eldrazi", "https://api.scryfall.com/cards/tpca/1/en?format=image");
            put("PCA/Eldrazi Spawn/1", "https://api.scryfall.com/cards/tpca/2/en?format=image");
            put("PCA/Eldrazi Spawn/2", "https://api.scryfall.com/cards/tpca/3/en?format=image");
            put("PCA/Eldrazi Spawn/3", "https://api.scryfall.com/cards/tpca/4/en?format=image");
            put("PCA/Phyrexian Germ", "https://api.scryfall.com/cards/tpca/7/en?format=image");
            put("PCA/Goat", "https://api.scryfall.com/cards/tpca/6/en?format=image");
            put("PCA/Goblin", "https://api.scryfall.com/cards/tpca/11/en?format=image");
            put("PCA/Hellion", "https://api.scryfall.com/cards/tpca/12/en?format=image");
            put("PCA/Insect", "https://api.scryfall.com/cards/tpca/15/en?format=image");
            put("PCA/Ooze/1", "https://api.scryfall.com/cards/tpca/16/en?format=image");
            put("PCA/Ooze/2", "https://api.scryfall.com/cards/tpca/17/en?format=image");
            put("PCA/Plant", "https://api.scryfall.com/cards/tpca/18/en?format=image");
            put("PCA/Saproling", "https://api.scryfall.com/cards/tpca/19/en?format=image");
            put("PCA/Spider", "https://api.scryfall.com/cards/tpca/8/en?format=image");
            put("PCA/Zombie", "https://api.scryfall.com/cards/tpca/9/en?format=image");
            put("PCA/Plane - Academy at Tolaria West", "https://api.scryfall.com/cards/opca/9/en?format=image");
            put("PCA/Plane - Agyrem", "https://api.scryfall.com/cards/opca/11/en?format=image");
            put("PCA/Plane - Akoum", "https://api.scryfall.com/cards/opca/12/en?format=image");
            put("PCA/Plane - Astral Arena", "https://api.scryfall.com/cards/opca/14/en?format=image");
            put("PCA/Plane - Bant", "https://api.scryfall.com/cards/opca/15/en?format=image");
            put("PCA/Plane - Edge of Malacol", "https://api.scryfall.com/cards/opca/20/en?format=image");
            put("PCA/Plane - Feeding Grounds", "https://api.scryfall.com/cards/opca/23/en?format=image");
            put("PCA/Plane - Fields of Summer", "https://api.scryfall.com/cards/opca/24/en?format=image");
            put("PCA/Plane - Hedron Fields of Agadeem", "https://api.scryfall.com/cards/opca/35/en?format=image");
            put("PCA/Plane - Lethe Lake", "https://api.scryfall.com/cards/opca/47/en?format=image");
            put("PCA/Plane - Naya", "https://api.scryfall.com/cards/opca/55/en?format=image");
            put("PCA/Plane - Panopticon", "https://api.scryfall.com/cards/opca/62/en?format=image");
            put("PCA/Plane - Tazeem", "https://api.scryfall.com/cards/opca/78/en?format=image");
            put("PCA/Plane - The Dark Barony", "https://api.scryfall.com/cards/opca/19/en?format=image");
            put("PCA/Plane - The Eon Fog", "https://api.scryfall.com/cards/opca/22/en?format=image");
            put("PCA/Plane - The Great Forest", "https://api.scryfall.com/cards/opca/32/en?format=image");
            put("PCA/Plane - The Zephyr Maze", "https://api.scryfall.com/cards/opca/86/en?format=image");
            put("PCA/Plane - Truga Jungle", "https://api.scryfall.com/cards/opca/81/en?format=image");
            put("PCA/Plane - Trail of the Mage-Rings", "https://api.scryfall.com/cards/opca/80/en?format=image");
            put("PCA/Plane - Turri Island", "https://api.scryfall.com/cards/opca/82/en?format=image");
            put("PCA/Plane - Undercity Reaches", "https://api.scryfall.com/cards/opca/83/en?format=image");

            // C20
            put("C20/Angel", "https://api.scryfall.com/cards/tc20/1/en?format=image");
            put("C20/Beast", "https://api.scryfall.com/cards/tc20/11/en?format=image");
            put("C20/Bird Illusion", "https://api.scryfall.com/cards/tc20/7/en?format=image");
            put("C20/Bird", "https://api.scryfall.com/cards/tc20/2/en?format=image");
            put("C20/Dinosaur Cat", "https://api.scryfall.com/cards/tc20/16/en?format=image");
            put("C20/Drake", "https://api.scryfall.com/cards/tc20/8/en?format=image");
            put("C20/Elemental/1", "https://api.scryfall.com/cards/tc20/10/en?format=image"); // 3/1
            put("C20/Elemental/2", "https://api.scryfall.com/cards/tc20/3/en?format=image"); // 4/4
            put("C20/Goblin Warrior", "https://api.scryfall.com/cards/tc20/17/en?format=image");
            put("C20/Human", "https://api.scryfall.com/cards/tc20/4/en?format=image");
            put("C20/Hydra", "https://api.scryfall.com/cards/tc20/12/en?format=image");
            put("C20/Insect/1", "https://api.scryfall.com/cards/tc20/13/en?format=image"); // deathtouch
            put("C20/Insect/2", "https://api.scryfall.com/cards/tc20/18/en?format=image"); // haste
            put("C20/Saproling", "https://api.scryfall.com/cards/tc20/14/en?format=image");
            put("C20/Snake", "https://api.scryfall.com/cards/tc20/15/en?format=image");
            put("C20/Soldier", "https://api.scryfall.com/cards/tc20/5/en?format=image");
            put("C20/Spirit", "https://api.scryfall.com/cards/tc20/6/en?format=image");
            put("C20/Treasure", "https://api.scryfall.com/cards/tc20/19/en?format=image");
            put("C20/Zombie", "https://api.scryfall.com/cards/tc20/9/en?format=image");

            // M21
            put("M21/Angel", "https://api.scryfall.com/cards/tm21/1/en?format=image");
            put("M21/Emblem Basri Ket", "https://api.scryfall.com/cards/tm21/16/en?format=image");
            put("M21/Beast", "https://api.scryfall.com/cards/tm21/10/en?format=image");
            put("M21/Bird", "https://api.scryfall.com/cards/tm21/2/en?format=image");
            put("M21/Cat/1", "https://api.scryfall.com/cards/tm21/20/en?format=image"); // 1/1
            put("M21/Cat/2", "https://api.scryfall.com/cards/tm21/11/en?format=image"); // 2/2
            put("M21/Construct", "https://api.scryfall.com/cards/tm21/14/en?format=image");
            put("M21/Demon", "https://api.scryfall.com/cards/tm21/6/en?format=image");
            put("M21/Dog", "https://api.scryfall.com/cards/tm21/19/en?format=image");
            put("M21/Emblem Garruk, Unleashed", "https://api.scryfall.com/cards/tm21/17/en?format=image");
            put("M21/Goblin Wizard", "https://api.scryfall.com/cards/tm21/8/en?format=image");
            put("M21/Griffin", "https://api.scryfall.com/cards/tm21/3/en?format=image");
            put("M21/Knight", "https://api.scryfall.com/cards/tm21/4/en?format=image");
            put("M21/Emblem Liliana, Waker of the Dead", "https://api.scryfall.com/cards/tm21/18/en?format=image");
            put("M21/Pirate", "https://api.scryfall.com/cards/tm21/9/en?format=image");
            put("M21/Saproling", "https://api.scryfall.com/cards/tm21/12/en?format=image");
            put("M21/Soldier", "https://api.scryfall.com/cards/tm21/5/en?format=image");
            put("M21/Treasure", "https://api.scryfall.com/cards/tm21/15/en?format=image");
            put("M21/Weird", "https://api.scryfall.com/cards/tm21/13/en?format=image");
            put("M21/Zombie", "https://api.scryfall.com/cards/tm21/7/en?format=image");

            // ZNR
            put("ZNR/Angel Warrior", "https://api.scryfall.com/cards/tznr/1/en?format=image");
            put("ZNR/Cat Beast", "https://api.scryfall.com/cards/tznr/3/en?format=image");
            put("ZNR/Cat", "https://api.scryfall.com/cards/tznr/2/en?format=image");
            put("ZNR/Construct", "https://api.scryfall.com/cards/tznr/10/en?format=image");
            put("ZNR/Drake", "https://api.scryfall.com/cards/tznr/5/en?format=image");
            put("ZNR/Goblin Construct", "https://api.scryfall.com/cards/tznr/11/en?format=image");
            put("ZNR/Hydra", "https://api.scryfall.com/cards/tznr/9/en?format=image");
            put("ZNR/Illusion", "https://api.scryfall.com/cards/tznr/6/en?format=image");
            put("ZNR/Insect", "https://api.scryfall.com/cards/tznr/7/en?format=image");
            put("ZNR/Kor Warrior", "https://api.scryfall.com/cards/tznr/4/en?format=image");
            put("ZNR/Plant", "https://api.scryfall.com/cards/tznr/8/en?format=image");

            // ZNC
            put("ZNC/Beast", "https://api.scryfall.com/cards/tznc/7/en?format=image");
            put("ZNC/Bird", "https://api.scryfall.com/cards/tznc/1/en?format=image");
            put("ZNC/Elemental/1", "https://api.scryfall.com/cards/tznc/10/en?format=image"); // 5/5
            put("ZNC/Elemental/2", "https://api.scryfall.com/cards/tznc/8/en?format=image"); // 2/2
            put("ZNC/Faerie Rogue", "https://api.scryfall.com/cards/tznc/3/en?format=image");
            //put("ZNC/Germ", "https://api.scryfall.com/cards/tznc/4/en?format=image"); // must be in chest or antology
            put("ZNC/Goblin Rogue", "https://api.scryfall.com/cards/tznc/5/en?format=image");
            put("ZNC/Kor Ally", "https://api.scryfall.com/cards/tznc/2/en?format=image");
            put("ZNC/Rat", "https://api.scryfall.com/cards/tznc/6/en?format=image");
            put("ZNC/Saproling", "https://api.scryfall.com/cards/tznc/9/en?format=image");
            put("ZNC/Thopter", "https://api.scryfall.com/cards/tznc/11/en?format=image");

            // CMR
            put("CMR/Angel", "https://api.scryfall.com/cards/tcmr/1/en?format=image");
            put("CMR/Beast/1", "https://api.scryfall.com/cards/tcmr/18/en?format=image"); // 3/3
            put("CMR/Beast/2", "https://api.scryfall.com/cards/tcmr/19/en?format=image"); // 4/4
            put("CMR/Cat", "https://api.scryfall.com/cards/tcmr/15/en?format=image");
            put("CMR/Dragon", "https://api.scryfall.com/cards/tcmr/7/en?format=image");
            put("CMR/Elephant", "https://api.scryfall.com/cards/tcmr/20/en?format=image");
            put("CMR/Elf Warrior", "https://api.scryfall.com/cards/tcmr/8/en?format=image");
            put("CMR/Golem", "https://api.scryfall.com/cards/tcmr/9/en?format=image");
            put("CMR/Phyrexian Horror", "https://api.scryfall.com/cards/tcmr/10/en?format=image");
            put("CMR/Illusion", "https://api.scryfall.com/cards/tcmr/17/en?format=image");
            put("CMR/Plant", "https://api.scryfall.com/cards/tcmr/21/en?format=image");
            put("CMR/Rock", "https://api.scryfall.com/cards/tcmr/11/en?format=image");
            put("CMR/Salamander Warrior", "https://api.scryfall.com/cards/tcmr/4/en?format=image");
            put("CMR/Saproling", "https://api.scryfall.com/cards/tcmr/22/en?format=image");
            put("CMR/Soldier/1", "https://api.scryfall.com/cards/tcmr/16/en?format=image");
            put("CMR/Soldier/2", "https://api.scryfall.com/cards/tcmr/2/en?format=image");
            put("CMR/Spirit", "https://api.scryfall.com/cards/tcmr/3/en?format=image");
            put("CMR/Thrull", "https://api.scryfall.com/cards/tcmr/5/en?format=image");
            put("CMR/Treasure", "https://api.scryfall.com/cards/tcmr/12/en?format=image");
            put("CMR/Zombie", "https://api.scryfall.com/cards/tcmr/6/en?format=image");

            // KHM
            put("KHM/Angel Warrior", "https://api.scryfall.com/cards/tkhm/2/en?format=image");
            put("KHM/Bear", "https://api.scryfall.com/cards/tkhm/13/en?format=image");
            put("KHM/Bird", "https://api.scryfall.com/cards/tkhm/5/en?format=image");
            put("KHM/Cat", "https://api.scryfall.com/cards/tkhm/14/en?format=image");
            put("KHM/Demon Berserker", "https://api.scryfall.com/cards/tkhm/10/en?format=image");
            put("KHM/Dragon", "https://api.scryfall.com/cards/tkhm/11/en?format=image");
            put("KHM/Dwarf Berserker", "https://api.scryfall.com/cards/tkhm/12/en?format=image");
            put("KHM/Elf Warrior", "https://api.scryfall.com/cards/tkhm/15/en?format=image");
            put("KHM/Giant Wizard", "https://api.scryfall.com/cards/tkhm/6/en?format=image");
            put("KHM/Human Warrior", "https://api.scryfall.com/cards/tkhm/3/en?format=image");
            put("KHM/Icy Manalith", "https://api.scryfall.com/cards/tkhm/17/en?format=image");
            put("KHM/Koma's Coil", "https://api.scryfall.com/cards/tkhm/7/en?format=image");
            put("KHM/Replicated Ring", "https://api.scryfall.com/cards/tkhm/18/en?format=image");
            put("KHM/Shapeshifter", "https://api.scryfall.com/cards/tkhm/8/en?format=image");
            put("KHM/Shard", "https://api.scryfall.com/cards/tkhm/1/en?format=image");
            put("KHM/Spirit", "https://api.scryfall.com/cards/tkhm/4/en?format=image");
            put("KHM/Treasure", "https://api.scryfall.com/cards/tkhm/19/en?format=image");
            put("KHM/Troll Warrior", "https://api.scryfall.com/cards/tkhm/16/en?format=image");
            put("KHM/Zombie Berserker", "https://api.scryfall.com/cards/tkhm/9/en?format=image");
            put("KHM/Emblem Kaya the Inexorable", "https://api.scryfall.com/cards/tkhm/20/en?format=image");
            put("KHM/Emblem Tibalt, Cosmic Impostor", "https://api.scryfall.com/cards/tkhm/21/en?format=image");
            put("KHM/Emblem Tyvar Kell", "https://api.scryfall.com/cards/tkhm/22/en?format=image");

            // KHC
            put("KHC/Bird", "https://api.scryfall.com/cards/tkhc/1/en?format=image");
            put("KHC/Boar", "https://api.scryfall.com/cards/tkhc/5/en?format=image");
            put("KHC/Elemental", "https://api.scryfall.com/cards/tkhc/6/en?format=image");
            put("KHC/Kithkin Soldier", "https://api.scryfall.com/cards/tkhc/2/en?format=image");
            put("KHC/Pegasus", "https://api.scryfall.com/cards/tkhc/3/en?format=image");
            put("KHC/Servo", "https://api.scryfall.com/cards/tkhc/7/en?format=image");
            put("KHC/Soldier", "https://api.scryfall.com/cards/tkhc/4/en?format=image");
            put("KHC/Thopter", "https://api.scryfall.com/cards/tkhc/8/en?format=image");

            // TSR
            put("TSR/Ape", "https://api.scryfall.com/cards/ttsr/10/en?format=image");
            put("TSR/Assembly-Worker", "https://api.scryfall.com/cards/ttsr/14/en?format=image");
            put("TSR/Bat", "https://api.scryfall.com/cards/ttsr/4/en?format=image");
            put("TSR/Cloud Sprite", "https://api.scryfall.com/cards/ttsr/3/en?format=image");
            put("TSR/Giant", "https://api.scryfall.com/cards/ttsr/7/en?format=image");
            put("TSR/Goblin", "https://api.scryfall.com/cards/ttsr/8/en?format=image");
            put("TSR/Griffin", "https://api.scryfall.com/cards/ttsr/1/en?format=image");
            put("TSR/Insect", "https://api.scryfall.com/cards/ttsr/11/en?format=image");
            put("TSR/Knight", "https://api.scryfall.com/cards/ttsr/5/en?format=image");
            put("TSR/Kobolds of Kher Keep", "https://api.scryfall.com/cards/ttsr/9/en?format=image");
            put("TSR/Llanowar Elves", "https://api.scryfall.com/cards/ttsr/12/en?format=image");
            put("TSR/Metallic Sliver", "https://api.scryfall.com/cards/ttsr/15/en?format=image");
            put("TSR/Saproling", "https://api.scryfall.com/cards/ttsr/13/en?format=image");
            put("TSR/Soldier", "https://api.scryfall.com/cards/ttsr/2/en?format=image");
            put("TSR/Spider", "https://api.scryfall.com/cards/ttsr/6/en?format=image");

            // STX
            put("STX/Avatar", "https://api.scryfall.com/cards/tstx/1/en?format=image");
            put("STX/Elemental", "https://api.scryfall.com/cards/tstx/2/en?format=image");
            put("STX/Fractal", "https://api.scryfall.com/cards/tstx/3/en?format=image");
            put("STX/Inkling", "https://api.scryfall.com/cards/tstx/4/en?format=image");
            put("STX/Emblem Lukka, Wayward Bonder", "https://api.scryfall.com/cards/tstx/8/en?format=image");
            put("STX/Pest", "https://api.scryfall.com/cards/tstx/5/en?format=image");
            put("STX/Emblem Rowan, Scholar of Sparks", "https://api.scryfall.com/cards/tstx/9/en?format=image");
            put("STX/Spirit", "https://api.scryfall.com/cards/tstx/6/en?format=image");
            put("STX/Treasure", "https://api.scryfall.com/cards/tstx/7/en?format=image");

            // C21
            put("C21/Beast/1", "https://api.scryfall.com/cards/tc21/10/en?format=image"); // 3/3
            put("C21/Beast/2", "https://api.scryfall.com/cards/tc21/11/en?format=image"); // 4/4
            put("C21/Boar", "https://api.scryfall.com/cards/tc21/12/en?format=image");
            put("C21/Champion of Wits", "https://api.scryfall.com/cards/tc21/6/en?format=image");
            put("C21/Construct/1", "https://api.scryfall.com/cards/tc21/22/en?format=image"); // x/x
            put("C21/Construct/2", "https://api.scryfall.com/cards/tc21/23/en?format=image"); // 0/0
            put("C21/Demon", "https://api.scryfall.com/cards/tc21/7/en?format=image");
            put("C21/Drake", "https://api.scryfall.com/cards/tc21/2/en?format=image");
            put("C21/Eldrazi", "https://api.scryfall.com/cards/tc21/1/en?format=image");
            put("C21/Elemental", "https://api.scryfall.com/cards/tc21/20/en?format=image");
            put("C21/Elephant", "https://api.scryfall.com/cards/tc21/13/en?format=image");
            put("C21/Fish", "https://api.scryfall.com/cards/tc21/3/en?format=image");
            put("C21/Food", "https://api.scryfall.com/cards/tc21/24/en?format=image");
            put("C21/Frog Lizard", "https://api.scryfall.com/cards/tc21/14/en?format=image");
            put("C21/Fungus Beast", "https://api.scryfall.com/cards/tc21/15/en?format=image");
            put("C21/Golem/1", "https://api.scryfall.com/cards/tc21/25/en?format=image"); // fly
            put("C21/Golem/2", "https://api.scryfall.com/cards/tc21/27/en?format=image"); // vigilance
            put("C21/Golem/3", "https://api.scryfall.com/cards/tc21/26/en?format=image"); // trample
            put("C21/Horror", "https://api.scryfall.com/cards/tc21/8/en?format=image");
            put("C21/Hydra", "https://api.scryfall.com/cards/tc21/16/en?format=image");
            put("C21/Insect", "https://api.scryfall.com/cards/tc21/17/en?format=image");
            put("C21/Kraken", "https://api.scryfall.com/cards/tc21/4/en?format=image");
            put("C21/Myr", "https://api.scryfall.com/cards/tc21/28/en?format=image");
            put("C21/Saproling", "https://api.scryfall.com/cards/tc21/18/en?format=image");
            put("C21/Spirit", "https://api.scryfall.com/cards/tc21/21/en?format=image");
            put("C21/Thopter", "https://api.scryfall.com/cards/tc21/29/en?format=image");
            put("C21/Whale", "https://api.scryfall.com/cards/tc21/5/en?format=image");
            put("C21/Wurm", "https://api.scryfall.com/cards/tc21/19/en?format=image");
            put("C21/Zombie", "https://api.scryfall.com/cards/tc21/9/en?format=image");

            // MH2
            put("MH2/Beast", "https://api.scryfall.com/cards/tmh2/9/en?format=image");
            put("MH2/Bird", "https://api.scryfall.com/cards/tmh2/1/en?format=image");
            put("MH2/Clue/1", "https://api.scryfall.com/cards/tmh2/14/en?format=image");
            put("MH2/Clue/2", "https://api.scryfall.com/cards/tmh2/15/en?format=image");
            put("MH2/Construct", "https://api.scryfall.com/cards/tmh2/16/en?format=image");
            put("MH2/Crab", "https://api.scryfall.com/cards/tmh2/2/en?format=image");
            put("MH2/Elemental", "https://api.scryfall.com/cards/tmh2/10/en?format=image");
            put("MH2/Food/1", "https://api.scryfall.com/cards/tmh2/17/en?format=image");
            put("MH2/Food/2", "https://api.scryfall.com/cards/tmh2/18/en?format=image");
            put("MH2/Goblin", "https://api.scryfall.com/cards/tmh2/8/en?format=image");
            put("MH2/Golem", "https://api.scryfall.com/cards/tmh2/12/en?format=image");
            put("MH2/Insect", "https://api.scryfall.com/cards/tmh2/13/en?format=image");
            put("MH2/Phyrexian Germ", "https://api.scryfall.com/cards/tmh2/3/en?format=image");
            put("MH2/Squirrel", "https://api.scryfall.com/cards/tmh2/11/en?format=image");
            put("MH2/Thopter", "https://api.scryfall.com/cards/tmh2/19/en?format=image");
            put("MH2/Treasure/1", "https://api.scryfall.com/cards/tmh2/20/en?format=image");
            put("MH2/Treasure/2", "https://api.scryfall.com/cards/tmh2/21/en?format=image");
            put("MH2/Zombie Army", "https://api.scryfall.com/cards/tmh2/7/en?format=image");
            put("MH2/Zombie", "https://api.scryfall.com/cards/tmh2/6/en?format=image");

            // AFR
            put("AFR/Angel", "https://api.scryfall.com/cards/tafr/1/en?format=image");
            put("AFR/Boo", "https://api.scryfall.com/cards/tafr/10/en?format=image");
            put("AFR/Devil", "https://api.scryfall.com/cards/tafr/11/en?format=image");
            put("AFR/Dog Illusion", "https://api.scryfall.com/cards/tafr/3/en?format=image");
            put("AFR/Dungeon of the Mad Mage", "https://api.scryfall.com/cards/tafr/20/en?format=image");
            put("AFR/Emblem Ellywick Tumblestrum", "https://api.scryfall.com/cards/tafr/16/en?format=image");
            put("AFR/Faerie Dragon", "https://api.scryfall.com/cards/tafr/4/en?format=image");
            put("AFR/Goblin", "https://api.scryfall.com/cards/tafr/12/en?format=image");
            put("AFR/Guenhwyvar", "https://api.scryfall.com/cards/tafr/13/en?format=image");
            put("AFR/Icingdeath, Frost Tongue", "https://api.scryfall.com/cards/tafr/2/en?format=image");
            put("AFR/Emblem Lolth, Spider Queen", "https://api.scryfall.com/cards/tafr/17/en?format=image");
            put("AFR/Lost Mine of Phandelver", "https://api.scryfall.com/cards/tafr/21/en?format=image");
            put("AFR/Emblem Mordenkainen", "https://api.scryfall.com/cards/tafr/18/en?format=image");
            put("AFR/Skeleton", "https://api.scryfall.com/cards/tafr/6/en?format=image");
            put("AFR/Spider", "https://api.scryfall.com/cards/tafr/7/en?format=image");
            put("AFR/The Atropal", "https://api.scryfall.com/cards/tafr/5/en?format=image");
            put("AFR/Tomb of Annihilation", "https://api.scryfall.com/cards/tafr/22/en?format=image");
            put("AFR/Treasure", "https://api.scryfall.com/cards/tafr/15/en?format=image");
            put("AFR/Vecna", "https://api.scryfall.com/cards/tafr/8/en?format=image");
            put("AFR/Wolf", "https://api.scryfall.com/cards/tafr/14/en?format=image");
            put("AFR/Emblem Zariel, Archduke of Avernus", "https://api.scryfall.com/cards/tafr/19/en?format=image");
            put("AFR/Zombie", "https://api.scryfall.com/cards/tafr/9/en?format=image");

            // AFC
            put("AFC/Angel", "https://api.scryfall.com/cards/tafc/1/en?format=image");
            put("AFC/Beast", "https://api.scryfall.com/cards/tafc/7/en?format=image");
            put("AFC/Clue", "https://api.scryfall.com/cards/tafc/10/en?format=image");
            put("AFC/Dragon Spirit", "https://api.scryfall.com/cards/tafc/9/en?format=image");
            put("AFC/Dragon", "https://api.scryfall.com/cards/tafc/6/en?format=image");
            put("AFC/Illusion", "https://api.scryfall.com/cards/tafc/3/en?format=image");
            put("AFC/Knight", "https://api.scryfall.com/cards/tafc/2/en?format=image");
            put("AFC/Rat", "https://api.scryfall.com/cards/tafc/5/en?format=image");
            put("AFC/Saproling", "https://api.scryfall.com/cards/tafc/8/en?format=image");
            put("AFC/Servo", "https://api.scryfall.com/cards/tafc/11/en?format=image");
            put("AFC/Thopter", "https://api.scryfall.com/cards/tafc/12/en?format=image");

            // MIC
            put("MIC/Beast", "https://api.scryfall.com/cards/tmic/7/en?format=image");
            put("MIC/Centaur", "https://api.scryfall.com/cards/tmic/8/en?format=image");
            put("MIC/Eldrazi Spawn", "https://api.scryfall.com/cards/tmic/1/en?format=image");
            put("MIC/Elephant", "https://api.scryfall.com/cards/tmic/9/en?format=image");
            put("MIC/Human Soldier", "https://api.scryfall.com/cards/tmic/2/en?format=image");
            put("MIC/Knight", "https://api.scryfall.com/cards/tmic/3/en?format=image");
            put("MIC/Rhino", "https://api.scryfall.com/cards/tmic/10/en?format=image");
            put("MIC/Snake", "https://api.scryfall.com/cards/tmic/11/en?format=image");
            put("MIC/Zombie Army", "https://api.scryfall.com/cards/tmic/6/en?format=image");
            put("MIC/Zombie/1", "https://api.scryfall.com/cards/tmic/5/en?format=image"); // 2/2
            put("MIC/Zombie/2", "https://api.scryfall.com/cards/tmic/4/en?format=image"); // */*

            // MID
            put("MID/Bat", "https://api.scryfall.com/cards/tmid/4/en?format=image");
            put("MID/Beast", "https://api.scryfall.com/cards/tmid/8/en?format=image");
            put("MID/Bird", "https://api.scryfall.com/cards/tmid/3/en?format=image");
            put("MID/Clue", "https://api.scryfall.com/cards/tmid/16/en?format=image");
            put("MID/Devil", "https://api.scryfall.com/cards/tmid/6/en?format=image");
            put("MID/Elemental", "https://api.scryfall.com/cards/tmid/7/en?format=image");
            put("MID/Human", "https://api.scryfall.com/cards/tmid/1/en?format=image");
            put("MID/Insect", "https://api.scryfall.com/cards/tmid/9/en?format=image");
            put("MID/Ooze", "https://api.scryfall.com/cards/tmid/10/en?format=image");
            put("MID/Spider", "https://api.scryfall.com/cards/tmid/11/en?format=image");
            put("MID/Spirit", "https://api.scryfall.com/cards/tmid/2/en?format=image");
            put("MID/Emblem Teferi, Who Slows the Sunset", "https://api.scryfall.com/cards/tmid/17/en?format=image");
            put("MID/Treefolk", "https://api.scryfall.com/cards/tmid/12/en?format=image");
            put("MID/Vampire", "https://api.scryfall.com/cards/tmid/14/en?format=image");
            put("MID/Wolf", "https://api.scryfall.com/cards/tmid/13/en?format=image");
            put("MID/Emblem Wrenn and Seven", "https://api.scryfall.com/cards/tmid/18/en?format=image");
            put("MID/Zombie/1", "https://api.scryfall.com/cards/tmid/5/en?format=image"); // decayed
            put("MID/Zombie/2", "https://api.scryfall.com/cards/tmid/15/en?format=image"); // menace

            // VOC
            put("VOC/Angel", "https://api.scryfall.com/cards/tvoc/2/en?format=image");
            put("VOC/Bat", "https://api.scryfall.com/cards/tvoc/4/en?format=image");
            put("VOC/Clue", "https://api.scryfall.com/cards/tvoc/5/en?format=image");
            put("VOC/Spirit/1", "https://api.scryfall.com/cards/tvoc/1/en?format=image"); // 1/1
            put("VOC/Spirit/2", "https://api.scryfall.com/cards/tvoc/3/en?format=image"); // 3/3
            put("VOC/Thopter", "https://api.scryfall.com/cards/tvoc/6/en?format=image");

            // VOW
            put("VOW/Blood", "https://api.scryfall.com/cards/tvow/17/en?format=image");
            put("VOW/Boar", "https://api.scryfall.com/cards/tvow/12/en?format=image");
            put("VOW/Emblem Chandra, Dressed to Kill", "https://api.scryfall.com/cards/tvow/20/en?format=image");
            put("VOW/Dragon Illusion", "https://api.scryfall.com/cards/tvow/9/en?format=image");
            put("VOW/Human Soldier", "https://api.scryfall.com/cards/tvow/15/en?format=image");
            put("VOW/Human/1", "https://api.scryfall.com/cards/tvow/10/en?format=image"); // red
            put("VOW/Human/2", "https://api.scryfall.com/cards/tvow/1/en?format=image"); // white
            put("VOW/Insect", "https://api.scryfall.com/cards/tvow/13/en?format=image");
            put("VOW/Slug", "https://api.scryfall.com/cards/tvow/6/en?format=image");
            put("VOW/Spirit Cleric", "https://api.scryfall.com/cards/tvow/4/en?format=image");
            put("VOW/Spirit/1", "https://api.scryfall.com/cards/tvow/2/en?format=image"); // 1/1
            put("VOW/Spirit/2", "https://api.scryfall.com/cards/tvow/3/en?format=image"); // 4/4
            put("VOW/Treasure", "https://api.scryfall.com/cards/tvow/18/en?format=image");
            put("VOW/Vampire/1", "https://api.scryfall.com/cards/tvow/16/en?format=image"); // lifelink
            put("VOW/Vampire/2", "https://api.scryfall.com/cards/tvow/7/en?format=image"); // flying, lifelink
            put("VOW/Wolf/1", "https://api.scryfall.com/cards/tvow/14/en?format=image"); // green
            put("VOW/Wolf/2", "https://api.scryfall.com/cards/tvow/11/en?format=image"); // red
            put("VOW/Zombie/1", "https://api.scryfall.com/cards/tvow/8/en?format=image"); // 2/2
            put("VOW/Zombie/2", "https://api.scryfall.com/cards/tvow/5/en?format=image"); // */*

            // UMA
            put ("UMA/Citizen", "https://api.scryfall.com/cards/tuma/1/en?format=image");
            put ("UMA/Drake", "https://api.scryfall.com/cards/tuma/3/en?format=image");
            put ("UMA/Elemental/1", "https://api.scryfall.com/cards/tuma/13/en?format=image"); // green
            put ("UMA/Elemental/2", "https://api.scryfall.com/cards/tuma/9/en?format=image");
            put ("UMA/Elemental/3", "https://api.scryfall.com/cards/tuma/10/en?format=image");
            put ("UMA/Faerie Rogue", "https://api.scryfall.com/cards/tuma/5/en?format=image");
            put ("UMA/Homunculus", "https://api.scryfall.com/cards/tuma/4/en?format=image");
            put ("UMA/Marit Lage", "https://api.scryfall.com/cards/tuma/6/en?format=image");
            put ("UMA/Ooze", "https://api.scryfall.com/cards/tuma/14/en?format=image");
            put ("UMA/Soldier", "https://api.scryfall.com/cards/tuma/11/en?format=image");
            put ("UMA/Spark Elemental", "https://api.scryfall.com/cards/tuma/12/en?format=image");
            put ("UMA/Spider", "https://api.scryfall.com/cards/tuma/15/en?format=image");
            put ("UMA/Spirit/1", "https://api.scryfall.com/cards/tuma/2/en?format=image"); // white
            put ("UMA/Spirit/2", "https://api.scryfall.com/cards/tuma/16/en?format=image"); // white, black
            put ("UMA/Wurm", "https://api.scryfall.com/cards/tuma/7/en?format=image");
            put ("UMA/Zombie", "https://api.scryfall.com/cards/tuma/8/en?format=image");

            // MMA
            put ("MMA/Bat", "https://api.scryfall.com/cards/tmma/5/en?format=image");
            put ("MMA/Dragon", "https://api.scryfall.com/cards/tmma/9/en?format=image");
            put ("MMA/Elemental", "https://api.scryfall.com/cards/tmma/11/en?format=image");
            put ("MMA/Emblem Elspeth, Knight Errant", "https://api.scryfall.com/cards/tmma/16/en?format=image");
            put ("MMA/Faerie Rogue", "https://api.scryfall.com/cards/tmma/14/en?format=image");
            put ("MMA/Giant Warrior", "https://api.scryfall.com/cards/tmma/1/en?format=image");
            put ("MMA/Goblin", "https://api.scryfall.com/cards/tmma/10/en?format=image");
            put ("MMA/Goblin Rogue", "https://api.scryfall.com/cards/tmma/6/en?format=image");
            put ("MMA/Illusion", "https://api.scryfall.com/cards/tmma/4/en?format=image");
            put ("MMA/Kithkin Soldier", "https://api.scryfall.com/cards/tmma/2/en?format=image");
            put ("MMA/Saproling", "https://api.scryfall.com/cards/tmma/12/en?format=image");
            put ("MMA/Soldier", "https://api.scryfall.com/cards/tmma/3/en?format=image");
            put ("MMA/Spider", "https://api.scryfall.com/cards/tmma/7/en?format=image");
            put ("MMA/Treefolk Shaman", "https://api.scryfall.com/cards/tmma/13/en?format=image");
            put ("MMA/Worm", "https://api.scryfall.com/cards/tmma/15/en?format=image");
            put ("MMA/Zombie", "https://api.scryfall.com/cards/tmma/8/en?format=image");

            // SHM
            put ("SHM/Elemental/1", "https://api.scryfall.com/cards/tshm/9/en?format=image"); // black, red
            put ("SHM/Elemental/2", "https://api.scryfall.com/cards/tshm/4/en?format=image"); // haste
            put ("SHM/Elf Warrior/1", "https://api.scryfall.com/cards/tshm/5/en?format=image"); // green
            put ("SHM/Elf Warrior/2", "https://api.scryfall.com/cards/tshm/12/en?format=image"); // white, green
            put ("SHM/Faerie Rogue", "https://api.scryfall.com/cards/tshm/8/en?format=image");
            put ("SHM/Giant Warrior", "https://api.scryfall.com/cards/tshm/10/en?format=image");
            put ("SHM/Goblin Warrior", "https://api.scryfall.com/cards/tshm/11/en?format=image");
            put ("SHM/Kithkin Soldier", "https://api.scryfall.com/cards/tshm/1/en?format=image");
            put ("SHM/Rat", "https://api.scryfall.com/cards/tshm/3/en?format=image");
            put ("SHM/Spider", "https://api.scryfall.com/cards/tshm/6/en?format=image");
            put ("SHM/Spirit", "https://api.scryfall.com/cards/tshm/2/en?format=image");
            put ("SHM/Wolf", "https://api.scryfall.com/cards/tshm/7/en?format=image");

            // NEO
            put ("NEO/Construct/1", "https://api.scryfall.com/cards/tneo/15/en?format=image"); // 1/1
            put ("NEO/Construct/2", "https://api.scryfall.com/cards/tneo/6/en?format=image"); // haste
            put ("NEO/Dragon Spirit", "https://api.scryfall.com/cards/tneo/7/en?format=image");
            put ("NEO/Goblin Shaman", "https://api.scryfall.com/cards/tneo/8/en?format=image");
            put ("NEO/Human Monk", "https://api.scryfall.com/cards/tneo/10/en?format=image");
            put ("NEO/Emblem Kaito Shizuki", "https://api.scryfall.com/cards/tneo/18/en?format=image");
            put ("NEO/Keimi", "https://api.scryfall.com/cards/tneo/13/en?format=image");
            put ("NEO/Mechtitan", "https://api.scryfall.com/cards/tneo/14/en?format=image");
            put ("NEO/Ninja", "https://api.scryfall.com/cards/tneo/4/en?format=image");
            put ("NEO/Pilot", "https://api.scryfall.com/cards/tneo/1/en?format=image");
            put ("NEO/Rat Rogue", "https://api.scryfall.com/cards/tneo/5/en?format=image");
            put ("NEO/Samurai", "https://api.scryfall.com/cards/tneo/3/en?format=image");
            put ("NEO/Spirit/1", "https://api.scryfall.com/cards/tneo/2/en?format=image"); // colorless
            put ("NEO/Spirit/2", "https://api.scryfall.com/cards/tneo/12/en?format=image"); // */*
            put ("NEO/Spirit/3", "https://api.scryfall.com/cards/tneo/11/en?format=image"); // green
            put ("NEO/Spirit/4", "https://api.scryfall.com/cards/tneo/9/en?format=image"); // red
            put ("NEO/Tamiyo's Notebook", "https://api.scryfall.com/cards/tneo/16/en?format=image");
            put ("NEO/Emblem Tezzeret, Betrayer of Flesh", "https://api.scryfall.com/cards/tneo/19/en?format=image");
            put ("NEO/Treasure", "https://api.scryfall.com/cards/tneo/17/en?format=image");

            // NEC
            put ("NEC/Angel", "https://api.scryfall.com/cards/tnec/2/en?format=image");
            put ("NEC/Beast", "https://api.scryfall.com/cards/tnec/7/en?format=image");
            put ("NEC/Elemental", "https://api.scryfall.com/cards/tnec/4/en?format=image");
            put ("NEC/Elephant", "https://api.scryfall.com/cards/tnec/8/en?format=image");
            put ("NEC/Goblin", "https://api.scryfall.com/cards/tnec/5/en?format=image");
            put ("NEC/Myr", "https://api.scryfall.com/cards/tnec/11/en?format=image");
            put ("NEC/Phyrexian Germ", "https://api.scryfall.com/cards/tnec/3/en?format=image");
            put ("NEC/Plant", "https://api.scryfall.com/cards/tnec/9/en?format=image");
            put ("NEC/Saproling", "https://api.scryfall.com/cards/tnec/10/en?format=image");
            put ("NEC/Shrine", "https://api.scryfall.com/cards/tnec/1/en?format=image");
            put ("NEC/Smoke Blessing", "https://api.scryfall.com/cards/tnec/6/en?format=image");
            put ("NEC/Thopter", "https://api.scryfall.com/cards/tnec/12/en?format=image");

            // SLD
            put ("SLD/Clue", "https://api.scryfall.com/cards/sld/348/en?format=image");
            put ("SLD/Faerie Rogue/1", "https://api.scryfall.com/cards/sld/13/en?format=image");
            put ("SLD/Faerie Rogue/2", "https://api.scryfall.com/cards/sld/14/en?format=image");
            put ("SLD/Faerie Rogue/3", "https://api.scryfall.com/cards/sld/15/en?format=image");
            put ("SLD/Faerie Rogue/4", "https://api.scryfall.com/cards/sld/16/en?format=image");
            put ("SLD/Treasure", "https://api.scryfall.com/cards/sld/153/en?format=image");
            put ("SLD/Walker/1", "https://api.scryfall.com/cards/sld/148/en?format=image");
            put ("SLD/Walker/2", "https://api.scryfall.com/cards/sld/149/en?format=image");
            put ("SLD/Walker/3", "https://api.scryfall.com/cards/sld/150/en?format=image");
            put ("SLD/Walker/4", "https://api.scryfall.com/cards/sld/151/en?format=image");
            put ("SLD/Walker/5", "https://api.scryfall.com/cards/sld/152/en?format=image");

            // 2XM
            put ("2XM/Angel", "https://api.scryfall.com/cards/t2xm/3/en?format=image");
            put ("2XM/Ape", "https://api.scryfall.com/cards/t2xm/12/en?format=image");
            put ("2XM/Beast", "https://api.scryfall.com/cards/t2xm/13/en?format=image");
            put ("2XM/Cat", "https://api.scryfall.com/cards/t2xm/4/en?format=image");
            put ("2XM/Clue", "https://api.scryfall.com/cards/t2xm/22/en?format=image");
            put ("2XM/Demon", "https://api.scryfall.com/cards/t2xm/9/en?format=image");
            put ("2XM/Eldrazi Spawn", "https://api.scryfall.com/cards/t2xm/1/en?format=image");
            put ("2XM/Elemental", "https://api.scryfall.com/cards/t2xm/20/en?format=image");
            put ("2XM/Elephant", "https://api.scryfall.com/cards/t2xm/14/en?format=image");
            put ("2XM/Elf Warrior", "https://api.scryfall.com/cards/t2xm/21/en?format=image");
            put ("2XM/Phyrexian Germ", "https://api.scryfall.com/cards/t2xm/10/en?format=image");
            put ("2XM/Golem", "https://api.scryfall.com/cards/t2xm/23/en?format=image");
            put ("2XM/Human Soldier", "https://api.scryfall.com/cards/t2xm/5/en?format=image");
            put ("2XM/Marit Lage", "https://api.scryfall.com/cards/t2xm/11/en?format=image");
            put ("2XM/Myr", "https://api.scryfall.com/cards/t2xm/24/en?format=image");
            put ("2XM/Phyrexian Myr", "https://api.scryfall.com/cards/t2xm/7/en?format=image");
            put ("2XM/Ooze", "https://api.scryfall.com/cards/t2xm/15/en?format=image");
            put ("2XM/Plant", "https://api.scryfall.com/cards/t2xm/16/en?format=image");
            put ("2XM/Saproling", "https://api.scryfall.com/cards/t2xm/17/en?format=image");
            put ("2XM/Servo", "https://api.scryfall.com/cards/t2xm/25/en?format=image");
            put ("2XM/Shapeshifter", "https://api.scryfall.com/cards/t2xm/2/en?format=image");
            put ("2XM/Soldier", "https://api.scryfall.com/cards/t2xm/6/en?format=image");
            put ("2XM/Squirrel", "https://api.scryfall.com/cards/t2xm/18/en?format=image");
            put ("2XM/Thopter/1", "https://api.scryfall.com/cards/t2xm/26/en?format=image");
            put ("2XM/Thopter/2", "https://api.scryfall.com/cards/t2xm/8/en?format=image");
            put ("2XM/Treasure", "https://api.scryfall.com/cards/t2xm/27/en?format=image");
            put ("2XM/Tuktuk the Returned", "https://api.scryfall.com/cards/t2xm/28/en?format=image");
            put ("2XM/Wolf", "https://api.scryfall.com/cards/t2xm/19/en?format=image");
            put ("2XM/Phyrexian Wurm/1", "https://api.scryfall.com/cards/t2xm/29/en?format=image");
            put ("2XM/Phyrexian Wurm/2", "https://api.scryfall.com/cards/t2xm/30/en?format=image");

            // DTK
            put ("DTK/Djinn Monk", "https://api.scryfall.com/cards/tdtk/2/en?format=image");
            put ("DTK/Dragon", "https://api.scryfall.com/cards/tdtk/5/en?format=image");
            put ("DTK/Goblin", "https://api.scryfall.com/cards/tdtk/6/en?format=image");
            put ("DTK/Emblem Narset", "https://api.scryfall.com/cards/tdtk/8/en?format=image");
            put ("DTK/Warrior", "https://api.scryfall.com/cards/tdtk/1/en?format=image");
            put ("DTK/Zombie", "https://api.scryfall.com/cards/tdtk/3/en?format=image");
            put ("DTK/Zombie Horror", "https://api.scryfall.com/cards/tdtk/4/en?format=image");

            // SNC
            put ("SNC/Angel", "https://api.scryfall.com/cards/tsnc/2/en?format=image");
            put ("SNC/Cat", "https://api.scryfall.com/cards/tsnc/9/en?format=image");
            put ("SNC/Citizen", "https://api.scryfall.com/cards/tsnc/12/en?format=image");
            put ("SNC/Devil", "https://api.scryfall.com/cards/tsnc/8/en?format=image");
            put ("SNC/Dog", "https://api.scryfall.com/cards/tsnc/10/en?format=image");
            put ("SNC/Fish", "https://api.scryfall.com/cards/tsnc/4/en?format=image");
            put ("SNC/Ogre Warrior", "https://api.scryfall.com/cards/tsnc/6/en?format=image");
            put ("SNC/Rhino Warrior", "https://api.scryfall.com/cards/tsnc/11/en?format=image");
            put ("SNC/Rogue", "https://api.scryfall.com/cards/tsnc/7/en?format=image");
            put ("SNC/Spirit", "https://api.scryfall.com/cards/tsnc/3/en?format=image");
            put ("SNC/Treasure/1", "https://api.scryfall.com/cards/tsnc/13/en?format=image");
            put ("SNC/Treasure/2", "https://api.scryfall.com/cards/tsnc/14/en?format=image");
            put ("SNC/Treasure/3", "https://api.scryfall.com/cards/tsnc/15/en?format=image");
            put ("SNC/Treasure/4", "https://api.scryfall.com/cards/tsnc/16/en?format=image");
            put ("SNC/Treasure/5", "https://api.scryfall.com/cards/tsnc/17/en?format=image");
            put ("SNC/Wizard", "https://api.scryfall.com/cards/tsnc/5/en?format=image");

            // C14
            put ("C14/Angel", "https://api.scryfall.com/cards/tc14/1/en?format=image");
            put ("C14/Ape", "https://api.scryfall.com/cards/tc14/18/en?format=image");
            put ("C14/Beast/1", "https://api.scryfall.com/cards/tc14/19/en?format=image");
            put ("C14/Beast/2", "https://api.scryfall.com/cards/tc14/20/en?format=image");
            put ("C14/Cat", "https://api.scryfall.com/cards/tc14/2/en?format=image");
            put ("C14/Emblem Daretti", "https://api.scryfall.com/cards/tc14/36/en?format=image");
            put ("C14/Demon/1", "https://api.scryfall.com/cards/tc14/13/en?format=image");
            put ("C14/Demon/2", "https://api.scryfall.com/cards/tc14/12/en?format=image");
            put ("C14/Elemental", "https://api.scryfall.com/cards/tc14/21/en?format=image");
            put ("C14/Elephant", "https://api.scryfall.com/cards/tc14/22/en?format=image");
            put ("C14/Elf Druid", "https://api.scryfall.com/cards/tc14/23/en?format=image");
            put ("C14/Elf Warrior", "https://api.scryfall.com/cards/tc14/24/en?format=image");
            put ("C14/Fish", "https://api.scryfall.com/cards/tc14/8/en?format=image");
            put ("C14/Gargoyle", "https://api.scryfall.com/cards/tc14/27/en?format=image");
            put ("C14/Phyrexian Germ", "https://api.scryfall.com/cards/tc14/14/en?format=image");
            put ("C14/Goat", "https://api.scryfall.com/cards/tc14/3/en?format=image");
            put ("C14/Goblin", "https://api.scryfall.com/cards/tc14/17/en?format=image");
            put ("C14/Horror", "https://api.scryfall.com/cards/tc14/15/en?format=image");
            put ("C14/Kor Soldier", "https://api.scryfall.com/cards/tc14/4/en?format=image");
            put ("C14/Kraken", "https://api.scryfall.com/cards/tc14/9/en?format=image");
            put ("C14/Myr", "https://api.scryfall.com/cards/tc14/28/en?format=image");
            put ("C14/Emblem Nixilis", "https://api.scryfall.com/cards/tc14/35/en?format=image");
            put ("C14/Pegasus", "https://api.scryfall.com/cards/tc14/5/en?format=image");
            put ("C14/Pentavite", "https://api.scryfall.com/cards/tc14/29/en?format=image");
            put ("C14/Soldier", "https://api.scryfall.com/cards/tc14/6/en?format=image");
            put ("C14/Spirit", "https://api.scryfall.com/cards/tc14/7/en?format=image");
            put ("C14/Stoneforged Blade", "https://api.scryfall.com/cards/tc14/30/en?format=image");
            put ("C14/Emblem Teferi", "https://api.scryfall.com/cards/tc14/34/en?format=image");
            put ("C14/Treefolk", "https://api.scryfall.com/cards/tc14/25/en?format=image");
            put ("C14/Tuktuk the Returned", "https://api.scryfall.com/cards/tc14/31/en?format=image");
            put ("C14/Whale", "https://api.scryfall.com/cards/tc14/10/en?format=image");
            put ("C14/Wolf", "https://api.scryfall.com/cards/tc14/26/en?format=image");
            put ("C14/Phyrexian Wurm/1", "https://api.scryfall.com/cards/tc14/32/en?format=image");
            put ("C14/Phyrexian Wurm/2", "https://api.scryfall.com/cards/tc14/33/en?format=image");
            put ("C14/Zombie/1", "https://api.scryfall.com/cards/tc14/16/en?format=image");
            put ("C14/Zombie/2", "https://api.scryfall.com/cards/tc14/11/en?format=image");

            // C15
            put ("C15/Angel", "https://api.scryfall.com/cards/tc15/2/en?format=image");
            put ("C15/Bear", "https://api.scryfall.com/cards/tc15/12/en?format=image");
            put ("C15/Beast", "https://api.scryfall.com/cards/tc15/13/en?format=image");
            put ("C15/Cat", "https://api.scryfall.com/cards/tc15/3/en?format=image");
            put ("C15/Dragon", "https://api.scryfall.com/cards/tc15/9/en?format=image");
            put ("C15/Drake", "https://api.scryfall.com/cards/tc15/6/en?format=image");
            put ("C15/Elemental", "https://api.scryfall.com/cards/tc15/20/en?format=image");
            put ("C15/Elemental Shaman", "https://api.scryfall.com/cards/tc15/10/en?format=image");
            put ("C15/Elephant", "https://api.scryfall.com/cards/tc15/14/en?format=image");
            put ("C15/Frog Lizard", "https://api.scryfall.com/cards/tc15/15/en?format=image");
            put ("C15/Phyrexian Germ", "https://api.scryfall.com/cards/tc15/7/en?format=image");
            put ("C15/Gold", "https://api.scryfall.com/cards/tc15/24/en?format=image");
            put ("C15/Knight/1", "https://api.scryfall.com/cards/tc15/4/en?format=image");
            put ("C15/Knight/2", "https://api.scryfall.com/cards/tc15/5/en?format=image");
            put ("C15/Lightning Rager", "https://api.scryfall.com/cards/tc15/11/en?format=image");
            put ("C15/Saproling", "https://api.scryfall.com/cards/tc15/16/en?format=image");
            put ("C15/Shapeshifter", "https://api.scryfall.com/cards/tc15/1/en?format=image");
            put ("C15/Snake/1", "https://api.scryfall.com/cards/tc15/17/en?format=image");
            put ("C15/Snake/2", "https://api.scryfall.com/cards/tc15/21/en?format=image");
            put ("C15/Spider", "https://api.scryfall.com/cards/tc15/18/en?format=image");
            put ("C15/Spirit/1", "https://api.scryfall.com/cards/tc15/23/en?format=image");
            put ("C15/Spirit/2", "https://api.scryfall.com/cards/tc15/22/en?format=image");
            put ("C15/Wolf", "https://api.scryfall.com/cards/tc15/19/en?format=image");
            put ("C15/Zombie", "https://api.scryfall.com/cards/tc15/8/en?format=image");

            // C16
            put ("C16/Beast", "https://api.scryfall.com/cards/tc16/14/en?format=image");
            put ("C16/Bird/1", "https://api.scryfall.com/cards/tc16/7/en?format=image");
            put ("C16/Bird/2", "https://api.scryfall.com/cards/tc16/2/en?format=image");
            put ("C16/Emblem Daretti", "https://api.scryfall.com/cards/tc16/21/en?format=image");
            put ("C16/Elemental", "https://api.scryfall.com/cards/tc16/3/en?format=image");
            put ("C16/Elf Warrior", "https://api.scryfall.com/cards/tc16/15/en?format=image");
            put ("C16/Phyrexian Germ", "https://api.scryfall.com/cards/tc16/10/en?format=image");
            put ("C16/Goat", "https://api.scryfall.com/cards/tc16/4/en?format=image");
            put ("C16/Goblin", "https://api.scryfall.com/cards/tc16/12/en?format=image");
            put ("C16/Phyrexian Horror", "https://api.scryfall.com/cards/tc16/19/en?format=image");
            put ("C16/Myr", "https://api.scryfall.com/cards/tc16/20/en?format=image");
            put ("C16/Ogre", "https://api.scryfall.com/cards/tc16/13/en?format=image");
            put ("C16/Saproling/1", "https://api.scryfall.com/cards/tc16/16/en?format=image");
            put ("C16/Saproling/2", "https://api.scryfall.com/cards/tc16/17/en?format=image");
            put ("C16/Soldier", "https://api.scryfall.com/cards/tc16/5/en?format=image");
            put ("C16/Spirit/1", "https://api.scryfall.com/cards/tc16/1/en?format=image");
            put ("C16/Spirit/2", "https://api.scryfall.com/cards/tc16/6/en?format=image");
            put ("C16/Squid", "https://api.scryfall.com/cards/tc16/8/en?format=image");
            put ("C16/Thopter", "https://api.scryfall.com/cards/tc16/9/en?format=image");
            put ("C16/Worm", "https://api.scryfall.com/cards/tc16/18/en?format=image");
            put ("C16/Zombie", "https://api.scryfall.com/cards/tc16/11/en?format=image");

            // C17
            put ("C17/Bat", "https://api.scryfall.com/cards/tc17/2/en?format=image");
            put ("C17/Cat", "https://api.scryfall.com/cards/tc17/1/en?format=image");
            put ("C17/Cat Dragon", "https://api.scryfall.com/cards/tc17/9/en?format=image");
            put ("C17/Cat Warrior", "https://api.scryfall.com/cards/tc17/8/en?format=image");
            put ("C17/Dragon/1", "https://api.scryfall.com/cards/tc17/6/en?format=image");
            put ("C17/Dragon/2", "https://api.scryfall.com/cards/tc17/7/en?format=image");
            put ("C17/Eldrazi Spawn", "https://api.scryfall.com/cards/tc17/11/en?format=image");
            put ("C17/Gold", "https://api.scryfall.com/cards/tc17/10/en?format=image");
            put ("C17/Rat", "https://api.scryfall.com/cards/tc17/3/en?format=image");
            put ("C17/Vampire", "https://api.scryfall.com/cards/tc17/4/en?format=image");
            put ("C17/Zombie", "https://api.scryfall.com/cards/tc17/5/en?format=image");

            // NCC
            put ("NCC/Beast", "https://api.scryfall.com/cards/tncc/21/en?format=image");
            put ("NCC/Cat Beast", "https://api.scryfall.com/cards/tncc/4/en?format=image");
            put ("NCC/Clue", "https://api.scryfall.com/cards/tncc/34/en?format=image");
            put ("NCC/Demon", "https://api.scryfall.com/cards/tncc/15/en?format=image");
            put ("NCC/Devil", "https://api.scryfall.com/cards/tncc/17/en?format=image");
            put ("NCC/Drake", "https://api.scryfall.com/cards/tncc/10/en?format=image");
            put ("NCC/Eldrazi", "https://api.scryfall.com/cards/tncc/1/en?format=image");
            put ("NCC/Eldrazi Spawn", "https://api.scryfall.com/cards/tncc/2/en?format=image");
            put ("NCC/Elemental/1", "https://api.scryfall.com/cards/tncc/18/en?format=image");
            put ("NCC/Elemental/2", "https://api.scryfall.com/cards/tncc/32/en?format=image");
            put ("NCC/Elemental/3", "https://api.scryfall.com/cards/tncc/5/en?format=image");
            put ("NCC/Elephant", "https://api.scryfall.com/cards/tncc/22/en?format=image");
            put ("NCC/Elf Warrior", "https://api.scryfall.com/cards/tncc/23/en?format=image");
            put ("NCC/Faerie", "https://api.scryfall.com/cards/tncc/11/en?format=image");
            put ("NCC/Food", "https://api.scryfall.com/cards/tncc/35/en?format=image");
            put ("NCC/Goat", "https://api.scryfall.com/cards/tncc/6/en?format=image");
            put ("NCC/Human", "https://api.scryfall.com/cards/tncc/7/en?format=image");
            put ("NCC/Human Soldier", "https://api.scryfall.com/cards/tncc/8/en?format=image");
            put ("NCC/Insect", "https://api.scryfall.com/cards/tncc/24/en?format=image");
            put ("NCC/Lightning Rager", "https://api.scryfall.com/cards/tncc/19/en?format=image");
            put ("NCC/Ogre", "https://api.scryfall.com/cards/tncc/20/en?format=image");
            put ("NCC/Ooze/1", "https://api.scryfall.com/cards/tncc/25/en?format=image");
            put ("NCC/Ooze/2", "https://api.scryfall.com/cards/tncc/26/en?format=image");
            put ("NCC/Plant", "https://api.scryfall.com/cards/tncc/27/en?format=image");
            put ("NCC/Saproling", "https://api.scryfall.com/cards/tncc/28/en?format=image");
            put ("NCC/Soldier/1", "https://api.scryfall.com/cards/tncc/9/en?format=image");
            put ("NCC/Soldier/2", "https://api.scryfall.com/cards/tncc/33/en?format=image");
            put ("NCC/Spider", "https://api.scryfall.com/cards/tncc/29/en?format=image");
            put ("NCC/Squid", "https://api.scryfall.com/cards/tncc/12/en?format=image");
            put ("NCC/Tentacle", "https://api.scryfall.com/cards/tncc/13/en?format=image");
            put ("NCC/Thopter", "https://api.scryfall.com/cards/tncc/36/en?format=image");
            put ("NCC/Treefolk", "https://api.scryfall.com/cards/tncc/30/en?format=image");
            put ("NCC/Wurm", "https://api.scryfall.com/cards/tncc/31/en?format=image");
            put ("NCC/Zombie", "https://api.scryfall.com/cards/tncc/16/en?format=image");

            // MED
            put ("MED/Beast", "https://api.scryfall.com/cards/tmed/W1/en?format=image");
            put ("MED/Construct/1", "https://api.scryfall.com/cards/tmed/R1/en?format=image");
            put ("MED/Construct/2", "https://api.scryfall.com/cards/tmed/G3/en?format=image");
            put ("MED/Emblem Dack", "https://api.scryfall.com/cards/tmed/R2/en?format=image");
            put ("MED/Emblem Domri", "https://api.scryfall.com/cards/tmed/R3/en?format=image");
            put ("MED/Dragon", "https://api.scryfall.com/cards/tmed/W2/en?format=image");
            put ("MED/Emblem Elspeth", "https://api.scryfall.com/cards/tmed/G4/en?format=image");
            put ("MED/Emblem Garruk", "https://api.scryfall.com/cards/tmed/W3/en?format=image");
            put ("MED/Emblem Jaya Ballard", "https://api.scryfall.com/cards/tmed/R4/en?format=image");
            put ("MED/Emblem Liliana", "https://api.scryfall.com/cards/tmed/G5/en?format=image");
            put ("MED/Emblem Ral", "https://api.scryfall.com/cards/tmed/G6/en?format=image");
            put ("MED/Soldier", "https://api.scryfall.com/cards/tmed/G1/en?format=image");
            put ("MED/Emblem Tamiyo", "https://api.scryfall.com/cards/tmed/R5/en?format=image");
            put ("MED/Emblem Teferi", "https://api.scryfall.com/cards/tmed/G7/en?format=image");
            put ("MED/Emblem Vraska", "https://api.scryfall.com/cards/tmed/G8/en?format=image");
            put ("MED/Zombie", "https://api.scryfall.com/cards/tmed/G2/en?format=image");

            // BBD
            put ("BBD/Beast", "https://api.scryfall.com/cards/tbbd/5/en?format=image");
            put ("BBD/Myr", "https://api.scryfall.com/cards/tbbd/6/en?format=image");
            put ("BBD/Emblem Rowan Kenrith", "https://api.scryfall.com/cards/tbbd/8/en?format=image");
            put ("BBD/Spirit", "https://api.scryfall.com/cards/tbbd/1/en?format=image");
            put ("BBD/Warrior", "https://api.scryfall.com/cards/tbbd/2/en?format=image");
            put ("BBD/Emblem Will Kenrith", "https://api.scryfall.com/cards/tbbd/7/en?format=image");
            put ("BBD/Zombie", "https://api.scryfall.com/cards/tbbd/3/en?format=image");
            put ("BBD/Zombie Giant", "https://api.scryfall.com/cards/tbbd/4/en?format=image");

            // DDU
            put ("DDU/Elf Warrior", "https://api.scryfall.com/cards/tddu/1/en?format=image");
            put ("DDU/Myr", "https://api.scryfall.com/cards/tddu/2/en?format=image");
            put ("DDU/Thopter/1", "https://api.scryfall.com/cards/tddu/3/en?format=image");
            put ("DDU/Thopter/2", "https://api.scryfall.com/cards/tddu/4/en?format=image");

            // IMA
            put ("IMA/Angel", "https://api.scryfall.com/cards/tima/1/en?format=image");
            put ("IMA/Beast", "https://api.scryfall.com/cards/tima/7/en?format=image");
            put ("IMA/Bird", "https://api.scryfall.com/cards/tima/2/en?format=image");
            put ("IMA/Djinn Monk", "https://api.scryfall.com/cards/tima/4/en?format=image");
            put ("IMA/Dragon/1", "https://api.scryfall.com/cards/tima/5/en?format=image");
            put ("IMA/Dragon/2", "https://api.scryfall.com/cards/tima/6/en?format=image");
            put ("IMA/Spirit", "https://api.scryfall.com/cards/tima/3/en?format=image");

            // CM2
            put ("CM2/Bird", "https://api.scryfall.com/cards/tcm2/2/en?format=image");
            put ("CM2/Emblem Daretti", "https://api.scryfall.com/cards/tcm2/18/en?format=image");
            put ("CM2/Elemental Shaman", "https://api.scryfall.com/cards/tcm2/8/en?format=image");
            put ("CM2/Phyrexian Germ", "https://api.scryfall.com/cards/tcm2/6/en?format=image");
            put ("CM2/Goat", "https://api.scryfall.com/cards/tcm2/3/en?format=image");
            put ("CM2/Goblin", "https://api.scryfall.com/cards/tcm2/9/en?format=image");
            put ("CM2/Knight", "https://api.scryfall.com/cards/tcm2/4/en?format=image");
            put ("CM2/Lightning Rager", "https://api.scryfall.com/cards/tcm2/10/en?format=image");
            put ("CM2/Myr", "https://api.scryfall.com/cards/tcm2/12/en?format=image");
            put ("CM2/Pentavite", "https://api.scryfall.com/cards/tcm2/13/en?format=image");
            put ("CM2/Saproling", "https://api.scryfall.com/cards/tcm2/11/en?format=image");
            put ("CM2/Shapeshifter", "https://api.scryfall.com/cards/tcm2/1/en?format=image");
            put ("CM2/Spirit", "https://api.scryfall.com/cards/tcm2/5/en?format=image");
            put ("CM2/Triskelavite", "https://api.scryfall.com/cards/tcm2/14/en?format=image");
            put ("CM2/Tuktuk the Returned", "https://api.scryfall.com/cards/tcm2/15/en?format=image");
            put ("CM2/Phyrexian Wurm/1", "https://api.scryfall.com/cards/tcm2/16/en?format=image");
            put ("CM2/Phyrexian Wurm/2", "https://api.scryfall.com/cards/tcm2/17/en?format=image");
            put ("CM2/Zombie", "https://api.scryfall.com/cards/tcm2/7/en?format=image");

            // CMA
            put ("CMA/Beast/1", "https://api.scryfall.com/cards/tcma/7/en?format=image");
            put ("CMA/Beast/2", "https://api.scryfall.com/cards/tcma/8/en?format=image");
            put ("CMA/Dragon", "https://api.scryfall.com/cards/tcma/6/en?format=image");
            put ("CMA/Drake", "https://api.scryfall.com/cards/tcma/18/en?format=image");
            put ("CMA/Elemental", "https://api.scryfall.com/cards/tcma/9/en?format=image");
            put ("CMA/Elephant", "https://api.scryfall.com/cards/tcma/10/en?format=image");
            put ("CMA/Elf Druid", "https://api.scryfall.com/cards/tcma/11/en?format=image");
            put ("CMA/Elf Warrior", "https://api.scryfall.com/cards/tcma/12/en?format=image");
            put ("CMA/Gargoyle", "https://api.scryfall.com/cards/tcma/19/en?format=image");
            put ("CMA/Phyrexian Germ", "https://api.scryfall.com/cards/tcma/4/en?format=image");
            put ("CMA/Kithkin Soldier", "https://api.scryfall.com/cards/tcma/1/en?format=image");
            put ("CMA/Knight", "https://api.scryfall.com/cards/tcma/2/en?format=image");
            put ("CMA/Saproling", "https://api.scryfall.com/cards/tcma/13/en?format=image");
            put ("CMA/Spider", "https://api.scryfall.com/cards/tcma/14/en?format=image");
            put ("CMA/Spirit", "https://api.scryfall.com/cards/tcma/3/en?format=image");
            put ("CMA/Treefolk", "https://api.scryfall.com/cards/tcma/15/en?format=image");
            put ("CMA/Wolf/1", "https://api.scryfall.com/cards/tcma/16/en?format=image");
            put ("CMA/Wolf/2", "https://api.scryfall.com/cards/tcma/17/en?format=image");
            put ("CMA/Zombie", "https://api.scryfall.com/cards/tcma/5/en?format=image");

            // M15
            put ("M15/Emblem Ajani", "https://api.scryfall.com/cards/tm15/13/en?format=image");
            put ("M15/Beast/1", "https://api.scryfall.com/cards/tm15/5/en?format=image");
            put ("M15/Beast/2", "https://api.scryfall.com/cards/tm15/9/en?format=image");
            put ("M15/Dragon", "https://api.scryfall.com/cards/tm15/7/en?format=image");
            put ("M15/Emblem Garruk", "https://api.scryfall.com/cards/tm15/14/en?format=image");
            put ("M15/Goblin", "https://api.scryfall.com/cards/tm15/8/en?format=image");
            put ("M15/Insect", "https://api.scryfall.com/cards/tm15/10/en?format=image");
            put ("M15/Land Mine", "https://api.scryfall.com/cards/tm15/12/en?format=image");
            put ("M15/Sliver", "https://api.scryfall.com/cards/tm15/1/en?format=image");
            put ("M15/Soldier", "https://api.scryfall.com/cards/tm15/2/en?format=image");
            put ("M15/Spirit", "https://api.scryfall.com/cards/tm15/3/en?format=image");
            put ("M15/Squid", "https://api.scryfall.com/cards/tm15/4/en?format=image");
            put ("M15/Treefolk Warrior", "https://api.scryfall.com/cards/tm15/11/en?format=image");
            put ("M15/Zombie", "https://api.scryfall.com/cards/tm15/6/en?format=image");

            // M14
            put ("M14/Angel", "https://api.scryfall.com/cards/tm14/2/en?format=image");
            put ("M14/Beast", "https://api.scryfall.com/cards/tm14/9/en?format=image");
            put ("M14/Cat", "https://api.scryfall.com/cards/tm14/3/en?format=image");
            put ("M14/Dragon", "https://api.scryfall.com/cards/tm14/6/en?format=image");
            put ("M14/Elemental/1", "https://api.scryfall.com/cards/tm14/7/en?format=image");
            put ("M14/Elemental/2", "https://api.scryfall.com/cards/tm14/8/en?format=image");
            put ("M14/Emblem Garruk, Caller of Beasts", "https://api.scryfall.com/cards/tm14/13/en?format=image");
            put ("M14/Goat", "https://api.scryfall.com/cards/tm14/4/en?format=image");
            put ("M14/Emblem Liliana of the Dark Realms", "https://api.scryfall.com/cards/tm14/12/en?format=image");
            put ("M14/Saproling", "https://api.scryfall.com/cards/tm14/10/en?format=image");
            put ("M14/Sliver", "https://api.scryfall.com/cards/tm14/1/en?format=image");
            put ("M14/Wolf", "https://api.scryfall.com/cards/tm14/11/en?format=image");
            put ("M14/Zombie", "https://api.scryfall.com/cards/tm14/5/en?format=image");

            // M13
            put ("M13/Beast", "https://api.scryfall.com/cards/tm13/8/en?format=image");
            put ("M13/Cat", "https://api.scryfall.com/cards/tm13/1/en?format=image");
            put ("M13/Drake", "https://api.scryfall.com/cards/tm13/4/en?format=image");
            put ("M13/Goat", "https://api.scryfall.com/cards/tm13/2/en?format=image");
            put ("M13/Goblin", "https://api.scryfall.com/cards/tm13/6/en?format=image");
            put ("M13/Hellion", "https://api.scryfall.com/cards/tm13/7/en?format=image");
            put ("M13/Emblem Liliana of the Dark Realms", "https://api.scryfall.com/cards/tm13/11/en?format=image");
            put ("M13/Saproling", "https://api.scryfall.com/cards/tm13/9/en?format=image");
            put ("M13/Soldier", "https://api.scryfall.com/cards/tm13/3/en?format=image");
            put ("M13/Wurm", "https://api.scryfall.com/cards/tm13/10/en?format=image");
            put ("M13/Zombie", "https://api.scryfall.com/cards/tm13/5/en?format=image");

            // M12
            put ("M12/Beast", "https://api.scryfall.com/cards/tm12/4/en?format=image");
            put ("M12/Bird", "https://api.scryfall.com/cards/tm12/1/en?format=image");
            put ("M12/Pentavite", "https://api.scryfall.com/cards/tm12/7/en?format=image");
            put ("M12/Saproling", "https://api.scryfall.com/cards/tm12/5/en?format=image");
            put ("M12/Soldier", "https://api.scryfall.com/cards/tm12/2/en?format=image");
            put ("M12/Wurm", "https://api.scryfall.com/cards/tm12/6/en?format=image");
            put ("M12/Zombie", "https://api.scryfall.com/cards/tm12/3/en?format=image");

            // M11
            put ("M11/Avatar", "https://api.scryfall.com/cards/tm11/1/en?format=image");
            put ("M11/Beast", "https://api.scryfall.com/cards/tm11/4/en?format=image");
            put ("M11/Bird", "https://api.scryfall.com/cards/tm11/2/en?format=image");
            put ("M11/Ooze/1", "https://api.scryfall.com/cards/tm11/6/en?format=image");
            put ("M11/Ooze/2", "https://api.scryfall.com/cards/tm11/5/en?format=image");
            put ("M11/Zombie", "https://api.scryfall.com/cards/tm11/3/en?format=image");

            // M10
            put ("M10/Avatar", "https://api.scryfall.com/cards/tm10/1/en?format=image");
            put ("M10/Beast", "https://api.scryfall.com/cards/tm10/5/en?format=image");
            put ("M10/Gargoyle", "https://api.scryfall.com/cards/tm10/8/en?format=image");
            put ("M10/Goblin", "https://api.scryfall.com/cards/tm10/4/en?format=image");
            put ("M10/Insect", "https://api.scryfall.com/cards/tm10/6/en?format=image");
            put ("M10/Soldier", "https://api.scryfall.com/cards/tm10/2/en?format=image");
            put ("M10/Wolf", "https://api.scryfall.com/cards/tm10/7/en?format=image");
            put ("M10/Zombie", "https://api.scryfall.com/cards/tm10/3/en?format=image");

            // ARB
            put ("ARB/Bird Soldier", "https://api.scryfall.com/cards/tarb/1/en?format=image");
            put ("ARB/Dragon", "https://api.scryfall.com/cards/tarb/3/en?format=image");
            put ("ARB/Lizard", "https://api.scryfall.com/cards/tarb/2/en?format=image");
            put ("ARB/Zombie Wizard", "https://api.scryfall.com/cards/tarb/4/en?format=image");

            // DDC
            put ("DDC/Demon", "https://api.scryfall.com/cards/tddc/2/en?format=image");
            put ("DDC/Spirit", "https://api.scryfall.com/cards/tddc/1/en?format=image");
            put ("DDC/Thrull", "https://api.scryfall.com/cards/tddc/3/en?format=image");

            // CON
            put ("CON/Angel", "https://api.scryfall.com/cards/tcon/1/en?format=image");
            put ("CON/Elemental", "https://api.scryfall.com/cards/tcon/2/en?format=image");

            // ALA
            put ("ALA/Beast", "https://api.scryfall.com/cards/tala/10/en?format=image");
            put ("ALA/Dragon", "https://api.scryfall.com/cards/tala/6/en?format=image");
            put ("ALA/Goblin", "https://api.scryfall.com/cards/tala/7/en?format=image");
            put ("ALA/Homunculus", "https://api.scryfall.com/cards/tala/2/en?format=image");
            put ("ALA/Ooze", "https://api.scryfall.com/cards/tala/8/en?format=image");
            put ("ALA/Saproling", "https://api.scryfall.com/cards/tala/9/en?format=image");
            put ("ALA/Skeleton", "https://api.scryfall.com/cards/tala/4/en?format=image");
            put ("ALA/Soldier", "https://api.scryfall.com/cards/tala/1/en?format=image");
            put ("ALA/Thopter", "https://api.scryfall.com/cards/tala/3/en?format=image");
            put ("ALA/Zombie", "https://api.scryfall.com/cards/tala/5/en?format=image");

            // MOR
            put ("MOR/Faerie Rogue", "https://api.scryfall.com/cards/tmor/2/en?format=image");
            put ("MOR/Giant Warrior", "https://api.scryfall.com/cards/tmor/1/en?format=image");
            put ("MOR/Treefolk Shaman", "https://api.scryfall.com/cards/tmor/3/en?format=image");

            // DD1
            put ("DD1/Elemental", "https://api.scryfall.com/cards/tdd1/T1/en?format=image");
            put ("DD1/Elf Warrior", "https://api.scryfall.com/cards/tdd1/T2/en?format=image");
            put ("DD1/Goblin", "https://api.scryfall.com/cards/tdd1/T3/en?format=image");

            // 10E
            put ("10E/Dragon", "https://api.scryfall.com/cards/t10e/3/en?format=image");
            put ("10E/Goblin", "https://api.scryfall.com/cards/t10e/4/en?format=image");
            put ("10E/Saproling", "https://api.scryfall.com/cards/t10e/5/en?format=image");
            put ("10E/Soldier", "https://api.scryfall.com/cards/t10e/1/en?format=image");
            put ("10E/Wasp", "https://api.scryfall.com/cards/t10e/6/en?format=image");
            put ("10E/Zombie", "https://api.scryfall.com/cards/t10e/2/en?format=image");

            // ZEN
            put ("ZEN/Angel", "https://api.scryfall.com/cards/tzen/1/en?format=image");
            put ("ZEN/Beast", "https://api.scryfall.com/cards/tzen/9/en?format=image");
            put ("ZEN/Bird", "https://api.scryfall.com/cards/tzen/2/en?format=image");
            put ("ZEN/Elemental", "https://api.scryfall.com/cards/tzen/8/en?format=image");
            put ("ZEN/Illusion", "https://api.scryfall.com/cards/tzen/4/en?format=image");
            put ("ZEN/Kor Soldier", "https://api.scryfall.com/cards/tzen/3/en?format=image");
            put ("ZEN/Merfolk", "https://api.scryfall.com/cards/tzen/5/en?format=image");
            put ("ZEN/Snake", "https://api.scryfall.com/cards/tzen/10/en?format=image");
            put ("ZEN/Vampire", "https://api.scryfall.com/cards/tzen/6/en?format=image");
            put ("ZEN/Wolf", "https://api.scryfall.com/cards/tzen/11/en?format=image");
            put ("ZEN/Zombie Giant", "https://api.scryfall.com/cards/tzen/7/en?format=image");

            // WWK
            put ("WWK/Construct", "https://api.scryfall.com/cards/twwk/6/en?format=image");
            put ("WWK/Dragon", "https://api.scryfall.com/cards/twwk/2/en?format=image");
            put ("WWK/Elephant", "https://api.scryfall.com/cards/twwk/4/en?format=image");
            put ("WWK/Ogre", "https://api.scryfall.com/cards/twwk/3/en?format=image");
            put ("WWK/Plant", "https://api.scryfall.com/cards/twwk/5/en?format=image");
            put ("WWK/Soldier Ally", "https://api.scryfall.com/cards/twwk/1/en?format=image");

            // ROE
            put ("ROE/Eldrazi Spawn/1", "https://api.scryfall.com/cards/troe/1a/en?format=image");
            put ("ROE/Eldrazi Spawn/2", "https://api.scryfall.com/cards/troe/1b/en?format=image");
            put ("ROE/Eldrazi Spawn/3", "https://api.scryfall.com/cards/troe/1c/en?format=image");
            put ("ROE/Elemental", "https://api.scryfall.com/cards/troe/2/en?format=image");
            put ("ROE/Hellion", "https://api.scryfall.com/cards/troe/3/en?format=image");
            put ("ROE/Ooze", "https://api.scryfall.com/cards/troe/4/en?format=image");
            put ("ROE/Tuktuk the Returned", "https://api.scryfall.com/cards/troe/5/en?format=image");

            // DDF
            put ("DDF/Soldier", "https://api.scryfall.com/cards/tddf/1/en?format=image");

            // DDE
            put ("DDE/Hornet", "https://api.scryfall.com/cards/tdde/1/en?format=image");
            put ("DDE/Phyrexian Minion", "https://api.scryfall.com/cards/tdde/2/en?format=image");
            put ("DDE/Saproling", "https://api.scryfall.com/cards/tdde/3/en?format=image");

            // DDD
            put ("DDD/Beast/1", "https://api.scryfall.com/cards/tddd/1/en?format=image");
            put ("DDD/Beast/2", "https://api.scryfall.com/cards/tddd/2/en?format=image");
            put ("DDD/Elephant", "https://api.scryfall.com/cards/tddd/3/en?format=image");

            // SOM
            put ("SOM/Cat", "https://api.scryfall.com/cards/tsom/1/en?format=image");
            put ("SOM/Goblin", "https://api.scryfall.com/cards/tsom/3/en?format=image");
            put ("SOM/Golem", "https://api.scryfall.com/cards/tsom/6/en?format=image");
            put ("SOM/Phyrexian Insect", "https://api.scryfall.com/cards/tsom/4/en?format=image");
            put ("SOM/Myr", "https://api.scryfall.com/cards/tsom/7/en?format=image");
            put ("SOM/Soldier", "https://api.scryfall.com/cards/tsom/2/en?format=image");
            put ("SOM/Wolf", "https://api.scryfall.com/cards/tsom/5/en?format=image");
            put ("SOM/Phyrexian Wurm/1", "https://api.scryfall.com/cards/tsom/8/en?format=image");
            put ("SOM/Phyrexian Wurm/2", "https://api.scryfall.com/cards/tsom/9/en?format=image");

            // MBS
            put ("MBS/Phyrexian Germ", "https://api.scryfall.com/cards/tmbs/1/en?format=image");
            put ("MBS/Golem", "https://api.scryfall.com/cards/tmbs/3/en?format=image");
            put ("MBS/Phyrexian Horror", "https://api.scryfall.com/cards/tmbs/4/en?format=image");
            put ("MBS/Thopter", "https://api.scryfall.com/cards/tmbs/5/en?format=image");
            put ("MBS/Zombie", "https://api.scryfall.com/cards/tmbs/2/en?format=image");

            // DDG
            put ("DDG/Goblin", "https://api.scryfall.com/cards/tddg/1/en?format=image");

            // NPH
            put ("NPH/Beast", "https://api.scryfall.com/cards/tnph/1/en?format=image");
            put ("NPH/Phyrexian Goblin", "https://api.scryfall.com/cards/tnph/2/en?format=image");
            put ("NPH/Phyrexian Golem", "https://api.scryfall.com/cards/tnph/3/en?format=image");
            put ("NPH/Phyrexian Myr", "https://api.scryfall.com/cards/tnph/4/en?format=image");

            // DDH
            put ("DDH/Griffin", "https://api.scryfall.com/cards/tddh/1/en?format=image");
            put ("DDH/Saproling", "https://api.scryfall.com/cards/tddh/2/en?format=image");

            // ISD
            put ("ISD/Angel", "https://api.scryfall.com/cards/tisd/1/en?format=image");
            put ("ISD/Demon", "https://api.scryfall.com/cards/tisd/4/en?format=image");
            put ("ISD/Homunculus", "https://api.scryfall.com/cards/tisd/3/en?format=image");
            put ("ISD/Ooze", "https://api.scryfall.com/cards/tisd/10/en?format=image");
            put ("ISD/Spider", "https://api.scryfall.com/cards/tisd/11/en?format=image");
            put ("ISD/Spirit", "https://api.scryfall.com/cards/tisd/2/en?format=image");
            put ("ISD/Vampire", "https://api.scryfall.com/cards/tisd/5/en?format=image");
            put ("ISD/Wolf/1", "https://api.scryfall.com/cards/tisd/6/en?format=image");
            put ("ISD/Wolf/2", "https://api.scryfall.com/cards/tisd/12/en?format=image");
            put ("ISD/Zombie/1", "https://api.scryfall.com/cards/tisd/7/en?format=image");
            put ("ISD/Zombie/2", "https://api.scryfall.com/cards/tisd/8/en?format=image");
            put ("ISD/Zombie/3", "https://api.scryfall.com/cards/tisd/9/en?format=image");

            // DKA
            put ("DKA/Human", "https://api.scryfall.com/cards/tdka/1/en?format=image");
            put ("DKA/Emblem Sorin, Lord of Innistrad", "https://api.scryfall.com/cards/tdka/3/en?format=image");
            put ("DKA/Vampire", "https://api.scryfall.com/cards/tdka/2/en?format=image");

            // DDI
            put ("DDI/Emblem Koth of the Hammer", "https://api.scryfall.com/cards/tddi/2/en?format=image");
            put ("DDI/Emblem Venser, the Sojourner", "https://api.scryfall.com/cards/tddi/1/en?format=image");

            // AVR
            put ("AVR/Angel", "https://api.scryfall.com/cards/tavr/1/en?format=image");
            put ("AVR/Demon", "https://api.scryfall.com/cards/tavr/5/en?format=image");
            put ("AVR/Human/1", "https://api.scryfall.com/cards/tavr/7/en?format=image");
            put ("AVR/Human/2", "https://api.scryfall.com/cards/tavr/2/en?format=image");
            put ("AVR/Spirit/1", "https://api.scryfall.com/cards/tavr/4/en?format=image");
            put ("AVR/Spirit/2", "https://api.scryfall.com/cards/tavr/3/en?format=image");
            put ("AVR/Emblem Tamiyo, the Moon Sage", "https://api.scryfall.com/cards/tavr/8/en?format=image");
            put ("AVR/Zombie", "https://api.scryfall.com/cards/tavr/6/en?format=image");

            // DDJ
            put ("DDJ/Saproling", "https://api.scryfall.com/cards/tddj/1/en?format=image");

            // RTR
            put ("RTR/Assassin", "https://api.scryfall.com/cards/trtr/4/en?format=image");
            put ("RTR/Bird", "https://api.scryfall.com/cards/trtr/1/en?format=image");
            put ("RTR/Centaur", "https://api.scryfall.com/cards/trtr/7/en?format=image");
            put ("RTR/Dragon", "https://api.scryfall.com/cards/trtr/5/en?format=image");
            put ("RTR/Elemental", "https://api.scryfall.com/cards/trtr/12/en?format=image");
            put ("RTR/Goblin", "https://api.scryfall.com/cards/trtr/6/en?format=image");
            put ("RTR/Knight", "https://api.scryfall.com/cards/trtr/2/en?format=image");
            put ("RTR/Ooze", "https://api.scryfall.com/cards/trtr/8/en?format=image");
            put ("RTR/Rhino", "https://api.scryfall.com/cards/trtr/9/en?format=image");
            put ("RTR/Saproling", "https://api.scryfall.com/cards/trtr/10/en?format=image");
            put ("RTR/Soldier", "https://api.scryfall.com/cards/trtr/3/en?format=image");
            put ("RTR/Wurm", "https://api.scryfall.com/cards/trtr/11/en?format=image");

            // GTC
            put ("GTC/Angel", "https://api.scryfall.com/cards/tgtc/1/en?format=image");
            put ("GTC/Cleric", "https://api.scryfall.com/cards/tgtc/4/en?format=image");
            put ("GTC/Emblem Domri Rade", "https://api.scryfall.com/cards/tgtc/8/en?format=image");
            put ("GTC/Frog Lizard", "https://api.scryfall.com/cards/tgtc/3/en?format=image");
            put ("GTC/Horror", "https://api.scryfall.com/cards/tgtc/5/en?format=image");
            put ("GTC/Rat", "https://api.scryfall.com/cards/tgtc/2/en?format=image");
            put ("GTC/Soldier", "https://api.scryfall.com/cards/tgtc/6/en?format=image");
            put ("GTC/Spirit", "https://api.scryfall.com/cards/tgtc/7/en?format=image");

            // DDK
            put ("DDK/Spirit", "https://api.scryfall.com/cards/tddk/1/en?format=image");

            // DGM
            put ("DGM/Elemental", "https://api.scryfall.com/cards/tdgm/1/en?format=image");

            // DDL
            put ("DDL/Beast", "https://api.scryfall.com/cards/tddl/2/en?format=image");
            put ("DDL/Griffin", "https://api.scryfall.com/cards/tddl/1/en?format=image");

            // THS
            put ("THS/Bird", "https://api.scryfall.com/cards/tths/4/en?format=image");
            put ("THS/Boar", "https://api.scryfall.com/cards/tths/8/en?format=image");
            put ("THS/Cleric", "https://api.scryfall.com/cards/tths/1/en?format=image");
            put ("THS/Elemental", "https://api.scryfall.com/cards/tths/5/en?format=image");
            put ("THS/Emblem Elspeth, Sun's Champion", "https://api.scryfall.com/cards/tths/11/en?format=image");
            put ("THS/Golem", "https://api.scryfall.com/cards/tths/10/en?format=image");
            put ("THS/Harpy", "https://api.scryfall.com/cards/tths/6/en?format=image");
            put ("THS/Satyr", "https://api.scryfall.com/cards/tths/9/en?format=image");
            put ("THS/Soldier/1", "https://api.scryfall.com/cards/tths/2/en?format=image");
            put ("THS/Soldier/2", "https://api.scryfall.com/cards/tths/3/en?format=image");
            put ("THS/Soldier/3", "https://api.scryfall.com/cards/tths/7/en?format=image");

            // EVE
            put ("EVE/Beast", "https://api.scryfall.com/cards/teve/3/en?format=image");
            put ("EVE/Bird", "https://api.scryfall.com/cards/teve/2/en?format=image");
            put ("EVE/Elemental", "https://api.scryfall.com/cards/teve/5/en?format=image");
            put ("EVE/Goat", "https://api.scryfall.com/cards/teve/1/en?format=image");
            put ("EVE/Goblin Soldier", "https://api.scryfall.com/cards/teve/7/en?format=image");
            put ("EVE/Spirit", "https://api.scryfall.com/cards/teve/4/en?format=image");
            put ("EVE/Worm", "https://api.scryfall.com/cards/teve/6/en?format=image");

            // BNG
            put ("BNG/Bird/1", "https://api.scryfall.com/cards/tbng/4/en?format=image");
            put ("BNG/Bird/2", "https://api.scryfall.com/cards/tbng/1/en?format=image");
            put ("BNG/Cat Soldier", "https://api.scryfall.com/cards/tbng/2/en?format=image");
            put ("BNG/Centaur", "https://api.scryfall.com/cards/tbng/8/en?format=image");
            put ("BNG/Elemental", "https://api.scryfall.com/cards/tbng/7/en?format=image");
            put ("BNG/Gold", "https://api.scryfall.com/cards/tbng/10/en?format=image");
            put ("BNG/Emblem Kiora, the Crashing Wave", "https://api.scryfall.com/cards/tbng/11/en?format=image");
            put ("BNG/Kraken", "https://api.scryfall.com/cards/tbng/5/en?format=image");
            put ("BNG/Soldier", "https://api.scryfall.com/cards/tbng/3/en?format=image");
            put ("BNG/Wolf", "https://api.scryfall.com/cards/tbng/9/en?format=image");
            put ("BNG/Zombie", "https://api.scryfall.com/cards/tbng/6/en?format=image");

            // DDM
            put ("DDM/Assassin", "https://api.scryfall.com/cards/tddm/1/en?format=image");

            // JOU
            put ("JOU/Hydra", "https://api.scryfall.com/cards/tjou/4/en?format=image");
            put ("JOU/Minotaur", "https://api.scryfall.com/cards/tjou/3/en?format=image");
            put ("JOU/Snake", "https://api.scryfall.com/cards/tjou/6/en?format=image");
            put ("JOU/Sphinx", "https://api.scryfall.com/cards/tjou/1/en?format=image");
            put ("JOU/Spider", "https://api.scryfall.com/cards/tjou/5/en?format=image");
            put ("JOU/Zombie", "https://api.scryfall.com/cards/tjou/2/en?format=image");

            // MD1
            put ("MD1/Emblem Elspeth, Knight Errant", "https://api.scryfall.com/cards/tmd1/4/en?format=image");
            put ("MD1/Phyrexian Myr", "https://api.scryfall.com/cards/tmd1/3/en?format=image");
            put ("MD1/Soldier", "https://api.scryfall.com/cards/tmd1/1/en?format=image");
            put ("MD1/Spirit", "https://api.scryfall.com/cards/tmd1/2/en?format=image");

            // CNS
            put ("CNS/Construct", "https://api.scryfall.com/cards/tcns/8/en?format=image");
            put ("CNS/Emblem Dack Fayden", "https://api.scryfall.com/cards/tcns/9/en?format=image");
            put ("CNS/Demon", "https://api.scryfall.com/cards/tcns/2/en?format=image");
            put ("CNS/Elephant", "https://api.scryfall.com/cards/tcns/5/en?format=image");
            put ("CNS/Spirit", "https://api.scryfall.com/cards/tcns/1/en?format=image");
            put ("CNS/Squirrel", "https://api.scryfall.com/cards/tcns/6/en?format=image");
            put ("CNS/Wolf", "https://api.scryfall.com/cards/tcns/7/en?format=image");
            put ("CNS/Zombie", "https://api.scryfall.com/cards/tcns/3/en?format=image");

            // DDN
            put ("DDN/Goblin", "https://api.scryfall.com/cards/ddn/82/en?format=image");

            // KTK
            put ("KTK/Bear", "https://api.scryfall.com/cards/tktk/8/en?format=image");
            put ("KTK/Bird", "https://api.scryfall.com/cards/tktk/1/en?format=image");
            put ("KTK/Goblin", "https://api.scryfall.com/cards/tktk/7/en?format=image");
            put ("KTK/Emblem Sarkhan", "https://api.scryfall.com/cards/tktk/12/en?format=image");
            put ("KTK/Snake", "https://api.scryfall.com/cards/tktk/9/en?format=image");
            put ("KTK/Emblem Sorin", "https://api.scryfall.com/cards/tktk/13/en?format=image");
            put ("KTK/Spirit", "https://api.scryfall.com/cards/tktk/2/en?format=image");
            put ("KTK/Spirit Warrior", "https://api.scryfall.com/cards/tktk/10/en?format=image");
            put ("KTK/Vampire", "https://api.scryfall.com/cards/tktk/5/en?format=image");
            put ("KTK/Warrior/1", "https://api.scryfall.com/cards/tktk/3/en?format=image");
            put ("KTK/Warrior/2", "https://api.scryfall.com/cards/tktk/4/en?format=image");
            put ("KTK/Zombie", "https://api.scryfall.com/cards/tktk/6/en?format=image");

            // EVG
            put ("EVG/Elemental", "https://api.scryfall.com/cards/tevg/1/en?format=image");
            put ("EVG/Elf Warrior", "https://api.scryfall.com/cards/tevg/2/en?format=image");
            put ("EVG/Goblin", "https://api.scryfall.com/cards/tevg/3/en?format=image");

            // GVL
            put ("GVL/Bat", "https://api.scryfall.com/cards/tgvl/11/en?format=image");
            put ("GVL/Beast/1", "https://api.scryfall.com/cards/tgvl/8/en?format=image");
            put ("GVL/Beast/2", "https://api.scryfall.com/cards/tgvl/9/en?format=image");
            put ("GVL/Elephant", "https://api.scryfall.com/cards/tgvl/10/en?format=image");

            // DVD
            put ("DVD/Demon", "https://api.scryfall.com/cards/tdvd/6/en?format=image");
            put ("DVD/Spirit", "https://api.scryfall.com/cards/tdvd/5/en?format=image");
            put ("DVD/Thrull", "https://api.scryfall.com/cards/tdvd/7/en?format=image");

            // FRF
            put ("FRF/Monk", "https://api.scryfall.com/cards/tfrf/1/en?format=image");
            put ("FRF/Spirit", "https://api.scryfall.com/cards/tfrf/2/en?format=image");
            put ("FRF/Warrior", "https://api.scryfall.com/cards/tfrf/3/en?format=image");

            // DDO
            put ("DDO/Kraken", "https://api.scryfall.com/cards/ddo/67/en?format=image");
            put ("DDO/Soldier", "https://api.scryfall.com/cards/ddo/66/en?format=image");

            // MM2
            put ("MM2/Eldrazi Spawn/1", "https://api.scryfall.com/cards/tmm2/1/en?format=image");
            put ("MM2/Eldrazi Spawn/2", "https://api.scryfall.com/cards/tmm2/2/en?format=image");
            put ("MM2/Eldrazi Spawn/3", "https://api.scryfall.com/cards/tmm2/3/en?format=image");
            put ("MM2/Elephant", "https://api.scryfall.com/cards/tmm2/9/en?format=image");
            put ("MM2/Faerie Rogue", "https://api.scryfall.com/cards/tmm2/6/en?format=image");
            put ("MM2/Phyrexian Germ", "https://api.scryfall.com/cards/tmm2/7/en?format=image");
            put ("MM2/Golem", "https://api.scryfall.com/cards/tmm2/15/en?format=image");
            put ("MM2/Insect", "https://api.scryfall.com/cards/tmm2/10/en?format=image");
            put ("MM2/Myr", "https://api.scryfall.com/cards/tmm2/16/en?format=image");
            put ("MM2/Saproling", "https://api.scryfall.com/cards/tmm2/11/en?format=image");
            put ("MM2/Snake", "https://api.scryfall.com/cards/tmm2/12/en?format=image");
            put ("MM2/Soldier", "https://api.scryfall.com/cards/tmm2/4/en?format=image");
            put ("MM2/Spirit", "https://api.scryfall.com/cards/tmm2/5/en?format=image");
            put ("MM2/Thrull", "https://api.scryfall.com/cards/tmm2/8/en?format=image");
            put ("MM2/Wolf", "https://api.scryfall.com/cards/tmm2/13/en?format=image");
            put ("MM2/Worm", "https://api.scryfall.com/cards/tmm2/14/en?format=image");

            // ORI
            put ("ORI/Angel", "https://api.scryfall.com/cards/tori/1/en?format=image");
            put ("ORI/Ashaya, the Awoken World", "https://api.scryfall.com/cards/tori/7/en?format=image");
            put ("ORI/Emblem Chandra", "https://api.scryfall.com/cards/tori/14/en?format=image");
            put ("ORI/Demon", "https://api.scryfall.com/cards/tori/4/en?format=image");
            put ("ORI/Elemental", "https://api.scryfall.com/cards/tori/8/en?format=image");
            put ("ORI/Elf Warrior", "https://api.scryfall.com/cards/tori/9/en?format=image");
            put ("ORI/Goblin", "https://api.scryfall.com/cards/tori/6/en?format=image");
            put ("ORI/Emblem Jace", "https://api.scryfall.com/cards/tori/12/en?format=image");
            put ("ORI/Knight", "https://api.scryfall.com/cards/tori/2/en?format=image");
            put ("ORI/Emblem Liliana", "https://api.scryfall.com/cards/tori/13/en?format=image");
            put ("ORI/Soldier", "https://api.scryfall.com/cards/tori/3/en?format=image");
            put ("ORI/Thopter/1", "https://api.scryfall.com/cards/tori/10/en?format=image");
            put ("ORI/Thopter/2", "https://api.scryfall.com/cards/tori/11/en?format=image");
            put ("ORI/Zombie", "https://api.scryfall.com/cards/tori/5/en?format=image");

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
