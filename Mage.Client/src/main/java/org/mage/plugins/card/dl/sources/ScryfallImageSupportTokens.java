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
            // same construct images uses for different classes, so KLD have 4 instead 2 tokens
            put("KLD/Construct/1", "https://api.scryfall.com/cards/tkld/2/en?format=image");
            put("KLD/Construct/2", "https://api.scryfall.com/cards/tkld/3/en?format=image");
            put("KLD/Construct/3", "https://api.scryfall.com/cards/tkld/2/en?format=image");
            put("KLD/Construct/4", "https://api.scryfall.com/cards/tkld/3/en?format=image");
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

            // PCA (planes)
            put("PCA/Eldrazi", "https://api.scryfall.com/cards/tpca/1/en?format=image");
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
            put("CMR/Horror", "https://api.scryfall.com/cards/tcmr/10/en?format=image");
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
            put("KHM/Emblem Tibalt Cosmic Imposter", "https://api.scryfall.com/cards/tkhm/21/en?format=image");
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
