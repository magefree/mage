package org.mage.plugins.card.dl.sources;

import java.util.HashMap;
import java.util.Map;

import mage.cards.repository.TokenRepository;

/**
 * @author JayDi85
 */
public class ScryfallImageSupportTokens {

    private static final Map<String, String> supportedSets = new HashMap<>();

    private static final Map<String, String> supportedCards = new HashMap<String, String>() {
        {
            // xmage token -> direct or api link:
            //
            // examples:
            //   direct example: https://cards.scryfall.io/large/back/d/c/dc26e13b-7a0f-4e7f-8593-4f22234f4517.jpg
            //   api example: https://api.scryfall.com/cards/trix/6/en?format=image
            //   api example: https://api.scryfall.com/cards/trix/6?format=image
            //   api example: https://api.scryfall.com/cards/tvow/21/en?format=image&face=back
            // api format is primary (direct images links can be changed by scryfall)
            //
            // code form for one token:
            //   set/token_name
            //
            // code form for same name tokens (alternative images):
            //   set/token_name/1
            //   set/token_name/2
            //
            // double faced cards:
            //  front face image: format=image&face=front
            //  back face image: format=image&face=back

            // XMAGE
            // additional tokens for reminder/helper images
            putAll(TokenRepository.instance.prepareScryfallDownloadList());

            // RIX
            // TODO: this should be readded when condition tokens are implemented
            // put("RIX/City's Blessing", "https://api.scryfall.com/cards/trix/6/en?format=image"); 
            put("RIX/Elemental/1", "https://api.scryfall.com/cards/trix/1/en?format=image");
            put("RIX/Elemental/2", "https://api.scryfall.com/cards/trix/2/en?format=image");
            put("RIX/Golem", "https://api.scryfall.com/cards/trix/4/en?format=image");
            put("RIX/Emblem Huatli", "https://api.scryfall.com/cards/trix/5/en?format=image");
            put("RIX/Saproling", "https://api.scryfall.com/cards/trix/3/en?format=image");

            // RNA
            put("RNA/Beast", "https://api.scryfall.com/cards/trna/8/en?format=image");
            put("RNA/Centaur", "https://api.scryfall.com/cards/trna/5/en?format=image");
            put("RNA/Emblem Domri", "https://api.scryfall.com/cards/trna/13/en?format=image");
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

            // GRN
            put("GRN/Angel", "https://api.scryfall.com/cards/tgrn/1/en?format=image");
            put("GRN/Bird Illusion", "https://api.scryfall.com/cards/tgrn/3/en?format=image");
            put("GRN/Elf Knight", "https://api.scryfall.com/cards/tgrn/6/en?format=image");
            put("GRN/Goblin", "https://api.scryfall.com/cards/tgrn/4/en?format=image");
            put("GRN/Insect", "https://api.scryfall.com/cards/tgrn/5/en?format=image");
            put("GRN/Emblem Ral", "https://api.scryfall.com/cards/tgrn/7/en?format=image");
            put("GRN/Soldier", "https://api.scryfall.com/cards/tgrn/2/en?format=image");
            put("GRN/Emblem Vraska", "https://api.scryfall.com/cards/tgrn/8/en?format=image");

            // DOM
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
            put("DOM/Emblem Teferi", "https://api.scryfall.com/cards/tdom/16/en?format=image");
            put("DOM/Zombie Knight", "https://api.scryfall.com/cards/tdom/5/en?format=image");

            // XLN
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

            // HOU
            put("HOU/Horse", "https://api.scryfall.com/cards/thou/10/en?format=image");
            put("HOU/Insect", "https://api.scryfall.com/cards/thou/12/en?format=image");
            put("HOU/Snake", "https://api.scryfall.com/cards/thou/11/en?format=image");

            // AKH - tokens
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

            // AER
            put("AER/Etherium Cell", "https://api.scryfall.com/cards/taer/3/en?format=image");
            put("AER/Gremlin", "https://api.scryfall.com/cards/taer/1/en?format=image");
            put("AER/Ragavan", "https://api.scryfall.com/cards/taer/2/en?format=image");
            put("AER/Emblem Tezzeret", "https://api.scryfall.com/cards/taer/4/en?format=image");

            // KLD
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

            // EMN
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

            // SOI
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

            // OGW
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

            // BFZ
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
            put("WAR/Emblem Nissa", "https://api.scryfall.com/cards/twar/19/en?format=image");
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
            put("MH1/Emblem Serra", "https://api.scryfall.com/cards/tmh1/20/en?format=image");
            put("MH1/Shapeshifter", "https://api.scryfall.com/cards/tmh1/1/en?format=image");
            put("MH1/Soldier", "https://api.scryfall.com/cards/tmh1/4/en?format=image");
            put("MH1/Spider", "https://api.scryfall.com/cards/tmh1/14/en?format=image");
            put("MH1/Spirit", "https://api.scryfall.com/cards/tmh1/16/en?format=image");
            put("MH1/Squirrel", "https://api.scryfall.com/cards/tmh1/15/en?format=image");
            put("MH1/Emblem Wrenn", "https://api.scryfall.com/cards/tmh1/21/en?format=image");
            put("MH1/Zombie", "https://api.scryfall.com/cards/tmh1/7/en?format=image");

            // M19
            put("M19/Emblem Ajani", "https://api.scryfall.com/cards/tm19/15/en?format=image");
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
            put("M19/Emblem Tezzeret", "https://api.scryfall.com/cards/tm19/16/en?format=image");
            put("M19/Thopter", "https://api.scryfall.com/cards/tm19/14/en?format=image");
            put("M19/Emblem Vivien", "https://api.scryfall.com/cards/tm19/17/en?format=image");
            put("M19/Zombie", "https://api.scryfall.com/cards/tm19/8/en?format=image");

            // M20
            put("M20/Ajani's Pridemate", "https://api.scryfall.com/cards/tm20/1/en?format=image");
            put("M20/Emblem Chandra", "https://api.scryfall.com/cards/tm20/11/en?format=image");
            put("M20/Demon", "https://api.scryfall.com/cards/tm20/5/en?format=image");
            put("M20/Elemental Bird", "https://api.scryfall.com/cards/tm20/4/en?format=image");
            put("M20/Elemental", "https://api.scryfall.com/cards/tm20/7/en?format=image");
            put("M20/Golem", "https://api.scryfall.com/cards/tm20/9/en?format=image");
            put("M20/Emblem Yanling", "https://api.scryfall.com/cards/tm20/12/en?format=image");
            put("M20/Soldier", "https://api.scryfall.com/cards/tm20/2/en?format=image");
            put("M20/Spirit", "https://api.scryfall.com/cards/tm20/3/en?format=image");
            put("M20/Treasure", "https://api.scryfall.com/cards/tm20/10/en?format=image");
            put("M20/Wolf", "https://api.scryfall.com/cards/tm20/8/en?format=image");
            put("M20/Zombie", "https://api.scryfall.com/cards/tm20/6/en?format=image");

            // C18
            put("C18/Angel", "https://api.scryfall.com/cards/tc18/3/en?format=image");
            put("C18/Beast/1", "https://api.scryfall.com/cards/tc18/13/en?format=image");
            put("C18/Beast/2", "https://api.scryfall.com/cards/tc18/14/en?format=image");
            put("C18/Cat", "https://api.scryfall.com/cards/tc18/5/en?format=image");
            put("C18/Cat Warrior", "https://api.scryfall.com/cards/tc18/15/en?format=image");
            put("C18/Clue", "https://api.scryfall.com/cards/tc18/19/en?format=image");
            put("C18/Construct/1", "https://api.scryfall.com/cards/tc18/20/en?format=image");
            put("C18/Construct/2", "https://api.scryfall.com/cards/tc18/21/en?format=image");
            put("C18/Dragon", "https://api.scryfall.com/cards/tc18/11/en?format=image");
            put("C18/Dragon Egg", "https://api.scryfall.com/cards/tc18/10/en?format=image");
            put("C18/Elemental", "https://api.scryfall.com/cards/tc18/16/en?format=image");
            put("C18/Phyrexian Horror", "https://api.scryfall.com/cards/tc18/22/en?format=image");
            put("C18/Mask", "https://api.scryfall.com/cards/tc18/4/en?format=image");
            put("C18/Myr", "https://api.scryfall.com/cards/tc18/23/en?format=image");
            put("C18/Phyrexian Myr", "https://api.scryfall.com/cards/tc18/7/en?format=image");
            put("C18/Plant", "https://api.scryfall.com/cards/tc18/17/en?format=image");
            put("C18/Servo", "https://api.scryfall.com/cards/tc18/24/en?format=image");
            put("C18/Shapeshifter", "https://api.scryfall.com/cards/tc18/2/en?format=image");
            put("C18/Soldier", "https://api.scryfall.com/cards/tc18/6/en?format=image");
            put("C18/Survivor", "https://api.scryfall.com/cards/tc18/12/en?format=image");
            put("C18/Thopter/1", "https://api.scryfall.com/cards/tc18/25/en?format=image");
            put("C18/Thopter/2", "https://api.scryfall.com/cards/tc18/26/en?format=image");
            put("C18/Thopter/3", "https://api.scryfall.com/cards/tc18/8/en?format=image");
            put("C18/Worm", "https://api.scryfall.com/cards/tc18/18/en?format=image");
            put("C18/Zombie", "https://api.scryfall.com/cards/tc18/9/en?format=image");

            // C19
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
            put("C19/Emblem Nixilis", "https://api.scryfall.com/cards/tc19/29/en?format=image");
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
            put("ELD/Emblem Garruk", "https://api.scryfall.com/cards/teld/19/en?format=image");
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

            // UND
            put("UND/Goblin", "https://api.scryfall.com/cards/tund/2?format=image");

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
            put("IKO/Emblem Narset", "https://api.scryfall.com/cards/tiko/12/en?format=image");
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
            put("M21/Emblem Basri", "https://api.scryfall.com/cards/tm21/16/en?format=image");
            put("M21/Beast", "https://api.scryfall.com/cards/tm21/10/en?format=image");
            put("M21/Bird", "https://api.scryfall.com/cards/tm21/2/en?format=image");
            put("M21/Cat/1", "https://api.scryfall.com/cards/tm21/20/en?format=image"); // 1/1
            put("M21/Cat/2", "https://api.scryfall.com/cards/tm21/11/en?format=image"); // 2/2
            put("M21/Construct", "https://api.scryfall.com/cards/tm21/14/en?format=image");
            put("M21/Demon", "https://api.scryfall.com/cards/tm21/6/en?format=image");
            put("M21/Dog", "https://api.scryfall.com/cards/tm21/19/en?format=image");
            put("M21/Emblem Garruk", "https://api.scryfall.com/cards/tm21/17/en?format=image");
            put("M21/Goblin Wizard", "https://api.scryfall.com/cards/tm21/8/en?format=image");
            put("M21/Griffin", "https://api.scryfall.com/cards/tm21/3/en?format=image");
            put("M21/Knight", "https://api.scryfall.com/cards/tm21/4/en?format=image");
            put("M21/Emblem Liliana", "https://api.scryfall.com/cards/tm21/18/en?format=image");
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
            put("ZNC/Phyrexian Germ", "https://api.scryfall.com/cards/tznc/4/en?format=image"); // must be in chest or antology
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
            put("KHM/Emblem Kaya", "https://api.scryfall.com/cards/tkhm/20/en?format=image");
            put("KHM/Emblem Tibalt", "https://api.scryfall.com/cards/tkhm/21/en?format=image");
            put("KHM/Emblem Tyvar", "https://api.scryfall.com/cards/tkhm/22/en?format=image");

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
            put("STX/Emblem Lukka", "https://api.scryfall.com/cards/tstx/8/en?format=image");
            put("STX/Pest", "https://api.scryfall.com/cards/tstx/5/en?format=image");
            put("STX/Emblem Rowan", "https://api.scryfall.com/cards/tstx/9/en?format=image");
            put("STX/Spirit", "https://api.scryfall.com/cards/tstx/6/en?format=image");
            put("STX/Treasure", "https://api.scryfall.com/cards/tstx/7/en?format=image");

            // C21
            put("C21/Beast/1", "https://api.scryfall.com/cards/tc21/10/en?format=image"); // 3/3
            put("C21/Beast/2", "https://api.scryfall.com/cards/tc21/11/en?format=image"); // 4/4
            put("C21/Boar", "https://api.scryfall.com/cards/tc21/12/en?format=image");
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
            put("AFR/Emblem Ellywick", "https://api.scryfall.com/cards/tafr/16/en?format=image");
            put("AFR/Faerie Dragon", "https://api.scryfall.com/cards/tafr/4/en?format=image");
            put("AFR/Goblin", "https://api.scryfall.com/cards/tafr/12/en?format=image");
            put("AFR/Guenhwyvar", "https://api.scryfall.com/cards/tafr/13/en?format=image");
            put("AFR/Icingdeath, Frost Tongue", "https://api.scryfall.com/cards/tafr/2/en?format=image");
            put("AFR/Emblem Lolth", "https://api.scryfall.com/cards/tafr/17/en?format=image");
            put("AFR/Lost Mine of Phandelver", "https://api.scryfall.com/cards/tafr/21/en?format=image");
            put("AFR/Emblem Mordenkainen", "https://api.scryfall.com/cards/tafr/18/en?format=image");
            put("AFR/Skeleton", "https://api.scryfall.com/cards/tafr/6/en?format=image");
            put("AFR/Spider", "https://api.scryfall.com/cards/tafr/7/en?format=image");
            put("AFR/The Atropal", "https://api.scryfall.com/cards/tafr/5/en?format=image");
            put("AFR/Tomb of Annihilation", "https://api.scryfall.com/cards/tafr/22/en?format=image");
            put("AFR/Treasure", "https://api.scryfall.com/cards/tafr/15/en?format=image");
            put("AFR/Vecna", "https://api.scryfall.com/cards/tafr/8/en?format=image");
            put("AFR/Wolf", "https://api.scryfall.com/cards/tafr/14/en?format=image");
            put("AFR/Emblem Zariel", "https://api.scryfall.com/cards/tafr/19/en?format=image");
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
            put("MID/Emblem Teferi", "https://api.scryfall.com/cards/tmid/17/en?format=image");
            put("MID/Treefolk", "https://api.scryfall.com/cards/tmid/12/en?format=image");
            put("MID/Vampire", "https://api.scryfall.com/cards/tmid/14/en?format=image");
            put("MID/Wolf", "https://api.scryfall.com/cards/tmid/13/en?format=image");
            put("MID/Emblem Wrenn", "https://api.scryfall.com/cards/tmid/18/en?format=image");
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
            put("VOW/Emblem Chandra", "https://api.scryfall.com/cards/tvow/20/en?format=image");
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
            put("UMA/Citizen", "https://api.scryfall.com/cards/tuma/1/en?format=image");
            put("UMA/Drake", "https://api.scryfall.com/cards/tuma/3/en?format=image");
            put("UMA/Elemental/1", "https://api.scryfall.com/cards/tuma/13/en?format=image"); // green
            put("UMA/Elemental/2", "https://api.scryfall.com/cards/tuma/9/en?format=image");
            put("UMA/Elemental/3", "https://api.scryfall.com/cards/tuma/10/en?format=image");
            put("UMA/Faerie Rogue", "https://api.scryfall.com/cards/tuma/5/en?format=image");
            put("UMA/Homunculus", "https://api.scryfall.com/cards/tuma/4/en?format=image");
            put("UMA/Marit Lage", "https://api.scryfall.com/cards/tuma/6/en?format=image");
            put("UMA/Ooze", "https://api.scryfall.com/cards/tuma/14/en?format=image");
            put("UMA/Soldier", "https://api.scryfall.com/cards/tuma/11/en?format=image");
            put("UMA/Spark Elemental", "https://api.scryfall.com/cards/tuma/12/en?format=image");
            put("UMA/Spider", "https://api.scryfall.com/cards/tuma/15/en?format=image");
            put("UMA/Spirit/1", "https://api.scryfall.com/cards/tuma/2/en?format=image"); // white
            put("UMA/Spirit/2", "https://api.scryfall.com/cards/tuma/16/en?format=image"); // white, black
            put("UMA/Wurm", "https://api.scryfall.com/cards/tuma/7/en?format=image");
            put("UMA/Zombie", "https://api.scryfall.com/cards/tuma/8/en?format=image");

            // MMA
            put("MMA/Bat", "https://api.scryfall.com/cards/tmma/5/en?format=image");
            put("MMA/Dragon", "https://api.scryfall.com/cards/tmma/9/en?format=image");
            put("MMA/Elemental", "https://api.scryfall.com/cards/tmma/11/en?format=image");
            put("MMA/Emblem Elspeth", "https://api.scryfall.com/cards/tmma/16/en?format=image");
            put("MMA/Faerie Rogue", "https://api.scryfall.com/cards/tmma/14/en?format=image");
            put("MMA/Giant Warrior", "https://api.scryfall.com/cards/tmma/1/en?format=image");
            put("MMA/Goblin", "https://api.scryfall.com/cards/tmma/10/en?format=image");
            put("MMA/Goblin Rogue", "https://api.scryfall.com/cards/tmma/6/en?format=image");
            put("MMA/Illusion", "https://api.scryfall.com/cards/tmma/4/en?format=image");
            put("MMA/Kithkin Soldier", "https://api.scryfall.com/cards/tmma/2/en?format=image");
            put("MMA/Saproling", "https://api.scryfall.com/cards/tmma/12/en?format=image");
            put("MMA/Soldier", "https://api.scryfall.com/cards/tmma/3/en?format=image");
            put("MMA/Spider", "https://api.scryfall.com/cards/tmma/7/en?format=image");
            put("MMA/Treefolk Shaman", "https://api.scryfall.com/cards/tmma/13/en?format=image");
            put("MMA/Worm", "https://api.scryfall.com/cards/tmma/15/en?format=image");
            put("MMA/Zombie", "https://api.scryfall.com/cards/tmma/8/en?format=image");

            // SHM
            put("SHM/Elemental/1", "https://api.scryfall.com/cards/tshm/9/en?format=image"); // black, red
            put("SHM/Elemental/2", "https://api.scryfall.com/cards/tshm/4/en?format=image"); // haste
            put("SHM/Elf Warrior/1", "https://api.scryfall.com/cards/tshm/5/en?format=image"); // green
            put("SHM/Elf Warrior/2", "https://api.scryfall.com/cards/tshm/12/en?format=image"); // white, green
            put("SHM/Faerie Rogue", "https://api.scryfall.com/cards/tshm/8/en?format=image");
            put("SHM/Giant Warrior", "https://api.scryfall.com/cards/tshm/10/en?format=image");
            put("SHM/Goblin Warrior", "https://api.scryfall.com/cards/tshm/11/en?format=image");
            put("SHM/Kithkin Soldier", "https://api.scryfall.com/cards/tshm/1/en?format=image");
            put("SHM/Rat", "https://api.scryfall.com/cards/tshm/3/en?format=image");
            put("SHM/Spider", "https://api.scryfall.com/cards/tshm/6/en?format=image");
            put("SHM/Spirit", "https://api.scryfall.com/cards/tshm/2/en?format=image");
            put("SHM/Wolf", "https://api.scryfall.com/cards/tshm/7/en?format=image");

            // NEO
            put("NEO/Construct/1", "https://api.scryfall.com/cards/tneo/15/en?format=image"); // 1/1
            put("NEO/Construct/2", "https://api.scryfall.com/cards/tneo/6/en?format=image"); // haste
            put("NEO/Dragon Spirit", "https://api.scryfall.com/cards/tneo/7/en?format=image");
            put("NEO/Goblin Shaman", "https://api.scryfall.com/cards/tneo/8/en?format=image");
            put("NEO/Human Monk", "https://api.scryfall.com/cards/tneo/10/en?format=image");
            put("NEO/Emblem Kaito", "https://api.scryfall.com/cards/tneo/18/en?format=image");
            put("NEO/Keimi", "https://api.scryfall.com/cards/tneo/13/en?format=image");
            put("NEO/Mechtitan", "https://api.scryfall.com/cards/tneo/14/en?format=image");
            put("NEO/Ninja", "https://api.scryfall.com/cards/tneo/4/en?format=image");
            put("NEO/Pilot", "https://api.scryfall.com/cards/tneo/1/en?format=image");
            put("NEO/Rat Rogue", "https://api.scryfall.com/cards/tneo/5/en?format=image");
            put("NEO/Samurai", "https://api.scryfall.com/cards/tneo/3/en?format=image");
            put("NEO/Spirit/1", "https://api.scryfall.com/cards/tneo/2/en?format=image"); // colorless
            put("NEO/Spirit/2", "https://api.scryfall.com/cards/tneo/12/en?format=image"); // */*
            put("NEO/Spirit/3", "https://api.scryfall.com/cards/tneo/11/en?format=image"); // green
            put("NEO/Spirit/4", "https://api.scryfall.com/cards/tneo/9/en?format=image"); // red
            put("NEO/Tamiyo's Notebook", "https://api.scryfall.com/cards/tneo/16/en?format=image");
            put("NEO/Emblem Tezzeret", "https://api.scryfall.com/cards/tneo/19/en?format=image");
            put("NEO/Treasure", "https://api.scryfall.com/cards/tneo/17/en?format=image");

            // NEC
            put("NEC/Angel", "https://api.scryfall.com/cards/tnec/2/en?format=image");
            put("NEC/Beast", "https://api.scryfall.com/cards/tnec/7/en?format=image");
            put("NEC/Elemental", "https://api.scryfall.com/cards/tnec/4/en?format=image");
            put("NEC/Elephant", "https://api.scryfall.com/cards/tnec/8/en?format=image");
            put("NEC/Goblin", "https://api.scryfall.com/cards/tnec/5/en?format=image");
            put("NEC/Myr", "https://api.scryfall.com/cards/tnec/11/en?format=image");
            put("NEC/Phyrexian Germ", "https://api.scryfall.com/cards/tnec/3/en?format=image");
            put("NEC/Plant", "https://api.scryfall.com/cards/tnec/9/en?format=image");
            put("NEC/Saproling", "https://api.scryfall.com/cards/tnec/10/en?format=image");
            put("NEC/Shrine", "https://api.scryfall.com/cards/tnec/1/en?format=image");
            put("NEC/Smoke Blessing", "https://api.scryfall.com/cards/tnec/6/en?format=image");
            put("NEC/Thopter", "https://api.scryfall.com/cards/tnec/12/en?format=image");

            // SLD
            put("SLD/Angel", "https://api.scryfall.com/cards/sld/1340?format=image");
            put("SLD/Cat/1", "https://api.scryfall.com/cards/sld/1517?format=image");
            put("SLD/Cat/2", "https://api.scryfall.com/cards/sld/27?format=image");
            put("SLD/Cat/3", "https://api.scryfall.com/cards/sld/28?format=image");
            put("SLD/Clue", "https://api.scryfall.com/cards/sld/348/en?format=image");
            put("SLD/Dog", "https://api.scryfall.com/cards/sld/1516?format=image");
            put("SLD/Egg", "https://api.scryfall.com/cards/sld/1398?format=image");
            put("SLD/Faerie Rogue/1", "https://api.scryfall.com/cards/sld/13/en?format=image");
            put("SLD/Faerie Rogue/2", "https://api.scryfall.com/cards/sld/14/en?format=image");
            put("SLD/Faerie Rogue/3", "https://api.scryfall.com/cards/sld/15/en?format=image");
            put("SLD/Faerie Rogue/4", "https://api.scryfall.com/cards/sld/16/en?format=image");
            put("SLD/Food/1", "https://api.scryfall.com/cards/sld/1938?format=image");
            put("SLD/Food/2", "https://api.scryfall.com/cards/sld/2010?format=image");
            put("SLD/Food/3", "https://api.scryfall.com/cards/sld/2011?format=image");
            put("SLD/Food/4", "https://api.scryfall.com/cards/sld/2012?format=image");
            put("SLD/Food/5", "https://api.scryfall.com/cards/sld/2013?format=image");
            put("SLD/Food/6", "https://api.scryfall.com/cards/sld/2064?format=image");
            put("SLD/Goblin", "https://api.scryfall.com/cards/sld/219?format=image");
            put("SLD/Hydra", "https://api.scryfall.com/cards/sld/1334?format=image");
            put("SLD/Icingdeath, Frost Tongue", "https://api.scryfall.com/cards/sld/1018?format=image");
            put("SLD/Marit Lage", "https://api.scryfall.com/cards/sld/1681?format=image");
            put("SLD/Mechtitan/1", "https://api.scryfall.com/cards/sld/1969?format=image");
            put("SLD/Mechtitan/2", "https://api.scryfall.com/cards/sld/1969/en?format=image&face=back");
            put("SLD/Mechtitan", "https://api.scryfall.com/cards/sld/1969?format=image");
            put("SLD/Myr", "https://api.scryfall.com/cards/sld/2101?format=image");
            put("SLD/Saproling", "https://api.scryfall.com/cards/sld/1139?format=image");
            put("SLD/Shapeshifter/1", "https://api.scryfall.com/cards/sld/1906?format=image");
            put("SLD/Shapeshifter/2", "https://api.scryfall.com/cards/sld/1907?format=image");
            put("SLD/Shapeshifter/3", "https://api.scryfall.com/cards/sld/1908?format=image");
            put("SLD/Shapeshifter/4", "https://api.scryfall.com/cards/sld/1909?format=image");
            put("SLD/Shrine", "https://api.scryfall.com/cards/sld/1835?format=image");
            put("SLD/Spirit/1", "https://api.scryfall.com/cards/sld/1341?format=image");
            put("SLD/Spirit/2", "https://api.scryfall.com/cards/sld/1852?format=image");
            put("SLD/Squirrel", "https://api.scryfall.com/cards/sld/200?format=image");
            put("SLD/Treasure/1", "https://api.scryfall.com/cards/sld/1432/en?format=image");
            put("SLD/Treasure/2", "https://api.scryfall.com/cards/sld/1736/en?format=image");
            put("SLD/Treasure/3", "https://api.scryfall.com/cards/sld/1507/en?format=image");
            put("SLD/Treasure/4", "https://api.scryfall.com/cards/sld/153/en?format=image");
            put("SLD/Treasure/5", "https://api.scryfall.com/cards/sld/2065/en?format=image");
            put("SLD/Treasure/6", "https://api.scryfall.com/cards/sld/2094/en?format=image");
            put("SLD/Walker/1", "https://api.scryfall.com/cards/sld/148/en?format=image");
            put("SLD/Walker/2", "https://api.scryfall.com/cards/sld/149/en?format=image");
            put("SLD/Walker/3", "https://api.scryfall.com/cards/sld/150/en?format=image");
            put("SLD/Walker/4", "https://api.scryfall.com/cards/sld/151/en?format=image");
            put("SLD/Walker/5", "https://api.scryfall.com/cards/sld/152/en?format=image");
            put("SLD/Warrior", "https://api.scryfall.com/cards/sld/1752?format=image");
            put("SLD/Wolf", "https://api.scryfall.com/cards/sld/1613?format=image");
            put("SLD/Wurm", "https://api.scryfall.com/cards/sld/1306?format=image");
            put("SLD/Zombie", "https://api.scryfall.com/cards/sld/1357?format=image");

            // 2XM
            put("2XM/Angel", "https://api.scryfall.com/cards/t2xm/3/en?format=image");
            put("2XM/Ape", "https://api.scryfall.com/cards/t2xm/12/en?format=image");
            put("2XM/Beast", "https://api.scryfall.com/cards/t2xm/13/en?format=image");
            put("2XM/Cat", "https://api.scryfall.com/cards/t2xm/4/en?format=image");
            put("2XM/Clue", "https://api.scryfall.com/cards/t2xm/22/en?format=image");
            put("2XM/Demon", "https://api.scryfall.com/cards/t2xm/9/en?format=image");
            put("2XM/Eldrazi Spawn", "https://api.scryfall.com/cards/t2xm/1/en?format=image");
            put("2XM/Elemental", "https://api.scryfall.com/cards/t2xm/20/en?format=image");
            put("2XM/Elephant", "https://api.scryfall.com/cards/t2xm/14/en?format=image");
            put("2XM/Elf Warrior", "https://api.scryfall.com/cards/t2xm/21/en?format=image");
            put("2XM/Phyrexian Germ", "https://api.scryfall.com/cards/t2xm/10/en?format=image");
            put("2XM/Golem", "https://api.scryfall.com/cards/t2xm/23/en?format=image");
            put("2XM/Human Soldier", "https://api.scryfall.com/cards/t2xm/5/en?format=image");
            put("2XM/Marit Lage", "https://api.scryfall.com/cards/t2xm/11/en?format=image");
            put("2XM/Myr", "https://api.scryfall.com/cards/t2xm/24/en?format=image");
            put("2XM/Phyrexian Myr", "https://api.scryfall.com/cards/t2xm/7/en?format=image");
            put("2XM/Ooze", "https://api.scryfall.com/cards/t2xm/15/en?format=image");
            put("2XM/Plant", "https://api.scryfall.com/cards/t2xm/16/en?format=image");
            put("2XM/Saproling", "https://api.scryfall.com/cards/t2xm/17/en?format=image");
            put("2XM/Servo", "https://api.scryfall.com/cards/t2xm/25/en?format=image");
            put("2XM/Shapeshifter", "https://api.scryfall.com/cards/t2xm/2/en?format=image");
            put("2XM/Soldier", "https://api.scryfall.com/cards/t2xm/6/en?format=image");
            put("2XM/Squirrel", "https://api.scryfall.com/cards/t2xm/18/en?format=image");
            put("2XM/Thopter/1", "https://api.scryfall.com/cards/t2xm/26/en?format=image");
            put("2XM/Thopter/2", "https://api.scryfall.com/cards/t2xm/8/en?format=image");
            put("2XM/Treasure", "https://api.scryfall.com/cards/t2xm/27/en?format=image");
            put("2XM/Tuktuk the Returned", "https://api.scryfall.com/cards/t2xm/28/en?format=image");
            put("2XM/Wolf", "https://api.scryfall.com/cards/t2xm/19/en?format=image");
            put("2XM/Phyrexian Wurm/1", "https://api.scryfall.com/cards/t2xm/29/en?format=image");
            put("2XM/Phyrexian Wurm/2", "https://api.scryfall.com/cards/t2xm/30/en?format=image");

            // DTK
            put("DTK/Djinn Monk", "https://api.scryfall.com/cards/tdtk/2/en?format=image");
            put("DTK/Dragon", "https://api.scryfall.com/cards/tdtk/5/en?format=image");
            put("DTK/Goblin", "https://api.scryfall.com/cards/tdtk/6/en?format=image");
            put("DTK/Emblem Narset", "https://api.scryfall.com/cards/tdtk/8/en?format=image");
            put("DTK/Warrior", "https://api.scryfall.com/cards/tdtk/1/en?format=image");
            put("DTK/Zombie", "https://api.scryfall.com/cards/tdtk/3/en?format=image");
            put("DTK/Zombie Horror", "https://api.scryfall.com/cards/tdtk/4/en?format=image");

            // SNC
            put("SNC/Angel", "https://api.scryfall.com/cards/tsnc/2/en?format=image");
            put("SNC/Cat", "https://api.scryfall.com/cards/tsnc/9/en?format=image");
            put("SNC/Citizen", "https://api.scryfall.com/cards/tsnc/12/en?format=image");
            put("SNC/Devil", "https://api.scryfall.com/cards/tsnc/8/en?format=image");
            put("SNC/Dog", "https://api.scryfall.com/cards/tsnc/10/en?format=image");
            put("SNC/Fish", "https://api.scryfall.com/cards/tsnc/4/en?format=image");
            put("SNC/Ogre Warrior", "https://api.scryfall.com/cards/tsnc/6/en?format=image");
            put("SNC/Rhino Warrior", "https://api.scryfall.com/cards/tsnc/11/en?format=image");
            put("SNC/Rogue", "https://api.scryfall.com/cards/tsnc/7/en?format=image");
            put("SNC/Spirit", "https://api.scryfall.com/cards/tsnc/3/en?format=image");
            put("SNC/Treasure/1", "https://api.scryfall.com/cards/tsnc/13/en?format=image");
            put("SNC/Treasure/2", "https://api.scryfall.com/cards/tsnc/14/en?format=image");
            put("SNC/Treasure/3", "https://api.scryfall.com/cards/tsnc/15/en?format=image");
            put("SNC/Treasure/4", "https://api.scryfall.com/cards/tsnc/16/en?format=image");
            put("SNC/Treasure/5", "https://api.scryfall.com/cards/tsnc/17/en?format=image");
            put("SNC/Wizard", "https://api.scryfall.com/cards/tsnc/5/en?format=image");

            // C14
            put("C14/Angel", "https://api.scryfall.com/cards/tc14/1/en?format=image");
            put("C14/Ape", "https://api.scryfall.com/cards/tc14/18/en?format=image");
            put("C14/Beast/1", "https://api.scryfall.com/cards/tc14/19/en?format=image");
            put("C14/Beast/2", "https://api.scryfall.com/cards/tc14/20/en?format=image");
            put("C14/Cat", "https://api.scryfall.com/cards/tc14/2/en?format=image");
            put("C14/Emblem Daretti", "https://api.scryfall.com/cards/tc14/36/en?format=image");
            put("C14/Demon/1", "https://api.scryfall.com/cards/tc14/13/en?format=image");
            put("C14/Demon/2", "https://api.scryfall.com/cards/tc14/12/en?format=image");
            put("C14/Elemental", "https://api.scryfall.com/cards/tc14/21/en?format=image");
            put("C14/Elephant", "https://api.scryfall.com/cards/tc14/22/en?format=image");
            put("C14/Elf Druid", "https://api.scryfall.com/cards/tc14/23/en?format=image");
            put("C14/Elf Warrior", "https://api.scryfall.com/cards/tc14/24/en?format=image");
            put("C14/Fish", "https://api.scryfall.com/cards/tc14/8/en?format=image");
            put("C14/Gargoyle", "https://api.scryfall.com/cards/tc14/27/en?format=image");
            put("C14/Phyrexian Germ", "https://api.scryfall.com/cards/tc14/14/en?format=image");
            put("C14/Goat", "https://api.scryfall.com/cards/tc14/3/en?format=image");
            put("C14/Goblin", "https://api.scryfall.com/cards/tc14/17/en?format=image");
            put("C14/Horror", "https://api.scryfall.com/cards/tc14/15/en?format=image");
            put("C14/Kor Soldier", "https://api.scryfall.com/cards/tc14/4/en?format=image");
            put("C14/Kraken", "https://api.scryfall.com/cards/tc14/9/en?format=image");
            put("C14/Myr", "https://api.scryfall.com/cards/tc14/28/en?format=image");
            put("C14/Emblem Nixilis", "https://api.scryfall.com/cards/tc14/35/en?format=image");
            put("C14/Pegasus", "https://api.scryfall.com/cards/tc14/5/en?format=image");
            put("C14/Pentavite", "https://api.scryfall.com/cards/tc14/29/en?format=image");
            put("C14/Soldier", "https://api.scryfall.com/cards/tc14/6/en?format=image");
            put("C14/Spirit", "https://api.scryfall.com/cards/tc14/7/en?format=image");
            put("C14/Stoneforged Blade", "https://api.scryfall.com/cards/tc14/30/en?format=image");
            put("C14/Emblem Teferi", "https://api.scryfall.com/cards/tc14/34/en?format=image");
            put("C14/Treefolk", "https://api.scryfall.com/cards/tc14/25/en?format=image");
            put("C14/Tuktuk the Returned", "https://api.scryfall.com/cards/tc14/31/en?format=image");
            put("C14/Whale", "https://api.scryfall.com/cards/tc14/10/en?format=image");
            put("C14/Wolf", "https://api.scryfall.com/cards/tc14/26/en?format=image");
            put("C14/Phyrexian Wurm/1", "https://api.scryfall.com/cards/tc14/32/en?format=image");
            put("C14/Phyrexian Wurm/2", "https://api.scryfall.com/cards/tc14/33/en?format=image");
            put("C14/Zombie/1", "https://api.scryfall.com/cards/tc14/16/en?format=image");
            put("C14/Zombie/2", "https://api.scryfall.com/cards/tc14/11/en?format=image");

            // C15
            put("C15/Angel", "https://api.scryfall.com/cards/tc15/2/en?format=image");
            put("C15/Bear", "https://api.scryfall.com/cards/tc15/12/en?format=image");
            put("C15/Beast", "https://api.scryfall.com/cards/tc15/13/en?format=image");
            put("C15/Cat", "https://api.scryfall.com/cards/tc15/3/en?format=image");
            put("C15/Dragon", "https://api.scryfall.com/cards/tc15/9/en?format=image");
            put("C15/Drake", "https://api.scryfall.com/cards/tc15/6/en?format=image");
            put("C15/Elemental", "https://api.scryfall.com/cards/tc15/20/en?format=image");
            put("C15/Elemental Shaman", "https://api.scryfall.com/cards/tc15/10/en?format=image");
            put("C15/Elephant", "https://api.scryfall.com/cards/tc15/14/en?format=image");
            put("C15/Frog Lizard", "https://api.scryfall.com/cards/tc15/15/en?format=image");
            put("C15/Phyrexian Germ", "https://api.scryfall.com/cards/tc15/7/en?format=image");
            put("C15/Gold", "https://api.scryfall.com/cards/tc15/24/en?format=image");
            put("C15/Knight/1", "https://api.scryfall.com/cards/tc15/4/en?format=image");
            put("C15/Knight/2", "https://api.scryfall.com/cards/tc15/5/en?format=image");
            put("C15/Lightning Rager", "https://api.scryfall.com/cards/tc15/11/en?format=image");
            put("C15/Saproling", "https://api.scryfall.com/cards/tc15/16/en?format=image");
            put("C15/Shapeshifter", "https://api.scryfall.com/cards/tc15/1/en?format=image");
            put("C15/Snake/1", "https://api.scryfall.com/cards/tc15/17/en?format=image");
            put("C15/Snake/2", "https://api.scryfall.com/cards/tc15/21/en?format=image");
            put("C15/Spider", "https://api.scryfall.com/cards/tc15/18/en?format=image");
            put("C15/Spirit/1", "https://api.scryfall.com/cards/tc15/23/en?format=image");
            put("C15/Spirit/2", "https://api.scryfall.com/cards/tc15/22/en?format=image");
            put("C15/Wolf", "https://api.scryfall.com/cards/tc15/19/en?format=image");
            put("C15/Zombie", "https://api.scryfall.com/cards/tc15/8/en?format=image");

            // C16
            put("C16/Beast", "https://api.scryfall.com/cards/tc16/14/en?format=image");
            put("C16/Bird/1", "https://api.scryfall.com/cards/tc16/7/en?format=image");
            put("C16/Bird/2", "https://api.scryfall.com/cards/tc16/2/en?format=image");
            put("C16/Emblem Daretti", "https://api.scryfall.com/cards/tc16/21/en?format=image");
            put("C16/Elemental", "https://api.scryfall.com/cards/tc16/3/en?format=image");
            put("C16/Elf Warrior", "https://api.scryfall.com/cards/tc16/15/en?format=image");
            put("C16/Phyrexian Germ", "https://api.scryfall.com/cards/tc16/10/en?format=image");
            put("C16/Goat", "https://api.scryfall.com/cards/tc16/4/en?format=image");
            put("C16/Goblin", "https://api.scryfall.com/cards/tc16/12/en?format=image");
            put("C16/Phyrexian Horror", "https://api.scryfall.com/cards/tc16/19/en?format=image");
            put("C16/Myr", "https://api.scryfall.com/cards/tc16/20/en?format=image");
            put("C16/Ogre", "https://api.scryfall.com/cards/tc16/13/en?format=image");
            put("C16/Saproling/1", "https://api.scryfall.com/cards/tc16/16/en?format=image");
            put("C16/Saproling/2", "https://api.scryfall.com/cards/tc16/17/en?format=image");
            put("C16/Soldier", "https://api.scryfall.com/cards/tc16/5/en?format=image");
            put("C16/Spirit/1", "https://api.scryfall.com/cards/tc16/1/en?format=image");
            put("C16/Spirit/2", "https://api.scryfall.com/cards/tc16/6/en?format=image");
            put("C16/Squid", "https://api.scryfall.com/cards/tc16/8/en?format=image");
            put("C16/Thopter", "https://api.scryfall.com/cards/tc16/9/en?format=image");
            put("C16/Worm", "https://api.scryfall.com/cards/tc16/18/en?format=image");
            put("C16/Zombie", "https://api.scryfall.com/cards/tc16/11/en?format=image");

            // C17
            put("C17/Bat", "https://api.scryfall.com/cards/tc17/2/en?format=image");
            put("C17/Cat", "https://api.scryfall.com/cards/tc17/1/en?format=image");
            put("C17/Cat Dragon", "https://api.scryfall.com/cards/tc17/9/en?format=image");
            put("C17/Cat Warrior", "https://api.scryfall.com/cards/tc17/8/en?format=image");
            put("C17/Dragon/1", "https://api.scryfall.com/cards/tc17/6/en?format=image");
            put("C17/Dragon/2", "https://api.scryfall.com/cards/tc17/7/en?format=image");
            put("C17/Eldrazi Spawn", "https://api.scryfall.com/cards/tc17/11/en?format=image");
            put("C17/Gold", "https://api.scryfall.com/cards/tc17/10/en?format=image");
            put("C17/Rat", "https://api.scryfall.com/cards/tc17/3/en?format=image");
            put("C17/Vampire", "https://api.scryfall.com/cards/tc17/4/en?format=image");
            put("C17/Zombie", "https://api.scryfall.com/cards/tc17/5/en?format=image");

            // NCC
            put("NCC/Beast", "https://api.scryfall.com/cards/tncc/21/en?format=image");
            put("NCC/Cat Beast", "https://api.scryfall.com/cards/tncc/4/en?format=image");
            put("NCC/Clue", "https://api.scryfall.com/cards/tncc/34/en?format=image");
            put("NCC/Demon", "https://api.scryfall.com/cards/tncc/15/en?format=image");
            put("NCC/Devil", "https://api.scryfall.com/cards/tncc/17/en?format=image");
            put("NCC/Drake", "https://api.scryfall.com/cards/tncc/10/en?format=image");
            put("NCC/Eldrazi", "https://api.scryfall.com/cards/tncc/1/en?format=image");
            put("NCC/Eldrazi Spawn", "https://api.scryfall.com/cards/tncc/2/en?format=image");
            put("NCC/Elemental/1", "https://api.scryfall.com/cards/tncc/18/en?format=image");
            put("NCC/Elemental/2", "https://api.scryfall.com/cards/tncc/32/en?format=image");
            put("NCC/Elemental/3", "https://api.scryfall.com/cards/tncc/5/en?format=image");
            put("NCC/Elephant", "https://api.scryfall.com/cards/tncc/22/en?format=image");
            put("NCC/Elf Warrior", "https://api.scryfall.com/cards/tncc/23/en?format=image");
            put("NCC/Faerie", "https://api.scryfall.com/cards/tncc/11/en?format=image");
            put("NCC/Food", "https://api.scryfall.com/cards/tncc/35/en?format=image");
            put("NCC/Goat", "https://api.scryfall.com/cards/tncc/6/en?format=image");
            put("NCC/Human", "https://api.scryfall.com/cards/tncc/7/en?format=image");
            put("NCC/Human Soldier", "https://api.scryfall.com/cards/tncc/8/en?format=image");
            put("NCC/Insect", "https://api.scryfall.com/cards/tncc/24/en?format=image");
            put("NCC/Lightning Rager", "https://api.scryfall.com/cards/tncc/19/en?format=image");
            put("NCC/Ogre", "https://api.scryfall.com/cards/tncc/20/en?format=image");
            put("NCC/Ooze/1", "https://api.scryfall.com/cards/tncc/25/en?format=image");
            put("NCC/Ooze/2", "https://api.scryfall.com/cards/tncc/26/en?format=image");
            put("NCC/Plant", "https://api.scryfall.com/cards/tncc/27/en?format=image");
            put("NCC/Saproling", "https://api.scryfall.com/cards/tncc/28/en?format=image");
            put("NCC/Soldier/1", "https://api.scryfall.com/cards/tncc/9/en?format=image");
            put("NCC/Soldier/2", "https://api.scryfall.com/cards/tncc/33/en?format=image");
            put("NCC/Spider", "https://api.scryfall.com/cards/tncc/29/en?format=image");
            put("NCC/Squid", "https://api.scryfall.com/cards/tncc/12/en?format=image");
            put("NCC/Tentacle", "https://api.scryfall.com/cards/tncc/13/en?format=image");
            put("NCC/Thopter", "https://api.scryfall.com/cards/tncc/36/en?format=image");
            put("NCC/Treefolk", "https://api.scryfall.com/cards/tncc/30/en?format=image");
            put("NCC/Wurm", "https://api.scryfall.com/cards/tncc/31/en?format=image");
            put("NCC/Zombie", "https://api.scryfall.com/cards/tncc/16/en?format=image");

            // MED
            put("MED/Beast", "https://api.scryfall.com/cards/tmed/W1/en?format=image");
            put("MED/Construct/1", "https://api.scryfall.com/cards/tmed/R1/en?format=image");
            put("MED/Construct/2", "https://api.scryfall.com/cards/tmed/G3/en?format=image");
            put("MED/Emblem Dack", "https://api.scryfall.com/cards/tmed/R2/en?format=image");
            put("MED/Emblem Domri", "https://api.scryfall.com/cards/tmed/R3/en?format=image");
            put("MED/Dragon", "https://api.scryfall.com/cards/tmed/W2/en?format=image");
            put("MED/Emblem Elspeth", "https://api.scryfall.com/cards/tmed/G4/en?format=image");
            put("MED/Emblem Garruk", "https://api.scryfall.com/cards/tmed/W3/en?format=image");
            put("MED/Emblem Jaya Ballard", "https://api.scryfall.com/cards/tmed/R4/en?format=image");
            put("MED/Emblem Liliana", "https://api.scryfall.com/cards/tmed/G5/en?format=image");
            put("MED/Emblem Ral", "https://api.scryfall.com/cards/tmed/G6/en?format=image");
            put("MED/Soldier", "https://api.scryfall.com/cards/tmed/G1/en?format=image");
            put("MED/Emblem Tamiyo", "https://api.scryfall.com/cards/tmed/R5/en?format=image");
            put("MED/Emblem Teferi", "https://api.scryfall.com/cards/tmed/G7/en?format=image");
            put("MED/Emblem Vraska", "https://api.scryfall.com/cards/tmed/G8/en?format=image");
            put("MED/Zombie", "https://api.scryfall.com/cards/tmed/G2/en?format=image");

            // BBD
            put("BBD/Beast", "https://api.scryfall.com/cards/tbbd/5/en?format=image");
            put("BBD/Myr", "https://api.scryfall.com/cards/tbbd/6/en?format=image");
            put("BBD/Emblem Rowan Kenrith", "https://api.scryfall.com/cards/tbbd/8/en?format=image");
            put("BBD/Spirit", "https://api.scryfall.com/cards/tbbd/1/en?format=image");
            put("BBD/Warrior", "https://api.scryfall.com/cards/tbbd/2/en?format=image");
            put("BBD/Emblem Will Kenrith", "https://api.scryfall.com/cards/tbbd/7/en?format=image");
            put("BBD/Zombie", "https://api.scryfall.com/cards/tbbd/3/en?format=image");
            put("BBD/Zombie Giant", "https://api.scryfall.com/cards/tbbd/4/en?format=image");

            // DDU
            put("DDU/Elf Warrior", "https://api.scryfall.com/cards/tddu/1/en?format=image");
            put("DDU/Myr", "https://api.scryfall.com/cards/tddu/2/en?format=image");
            put("DDU/Thopter/1", "https://api.scryfall.com/cards/tddu/3/en?format=image");
            put("DDU/Thopter/2", "https://api.scryfall.com/cards/tddu/4/en?format=image");

            // IMA
            put("IMA/Angel", "https://api.scryfall.com/cards/tima/1/en?format=image");
            put("IMA/Beast", "https://api.scryfall.com/cards/tima/7/en?format=image");
            put("IMA/Bird", "https://api.scryfall.com/cards/tima/2/en?format=image");
            put("IMA/Djinn Monk", "https://api.scryfall.com/cards/tima/4/en?format=image");
            put("IMA/Dragon/1", "https://api.scryfall.com/cards/tima/5/en?format=image");
            put("IMA/Dragon/2", "https://api.scryfall.com/cards/tima/6/en?format=image");
            put("IMA/Spirit", "https://api.scryfall.com/cards/tima/3/en?format=image");

            // CM2
            put("CM2/Bird", "https://api.scryfall.com/cards/tcm2/2/en?format=image");
            put("CM2/Emblem Daretti", "https://api.scryfall.com/cards/tcm2/18/en?format=image");
            put("CM2/Elemental Shaman", "https://api.scryfall.com/cards/tcm2/8/en?format=image");
            put("CM2/Phyrexian Germ", "https://api.scryfall.com/cards/tcm2/6/en?format=image");
            put("CM2/Goat", "https://api.scryfall.com/cards/tcm2/3/en?format=image");
            put("CM2/Goblin", "https://api.scryfall.com/cards/tcm2/9/en?format=image");
            put("CM2/Knight", "https://api.scryfall.com/cards/tcm2/4/en?format=image");
            put("CM2/Lightning Rager", "https://api.scryfall.com/cards/tcm2/10/en?format=image");
            put("CM2/Myr", "https://api.scryfall.com/cards/tcm2/12/en?format=image");
            put("CM2/Pentavite", "https://api.scryfall.com/cards/tcm2/13/en?format=image");
            put("CM2/Saproling", "https://api.scryfall.com/cards/tcm2/11/en?format=image");
            put("CM2/Shapeshifter", "https://api.scryfall.com/cards/tcm2/1/en?format=image");
            put("CM2/Spirit", "https://api.scryfall.com/cards/tcm2/5/en?format=image");
            put("CM2/Triskelavite", "https://api.scryfall.com/cards/tcm2/14/en?format=image");
            put("CM2/Tuktuk the Returned", "https://api.scryfall.com/cards/tcm2/15/en?format=image");
            put("CM2/Phyrexian Wurm/1", "https://api.scryfall.com/cards/tcm2/16/en?format=image");
            put("CM2/Phyrexian Wurm/2", "https://api.scryfall.com/cards/tcm2/17/en?format=image");
            put("CM2/Zombie", "https://api.scryfall.com/cards/tcm2/7/en?format=image");

            // CMA
            put("CMA/Beast/1", "https://api.scryfall.com/cards/tcma/7/en?format=image");
            put("CMA/Beast/2", "https://api.scryfall.com/cards/tcma/8/en?format=image");
            put("CMA/Dragon", "https://api.scryfall.com/cards/tcma/6/en?format=image");
            put("CMA/Drake", "https://api.scryfall.com/cards/tcma/18/en?format=image");
            put("CMA/Elemental", "https://api.scryfall.com/cards/tcma/9/en?format=image");
            put("CMA/Elephant", "https://api.scryfall.com/cards/tcma/10/en?format=image");
            put("CMA/Elf Druid", "https://api.scryfall.com/cards/tcma/11/en?format=image");
            put("CMA/Elf Warrior", "https://api.scryfall.com/cards/tcma/12/en?format=image");
            put("CMA/Gargoyle", "https://api.scryfall.com/cards/tcma/19/en?format=image");
            put("CMA/Phyrexian Germ", "https://api.scryfall.com/cards/tcma/4/en?format=image");
            put("CMA/Kithkin Soldier", "https://api.scryfall.com/cards/tcma/1/en?format=image");
            put("CMA/Knight", "https://api.scryfall.com/cards/tcma/2/en?format=image");
            put("CMA/Saproling", "https://api.scryfall.com/cards/tcma/13/en?format=image");
            put("CMA/Spider", "https://api.scryfall.com/cards/tcma/14/en?format=image");
            put("CMA/Spirit", "https://api.scryfall.com/cards/tcma/3/en?format=image");
            put("CMA/Treefolk", "https://api.scryfall.com/cards/tcma/15/en?format=image");
            put("CMA/Wolf/1", "https://api.scryfall.com/cards/tcma/16/en?format=image");
            put("CMA/Wolf/2", "https://api.scryfall.com/cards/tcma/17/en?format=image");
            put("CMA/Zombie", "https://api.scryfall.com/cards/tcma/5/en?format=image");

            // M15
            put("M15/Emblem Ajani", "https://api.scryfall.com/cards/tm15/13/en?format=image");
            put("M15/Beast/1", "https://api.scryfall.com/cards/tm15/5/en?format=image");
            put("M15/Beast/2", "https://api.scryfall.com/cards/tm15/9/en?format=image");
            put("M15/Dragon", "https://api.scryfall.com/cards/tm15/7/en?format=image");
            put("M15/Emblem Garruk", "https://api.scryfall.com/cards/tm15/14/en?format=image");
            put("M15/Goblin", "https://api.scryfall.com/cards/tm15/8/en?format=image");
            put("M15/Insect", "https://api.scryfall.com/cards/tm15/10/en?format=image");
            put("M15/Land Mine", "https://api.scryfall.com/cards/tm15/12/en?format=image");
            put("M15/Sliver", "https://api.scryfall.com/cards/tm15/1/en?format=image");
            put("M15/Soldier", "https://api.scryfall.com/cards/tm15/2/en?format=image");
            put("M15/Spirit", "https://api.scryfall.com/cards/tm15/3/en?format=image");
            put("M15/Squid", "https://api.scryfall.com/cards/tm15/4/en?format=image");
            put("M15/Treefolk Warrior", "https://api.scryfall.com/cards/tm15/11/en?format=image");
            put("M15/Zombie", "https://api.scryfall.com/cards/tm15/6/en?format=image");

            // M14
            put("M14/Angel", "https://api.scryfall.com/cards/tm14/2/en?format=image");
            put("M14/Beast", "https://api.scryfall.com/cards/tm14/9/en?format=image");
            put("M14/Cat", "https://api.scryfall.com/cards/tm14/3/en?format=image");
            put("M14/Dragon", "https://api.scryfall.com/cards/tm14/6/en?format=image");
            put("M14/Elemental/1", "https://api.scryfall.com/cards/tm14/7/en?format=image");
            put("M14/Elemental/2", "https://api.scryfall.com/cards/tm14/8/en?format=image");
            put("M14/Emblem Garruk", "https://api.scryfall.com/cards/tm14/13/en?format=image");
            put("M14/Goat", "https://api.scryfall.com/cards/tm14/4/en?format=image");
            put("M14/Emblem Liliana", "https://api.scryfall.com/cards/tm14/12/en?format=image");
            put("M14/Saproling", "https://api.scryfall.com/cards/tm14/10/en?format=image");
            put("M14/Sliver", "https://api.scryfall.com/cards/tm14/1/en?format=image");
            put("M14/Wolf", "https://api.scryfall.com/cards/tm14/11/en?format=image");
            put("M14/Zombie", "https://api.scryfall.com/cards/tm14/5/en?format=image");

            // M13
            put("M13/Beast", "https://api.scryfall.com/cards/tm13/8/en?format=image");
            put("M13/Cat", "https://api.scryfall.com/cards/tm13/1/en?format=image");
            put("M13/Drake", "https://api.scryfall.com/cards/tm13/4/en?format=image");
            put("M13/Goat", "https://api.scryfall.com/cards/tm13/2/en?format=image");
            put("M13/Goblin", "https://api.scryfall.com/cards/tm13/6/en?format=image");
            put("M13/Hellion", "https://api.scryfall.com/cards/tm13/7/en?format=image");
            put("M13/Emblem Liliana", "https://api.scryfall.com/cards/tm13/11/en?format=image");
            put("M13/Saproling", "https://api.scryfall.com/cards/tm13/9/en?format=image");
            put("M13/Soldier", "https://api.scryfall.com/cards/tm13/3/en?format=image");
            put("M13/Wurm", "https://api.scryfall.com/cards/tm13/10/en?format=image");
            put("M13/Zombie", "https://api.scryfall.com/cards/tm13/5/en?format=image");

            // M12
            put("M12/Beast", "https://api.scryfall.com/cards/tm12/4/en?format=image");
            put("M12/Bird", "https://api.scryfall.com/cards/tm12/1/en?format=image");
            put("M12/Pentavite", "https://api.scryfall.com/cards/tm12/7/en?format=image");
            put("M12/Saproling", "https://api.scryfall.com/cards/tm12/5/en?format=image");
            put("M12/Soldier", "https://api.scryfall.com/cards/tm12/2/en?format=image");
            put("M12/Wurm", "https://api.scryfall.com/cards/tm12/6/en?format=image");
            put("M12/Zombie", "https://api.scryfall.com/cards/tm12/3/en?format=image");

            // M11
            put("M11/Avatar", "https://api.scryfall.com/cards/tm11/1/en?format=image");
            put("M11/Beast", "https://api.scryfall.com/cards/tm11/4/en?format=image");
            put("M11/Bird", "https://api.scryfall.com/cards/tm11/2/en?format=image");
            put("M11/Ooze/1", "https://api.scryfall.com/cards/tm11/6/en?format=image");
            put("M11/Ooze/2", "https://api.scryfall.com/cards/tm11/5/en?format=image");
            put("M11/Zombie", "https://api.scryfall.com/cards/tm11/3/en?format=image");

            // M10
            put("M10/Avatar", "https://api.scryfall.com/cards/tm10/1/en?format=image");
            put("M10/Beast", "https://api.scryfall.com/cards/tm10/5/en?format=image");
            put("M10/Gargoyle", "https://api.scryfall.com/cards/tm10/8/en?format=image");
            put("M10/Goblin", "https://api.scryfall.com/cards/tm10/4/en?format=image");
            put("M10/Insect", "https://api.scryfall.com/cards/tm10/6/en?format=image");
            put("M10/Soldier", "https://api.scryfall.com/cards/tm10/2/en?format=image");
            put("M10/Wolf", "https://api.scryfall.com/cards/tm10/7/en?format=image");
            put("M10/Zombie", "https://api.scryfall.com/cards/tm10/3/en?format=image");

            // ARB
            put("ARB/Bird Soldier", "https://api.scryfall.com/cards/tarb/1/en?format=image");
            put("ARB/Dragon", "https://api.scryfall.com/cards/tarb/3/en?format=image");
            put("ARB/Lizard", "https://api.scryfall.com/cards/tarb/2/en?format=image");
            put("ARB/Zombie Wizard", "https://api.scryfall.com/cards/tarb/4/en?format=image");

            // DDC
            put("DDC/Demon", "https://api.scryfall.com/cards/tddc/2/en?format=image");
            put("DDC/Spirit", "https://api.scryfall.com/cards/tddc/1/en?format=image");
            put("DDC/Thrull", "https://api.scryfall.com/cards/tddc/3/en?format=image");

            // CON
            put("CON/Angel", "https://api.scryfall.com/cards/tcon/1/en?format=image");
            put("CON/Elemental", "https://api.scryfall.com/cards/tcon/2/en?format=image");

            // ALA
            put("ALA/Beast", "https://api.scryfall.com/cards/tala/10/en?format=image");
            put("ALA/Dragon", "https://api.scryfall.com/cards/tala/6/en?format=image");
            put("ALA/Goblin", "https://api.scryfall.com/cards/tala/7/en?format=image");
            put("ALA/Homunculus", "https://api.scryfall.com/cards/tala/2/en?format=image");
            put("ALA/Ooze", "https://api.scryfall.com/cards/tala/8/en?format=image");
            put("ALA/Saproling", "https://api.scryfall.com/cards/tala/9/en?format=image");
            put("ALA/Skeleton", "https://api.scryfall.com/cards/tala/4/en?format=image");
            put("ALA/Soldier", "https://api.scryfall.com/cards/tala/1/en?format=image");
            put("ALA/Thopter", "https://api.scryfall.com/cards/tala/3/en?format=image");
            put("ALA/Zombie", "https://api.scryfall.com/cards/tala/5/en?format=image");

            // MOR
            put("MOR/Faerie Rogue", "https://api.scryfall.com/cards/tmor/2/en?format=image");
            put("MOR/Giant Warrior", "https://api.scryfall.com/cards/tmor/1/en?format=image");
            put("MOR/Treefolk Shaman", "https://api.scryfall.com/cards/tmor/3/en?format=image");

            // DD1
            put("DD1/Elemental", "https://api.scryfall.com/cards/tdd1/T1/en?format=image");
            put("DD1/Elf Warrior", "https://api.scryfall.com/cards/tdd1/T2/en?format=image");
            put("DD1/Goblin", "https://api.scryfall.com/cards/tdd1/T3/en?format=image");

            // 10E
            put("10E/Dragon", "https://api.scryfall.com/cards/t10e/3/en?format=image");
            put("10E/Goblin", "https://api.scryfall.com/cards/t10e/4/en?format=image");
            put("10E/Saproling", "https://api.scryfall.com/cards/t10e/5/en?format=image");
            put("10E/Soldier", "https://api.scryfall.com/cards/t10e/1/en?format=image");
            put("10E/Wasp", "https://api.scryfall.com/cards/t10e/6/en?format=image");
            put("10E/Zombie", "https://api.scryfall.com/cards/t10e/2/en?format=image");

            // ZEN
            put("ZEN/Angel", "https://api.scryfall.com/cards/tzen/1/en?format=image");
            put("ZEN/Beast", "https://api.scryfall.com/cards/tzen/9/en?format=image");
            put("ZEN/Bird", "https://api.scryfall.com/cards/tzen/2/en?format=image");
            put("ZEN/Elemental", "https://api.scryfall.com/cards/tzen/8/en?format=image");
            put("ZEN/Illusion", "https://api.scryfall.com/cards/tzen/4/en?format=image");
            put("ZEN/Kor Soldier", "https://api.scryfall.com/cards/tzen/3/en?format=image");
            put("ZEN/Merfolk", "https://api.scryfall.com/cards/tzen/5/en?format=image");
            put("ZEN/Snake", "https://api.scryfall.com/cards/tzen/10/en?format=image");
            put("ZEN/Vampire", "https://api.scryfall.com/cards/tzen/6/en?format=image");
            put("ZEN/Wolf", "https://api.scryfall.com/cards/tzen/11/en?format=image");
            put("ZEN/Zombie Giant", "https://api.scryfall.com/cards/tzen/7/en?format=image");

            // WWK
            put("WWK/Construct", "https://api.scryfall.com/cards/twwk/6/en?format=image");
            put("WWK/Dragon", "https://api.scryfall.com/cards/twwk/2/en?format=image");
            put("WWK/Elephant", "https://api.scryfall.com/cards/twwk/4/en?format=image");
            put("WWK/Ogre", "https://api.scryfall.com/cards/twwk/3/en?format=image");
            put("WWK/Plant", "https://api.scryfall.com/cards/twwk/5/en?format=image");
            put("WWK/Soldier Ally", "https://api.scryfall.com/cards/twwk/1/en?format=image");

            // ROE
            put("ROE/Eldrazi Spawn/1", "https://api.scryfall.com/cards/troe/1a/en?format=image");
            put("ROE/Eldrazi Spawn/2", "https://api.scryfall.com/cards/troe/1b/en?format=image");
            put("ROE/Eldrazi Spawn/3", "https://api.scryfall.com/cards/troe/1c/en?format=image");
            put("ROE/Elemental", "https://api.scryfall.com/cards/troe/2/en?format=image");
            put("ROE/Hellion", "https://api.scryfall.com/cards/troe/3/en?format=image");
            put("ROE/Ooze", "https://api.scryfall.com/cards/troe/4/en?format=image");
            put("ROE/Tuktuk the Returned", "https://api.scryfall.com/cards/troe/5/en?format=image");

            // DDF
            put("DDF/Soldier", "https://api.scryfall.com/cards/tddf/1/en?format=image");

            // DDE
            put("DDE/Hornet", "https://api.scryfall.com/cards/tdde/1/en?format=image");
            put("DDE/Phyrexian Minion", "https://api.scryfall.com/cards/tdde/2/en?format=image");
            put("DDE/Saproling", "https://api.scryfall.com/cards/tdde/3/en?format=image");

            // DDD
            put("DDD/Beast/1", "https://api.scryfall.com/cards/tddd/T1/en?format=image");
            put("DDD/Beast/2", "https://api.scryfall.com/cards/tddd/T2/en?format=image");
            put("DDD/Elephant", "https://api.scryfall.com/cards/tddd/T3/en?format=image");

            // SOM
            put("SOM/Cat", "https://api.scryfall.com/cards/tsom/1/en?format=image");
            put("SOM/Goblin", "https://api.scryfall.com/cards/tsom/3/en?format=image");
            put("SOM/Golem", "https://api.scryfall.com/cards/tsom/6/en?format=image");
            put("SOM/Phyrexian Insect", "https://api.scryfall.com/cards/tsom/4/en?format=image");
            put("SOM/Myr", "https://api.scryfall.com/cards/tsom/7/en?format=image");
            put("SOM/Soldier", "https://api.scryfall.com/cards/tsom/2/en?format=image");
            put("SOM/Wolf", "https://api.scryfall.com/cards/tsom/5/en?format=image");
            put("SOM/Phyrexian Wurm/1", "https://api.scryfall.com/cards/tsom/8/en?format=image");
            put("SOM/Phyrexian Wurm/2", "https://api.scryfall.com/cards/tsom/9/en?format=image");

            // MBS
            put("MBS/Phyrexian Germ", "https://api.scryfall.com/cards/tmbs/1/en?format=image");
            put("MBS/Golem", "https://api.scryfall.com/cards/tmbs/3/en?format=image");
            put("MBS/Phyrexian Horror", "https://api.scryfall.com/cards/tmbs/4/en?format=image");
            put("MBS/Thopter", "https://api.scryfall.com/cards/tmbs/5/en?format=image");
            put("MBS/Zombie", "https://api.scryfall.com/cards/tmbs/2/en?format=image");

            // DDG
            put("DDG/Goblin", "https://api.scryfall.com/cards/tddg/1/en?format=image");

            // NPH
            put("NPH/Beast", "https://api.scryfall.com/cards/tnph/1/en?format=image");
            put("NPH/Phyrexian Goblin", "https://api.scryfall.com/cards/tnph/2/en?format=image");
            put("NPH/Phyrexian Golem", "https://api.scryfall.com/cards/tnph/3/en?format=image");
            put("NPH/Phyrexian Myr", "https://api.scryfall.com/cards/tnph/4/en?format=image");

            // DDH
            put("DDH/Griffin", "https://api.scryfall.com/cards/tddh/1/en?format=image");
            put("DDH/Saproling", "https://api.scryfall.com/cards/tddh/2/en?format=image");

            // ISD
            put("ISD/Angel", "https://api.scryfall.com/cards/tisd/1/en?format=image");
            put("ISD/Demon", "https://api.scryfall.com/cards/tisd/4/en?format=image");
            put("ISD/Homunculus", "https://api.scryfall.com/cards/tisd/3/en?format=image");
            put("ISD/Ooze", "https://api.scryfall.com/cards/tisd/10/en?format=image");
            put("ISD/Spider", "https://api.scryfall.com/cards/tisd/11/en?format=image");
            put("ISD/Spirit", "https://api.scryfall.com/cards/tisd/2/en?format=image");
            put("ISD/Vampire", "https://api.scryfall.com/cards/tisd/5/en?format=image");
            put("ISD/Wolf/1", "https://api.scryfall.com/cards/tisd/6/en?format=image");
            put("ISD/Wolf/2", "https://api.scryfall.com/cards/tisd/12/en?format=image");
            put("ISD/Zombie/1", "https://api.scryfall.com/cards/tisd/7/en?format=image");
            put("ISD/Zombie/2", "https://api.scryfall.com/cards/tisd/8/en?format=image");
            put("ISD/Zombie/3", "https://api.scryfall.com/cards/tisd/9/en?format=image");

            // DKA
            put("DKA/Human", "https://api.scryfall.com/cards/tdka/1/en?format=image");
            put("DKA/Emblem Sorin", "https://api.scryfall.com/cards/tdka/3/en?format=image");
            put("DKA/Vampire", "https://api.scryfall.com/cards/tdka/2/en?format=image");

            // DDI
            put("DDI/Emblem Koth", "https://api.scryfall.com/cards/tddi/2/en?format=image");
            put("DDI/Emblem Venser", "https://api.scryfall.com/cards/tddi/1/en?format=image");

            // AVR
            put("AVR/Angel", "https://api.scryfall.com/cards/tavr/1/en?format=image");
            put("AVR/Demon", "https://api.scryfall.com/cards/tavr/5/en?format=image");
            put("AVR/Human/1", "https://api.scryfall.com/cards/tavr/7/en?format=image");
            put("AVR/Human/2", "https://api.scryfall.com/cards/tavr/2/en?format=image");
            put("AVR/Spirit/1", "https://api.scryfall.com/cards/tavr/4/en?format=image");
            put("AVR/Spirit/2", "https://api.scryfall.com/cards/tavr/3/en?format=image");
            put("AVR/Emblem Tamiyo", "https://api.scryfall.com/cards/tavr/8/en?format=image");
            put("AVR/Zombie", "https://api.scryfall.com/cards/tavr/6/en?format=image");

            // DDJ
            put("DDJ/Saproling", "https://api.scryfall.com/cards/tddj/1/en?format=image");

            // RTR
            put("RTR/Assassin", "https://api.scryfall.com/cards/trtr/4/en?format=image");
            put("RTR/Bird", "https://api.scryfall.com/cards/trtr/1/en?format=image");
            put("RTR/Centaur", "https://api.scryfall.com/cards/trtr/7/en?format=image");
            put("RTR/Dragon", "https://api.scryfall.com/cards/trtr/5/en?format=image");
            put("RTR/Elemental", "https://api.scryfall.com/cards/trtr/12/en?format=image");
            put("RTR/Goblin", "https://api.scryfall.com/cards/trtr/6/en?format=image");
            put("RTR/Knight", "https://api.scryfall.com/cards/trtr/2/en?format=image");
            put("RTR/Ooze", "https://api.scryfall.com/cards/trtr/8/en?format=image");
            put("RTR/Rhino", "https://api.scryfall.com/cards/trtr/9/en?format=image");
            put("RTR/Saproling", "https://api.scryfall.com/cards/trtr/10/en?format=image");
            put("RTR/Soldier", "https://api.scryfall.com/cards/trtr/3/en?format=image");
            put("RTR/Wurm", "https://api.scryfall.com/cards/trtr/11/en?format=image");

            // GTC
            put("GTC/Angel", "https://api.scryfall.com/cards/tgtc/1/en?format=image");
            put("GTC/Cleric", "https://api.scryfall.com/cards/tgtc/4/en?format=image");
            put("GTC/Emblem Domri", "https://api.scryfall.com/cards/tgtc/8/en?format=image");
            put("GTC/Frog Lizard", "https://api.scryfall.com/cards/tgtc/3/en?format=image");
            put("GTC/Horror", "https://api.scryfall.com/cards/tgtc/5/en?format=image");
            put("GTC/Rat", "https://api.scryfall.com/cards/tgtc/2/en?format=image");
            put("GTC/Soldier", "https://api.scryfall.com/cards/tgtc/6/en?format=image");
            put("GTC/Spirit", "https://api.scryfall.com/cards/tgtc/7/en?format=image");

            // DDK
            put("DDK/Spirit", "https://api.scryfall.com/cards/tddk/1/en?format=image");

            // DGM
            put("DGM/Elemental", "https://api.scryfall.com/cards/tdgm/1/en?format=image");

            // DDL
            put("DDL/Beast", "https://api.scryfall.com/cards/tddl/2/en?format=image");
            put("DDL/Griffin", "https://api.scryfall.com/cards/tddl/1/en?format=image");

            // THS
            put("THS/Bird", "https://api.scryfall.com/cards/tths/4/en?format=image");
            put("THS/Boar", "https://api.scryfall.com/cards/tths/8/en?format=image");
            put("THS/Cleric", "https://api.scryfall.com/cards/tths/1/en?format=image");
            put("THS/Elemental", "https://api.scryfall.com/cards/tths/5/en?format=image");
            put("THS/Emblem Elspeth", "https://api.scryfall.com/cards/tths/11/en?format=image");
            put("THS/Golem", "https://api.scryfall.com/cards/tths/10/en?format=image");
            put("THS/Harpy", "https://api.scryfall.com/cards/tths/6/en?format=image");
            put("THS/Satyr", "https://api.scryfall.com/cards/tths/9/en?format=image");
            put("THS/Soldier/1", "https://api.scryfall.com/cards/tths/2/en?format=image");
            put("THS/Soldier/2", "https://api.scryfall.com/cards/tths/3/en?format=image");
            put("THS/Soldier/3", "https://api.scryfall.com/cards/tths/7/en?format=image");

            // EVE
            put("EVE/Beast", "https://api.scryfall.com/cards/teve/3/en?format=image");
            put("EVE/Bird", "https://api.scryfall.com/cards/teve/2/en?format=image");
            put("EVE/Elemental", "https://api.scryfall.com/cards/teve/5/en?format=image");
            put("EVE/Goat", "https://api.scryfall.com/cards/teve/1/en?format=image");
            put("EVE/Goblin Soldier", "https://api.scryfall.com/cards/teve/7/en?format=image");
            put("EVE/Spirit", "https://api.scryfall.com/cards/teve/4/en?format=image");
            put("EVE/Worm", "https://api.scryfall.com/cards/teve/6/en?format=image");

            // BNG
            put("BNG/Bird/1", "https://api.scryfall.com/cards/tbng/4/en?format=image");
            put("BNG/Bird/2", "https://api.scryfall.com/cards/tbng/1/en?format=image");
            put("BNG/Cat Soldier", "https://api.scryfall.com/cards/tbng/2/en?format=image");
            put("BNG/Centaur", "https://api.scryfall.com/cards/tbng/8/en?format=image");
            put("BNG/Elemental", "https://api.scryfall.com/cards/tbng/7/en?format=image");
            put("BNG/Gold", "https://api.scryfall.com/cards/tbng/10/en?format=image");
            put("BNG/Emblem Kiora", "https://api.scryfall.com/cards/tbng/11/en?format=image");
            put("BNG/Kraken", "https://api.scryfall.com/cards/tbng/5/en?format=image");
            put("BNG/Soldier", "https://api.scryfall.com/cards/tbng/3/en?format=image");
            put("BNG/Wolf", "https://api.scryfall.com/cards/tbng/9/en?format=image");
            put("BNG/Zombie", "https://api.scryfall.com/cards/tbng/6/en?format=image");

            // DDM
            put("DDM/Assassin", "https://api.scryfall.com/cards/tddm/1/en?format=image");

            // JOU
            put("JOU/Hydra", "https://api.scryfall.com/cards/tjou/4/en?format=image");
            put("JOU/Minotaur", "https://api.scryfall.com/cards/tjou/3/en?format=image");
            put("JOU/Snake", "https://api.scryfall.com/cards/tjou/6/en?format=image");
            put("JOU/Sphinx", "https://api.scryfall.com/cards/tjou/1/en?format=image");
            put("JOU/Spider", "https://api.scryfall.com/cards/tjou/5/en?format=image");
            put("JOU/Zombie", "https://api.scryfall.com/cards/tjou/2/en?format=image");

            // MD1
            put("MD1/Emblem Elspeth", "https://api.scryfall.com/cards/tmd1/4/en?format=image");
            put("MD1/Phyrexian Myr", "https://api.scryfall.com/cards/tmd1/3/en?format=image");
            put("MD1/Soldier", "https://api.scryfall.com/cards/tmd1/1/en?format=image");
            put("MD1/Spirit", "https://api.scryfall.com/cards/tmd1/2/en?format=image");

            // CNS
            put("CNS/Construct", "https://api.scryfall.com/cards/tcns/8/en?format=image");
            put("CNS/Emblem Dack", "https://api.scryfall.com/cards/tcns/9/en?format=image");
            put("CNS/Demon", "https://api.scryfall.com/cards/tcns/2/en?format=image");
            put("CNS/Elephant", "https://api.scryfall.com/cards/tcns/5/en?format=image");
            put("CNS/Spirit", "https://api.scryfall.com/cards/tcns/1/en?format=image");
            put("CNS/Squirrel", "https://api.scryfall.com/cards/tcns/6/en?format=image");
            put("CNS/Wolf", "https://api.scryfall.com/cards/tcns/7/en?format=image");
            put("CNS/Zombie", "https://api.scryfall.com/cards/tcns/3/en?format=image");

            // DDN
            put("DDN/Goblin", "https://api.scryfall.com/cards/ddn/82/en?format=image");

            // KTK
            put("KTK/Bear", "https://api.scryfall.com/cards/tktk/8/en?format=image");
            put("KTK/Bird", "https://api.scryfall.com/cards/tktk/1/en?format=image");
            put("KTK/Goblin", "https://api.scryfall.com/cards/tktk/7/en?format=image");
            put("KTK/Emblem Sarkhan", "https://api.scryfall.com/cards/tktk/12/en?format=image");
            put("KTK/Snake", "https://api.scryfall.com/cards/tktk/9/en?format=image");
            put("KTK/Emblem Sorin", "https://api.scryfall.com/cards/tktk/13/en?format=image");
            put("KTK/Spirit", "https://api.scryfall.com/cards/tktk/2/en?format=image");
            put("KTK/Spirit Warrior", "https://api.scryfall.com/cards/tktk/10/en?format=image");
            put("KTK/Vampire", "https://api.scryfall.com/cards/tktk/5/en?format=image");
            put("KTK/Warrior/1", "https://api.scryfall.com/cards/tktk/3/en?format=image");
            put("KTK/Warrior/2", "https://api.scryfall.com/cards/tktk/4/en?format=image");
            put("KTK/Zombie", "https://api.scryfall.com/cards/tktk/6/en?format=image");

            // EVG
            put("EVG/Elemental", "https://api.scryfall.com/cards/tevg/1/en?format=image");
            put("EVG/Elf Warrior", "https://api.scryfall.com/cards/tevg/2/en?format=image");
            put("EVG/Goblin", "https://api.scryfall.com/cards/tevg/3/en?format=image");

            // GVL
            put("GVL/Bat", "https://api.scryfall.com/cards/tgvl/11/en?format=image");
            put("GVL/Beast/1", "https://api.scryfall.com/cards/tgvl/8/en?format=image");
            put("GVL/Beast/2", "https://api.scryfall.com/cards/tgvl/9/en?format=image");
            put("GVL/Elephant", "https://api.scryfall.com/cards/tgvl/10/en?format=image");

            // DVD
            put("DVD/Demon", "https://api.scryfall.com/cards/tdvd/6/en?format=image");
            put("DVD/Spirit", "https://api.scryfall.com/cards/tdvd/5/en?format=image");
            put("DVD/Thrull", "https://api.scryfall.com/cards/tdvd/7/en?format=image");

            // FRF
            put("FRF/Monk", "https://api.scryfall.com/cards/tfrf/1/en?format=image");
            put("FRF/Spirit", "https://api.scryfall.com/cards/tfrf/2/en?format=image");
            put("FRF/Warrior", "https://api.scryfall.com/cards/tfrf/3/en?format=image");

            // DDO
            put("DDO/Kraken", "https://api.scryfall.com/cards/ddo/67/en?format=image");
            put("DDO/Soldier", "https://api.scryfall.com/cards/ddo/66/en?format=image");

            // MM2
            put("MM2/Eldrazi Spawn/1", "https://api.scryfall.com/cards/tmm2/1/en?format=image");
            put("MM2/Eldrazi Spawn/2", "https://api.scryfall.com/cards/tmm2/2/en?format=image");
            put("MM2/Eldrazi Spawn/3", "https://api.scryfall.com/cards/tmm2/3/en?format=image");
            put("MM2/Elephant", "https://api.scryfall.com/cards/tmm2/9/en?format=image");
            put("MM2/Faerie Rogue", "https://api.scryfall.com/cards/tmm2/6/en?format=image");
            put("MM2/Phyrexian Germ", "https://api.scryfall.com/cards/tmm2/7/en?format=image");
            put("MM2/Golem", "https://api.scryfall.com/cards/tmm2/15/en?format=image");
            put("MM2/Insect", "https://api.scryfall.com/cards/tmm2/10/en?format=image");
            put("MM2/Myr", "https://api.scryfall.com/cards/tmm2/16/en?format=image");
            put("MM2/Saproling", "https://api.scryfall.com/cards/tmm2/11/en?format=image");
            put("MM2/Snake", "https://api.scryfall.com/cards/tmm2/12/en?format=image");
            put("MM2/Soldier", "https://api.scryfall.com/cards/tmm2/4/en?format=image");
            put("MM2/Spirit", "https://api.scryfall.com/cards/tmm2/5/en?format=image");
            put("MM2/Thrull", "https://api.scryfall.com/cards/tmm2/8/en?format=image");
            put("MM2/Wolf", "https://api.scryfall.com/cards/tmm2/13/en?format=image");
            put("MM2/Worm", "https://api.scryfall.com/cards/tmm2/14/en?format=image");

            // ORI
            put("ORI/Angel", "https://api.scryfall.com/cards/tori/1/en?format=image");
            put("ORI/Ashaya, the Awoken World", "https://api.scryfall.com/cards/tori/7/en?format=image");
            put("ORI/Emblem Chandra", "https://api.scryfall.com/cards/tori/14/en?format=image");
            put("ORI/Demon", "https://api.scryfall.com/cards/tori/4/en?format=image");
            put("ORI/Elemental", "https://api.scryfall.com/cards/tori/8/en?format=image");
            put("ORI/Elf Warrior", "https://api.scryfall.com/cards/tori/9/en?format=image");
            put("ORI/Goblin", "https://api.scryfall.com/cards/tori/6/en?format=image");
            put("ORI/Emblem Jace", "https://api.scryfall.com/cards/tori/12/en?format=image");
            put("ORI/Knight", "https://api.scryfall.com/cards/tori/2/en?format=image");
            put("ORI/Emblem Liliana", "https://api.scryfall.com/cards/tori/13/en?format=image");
            put("ORI/Soldier", "https://api.scryfall.com/cards/tori/3/en?format=image");
            put("ORI/Thopter/1", "https://api.scryfall.com/cards/tori/10/en?format=image");
            put("ORI/Thopter/2", "https://api.scryfall.com/cards/tori/11/en?format=image");
            put("ORI/Zombie", "https://api.scryfall.com/cards/tori/5/en?format=image");

            // DDP
            put("DDP/Eldrazi Spawn/1", "https://api.scryfall.com/cards/ddp/76/en?format=image");
            put("DDP/Eldrazi Spawn/2", "https://api.scryfall.com/cards/ddp/77/en?format=image");
            put("DDP/Eldrazi Spawn/3", "https://api.scryfall.com/cards/ddp/78/en?format=image");
            put("DDP/Hellion", "https://api.scryfall.com/cards/ddp/79/en?format=image");
            put("DDP/Plant", "https://api.scryfall.com/cards/ddp/80/en?format=image");

            // DDQ
            put("DDQ/Angel", "https://api.scryfall.com/cards/ddq/77/en?format=image");
            put("DDQ/Human", "https://api.scryfall.com/cards/ddq/78/en?format=image");
            put("DDQ/Spirit", "https://api.scryfall.com/cards/ddq/79/en?format=image");
            put("DDQ/Zombie", "https://api.scryfall.com/cards/ddq/80/en?format=image");

            // EMA
            put("EMA/Carnivore", "https://api.scryfall.com/cards/tema/7/en?format=image");
            put("EMA/Emblem Dack", "https://api.scryfall.com/cards/tema/16/en?format=image");
            put("EMA/Dragon", "https://api.scryfall.com/cards/tema/8/en?format=image");
            put("EMA/Elemental/1", "https://api.scryfall.com/cards/tema/9/en?format=image");
            put("EMA/Elemental/2", "https://api.scryfall.com/cards/tema/14/en?format=image");
            put("EMA/Elephant", "https://api.scryfall.com/cards/tema/11/en?format=image");
            put("EMA/Elf Warrior", "https://api.scryfall.com/cards/tema/12/en?format=image");
            put("EMA/Goblin", "https://api.scryfall.com/cards/tema/10/en?format=image");
            put("EMA/Goblin Soldier", "https://api.scryfall.com/cards/tema/15/en?format=image");
            put("EMA/Serf", "https://api.scryfall.com/cards/tema/5/en?format=image");
            put("EMA/Soldier", "https://api.scryfall.com/cards/tema/2/en?format=image");
            put("EMA/Spirit/1", "https://api.scryfall.com/cards/tema/1/en?format=image");
            put("EMA/Spirit/2", "https://api.scryfall.com/cards/tema/3/en?format=image");
            put("EMA/Wall", "https://api.scryfall.com/cards/tema/4/en?format=image");
            put("EMA/Wurm", "https://api.scryfall.com/cards/tema/13/en?format=image");
            put("EMA/Zombie", "https://api.scryfall.com/cards/tema/6/en?format=image");

            // V16
            put("V16/Marit Lage", "https://api.scryfall.com/cards/v16/16/en?format=image");

            // CN2
            put("CN2/Assassin", "https://api.scryfall.com/cards/tcn2/5/en?format=image");
            put("CN2/Beast", "https://api.scryfall.com/cards/tcn2/10/en?format=image");
            put("CN2/Construct", "https://api.scryfall.com/cards/tcn2/12/en?format=image");
            put("CN2/Dragon", "https://api.scryfall.com/cards/tcn2/7/en?format=image");
            put("CN2/Insect", "https://api.scryfall.com/cards/tcn2/11/en?format=image");
            put("CN2/Lizard", "https://api.scryfall.com/cards/tcn2/9/en?format=image");
            put("CN2/Soldier", "https://api.scryfall.com/cards/tcn2/2/en?format=image");
            put("CN2/Spirit", "https://api.scryfall.com/cards/tcn2/4/en?format=image");
            put("CN2/Zombie", "https://api.scryfall.com/cards/tcn2/6/en?format=image");

            // DDR
            put("DDR/Eldrazi Scion", "https://api.scryfall.com/cards/ddr/71/en?format=image");
            put("DDR/Demon", "https://api.scryfall.com/cards/ddr/72/en?format=image");
            put("DDR/Zombie Giant", "https://api.scryfall.com/cards/ddr/73/en?format=image");
            put("DDR/Elemental", "https://api.scryfall.com/cards/ddr/74/en?format=image");
            put("DDR/Plant", "https://api.scryfall.com/cards/ddr/75/en?format=image");
            put("DDR/Emblem Nixilis", "https://api.scryfall.com/cards/ddr/76/en?format=image");

            // MM3
            put("MM3/Angel", "https://api.scryfall.com/cards/tmm3/1/en?format=image");
            put("MM3/Beast/1", "https://api.scryfall.com/cards/tmm3/9/en?format=image");
            put("MM3/Beast/2", "https://api.scryfall.com/cards/tmm3/10/en?format=image");
            put("MM3/Bird", "https://api.scryfall.com/cards/tmm3/2/en?format=image");
            put("MM3/Centaur", "https://api.scryfall.com/cards/tmm3/11/en?format=image");
            put("MM3/Emblem Domri", "https://api.scryfall.com/cards/tmm3/21/en?format=image");
            put("MM3/Dragon", "https://api.scryfall.com/cards/tmm3/7/en?format=image");
            put("MM3/Elemental", "https://api.scryfall.com/cards/tmm3/16/en?format=image");
            put("MM3/Elephant", "https://api.scryfall.com/cards/tmm3/12/en?format=image");
            put("MM3/Giant Warrior", "https://api.scryfall.com/cards/tmm3/17/en?format=image");
            put("MM3/Goblin", "https://api.scryfall.com/cards/tmm3/8/en?format=image");
            put("MM3/Goblin Warrior", "https://api.scryfall.com/cards/tmm3/18/en?format=image");
            put("MM3/Phyrexian Golem", "https://api.scryfall.com/cards/tmm3/20/en?format=image");
            put("MM3/Ooze", "https://api.scryfall.com/cards/tmm3/13/en?format=image");
            put("MM3/Saproling", "https://api.scryfall.com/cards/tmm3/14/en?format=image");
            put("MM3/Soldier/1", "https://api.scryfall.com/cards/tmm3/3/en?format=image");
            put("MM3/Soldier/2", "https://api.scryfall.com/cards/tmm3/19/en?format=image");
            put("MM3/Spider", "https://api.scryfall.com/cards/tmm3/5/en?format=image");
            put("MM3/Spirit", "https://api.scryfall.com/cards/tmm3/4/en?format=image");
            put("MM3/Wurm", "https://api.scryfall.com/cards/tmm3/15/en?format=image");
            put("MM3/Zombie", "https://api.scryfall.com/cards/tmm3/6/en?format=image");

            // DDS
            put("DDS/Beast", "https://api.scryfall.com/cards/tdds/4/en?format=image");
            put("DDS/Drake", "https://api.scryfall.com/cards/tdds/1/en?format=image");
            put("DDS/Elemental", "https://api.scryfall.com/cards/tdds/2/en?format=image");
            put("DDS/Elephant", "https://api.scryfall.com/cards/tdds/5/en?format=image");
            put("DDS/Elf Warrior", "https://api.scryfall.com/cards/tdds/6/en?format=image");
            put("DDS/Goblin", "https://api.scryfall.com/cards/tdds/3/en?format=image");
            put("DDS/Wurm", "https://api.scryfall.com/cards/tdds/7/en?format=image");

            // E01
            put("E01/Beast/1", "https://api.scryfall.com/cards/te01/4/en?format=image");
            put("E01/Beast/2", "https://api.scryfall.com/cards/te01/5/en?format=image");
            put("E01/Soldier", "https://api.scryfall.com/cards/te01/1/en?format=image");
            put("E01/Spirit", "https://api.scryfall.com/cards/te01/2/en?format=image");

            // DDT
            put("DDT/Elemental", "https://api.scryfall.com/cards/tddt/1/en?format=image");
            put("DDT/Goblin", "https://api.scryfall.com/cards/tddt/3/en?format=image");
            put("DDT/Wall", "https://api.scryfall.com/cards/tddt/2/en?format=image");

            // E02
            put("E02/Saproling", "https://api.scryfall.com/cards/e02/T1/en?format=image");

            // A25
            put("A25/Angel", "https://api.scryfall.com/cards/ta25/2/en?format=image");
            put("A25/Elf Warrior", "https://api.scryfall.com/cards/ta25/11/en?format=image");
            put("A25/Fish", "https://api.scryfall.com/cards/ta25/5/en?format=image");
            put("A25/Goblin", "https://api.scryfall.com/cards/ta25/9/en?format=image");
            put("A25/Insect", "https://api.scryfall.com/cards/ta25/12/en?format=image");
            put("A25/Kobolds of Kher Keep", "https://api.scryfall.com/cards/ta25/10/en?format=image");
            put("A25/Kraken", "https://api.scryfall.com/cards/ta25/6/en?format=image");
            put("A25/Skeleton", "https://api.scryfall.com/cards/ta25/8/en?format=image");
            put("A25/Soldier", "https://api.scryfall.com/cards/ta25/3/en?format=image");
            put("A25/Spirit/1", "https://api.scryfall.com/cards/ta25/1/en?format=image");
            put("A25/Spirit/2", "https://api.scryfall.com/cards/ta25/4/en?format=image");
            put("A25/Stangg Twin", "https://api.scryfall.com/cards/ta25/14/en?format=image");
            put("A25/Whale", "https://api.scryfall.com/cards/ta25/7/en?format=image");
            put("A25/Wolf", "https://api.scryfall.com/cards/ta25/13/en?format=image");

            // GS1
            put("GS1/Mowu", "https://api.scryfall.com/cards/gs1/T1/en?format=image");

            // GK1
            put("GK1/Centaur", "https://api.scryfall.com/cards/tgk1/10/en?format=image&face=back");
            put("GK1/Elemental", "https://api.scryfall.com/cards/tgk1/10/en?format=image");
            put("GK1/Goblin", "https://api.scryfall.com/cards/tgk1/3/en?format=image&face=back");
            put("GK1/Horror", "https://api.scryfall.com/cards/tgk1/1/en?format=image&face=back");
            put("GK1/Saproling/1", "https://api.scryfall.com/cards/tgk1/8/en?format=image");
            put("GK1/Saproling/2", "https://api.scryfall.com/cards/tgk1/5/en?format=image");
            put("GK1/Soldier", "https://api.scryfall.com/cards/tgk1/6/en?format=image");
            put("GK1/Voja", "https://api.scryfall.com/cards/tgk1/11/en?format=image");
            put("GK1/Weird", "https://api.scryfall.com/cards/tgk1/3/en?format=image");
            put("GK1/Wurm", "https://api.scryfall.com/cards/tgk1/9/en?format=image");

            // GK2
            put("GK2/Bat", "https://api.scryfall.com/cards/tgk2/3/en?format=image");
            put("GK2/Bird", "https://api.scryfall.com/cards/tgk2/1/en?format=image");
            put("GK2/Cleric", "https://api.scryfall.com/cards/tgk2/4/en?format=image");
            put("GK2/Dragon", "https://api.scryfall.com/cards/tgk2/5/en?format=image");
            put("GK2/Goblin", "https://api.scryfall.com/cards/tgk2/6/en?format=image");
            put("GK2/Ooze", "https://api.scryfall.com/cards/tgk2/9/en?format=image");
            put("GK2/Saproling", "https://api.scryfall.com/cards/tgk2/7/en?format=image");
            put("GK2/Spirit", "https://api.scryfall.com/cards/tgk2/2/en?format=image");
            put("GK2/Wurm", "https://api.scryfall.com/cards/tgk2/8/en?format=image");

            // GN2
            put("GN2/Dinosaur", "https://api.scryfall.com/cards/tgn2/3/en?format=image");
            put("GN2/Dragon/1", "https://api.scryfall.com/cards/tgn2/1/en?format=image");
            put("GN2/Dragon/2", "https://api.scryfall.com/cards/tgn2/2/en?format=image");

            // CLB
            put("CLB/Angel Warrior", "https://api.scryfall.com/cards/tclb/25/en?format=image");
            put("CLB/Beast", "https://api.scryfall.com/cards/tclb/38/en?format=image");
            put("CLB/Boar", "https://api.scryfall.com/cards/tclb/12/en?format=image");
            put("CLB/Boo", "https://api.scryfall.com/cards/tclb/9/en?format=image");
            put("CLB/Centaur", "https://api.scryfall.com/cards/tclb/39/en?format=image");
            put("CLB/Clue", "https://api.scryfall.com/cards/tclb/47/en?format=image");
            put("CLB/Construct", "https://api.scryfall.com/cards/tclb/16/en?format=image");
            put("CLB/Demon", "https://api.scryfall.com/cards/tclb/7/en?format=image");
            put("CLB/Devil", "https://api.scryfall.com/cards/tclb/10/en?format=image");
            put("CLB/Dragon/1", "https://api.scryfall.com/cards/tclb/11/en?format=image");
            put("CLB/Dragon/2", "https://api.scryfall.com/cards/tclb/33/en?format=image");
            put("CLB/Eldrazi Horror", "https://api.scryfall.com/cards/tclb/21/en?format=image");
            put("CLB/Faerie Dragon", "https://api.scryfall.com/cards/tclb/6/en?format=image");
            put("CLB/Goat", "https://api.scryfall.com/cards/tclb/1/en?format=image");
            put("CLB/Goblin", "https://api.scryfall.com/cards/tclb/34/en?format=image");
            put("CLB/Gold", "https://api.scryfall.com/cards/tclb/48/en?format=image");
            put("CLB/Horror", "https://api.scryfall.com/cards/tclb/31/en?format=image");
            put("CLB/Human", "https://api.scryfall.com/cards/tclb/26/en?format=image");
            put("CLB/Inkling", "https://api.scryfall.com/cards/tclb/45/en?format=image");
            put("CLB/Insect", "https://api.scryfall.com/cards/tclb/40/en?format=image");
            put("CLB/Knight", "https://api.scryfall.com/cards/tclb/2/en?format=image");
            put("CLB/Kobolds of Kher Keep", "https://api.scryfall.com/cards/tclb/35/en?format=image");
            put("CLB/Kor Warrior", "https://api.scryfall.com/cards/tclb/27/en?format=image");
            put("CLB/Ogre", "https://api.scryfall.com/cards/tclb/36/en?format=image");
            put("CLB/Ox", "https://api.scryfall.com/cards/tclb/13/en?format=image");
            put("CLB/Pegasus", "https://api.scryfall.com/cards/tclb/3/en?format=image");
            put("CLB/Phyrexian Beast", "https://api.scryfall.com/cards/tclb/41/en?format=image");
            put("CLB/Pirate", "https://api.scryfall.com/cards/tclb/37/en?format=image");
            put("CLB/Rabbit", "https://api.scryfall.com/cards/tclb/4/en?format=image");
            put("CLB/Emblem Rowan Kenrith", "https://api.scryfall.com/cards/tclb/49/en?format=image");
            put("CLB/Saproling", "https://api.scryfall.com/cards/tclb/14/en?format=image");
            put("CLB/Satyr", "https://api.scryfall.com/cards/tclb/46/en?format=image");
            put("CLB/Shapeshifter/1", "https://api.scryfall.com/cards/tclb/22/en?format=image");
            put("CLB/Shapeshifter/2", "https://api.scryfall.com/cards/tclb/23/en?format=image");
            put("CLB/Shapeshifter/3", "https://api.scryfall.com/cards/tclb/24/en?format=image");
            put("CLB/Shapeshifter/4", "https://api.scryfall.com/cards/tclb/28/en?format=image");
            put("CLB/Skeleton", "https://api.scryfall.com/cards/tclb/8/en?format=image");
            put("CLB/Soldier", "https://api.scryfall.com/cards/tclb/5/en?format=image");
            put("CLB/Spider", "https://api.scryfall.com/cards/tclb/42/en?format=image");
            put("CLB/Squid", "https://api.scryfall.com/cards/tclb/29/en?format=image");
            put("CLB/Squirrel", "https://api.scryfall.com/cards/tclb/15/en?format=image");
            put("CLB/Treasure", "https://api.scryfall.com/cards/tclb/17/en?format=image");
            put("CLB/Undercity", "https://api.scryfall.com/cards/tclb/20/en?format=image");
            put("CLB/Volo's Journal", "https://api.scryfall.com/cards/tclb/18/en?format=image");
            put("CLB/Warrior", "https://api.scryfall.com/cards/tclb/32/en?format=image");
            put("CLB/Emblem Will Kenrith", "https://api.scryfall.com/cards/tclb/50/en?format=image");
            put("CLB/Wizard", "https://api.scryfall.com/cards/tclb/30/en?format=image");
            put("CLB/Wolf", "https://api.scryfall.com/cards/tclb/43/en?format=image");
            put("CLB/Wurm", "https://api.scryfall.com/cards/tclb/44/en?format=image");

            // LRW
            put("LRW/Avatar", "https://api.scryfall.com/cards/tlrw/1/en?format=image");
            put("LRW/Beast", "https://api.scryfall.com/cards/tlrw/7/en?format=image");
            put("LRW/Elemental/1", "https://api.scryfall.com/cards/tlrw/8/en?format=image");
            put("LRW/Elemental/2", "https://api.scryfall.com/cards/tlrw/2/en?format=image");
            put("LRW/Elemental Shaman", "https://api.scryfall.com/cards/tlrw/6/en?format=image");
            put("LRW/Elf Warrior", "https://api.scryfall.com/cards/tlrw/9/en?format=image");
            put("LRW/Goblin Rogue", "https://api.scryfall.com/cards/tlrw/5/en?format=image");
            put("LRW/Kithkin Soldier", "https://api.scryfall.com/cards/tlrw/3/en?format=image");
            put("LRW/Merfolk Wizard", "https://api.scryfall.com/cards/tlrw/4/en?format=image");
            put("LRW/Shapeshifter", "https://api.scryfall.com/cards/tlrw/11/en?format=image");
            put("LRW/Wolf", "https://api.scryfall.com/cards/tlrw/10/en?format=image");

            // 2X2
            put("2X2/Angel", "https://api.scryfall.com/cards/t2x2/3/en?format=image");
            put("2X2/Bear", "https://api.scryfall.com/cards/t2x2/14/en?format=image");
            put("2X2/Boar", "https://api.scryfall.com/cards/t2x2/15/en?format=image");
            put("2X2/Cat Dragon", "https://api.scryfall.com/cards/t2x2/19/en?format=image");
            put("2X2/Drake", "https://api.scryfall.com/cards/t2x2/10/en?format=image");
            put("2X2/Egg", "https://api.scryfall.com/cards/t2x2/16/en?format=image");
            put("2X2/Eldrazi Scion", "https://api.scryfall.com/cards/t2x2/1/en?format=image");
            put("2X2/Elemental", "https://api.scryfall.com/cards/t2x2/13/en?format=image");
            put("2X2/Faerie Rogue", "https://api.scryfall.com/cards/t2x2/11/en?format=image");
            put("2X2/Knight", "https://api.scryfall.com/cards/t2x2/5/en?format=image");
            put("2X2/Emblem Liliana", "https://api.scryfall.com/cards/t2x2/23/en?format=image");
            put("2X2/Monk", "https://api.scryfall.com/cards/t2x2/6/en?format=image");
            put("2X2/Phyrexian Golem", "https://api.scryfall.com/cards/t2x2/21/en?format=image");
            put("2X2/Saproling", "https://api.scryfall.com/cards/t2x2/17/en?format=image");
            put("2X2/Soldier", "https://api.scryfall.com/cards/t2x2/7/en?format=image");
            put("2X2/Spider", "https://api.scryfall.com/cards/t2x2/18/en?format=image");
            put("2X2/Spirit/1", "https://api.scryfall.com/cards/t2x2/2/en?format=image");
            put("2X2/Spirit/2", "https://api.scryfall.com/cards/t2x2/8/en?format=image");
            put("2X2/Treasure", "https://api.scryfall.com/cards/t2x2/22/en?format=image");
            put("2X2/Vampire", "https://api.scryfall.com/cards/t2x2/9/en?format=image");
            put("2X2/Worm", "https://api.scryfall.com/cards/t2x2/20/en?format=image");
            put("2X2/Emblem Wrenn", "https://api.scryfall.com/cards/t2x2/24/en?format=image");
            put("2X2/Zombie", "https://api.scryfall.com/cards/t2x2/12/en?format=image");

            // CC2
            put("CC2/Snake", "https://api.scryfall.com/cards/cc2/9/en?format=image&face=front");
            put("CC2/Zombie", "https://api.scryfall.com/cards/cc2/9/en?format=image&face=back");

            // DMU
            put("DMU/Emblem Ajani", "https://api.scryfall.com/cards/tdmu/25/en?format=image");
            // TODO: KarnLivingLegacyEmblem don't have official emblem card, so it must be replaced by default
            put("DMU/Beast", "https://api.scryfall.com/cards/tdmu/16/en?format=image");
            put("DMU/Bird/1", "https://api.scryfall.com/cards/tdmu/6/en?format=image");
            put("DMU/Bird/2", "https://api.scryfall.com/cards/tdmu/2/en?format=image");
            put("DMU/Dragon", "https://api.scryfall.com/cards/tdmu/10/en?format=image");
            put("DMU/Elemental", "https://api.scryfall.com/cards/tdmu/11/en?format=image");
            put("DMU/Goblin", "https://api.scryfall.com/cards/tdmu/12/en?format=image");
            put("DMU/Emblem Jaya", "https://api.scryfall.com/cards/tdmu/26/en?format=image");
            put("DMU/Monk", "https://api.scryfall.com/cards/tdmu/14/en?format=image");
            put("DMU/Ornithopter", "https://api.scryfall.com/cards/tdmu/22/en?format=image");
            put("DMU/Phyrexian", "https://api.scryfall.com/cards/tdmu/8/en?format=image");
            put("DMU/Powerstone", "https://api.scryfall.com/cards/tdmu/23/en?format=image");
            put("DMU/Saproling", "https://api.scryfall.com/cards/tdmu/18/en?format=image");
            put("DMU/Soldier", "https://api.scryfall.com/cards/tdmu/4/en?format=image");

            // DMC
            put("DMC/Angel", "https://api.scryfall.com/cards/tdmu/1/en?format=image");
            put("DMC/Badger", "https://api.scryfall.com/cards/tdmu/15/en?format=image");
            put("DMC/Bear", "https://api.scryfall.com/cards/tdmc/7/en?format=image");
            put("DMC/Cat Warrior", "https://api.scryfall.com/cards/tdmu/17/en?format=image");
            put("DMC/Egg", "https://api.scryfall.com/cards/tdmc/8/en?format=image");
            put("DMC/Elephant", "https://api.scryfall.com/cards/tdmc/9/en?format=image");
            put("DMC/Griffin", "https://api.scryfall.com/cards/tdmc/1/en?format=image");
            put("DMC/Human", "https://api.scryfall.com/cards/tdmc/2/en?format=image");
            put("DMC/Hydra", "https://api.scryfall.com/cards/tdmc/10/en?format=image");
            put("DMC/Insect", "https://api.scryfall.com/cards/tdmu/7/en?format=image");
            put("DMC/Kavu", "https://api.scryfall.com/cards/tdmc/12/en?format=image");
            put("DMC/Knight/1", "https://api.scryfall.com/cards/tdmc/3/en?format=image");
            put("DMC/Knight/2", "https://api.scryfall.com/cards/tdmu/3/en?format=image");
            put("DMC/Kobolds of Kher Keep", "https://api.scryfall.com/cards/tdmu/13/en?format=image");
            put("DMC/Merfolk", "https://api.scryfall.com/cards/tdmu/5/en?format=image");
            put("DMC/Ragavan", "https://api.scryfall.com/cards/tdmc/6/en?format=image");
            put("DMC/Sand Warrior", "https://api.scryfall.com/cards/tdmu/20/en?format=image");
            put("DMC/Snake", "https://api.scryfall.com/cards/tdmc/11/en?format=image");
            put("DMC/Stangg Twin", "https://api.scryfall.com/cards/tdmu/21/en?format=image");
            put("DMC/Treasure", "https://api.scryfall.com/cards/tdmu/24/en?format=image");
            put("DMC/Warrior", "https://api.scryfall.com/cards/tdmc/4/en?format=image");
            put("DMC/Wurm", "https://api.scryfall.com/cards/tdmu/19/en?format=image");
            put("DMC/Zombie", "https://api.scryfall.com/cards/tdmu/9/en?format=image");
            put("DMC/Zombie Knight", "https://api.scryfall.com/cards/tdmc/5/en?format=image");

            // GN3
            put("GN3/Angel", "https://api.scryfall.com/cards/tgn3/1/en?format=image");
            put("GN3/Bird Illusion", "https://api.scryfall.com/cards/tgn3/4/en?format=image");
            put("GN3/Demon", "https://api.scryfall.com/cards/tgn3/6/en?format=image");
            put("GN3/Dragon", "https://api.scryfall.com/cards/tgn3/8/en?format=image");
            put("GN3/Drake", "https://api.scryfall.com/cards/tgn3/5/en?format=image");
            put("GN3/Elf Warrior", "https://api.scryfall.com/cards/tgn3/9/en?format=image");
            put("GN3/Human Soldier", "https://api.scryfall.com/cards/tgn3/2/en?format=image");
            put("GN3/Soldier", "https://api.scryfall.com/cards/tgn3/3/en?format=image");
            put("GN3/Treasure", "https://api.scryfall.com/cards/tgn3/10/en?format=image");
            put("GN3/Zombie", "https://api.scryfall.com/cards/tgn3/7/en?format=image");

            // 40K
            put("40K/Astartes Warrior/1", "https://api.scryfall.com/cards/t40k/12/en?format=image");
            put("40K/Astartes Warrior/2", "https://api.scryfall.com/cards/t40k/1/en?format=image");
            put("40K/Blue Horror", "https://api.scryfall.com/cards/t40k/20/en?format=image");
            put("40K/Cherubael", "https://api.scryfall.com/cards/t40k/13/en?format=image");
            put("40K/Clue", "https://api.scryfall.com/cards/t40k/21/en?format=image");
            put("40K/Insect", "https://api.scryfall.com/cards/t40k/22/en?format=image");
            put("40K/Necron Warrior", "https://api.scryfall.com/cards/t40k/14/en?format=image");
            put("40K/Plaguebearer of Nurgle", "https://api.scryfall.com/cards/t40k/15/en?format=image");
            put("40K/Robot", "https://api.scryfall.com/cards/t40k/23/en?format=image");
            put("40K/Soldier/1", "https://api.scryfall.com/cards/t40k/2/en?format=image");
            put("40K/Soldier/2", "https://api.scryfall.com/cards/t40k/3/en?format=image");
            put("40K/Soldier/3", "https://api.scryfall.com/cards/t40k/4/en?format=image");
            put("40K/Spawn", "https://api.scryfall.com/cards/t40k/16/en?format=image");
            put("40K/Tyranid/1", "https://api.scryfall.com/cards/t40k/17/en?format=image");
            put("40K/Tyranid/2", "https://api.scryfall.com/cards/t40k/18/en?format=image");
            put("40K/Tyranid Gargoyle", "https://api.scryfall.com/cards/t40k/9/en?format=image");
            put("40K/Tyranid Warrior", "https://api.scryfall.com/cards/t40k/19/en?format=image");

            // UNF
            put("UNF/Clown Robot/1", "https://api.scryfall.com/cards/tunf/2?format=image");
            put("UNF/Clown Robot/2", "https://api.scryfall.com/cards/tunf/3?format=image");
            put("UNF/Storm Crow", "https://api.scryfall.com/cards/tunf/5?format=image");
            put("UNF/Squirrel", "https://api.scryfall.com/cards/tunf/8?format=image");

            // BRO
            put("BRO/Bear", "https://api.scryfall.com/cards/tbro/2/en?format=image");
            put("BRO/Construct/1", "https://api.scryfall.com/cards/tbro/5/en?format=image");
            put("BRO/Construct/2", "https://api.scryfall.com/cards/tbro/4/en?format=image");
            put("BRO/Forest Dryad", "https://api.scryfall.com/cards/tbro/3/en?format=image");
            put("BRO/Golem", "https://api.scryfall.com/cards/tbro/6/en?format=image");
            put("BRO/Powerstone", "https://api.scryfall.com/cards/tbro/7/en?format=image");
            put("BRO/Emblem Saheeli", "https://api.scryfall.com/cards/tbro/12/en?format=image");
            put("BRO/Soldier/1", "https://api.scryfall.com/cards/tbro/8/en?format=image");
            put("BRO/Soldier/2", "https://api.scryfall.com/cards/tbro/9/en?format=image");
            put("BRO/Spirit", "https://api.scryfall.com/cards/tbro/1/en?format=image");
            put("BRO/Thopter", "https://api.scryfall.com/cards/tbro/10/en?format=image");
            put("BRO/Zombie", "https://api.scryfall.com/cards/tbro/11/en?format=image");

            // BRC
            put("BRC/Construct", "https://api.scryfall.com/cards/tbrc/8/en?format=image");
            put("BRC/Eldrazi", "https://api.scryfall.com/cards/tbrc/2/en?format=image");
            put("BRC/Elemental", "https://api.scryfall.com/cards/tbrc/14/en?format=image");
            put("BRC/Faerie", "https://api.scryfall.com/cards/tbrc/4/en?format=image");
            put("BRC/Goat", "https://api.scryfall.com/cards/tbrc/3/en?format=image");
            put("BRC/Inkling", "https://api.scryfall.com/cards/tbrc/7/en?format=image");
            put("BRC/Myr", "https://api.scryfall.com/cards/tbrc/10/en?format=image");
            put("BRC/Phyrexian Horror", "https://api.scryfall.com/cards/tbrc/11/en?format=image");
            put("BRC/Phyrexian Myr", "https://api.scryfall.com/cards/tbrc/5/en?format=image");
            put("BRC/Scrap", "https://api.scryfall.com/cards/tbrc/12/en?format=image");
            put("BRC/Servo", "https://api.scryfall.com/cards/tbrc/13/en?format=image");
            put("BRC/Thopter", "https://api.scryfall.com/cards/tbrc/6/en?format=image");

            // DMR
            put("DMR/Bird", "https://api.scryfall.com/cards/tdmr/1/en?format=image");
            put("DMR/Cat/1", "https://api.scryfall.com/cards/tdmr/3/en?format=image");
            put("DMR/Cat/2", "https://api.scryfall.com/cards/tdmr/8/en?format=image");
            put("DMR/Construct", "https://api.scryfall.com/cards/tdmr/14/en?format=image");
            put("DMR/Elemental", "https://api.scryfall.com/cards/tdmr/6/en?format=image");
            put("DMR/Elephant", "https://api.scryfall.com/cards/tdmr/9/en?format=image");
            put("DMR/Goblin", "https://api.scryfall.com/cards/tdmr/7/en?format=image");
            put("DMR/Griffin", "https://api.scryfall.com/cards/tdmr/2/en?format=image");
            put("DMR/Insect", "https://api.scryfall.com/cards/tdmr/10/en?format=image");
            put("DMR/Marit Lage", "https://api.scryfall.com/cards/tdmr/4/en?format=image");
            put("DMR/Saproling", "https://api.scryfall.com/cards/tdmr/11/en?format=image");
            put("DMR/Sheep", "https://api.scryfall.com/cards/tdmr/12/en?format=image");
            put("DMR/Squirrel", "https://api.scryfall.com/cards/tdmr/13/en?format=image");
            put("DMR/Zombie", "https://api.scryfall.com/cards/tdmr/5/en?format=image");

            // ONE
            put("ONE/Cat", "https://api.scryfall.com/cards/tone/1/en?format=image");
            put("ONE/Drone", "https://api.scryfall.com/cards/tone/8/en?format=image");
            put("ONE/Emblem Koth", "https://api.scryfall.com/cards/tone/13/en?format=image");
            put("ONE/Phyrexian Beast", "https://api.scryfall.com/cards/tone/6/en?format=image");
            put("ONE/Phyrexian Goblin", "https://api.scryfall.com/cards/tone/3/en?format=image");
            put("ONE/Phyrexian Golem", "https://api.scryfall.com/cards/tone/10/en?format=image");
            put("ONE/Phyrexian Horror/1", "https://api.scryfall.com/cards/tone/7/en?format=image");
            put("ONE/Phyrexian Horror/2", "https://api.scryfall.com/cards/tone/4/en?format=image");
            put("ONE/Phyrexian Mite/1", "https://api.scryfall.com/cards/tone/11/en?format=image");
            put("ONE/Phyrexian Mite/2", "https://api.scryfall.com/cards/tone/12/en?format=image");
            put("ONE/Rebel", "https://api.scryfall.com/cards/tone/5/en?format=image");
            put("ONE/Samurai", "https://api.scryfall.com/cards/tone/2/en?format=image");
            put("ONE/The Hollow Sentinel", "https://api.scryfall.com/cards/tone/9/en?format=image");

            // ONC
            put("ONC/Angel/1", "https://api.scryfall.com/cards/tonc/2/en?format=image");
            put("ONC/Angel/2", "https://api.scryfall.com/cards/tonc/3/en?format=image");
            put("ONC/Beast", "https://api.scryfall.com/cards/tonc/13/en?format=image");
            put("ONC/Bird", "https://api.scryfall.com/cards/tonc/4/en?format=image");
            put("ONC/Dragon", "https://api.scryfall.com/cards/tonc/10/en?format=image");
            put("ONC/Eldrazi", "https://api.scryfall.com/cards/tonc/1/en?format=image");
            put("ONC/Elephant", "https://api.scryfall.com/cards/tonc/14/en?format=image");
            put("ONC/Goblin", "https://api.scryfall.com/cards/tonc/11/en?format=image");
            put("ONC/Golem", "https://api.scryfall.com/cards/tonc/18/en?format=image");
            put("ONC/Human", "https://api.scryfall.com/cards/tonc/5/en?format=image");
            put("ONC/Human Soldier", "https://api.scryfall.com/cards/tonc/6/en?format=image");
            put("ONC/Kobolds of Kher Keep", "https://api.scryfall.com/cards/tonc/12/en?format=image");
            put("ONC/Myr", "https://api.scryfall.com/cards/tonc/19/en?format=image");
            put("ONC/Phyrexian Germ", "https://api.scryfall.com/cards/tonc/23/en?format=image");
            put("ONC/Phyrexian Horror", "https://api.scryfall.com/cards/tonc/20/en?format=image");
            put("ONC/Phyrexian Insect", "https://api.scryfall.com/cards/tonc/15/en?format=image");
            put("ONC/Phyrexian Wurm", "https://api.scryfall.com/cards/tonc/16/en?format=image");
            put("ONC/Soldier/1", "https://api.scryfall.com/cards/tonc/7/en?format=image");
            put("ONC/Soldier/2", "https://api.scryfall.com/cards/tonc/8/en?format=image");
            put("ONC/Soldier/3", "https://api.scryfall.com/cards/tonc/17/en?format=image");
            put("ONC/Spirit", "https://api.scryfall.com/cards/tonc/9/en?format=image");
            put("ONC/Thopter", "https://api.scryfall.com/cards/tonc/21/en?format=image");

            // 30A
            put("30A/Beast", "https://api.scryfall.com/cards/t30a/8/en?format=image");
            put("30A/Bird", "https://api.scryfall.com/cards/t30a/4/en?format=image");
            put("30A/Clue/1", "https://api.scryfall.com/cards/t30a/11/en?format=image");
            put("30A/Clue/2", "https://api.scryfall.com/cards/t30a/12/en?format=image");
            put("30A/Demon", "https://api.scryfall.com/cards/t30a/6/en?format=image");
            put("30A/Human", "https://api.scryfall.com/cards/t30a/1/en?format=image");
            put("30A/Human Cleric", "https://api.scryfall.com/cards/t30a/10/en?format=image");
            put("30A/Human Warrior", "https://api.scryfall.com/cards/t30a/2/en?format=image");
            put("30A/Human Wizard", "https://api.scryfall.com/cards/t30a/5/en?format=image");
            put("30A/Skeleton", "https://api.scryfall.com/cards/t30a/7/en?format=image");
            put("30A/Soldier", "https://api.scryfall.com/cards/t30a/3/en?format=image");
            put("30A/Treasure/1", "https://api.scryfall.com/cards/t30a/13/en?format=image");
            put("30A/Treasure/2", "https://api.scryfall.com/cards/t30a/14/en?format=image");
            put("30A/Treasure/3", "https://api.scryfall.com/cards/t30a/15/en?format=image");
            put("30A/Wasp", "https://api.scryfall.com/cards/t30a/16/en?format=image");
            put("30A/Wolf", "https://api.scryfall.com/cards/t30a/9/en?format=image");

            // MOM
            put("MOM/Dinosaur", "https://api.scryfall.com/cards/tmom/7/en?format=image");
            put("MOM/Elemental", "https://api.scryfall.com/cards/tmom/9/en?format=image");
            put("MOM/First Mate Ragavan", "https://api.scryfall.com/cards/tmom/6/en?format=image");
            put("MOM/Incubator", "https://api.scryfall.com/cards/tmom/16/en?format=image&face=front");
            put("MOM/Knight", "https://api.scryfall.com/cards/tmom/10/en?format=image");
            put("MOM/Kraken", "https://api.scryfall.com/cards/tmom/4/en?format=image");
            put("MOM/Monk", "https://api.scryfall.com/cards/tmom/1/en?format=image");
            put("MOM/Phyrexian/1", "https://api.scryfall.com/cards/tmom/16/en?format=image&face=back");
            put("MOM/Phyrexian/2", "https://api.scryfall.com/cards/tmom/17/en?format=image&face=back");
            put("MOM/Phyrexian/3", "https://api.scryfall.com/cards/tmom/18/en?format=image&face=back");
            put("MOM/Phyrexian Hydra/1", "https://api.scryfall.com/cards/tmom/12/en?format=image");
            put("MOM/Phyrexian Hydra/2", "https://api.scryfall.com/cards/tmom/11/en?format=image");
            put("MOM/Phyrexian Saproling", "https://api.scryfall.com/cards/tmom/8/en?format=image");
            put("MOM/Soldier", "https://api.scryfall.com/cards/tmom/2/en?format=image");
            put("MOM/Spirit/1", "https://api.scryfall.com/cards/tmom/14/en?format=image");
            put("MOM/Spirit/2", "https://api.scryfall.com/cards/tmom/13/en?format=image");
            put("MOM/Emblem Teferi", "https://api.scryfall.com/cards/tmom/22/en?format=image");
            put("MOM/Thopter", "https://api.scryfall.com/cards/tmom/19/en?format=image");
            put("MOM/Treasure/1", "https://api.scryfall.com/cards/tmom/20/en?format=image");
            put("MOM/Treasure/2", "https://api.scryfall.com/cards/tmom/21/en?format=image");
            put("MOM/Vampire", "https://api.scryfall.com/cards/tmom/3/en?format=image");
            put("MOM/Warrior", "https://api.scryfall.com/cards/tmom/15/en?format=image");
            put("MOM/Emblem Wrenn", "https://api.scryfall.com/cards/tmom/23/en?format=image");
            put("MOM/Zombie", "https://api.scryfall.com/cards/tmom/5/en?format=image");

            // MOC
            put("MOC/Angel/1", "https://api.scryfall.com/cards/tmoc/3/en?format=image");
            put("MOC/Angel/2", "https://api.scryfall.com/cards/tmoc/4/en?format=image");
            put("MOC/Assassin", "https://api.scryfall.com/cards/tmoc/17/en?format=image");
            put("MOC/Beast", "https://api.scryfall.com/cards/tmoc/28/en?format=image");
            put("MOC/Bird", "https://api.scryfall.com/cards/tmoc/5/en?format=image");
            put("MOC/Blood", "https://api.scryfall.com/cards/tmoc/32/en?format=image");
            put("MOC/Butterfly", "https://api.scryfall.com/cards/tmoc/29/en?format=image");
            put("MOC/Clue", "https://api.scryfall.com/cards/tmoc/33/en?format=image");
            put("MOC/Construct", "https://api.scryfall.com/cards/tmoc/34/en?format=image");
            put("MOC/Demon", "https://api.scryfall.com/cards/tmoc/18/en?format=image");
            put("MOC/Eldrazi", "https://api.scryfall.com/cards/tmoc/1/en?format=image");
            put("MOC/Elemental", "https://api.scryfall.com/cards/tmoc/22/en?format=image");
            put("MOC/Elephant", "https://api.scryfall.com/cards/tmoc/30/en?format=image");
            put("MOC/Emblem Elspeth", "https://api.scryfall.com/cards/tmoc/43/en?format=image");
            put("MOC/Faerie", "https://api.scryfall.com/cards/tmoc/11/en?format=image");
            put("MOC/Feather", "https://api.scryfall.com/cards/tmoc/23/en?format=image");
            put("MOC/Food", "https://api.scryfall.com/cards/tmoc/35/en?format=image");
            put("MOC/Goblin", "https://api.scryfall.com/cards/tmoc/24/en?format=image");
            put("MOC/Gold", "https://api.scryfall.com/cards/tmoc/36/en?format=image");
            put("MOC/Golem", "https://api.scryfall.com/cards/tmoc/37/en?format=image");
            put("MOC/Gremlin", "https://api.scryfall.com/cards/tmoc/25/en?format=image");
            put("MOC/Human/1", "https://api.scryfall.com/cards/tmoc/26/en?format=image");
            put("MOC/Human/2", "https://api.scryfall.com/cards/tmoc/6/en?format=image");
            put("MOC/Insect", "https://api.scryfall.com/cards/tmoc/31/en?format=image");
            put("MOC/Knight", "https://api.scryfall.com/cards/tmoc/7/en?format=image");
            put("MOC/Kobolds of Kher Keep", "https://api.scryfall.com/cards/tmoc/27/en?format=image");
            put("MOC/Myr", "https://api.scryfall.com/cards/tmoc/38/en?format=image");
            put("MOC/Phyrexian Germ", "https://api.scryfall.com/cards/tmoc/19/en?format=image");
            put("MOC/Phyrexian Golem", "https://api.scryfall.com/cards/tmoc/39/en?format=image");
            put("MOC/Phyrexian Horror", "https://api.scryfall.com/cards/tmoc/40/en?format=image");
            put("MOC/Replicated Ring", "https://api.scryfall.com/cards/tmoc/41/en?format=image");
            put("MOC/Servo", "https://api.scryfall.com/cards/tmoc/42/en?format=image");
            put("MOC/Shapeshifter", "https://api.scryfall.com/cards/tmoc/12/en?format=image");
            put("MOC/Sliver", "https://api.scryfall.com/cards/tmoc/2/en?format=image");
            put("MOC/Soldier", "https://api.scryfall.com/cards/tmoc/8/en?format=image");
            put("MOC/Spirit/1", "https://api.scryfall.com/cards/tmoc/13/en?format=image");
            put("MOC/Spirit/2", "https://api.scryfall.com/cards/tmoc/9/en?format=image");
            put("MOC/Squid", "https://api.scryfall.com/cards/tmoc/14/en?format=image");
            put("MOC/Emblem Teferi", "https://api.scryfall.com/cards/tmoc/44/en?format=image");
            put("MOC/Tentacle", "https://api.scryfall.com/cards/tmoc/15/en?format=image");
            put("MOC/Thopter", "https://api.scryfall.com/cards/tmoc/16/en?format=image");
            put("MOC/Vampire Knight", "https://api.scryfall.com/cards/tmoc/20/en?format=image");
            put("MOC/Warrior", "https://api.scryfall.com/cards/tmoc/10/en?format=image");
            put("MOC/Zombie Knight", "https://api.scryfall.com/cards/tmoc/21/en?format=image");

            // ODY
            put("ODY/Bear", "https://api.scryfall.com/cards/mpr/7/en?format=image");
            put("ODY/Beast", "https://api.scryfall.com/cards/mpr/8/en?format=image");
            put("ODY/Elephant", "https://api.scryfall.com/cards/pr2/5/en?format=image");
            put("ODY/Squirrel", "https://api.scryfall.com/cards/pr2/3/en?format=image");
            put("ODY/Wurm", "https://api.scryfall.com/cards/pr2/6/en?format=image");
            put("ODY/Zombie", "https://api.scryfall.com/cards/pr2/4/en?format=image");

            // DIS
            put("DIS/Emblem Momir", "https://api.scryfall.com/cards/pmoa/61/en?format=image");
            put("DIS/Elemental", "https://api.scryfall.com/cards/togw/9?format=image");

            // MUL
            put("MUL/Elemental", "https://api.scryfall.com/cards/tmul/2/en?format=image");
            put("MUL/Phyrexian Myr", "https://api.scryfall.com/cards/tmul/1/en?format=image");

            // LTR
            put("LTR/Ballistic Boulder", "https://api.scryfall.com/cards/tltr/8/en?format=image");
            put("LTR/Food/1", "https://api.scryfall.com/cards/tltr/9/en?format=image");
            put("LTR/Food/2", "https://api.scryfall.com/cards/tltr/10/en?format=image");
            put("LTR/Food/3", "https://api.scryfall.com/cards/tltr/11/en?format=image");
            put("LTR/Human Soldier/1", "https://api.scryfall.com/cards/tltr/1/en?format=image");
            put("LTR/Human Soldier/2", "https://api.scryfall.com/cards/tltr/2/en?format=image");
            put("LTR/Orc Army/1", "https://api.scryfall.com/cards/tltr/5/en?format=image");
            put("LTR/Orc Army/2", "https://api.scryfall.com/cards/tltr/6/en?format=image");
            put("LTR/Smaug", "https://api.scryfall.com/cards/tltr/7/en?format=image");
            put("LTR/Spirit", "https://api.scryfall.com/cards/tltr/3/en?format=image");
            put("LTR/Tentacle", "https://api.scryfall.com/cards/tltr/4/en?format=image");
            put("LTR/Treasure", "https://api.scryfall.com/cards/tltr/12/en?format=image");

            // LTC
            put("LTC/Beast", "https://api.scryfall.com/cards/tltc/10/en?format=image");
            put("LTC/Bird/1", "https://api.scryfall.com/cards/tltc/6/en?format=image");
            put("LTC/Bird/2", "https://api.scryfall.com/cards/tltc/1/en?format=image");
            put("LTC/Dragon", "https://api.scryfall.com/cards/tltc/14/en?format=image");
            put("LTC/Elf Warrior", "https://api.scryfall.com/cards/tltc/11/en?format=image");
            put("LTC/Goat", "https://api.scryfall.com/cards/tltc/2/en?format=image");
            put("LTC/Goblin", "https://api.scryfall.com/cards/tltc/8/en?format=image");
            put("LTC/Halfling", "https://api.scryfall.com/cards/tltc/3/en?format=image");
            put("LTC/Human", "https://api.scryfall.com/cards/tltc/4/en?format=image");
            put("LTC/Human Knight", "https://api.scryfall.com/cards/tltc/9/en?format=image");
            put("LTC/Insect", "https://api.scryfall.com/cards/tltc/12/en?format=image");
            put("LTC/Soldier", "https://api.scryfall.com/cards/tltc/5/en?format=image");
            put("LTC/Treefolk", "https://api.scryfall.com/cards/tltc/13/en?format=image");
            put("LTC/Wraith", "https://api.scryfall.com/cards/tltc/7/en?format=image");

            // CMM
            put("CMM/Emblem Ajani", "https://api.scryfall.com/cards/tcmm/77/en?format=image");
            put("CMM/Angel", "https://api.scryfall.com/cards/tcmm/59/en?format=image");
            put("CMM/Assassin", "https://api.scryfall.com/cards/tcmm/14/en?format=image");
            put("CMM/Avacyn", "https://api.scryfall.com/cards/tcmm/60/en?format=image");
            put("CMM/Bird", "https://api.scryfall.com/cards/tcmm/4/en?format=image");
            put("CMM/Bird Illusion", "https://api.scryfall.com/cards/tcmm/11/en?format=image");
            put("CMM/Cat/1", "https://api.scryfall.com/cards/tcmm/30/en?format=image");
            put("CMM/Cat/2", "https://api.scryfall.com/cards/tcmm/5/en?format=image");
            put("CMM/Cat Beast", "https://api.scryfall.com/cards/tcmm/61/en?format=image");
            put("CMM/Emblem Chandra/1", "https://api.scryfall.com/cards/tcmm/78/en?format=image");
            put("CMM/Emblem Chandra/2", "https://api.scryfall.com/cards/tcmm/79/en?format=image");
            put("CMM/Cleric", "https://api.scryfall.com/cards/tcmm/62/en?format=image");
            put("CMM/Clue", "https://api.scryfall.com/cards/tcmm/40/en?format=image");
            put("CMM/Construct/1", "https://api.scryfall.com/cards/tcmm/42/en?format=image");
            put("CMM/Construct/2", "https://api.scryfall.com/cards/tcmm/41/en?format=image");
            put("CMM/Construct/3", "https://api.scryfall.com/cards/tcmm/74/en?format=image");
            put("CMM/Construct/4", "https://api.scryfall.com/cards/tcmm/75/en?format=image");
            put("CMM/Emblem Daretti", "https://api.scryfall.com/cards/tcmm/51/en?format=image");
            put("CMM/Demon", "https://api.scryfall.com/cards/tcmm/15/en?format=image");
            put("CMM/Dragon/1", "https://api.scryfall.com/cards/tcmm/20/en?format=image");
            put("CMM/Dragon/2", "https://api.scryfall.com/cards/tcmm/70/en?format=image");
            put("CMM/Dragon/3", "https://api.scryfall.com/cards/tcmm/21/en?format=image");
            put("CMM/Dragon Egg", "https://api.scryfall.com/cards/tcmm/22/en?format=image");
            put("CMM/Drake", "https://api.scryfall.com/cards/tcmm/12/en?format=image");
            put("CMM/Dwarf Berserker", "https://api.scryfall.com/cards/tcmm/23/en?format=image");
            put("CMM/Eldrazi", "https://api.scryfall.com/cards/tcmm/1/en?format=image");
            put("CMM/Eldrazi Scion", "https://api.scryfall.com/cards/tcmm/2/en?format=image");
            put("CMM/Eldrazi Spawn", "https://api.scryfall.com/cards/tcmm/3/en?format=image");
            put("CMM/Elemental/1", "https://api.scryfall.com/cards/tcmm/24/en?format=image");
            put("CMM/Elemental/2", "https://api.scryfall.com/cards/tcmm/25/en?format=image");
            put("CMM/Elemental/3", "https://api.scryfall.com/cards/tcmm/26/en?format=image");
            put("CMM/Elemental/4", "https://api.scryfall.com/cards/tcmm/37/en?format=image");
            put("CMM/Elephant", "https://api.scryfall.com/cards/tcmm/31/en?format=image");
            put("CMM/Elf Druid", "https://api.scryfall.com/cards/tcmm/32/en?format=image");
            put("CMM/Elf Warrior", "https://api.scryfall.com/cards/tcmm/72/en?format=image");
            put("CMM/Emblem Elspeth", "https://api.scryfall.com/cards/tcmm/80/en?format=image");
            put("CMM/Goblin", "https://api.scryfall.com/cards/tcmm/27/en?format=image");
            put("CMM/Graveborn", "https://api.scryfall.com/cards/tcmm/38/en?format=image");
            put("CMM/Human Soldier", "https://api.scryfall.com/cards/tcmm/6/en?format=image");
            put("CMM/Human Warrior", "https://api.scryfall.com/cards/tcmm/63/en?format=image");
            put("CMM/Insect", "https://api.scryfall.com/cards/tcmm/33/en?format=image");
            put("CMM/Knight", "https://api.scryfall.com/cards/tcmm/7/en?format=image");
            put("CMM/Kor Ally", "https://api.scryfall.com/cards/tcmm/64/en?format=image");
            put("CMM/Kor Soldier", "https://api.scryfall.com/cards/tcmm/8/en?format=image");
            put("CMM/Myr", "https://api.scryfall.com/cards/tcmm/43/en?format=image");
            put("CMM/Emblem Narset", "https://api.scryfall.com/cards/tcmm/81/en?format=image");
            put("CMM/Emblem Nixilis", "https://api.scryfall.com/cards/tcmm/52/en?format=image");
            put("CMM/Ogre", "https://api.scryfall.com/cards/tcmm/28/en?format=image");
            put("CMM/Ox", "https://api.scryfall.com/cards/tcmm/65/en?format=image");
            put("CMM/Pegasus", "https://api.scryfall.com/cards/tcmm/66/en?format=image");
            put("CMM/Phyrexian Beast", "https://api.scryfall.com/cards/tcmm/34/en?format=image");
            put("CMM/Phyrexian Germ", "https://api.scryfall.com/cards/tcmm/16/en?format=image");
            put("CMM/Phyrexian Golem", "https://api.scryfall.com/cards/tcmm/76/en?format=image");
            put("CMM/Phyrexian Myr", "https://api.scryfall.com/cards/tcmm/44/en?format=image");
            put("CMM/Rat", "https://api.scryfall.com/cards/tcmm/17/en?format=image");
            put("CMM/Saproling", "https://api.scryfall.com/cards/tcmm/35/en?format=image");
            put("CMM/Satyr", "https://api.scryfall.com/cards/tcmm/29/en?format=image");
            put("CMM/Servo", "https://api.scryfall.com/cards/tcmm/45/en?format=image");
            put("CMM/Sliver", "https://api.scryfall.com/cards/tcmm/57/en?format=image");
            put("CMM/Sliver Army", "https://api.scryfall.com/cards/tcmm/68/en?format=image");
            put("CMM/Soldier", "https://api.scryfall.com/cards/tcmm/9/en?format=image");
            put("CMM/Spider", "https://api.scryfall.com/cards/tcmm/36/en?format=image");
            put("CMM/Spirit/1", "https://api.scryfall.com/cards/tcmm/58/en?format=image");
            put("CMM/Spirit/2", "https://api.scryfall.com/cards/tcmm/10/en?format=image");
            put("CMM/Spirit/3", "https://api.scryfall.com/cards/tcmm/39/en?format=image");
            put("CMM/Stoneforged Blade", "https://api.scryfall.com/cards/tcmm/46/en?format=image");
            put("CMM/Emblem Teferi", "https://api.scryfall.com/cards/tcmm/53/en?format=image");
            put("CMM/Thopter", "https://api.scryfall.com/cards/tcmm/47/en?format=image");
            put("CMM/Thrull", "https://api.scryfall.com/cards/tcmm/18/en?format=image");
            put("CMM/Treasure", "https://api.scryfall.com/cards/tcmm/48/en?format=image");
            put("CMM/Wall", "https://api.scryfall.com/cards/tcmm/67/en?format=image");
            put("CMM/Wizard", "https://api.scryfall.com/cards/tcmm/71/en?format=image");
            put("CMM/Wurm", "https://api.scryfall.com/cards/tcmm/73/en?format=image");
            put("CMM/Zombie/1", "https://api.scryfall.com/cards/tcmm/19/en?format=image");
            put("CMM/Zombie/2", "https://api.scryfall.com/cards/tcmm/13/en?format=image");
            put("CMM/Zombie Army", "https://api.scryfall.com/cards/tcmm/69/en?format=image");

            // WOE
            put("WOE/Beast", "https://api.scryfall.com/cards/twoe/8/en?format=image");
            put("WOE/Bird", "https://api.scryfall.com/cards/twoe/1/en?format=image");
            put("WOE/Cursed", "https://api.scryfall.com/cards/twoe/17/en?format=image");
            put("WOE/Elemental", "https://api.scryfall.com/cards/twoe/9/en?format=image");
            put("WOE/Faerie", "https://api.scryfall.com/cards/twoe/5/en?format=image");
            put("WOE/Food/1", "https://api.scryfall.com/cards/twoe/10/en?format=image");
            put("WOE/Food/2", "https://api.scryfall.com/cards/twoe/11/en?format=image");
            put("WOE/Food/3", "https://api.scryfall.com/cards/twoe/12/en?format=image");
            put("WOE/Food/4", "https://api.scryfall.com/cards/twoe/13/en?format=image");
            put("WOE/Human", "https://api.scryfall.com/cards/twoe/2/en?format=image");
            put("WOE/Knight", "https://api.scryfall.com/cards/twoe/3/en?format=image");
            put("WOE/Monster", "https://api.scryfall.com/cards/twoe/15/en?format=image");
            put("WOE/Mouse", "https://api.scryfall.com/cards/twoe/4/en?format=image");
            put("WOE/Nightmare", "https://api.scryfall.com/cards/twoe/6/en?format=image");
            put("WOE/Rat", "https://api.scryfall.com/cards/twoe/7/en?format=image");
            put("WOE/Royal", "https://api.scryfall.com/cards/twoe/16/en?format=image");
            put("WOE/Sorcerer", "https://api.scryfall.com/cards/twoe/15/en?format=image");
            put("WOE/Treasure", "https://api.scryfall.com/cards/twoe/14/en?format=image");
            put("WOE/Wicked", "https://api.scryfall.com/cards/twoe/17/en?format=image");
            put("WOE/Young Hero", "https://api.scryfall.com/cards/twoe/16/en?format=image");

            // WOC
            put("WOC/Faerie", "https://api.scryfall.com/cards/twoc/10/en?format=image");
            put("WOC/Faerie Rogue/1", "https://api.scryfall.com/cards/twoc/11/en?format=image");
            put("WOC/Faerie Rogue/2", "https://api.scryfall.com/cards/twoc/16/en?format=image");
            put("WOC/Human Soldier", "https://api.scryfall.com/cards/twoc/7/en?format=image");
            put("WOC/Monster", "https://api.scryfall.com/cards/twoc/1/en?format=image");
            put("WOC/Pegasus", "https://api.scryfall.com/cards/twoc/9/en?format=image");
            put("WOC/Pirate", "https://api.scryfall.com/cards/twoc/12/en?format=image");
            put("WOC/Royal", "https://api.scryfall.com/cards/twoc/2/en?format=image");
            put("WOC/Saproling", "https://api.scryfall.com/cards/twoc/15/en?format=image");
            put("WOC/Virtuous", "https://api.scryfall.com/cards/twoc/2/en?format=image");

            // WHO
            put("WHO/Alien", "https://api.scryfall.com/cards/twho/2?format=image");
            put("WHO/Alien Angel", "https://api.scryfall.com/cards/twho/11?format=image");
            put("WHO/Alien Insect", "https://api.scryfall.com/cards/twho/19/en?format=image");
            put("WHO/Alien Rhino", "https://api.scryfall.com/cards/twho/3/en?format=image");
            put("WHO/Alien Salamander", "https://api.scryfall.com/cards/twho/16?format=image");
            put("WHO/Alien Warrior", "https://api.scryfall.com/cards/twho/14?format=image");
            put("WHO/Beast", "https://api.scryfall.com/cards/twho/17?format=image");
            put("WHO/Clue/1", "https://api.scryfall.com/cards/twho/21?format=image");
            put("WHO/Clue/2", "https://api.scryfall.com/cards/twho/22?format=image");
            put("WHO/Clue/3", "https://api.scryfall.com/cards/twho/23?format=image");
            put("WHO/Dalek", "https://api.scryfall.com/cards/twho/12?format=image");
            put("WHO/Dinosaur", "https://api.scryfall.com/cards/twho/20?format=image");
            put("WHO/Fish", "https://api.scryfall.com/cards/twho/10?format=image");
            put("WHO/Food/1", "https://api.scryfall.com/cards/twho/25?format=image");
            put("WHO/Food/2", "https://api.scryfall.com/cards/twho/26?format=image");
            put("WHO/Food/3", "https://api.scryfall.com/cards/twho/27?format=image");
            put("WHO/Horse", "https://api.scryfall.com/cards/twho/4/en?format=image");
            put("WHO/Human/1", "https://api.scryfall.com/cards/twho/6/en?format=image");
            put("WHO/Human/2", "https://api.scryfall.com/cards/twho/5/en?format=image");
            put("WHO/Human Noble", "https://api.scryfall.com/cards/twho/7/en?format=image");
            put("WHO/Mark of the Rani", "https://api.scryfall.com/cards/twho/15?format=image");
            put("WHO/Mutant", "https://api.scryfall.com/cards/twho/18?format=image");
            put("WHO/Soldier", "https://api.scryfall.com/cards/twho/8?format=image");
            put("WHO/Treasure/1", "https://api.scryfall.com/cards/twho/28?format=image");
            put("WHO/Treasure/2", "https://api.scryfall.com/cards/twho/29?format=image");
            put("WHO/Treasure/3", "https://api.scryfall.com/cards/twho/30?format=image");
            put("WHO/Treasure/4", "https://api.scryfall.com/cards/twho/31?format=image");
            put("WHO/Warrior", "https://api.scryfall.com/cards/twho/9?format=image");            

            // 8ED
            put("8ED/Bird", "https://api.scryfall.com/cards/p03/7/en?format=image");

            // LCI
            put("LCI/Angel", "https://api.scryfall.com/cards/tlci/2/en?format=image");
            put("LCI/Bat", "https://api.scryfall.com/cards/tlci/6/en?format=image");
            put("LCI/Dinosaur/1", "https://api.scryfall.com/cards/tlci/10/en?format=image");
            put("LCI/Dinosaur/2", "https://api.scryfall.com/cards/tlci/9/en?format=image");
            put("LCI/Dinosaur Egg", "https://api.scryfall.com/cards/tlci/11/en?format=image");
            put("LCI/Fungus", "https://api.scryfall.com/cards/tlci/7/en?format=image");
            put("LCI/Fungus Dinosaur", "https://api.scryfall.com/cards/tlci/12/en?format=image");
            put("LCI/Gnome", "https://api.scryfall.com/cards/tlci/16/en?format=image");
            put("LCI/Gnome Soldier", "https://api.scryfall.com/cards/tlci/3/en?format=image");
            put("LCI/Golem", "https://api.scryfall.com/cards/tlci/13/en?format=image");
            put("LCI/Map", "https://api.scryfall.com/cards/tlci/17/en?format=image");
            put("LCI/Merfolk", "https://api.scryfall.com/cards/tlci/5/en?format=image");
            put("LCI/Skeleton Pirate", "https://api.scryfall.com/cards/tlci/8/en?format=image");
            put("LCI/Spirit", "https://api.scryfall.com/cards/tlci/14/en?format=image");
            put("LCI/Treasure", "https://api.scryfall.com/cards/tlci/18/en?format=image");
            put("LCI/Vampire", "https://api.scryfall.com/cards/tlci/4/en?format=image");
            put("LCI/Vampire Demon", "https://api.scryfall.com/cards/tlci/15/en?format=image");

            // LCC
            put("LCC/Beast", "https://api.scryfall.com/cards/tlcc/8/en?format=image");
            put("LCC/Bird", "https://api.scryfall.com/cards/tlcc/2/en?format=image");
            put("LCC/Blood", "https://api.scryfall.com/cards/tlcc/15/en?format=image");
            put("LCC/Boar", "https://api.scryfall.com/cards/tlcc/9/en?format=image");
            put("LCC/Dinosaur", "https://api.scryfall.com/cards/tlcc/10/en?format=image");
            put("LCC/Dinosaur Beast", "https://api.scryfall.com/cards/tlcc/11/en?format=image");
            put("LCC/Elephant", "https://api.scryfall.com/cards/tlcc/12/en?format=image");
            put("LCC/Frog Lizard", "https://api.scryfall.com/cards/tlcc/13/en?format=image");
            put("LCC/Merfolk", "https://api.scryfall.com/cards/tlcc/3/en?format=image");
            put("LCC/Pirate", "https://api.scryfall.com/cards/tlcc/5/en?format=image");
            put("LCC/Ragavan", "https://api.scryfall.com/cards/tlcc/7/en?format=image");
            put("LCC/Salamander Warrior", "https://api.scryfall.com/cards/tlcc/4/en?format=image");
            put("LCC/Shapeshifter", "https://api.scryfall.com/cards/tlcc/1/en?format=image");
            put("LCC/Emblem Sorin", "https://api.scryfall.com/cards/tlcc/16/en?format=image");
            put("LCC/Vampire/1", "https://api.scryfall.com/cards/tlcc/6/en?format=image");
            put("LCC/Vampire/2", "https://api.scryfall.com/cards/tlcc/14/en?format=image");

            // RVR
            put("RVR/Angel/1", "https://api.scryfall.com/cards/trvr/2/en?format=image");
            put("RVR/Angel/2", "https://api.scryfall.com/cards/trvr/3/en?format=image");
            put("RVR/Beast", "https://api.scryfall.com/cards/trvr/14/en?format=image");
            put("RVR/Bird", "https://api.scryfall.com/cards/trvr/1/en?format=image");
            put("RVR/Bird Illusion", "https://api.scryfall.com/cards/trvr/5/en?format=image");
            put("RVR/Centaur", "https://api.scryfall.com/cards/trvr/10/en?format=image");
            put("RVR/Emblem Domri", "https://api.scryfall.com/cards/trvr/20/en?format=image");
            put("RVR/Dragon", "https://api.scryfall.com/cards/trvr/7/en?format=image");
            put("RVR/Elf Knight", "https://api.scryfall.com/cards/trvr/15/en?format=image");
            put("RVR/Goblin/1", "https://api.scryfall.com/cards/trvr/8/en?format=image");
            put("RVR/Goblin/2", "https://api.scryfall.com/cards/trvr/9/en?format=image");
            put("RVR/Rhino", "https://api.scryfall.com/cards/trvr/11/en?format=image");
            put("RVR/Saproling", "https://api.scryfall.com/cards/trvr/12/en?format=image");
            put("RVR/Soldier", "https://api.scryfall.com/cards/trvr/16/en?format=image");
            put("RVR/Sphinx", "https://api.scryfall.com/cards/trvr/17/en?format=image");
            put("RVR/Spirit/1", "https://api.scryfall.com/cards/trvr/4/en?format=image");
            put("RVR/Spirit/2", "https://api.scryfall.com/cards/trvr/18/en?format=image");
            put("RVR/Voja", "https://api.scryfall.com/cards/trvr/19/en?format=image");
            put("RVR/Wurm", "https://api.scryfall.com/cards/trvr/13/en?format=image");
            put("RVR/Zombie", "https://api.scryfall.com/cards/trvr/6/en?format=image");

            // MKM
            put("MKM/Bat", "https://api.scryfall.com/cards/tmkm/4/en?format=image");
            put("MKM/Clue/1", "https://api.scryfall.com/cards/tmkm/14/en?format=image");
            put("MKM/Clue/2", "https://api.scryfall.com/cards/tmkm/15/en?format=image");
            put("MKM/Clue/3", "https://api.scryfall.com/cards/tmkm/16/en?format=image");
            put("MKM/Clue/4", "https://api.scryfall.com/cards/tmkm/17/en?format=image");
            put("MKM/Clue/5", "https://api.scryfall.com/cards/tmkm/18/en?format=image");
            put("MKM/Detective", "https://api.scryfall.com/cards/tmkm/10/en?format=image");
            put("MKM/Dog", "https://api.scryfall.com/cards/tmkm/1/en?format=image");
            put("MKM/Goblin", "https://api.scryfall.com/cards/tmkm/6/en?format=image");
            put("MKM/Human", "https://api.scryfall.com/cards/tmkm/2/en?format=image");
            put("MKM/Imp", "https://api.scryfall.com/cards/tmkm/7/en?format=image");
            put("MKM/Merfolk", "https://api.scryfall.com/cards/tmkm/3/en?format=image");
            put("MKM/Ooze", "https://api.scryfall.com/cards/tmkm/8/en?format=image");
            put("MKM/Plant", "https://api.scryfall.com/cards/tmkm/9/en?format=image");
            put("MKM/Skeleton", "https://api.scryfall.com/cards/tmkm/5/en?format=image");
            put("MKM/Spider", "https://api.scryfall.com/cards/tmkm/11/en?format=image");
            put("MKM/Spirit", "https://api.scryfall.com/cards/tmkm/12/en?format=image");
            put("MKM/Thopter/1", "https://api.scryfall.com/cards/tmkm/19/en?format=image");
            put("MKM/Thopter/2", "https://api.scryfall.com/cards/tmkm/20/en?format=image");
            put("MKM/Voja Fenstalker", "https://api.scryfall.com/cards/tmkm/13/en?format=image");

            // MKC
            put("MKC/Cat", "https://api.scryfall.com/cards/tmkc/15/en?format=image");
            put("MKC/Clue", "https://api.scryfall.com/cards/tmkc/22/en?format=image");
            put("MKC/Construct", "https://api.scryfall.com/cards/tmkc/23/en?format=image");
            put("MKC/Drake", "https://api.scryfall.com/cards/tmkc/6/en?format=image");
            put("MKC/Eldrazi", "https://api.scryfall.com/cards/tmkc/1/en?format=image");
            put("MKC/Food", "https://api.scryfall.com/cards/tmkc/24/en?format=image");
            put("MKC/Gold", "https://api.scryfall.com/cards/tmkc/25/en?format=image");
            put("MKC/Human Soldier", "https://api.scryfall.com/cards/tmkc/2/en?format=image");
            put("MKC/Insect/1", "https://api.scryfall.com/cards/tmkc/16/en?format=image");
            put("MKC/Insect/2", "https://api.scryfall.com/cards/tmkc/17/en?format=image");
            put("MKC/Kobolds of Kher Keep", "https://api.scryfall.com/cards/tmkc/12/en?format=image");
            put("MKC/Koma's Coil", "https://api.scryfall.com/cards/tmkc/7/en?format=image");
            put("MKC/Lightning Rager", "https://api.scryfall.com/cards/tmkc/13/en?format=image");
            put("MKC/Ogre", "https://api.scryfall.com/cards/tmkc/14/en?format=image");
            put("MKC/Phyrexian Germ", "https://api.scryfall.com/cards/tmkc/10/en?format=image");
            put("MKC/Rhino Warrior", "https://api.scryfall.com/cards/tmkc/18/en?format=image");
            put("MKC/Salamander Warrior", "https://api.scryfall.com/cards/tmkc/8/en?format=image");
            put("MKC/Saproling", "https://api.scryfall.com/cards/tmkc/19/en?format=image");
            put("MKC/Snake", "https://api.scryfall.com/cards/tmkc/20/en?format=image");
            put("MKC/Soldier", "https://api.scryfall.com/cards/tmkc/3/en?format=image");
            put("MKC/Spirit", "https://api.scryfall.com/cards/tmkc/4/en?format=image");
            put("MKC/Tentacle", "https://api.scryfall.com/cards/tmkc/9/en?format=image");
            put("MKC/Tiny", "https://api.scryfall.com/cards/tmkc/21/en?format=image");
            put("MKC/Treasure", "https://api.scryfall.com/cards/tmkc/26/en?format=image");
            put("MKC/Zombie", "https://api.scryfall.com/cards/tmkc/11/en?format=image");

            // OTJ
            put("OTJ/Angel", "https://api.scryfall.com/cards/totj/2/en?format=image");
            put("OTJ/Beau", "https://api.scryfall.com/cards/totj/6/en?format=image");
            put("OTJ/Bird", "https://api.scryfall.com/cards/totj/7/en?format=image");
            put("OTJ/Clue", "https://api.scryfall.com/cards/totj/16/en?format=image");
            put("OTJ/Dinosaur", "https://api.scryfall.com/cards/totj/9/en?format=image");
            put("OTJ/Elemental", "https://api.scryfall.com/cards/totj/12/en?format=image");
            put("OTJ/Elk", "https://api.scryfall.com/cards/totj/13/en?format=image");
            put("OTJ/Mercenary", "https://api.scryfall.com/cards/totj/10/en?format=image");
            put("OTJ/Meteorite", "https://api.scryfall.com/cards/totj/17/en?format=image");
            put("OTJ/Ox", "https://api.scryfall.com/cards/totj/3/en?format=image");
            put("OTJ/Scorpion Dragon", "https://api.scryfall.com/cards/totj/11/en?format=image");
            put("OTJ/Sheep", "https://api.scryfall.com/cards/totj/4/en?format=image");
            put("OTJ/Spirit", "https://api.scryfall.com/cards/totj/5/en?format=image");
            put("OTJ/Treasure", "https://api.scryfall.com/cards/totj/18/en?format=image");
            put("OTJ/Vampire Rogue", "https://api.scryfall.com/cards/totj/8/en?format=image");
            put("OTJ/Varmint", "https://api.scryfall.com/cards/totj/14/en?format=image");
            put("OTJ/Zombie Rogue", "https://api.scryfall.com/cards/totj/15/en?format=image");

            // OTC
            put("OTC/Angel", "https://api.scryfall.com/cards/totc/3/en?format=image");
            put("OTC/Ape", "https://api.scryfall.com/cards/totc/15/en?format=image");
            put("OTC/Assassin", "https://api.scryfall.com/cards/totc/7/en?format=image");
            put("OTC/Bird Illusion", "https://api.scryfall.com/cards/totc/4/en?format=image");
            put("OTC/Blood", "https://api.scryfall.com/cards/totc/24/en?format=image");
            put("OTC/Boar", "https://api.scryfall.com/cards/totc/16/en?format=image");
            put("OTC/Dragon", "https://api.scryfall.com/cards/totc/11/en?format=image");
            put("OTC/Dragon Egg", "https://api.scryfall.com/cards/totc/12/en?format=image");
            put("OTC/Dragon Elemental", "https://api.scryfall.com/cards/totc/13/en?format=image");
            put("OTC/Drake", "https://api.scryfall.com/cards/totc/5/en?format=image");
            put("OTC/Eldrazi", "https://api.scryfall.com/cards/totc/1/en?format=image");
            put("OTC/Eldrazi Scion", "https://api.scryfall.com/cards/totc/2/en?format=image");
            put("OTC/Elemental/1", "https://api.scryfall.com/cards/totc/17/en?format=image");
            put("OTC/Elemental/2", "https://api.scryfall.com/cards/totc/14/en?format=image");
            put("OTC/Elemental/3", "https://api.scryfall.com/cards/totc/21/en?format=image");
            put("OTC/Food", "https://api.scryfall.com/cards/totc/25/en?format=image");
            put("OTC/Inkling", "https://api.scryfall.com/cards/totc/22/en?format=image");
            put("OTC/Insect", "https://api.scryfall.com/cards/totc/18/en?format=image");
            put("OTC/Plant", "https://api.scryfall.com/cards/totc/19/en?format=image");
            put("OTC/Plant Warrior", "https://api.scryfall.com/cards/totc/20/en?format=image");
            put("OTC/Rat", "https://api.scryfall.com/cards/totc/8/en?format=image");
            put("OTC/Rogue", "https://api.scryfall.com/cards/totc/9/en?format=image");
            put("OTC/Sand Warrior", "https://api.scryfall.com/cards/totc/23/en?format=image");
            put("OTC/Shark", "https://api.scryfall.com/cards/totc/6/en?format=image");
            put("OTC/Soldier", "https://api.scryfall.com/cards/totc/26/en?format=image");
            put("OTC/Thopter", "https://api.scryfall.com/cards/totc/27/en?format=image");
            put("OTC/Zombie", "https://api.scryfall.com/cards/totc/10/en?format=image");

            // BIG
            put("BIG/Bat", "https://api.scryfall.com/cards/tbig/1/en?format=image");
            put("BIG/Blood", "https://api.scryfall.com/cards/tbig/2/en?format=image");
            put("BIG/Construct", "https://api.scryfall.com/cards/tbig/3/en?format=image");
            put("BIG/Food", "https://api.scryfall.com/cards/tbig/4/en?format=image");
            put("BIG/Gnome", "https://api.scryfall.com/cards/tbig/5/en?format=image");
            put("BIG/Golem", "https://api.scryfall.com/cards/tbig/6/en?format=image");
            put("BIG/Map", "https://api.scryfall.com/cards/tbig/7/en?format=image");

            // OTP
            put("OTP/Food", "https://api.scryfall.com/cards/totp/5/en?format=image");
            put("OTP/Human Cleric", "https://api.scryfall.com/cards/totp/1/en?format=image");
            put("OTP/Human Rogue", "https://api.scryfall.com/cards/totp/2/en?format=image");
            put("OTP/Human Warrior", "https://api.scryfall.com/cards/totp/3/en?format=image");
            put("OTP/Pest", "https://api.scryfall.com/cards/totp/4/en?format=image");

             // SCD
            put("SCD/Beast", "https://api.scryfall.com/cards/tscd/19/en?format=image");
            put("SCD/Bird", "https://api.scryfall.com/cards/tscd/2/en?format=image");
            put("SCD/Cat", "https://api.scryfall.com/cards/tscd/3/en?format=image");
            put("SCD/Cat Beast", "https://api.scryfall.com/cards/tscd/4/en?format=image");
            put("SCD/Cat Bird", "https://api.scryfall.com/cards/tscd/5/en?format=image");
            put("SCD/Demon", "https://api.scryfall.com/cards/tscd/12/en?format=image");
            put("SCD/Dragon", "https://api.scryfall.com/cards/tscd/16/en?format=image");
            put("SCD/Eldrazi", "https://api.scryfall.com/cards/tscd/1/en?format=image");
            put("SCD/Elephant", "https://api.scryfall.com/cards/tscd/20/en?format=image");
            put("SCD/Elf Warrior", "https://api.scryfall.com/cards/tscd/21/en?format=image");
            put("SCD/Faerie", "https://api.scryfall.com/cards/tscd/10/en?format=image");
            put("SCD/Human Warrior", "https://api.scryfall.com/cards/tscd/6/en?format=image");
            put("SCD/Insect", "https://api.scryfall.com/cards/tscd/22/en?format=image");
            put("SCD/Karox Bladewing", "https://api.scryfall.com/cards/tscd/17/en?format=image");
            put("SCD/Emblem Nixilis", "https://api.scryfall.com/cards/tscd/26/en?format=image");
            put("SCD/Ogre", "https://api.scryfall.com/cards/tscd/18/en?format=image");
            put("SCD/Pegasus", "https://api.scryfall.com/cards/tscd/7/en?format=image");
            put("SCD/Saproling", "https://api.scryfall.com/cards/tscd/23/en?format=image");
            put("SCD/Emblem Sarkhan", "https://api.scryfall.com/cards/tscd/27/en?format=image");
            put("SCD/Soldier", "https://api.scryfall.com/cards/tscd/8/en?format=image");
            put("SCD/Spirit", "https://api.scryfall.com/cards/tscd/9/en?format=image");
            put("SCD/Thopter/1", "https://api.scryfall.com/cards/tscd/24/en?format=image");
            put("SCD/Thopter/2", "https://api.scryfall.com/cards/tscd/11/en?format=image");
            put("SCD/Treasure", "https://api.scryfall.com/cards/tscd/25/en?format=image");
            put("SCD/Zombie", "https://api.scryfall.com/cards/tscd/13/en?format=image");
            put("SCD/Zombie Army", "https://api.scryfall.com/cards/tscd/14/en?format=image");
            put("SCD/Zombie Knight", "https://api.scryfall.com/cards/tscd/15/en?format=image");

            // MH3
            put("MH3/Angel", "https://api.scryfall.com/cards/tmh3/3/en?format=image");
            put("MH3/Bird", "https://api.scryfall.com/cards/tmh3/9/en?format=image");
            put("MH3/Blood", "https://api.scryfall.com/cards/tmh3/29/en?format=image");
            put("MH3/Cat", "https://api.scryfall.com/cards/tmh3/4/en?format=image");
            put("MH3/Cat Warrior", "https://api.scryfall.com/cards/tmh3/5/en?format=image");
            put("MH3/Clue", "https://api.scryfall.com/cards/tmh3/30/en?format=image");
            put("MH3/Eldrazi Spawn", "https://api.scryfall.com/cards/tmh3/2/en?format=image");
            put("MH3/Fish", "https://api.scryfall.com/cards/tmh3/10/en?format=image");
            put("MH3/Food", "https://api.scryfall.com/cards/tmh3/31/en?format=image");
            put("MH3/Fox", "https://api.scryfall.com/cards/tmh3/6/en?format=image");
            put("MH3/Gremlin", "https://api.scryfall.com/cards/tmh3/23/en?format=image");
            put("MH3/Insect/1", "https://api.scryfall.com/cards/tmh3/27/en?format=image");
            put("MH3/Insect/2", "https://api.scryfall.com/cards/tmh3/25/en?format=image");
            put("MH3/Kraken", "https://api.scryfall.com/cards/tmh3/11/en?format=image");
            put("MH3/Moonfolk", "https://api.scryfall.com/cards/tmh3/12/en?format=image");
            put("MH3/Phyrexian Germ", "https://api.scryfall.com/cards/tmh3/16/en?format=image");
            put("MH3/Phyrexian Golem", "https://api.scryfall.com/cards/tmh3/32/en?format=image");
            put("MH3/Phyrexian Wurm/1", "https://api.scryfall.com/cards/tmh3/17/en?format=image");
            put("MH3/Phyrexian Wurm/2", "https://api.scryfall.com/cards/tmh3/18/en?format=image");
            put("MH3/Plant", "https://api.scryfall.com/cards/tmh3/26/en?format=image");
            put("MH3/Rat", "https://api.scryfall.com/cards/tmh3/19/en?format=image");
            put("MH3/Servo", "https://api.scryfall.com/cards/tmh3/33/en?format=image");
            put("MH3/Snake", "https://api.scryfall.com/cards/tmh3/20/en?format=image");
            put("MH3/Soldier", "https://api.scryfall.com/cards/tmh3/7/en?format=image");
            put("MH3/Spellgorger Weird", "https://api.scryfall.com/cards/tmh3/24/en?format=image");
            put("MH3/Spirit/1", "https://api.scryfall.com/cards/tmh3/8/en?format=image");
            put("MH3/Spirit/2", "https://api.scryfall.com/cards/tmh3/28/en?format=image");
            put("MH3/Emblem Tamiyo", "https://api.scryfall.com/cards/tmh3/35/en?format=image");
            put("MH3/Thopter", "https://api.scryfall.com/cards/tmh3/13/en?format=image");
            put("MH3/Treasure", "https://api.scryfall.com/cards/tmh3/34/en?format=image");
            put("MH3/Whale", "https://api.scryfall.com/cards/tmh3/14/en?format=image");
            put("MH3/Zombie", "https://api.scryfall.com/cards/tmh3/21/en?format=image");
            put("MH3/Zombie Army", "https://api.scryfall.com/cards/tmh3/22/en?format=image");

            // M3C
            put("M3C/Aetherborn", "https://api.scryfall.com/cards/tm3c/9/en?format=image");
            put("M3C/Ape", "https://api.scryfall.com/cards/tm3c/14/en?format=image");
            put("M3C/Beast/1", "https://api.scryfall.com/cards/tm3c/10/en?format=image");
            put("M3C/Beast/2", "https://api.scryfall.com/cards/tm3c/15/en?format=image");
            put("M3C/Beast/3", "https://api.scryfall.com/cards/tm3c/16/en?format=image");
            put("M3C/Boar", "https://api.scryfall.com/cards/tm3c/17/en?format=image");
            put("M3C/Construct", "https://api.scryfall.com/cards/tm3c/24/en?format=image");
            put("M3C/Dragon", "https://api.scryfall.com/cards/tm3c/12/en?format=image");
            put("M3C/Eldrazi", "https://api.scryfall.com/cards/tm3c/1/en?format=image");
            put("M3C/Eldrazi Angel", "https://api.scryfall.com/cards/tm3c/2/en?format=image");
            put("M3C/Eldrazi Scion", "https://api.scryfall.com/cards/tm3c/3/en?format=image");
            put("M3C/Elephant", "https://api.scryfall.com/cards/tm3c/18/en?format=image");
            put("M3C/Forest Dryad", "https://api.scryfall.com/cards/tm3c/19/en?format=image");
            put("M3C/Emblem Garruk", "https://api.scryfall.com/cards/tm3c/27/en?format=image");
            put("M3C/Goblin", "https://api.scryfall.com/cards/tm3c/13/en?format=image");
            put("M3C/Hydra", "https://api.scryfall.com/cards/tm3c/20/en?format=image");
            put("M3C/Illusion", "https://api.scryfall.com/cards/tm3c/6/en?format=image");
            put("M3C/Marit Lage", "https://api.scryfall.com/cards/tm3c/11/en?format=image");
            put("M3C/Myr", "https://api.scryfall.com/cards/tm3c/25/en?format=image");
            put("M3C/Phyrexian Myr", "https://api.scryfall.com/cards/tm3c/7/en?format=image");
            put("M3C/Sand Warrior", "https://api.scryfall.com/cards/tm3c/23/en?format=image");
            put("M3C/Saproling", "https://api.scryfall.com/cards/tm3c/21/en?format=image");
            put("M3C/Shapeshifter/1", "https://api.scryfall.com/cards/tm3c/4/en?format=image");
            put("M3C/Shapeshifter/2", "https://api.scryfall.com/cards/tm3c/8/en?format=image");
            put("M3C/Spirit", "https://api.scryfall.com/cards/tm3c/5/en?format=image");
            put("M3C/Tarmogoyf", "https://api.scryfall.com/cards/tm3c/22/en?format=image");
            put("M3C/Thopter", "https://api.scryfall.com/cards/tm3c/26/en?format=image");
            put("M3C/Emblem Vivien", "https://api.scryfall.com/cards/tm3c/28/en?format=image");

            // BLB
            put("BLB/Bat", "https://api.scryfall.com/cards/tblb/10/en?format=image");
            put("BLB/Cragflame", "https://api.scryfall.com/cards/tblb/26/en?format=image");
            put("BLB/Fish", "https://api.scryfall.com/cards/tblb/7/en?format=image");
            put("BLB/Food", "https://api.scryfall.com/cards/tblb/27/en?format=image");
            put("BLB/Otter", "https://api.scryfall.com/cards/tblb/25/en?format=image");
            put("BLB/Rabbit", "https://api.scryfall.com/cards/tblb/3/en?format=image");
            put("BLB/Emblem Ral", "https://api.scryfall.com/cards/tblb/30/en?format=image");
            put("BLB/Rat", "https://api.scryfall.com/cards/tblb/13/en?format=image");
            put("BLB/Snail", "https://api.scryfall.com/cards/tblb/14/en?format=image");
            put("BLB/Squirrel", "https://api.scryfall.com/cards/tblb/23/en?format=image");
            put("BLB/Sword", "https://api.scryfall.com/cards/tblb/28/en?format=image");
            put("BLB/Treasure", "https://api.scryfall.com/cards/tblb/29/en?format=image");
            put("BLB/Wall", "https://api.scryfall.com/cards/tblb/4/en?format=image");

            // BLC
            put("BLC/Beast/1", "https://api.scryfall.com/cards/tblc/24/en?format=image");
            put("BLC/Beast/2", "https://api.scryfall.com/cards/tblc/25/en?format=image");
            put("BLC/Bird/1", "https://api.scryfall.com/cards/tblc/11/en?format=image");
            put("BLC/Bird/2", "https://api.scryfall.com/cards/tblc/3/en?format=image");
            put("BLC/Blood", "https://api.scryfall.com/cards/tblc/36/en?format=image");
            put("BLC/Cat", "https://api.scryfall.com/cards/tblc/26/en?format=image");
            put("BLC/Citizen", "https://api.scryfall.com/cards/tblc/33/en?format=image");
            put("BLC/Clue", "https://api.scryfall.com/cards/tblc/37/en?format=image");
            put("BLC/Eldrazi", "https://api.scryfall.com/cards/tblc/2/en?format=image");
            put("BLC/Elemental", "https://api.scryfall.com/cards/tblc/4/en?format=image");
            put("BLC/Elephant", "https://api.scryfall.com/cards/tblc/27/en?format=image");
            put("BLC/Faerie", "https://api.scryfall.com/cards/tblc/12/en?format=image");
            put("BLC/Frog Lizard", "https://api.scryfall.com/cards/tblc/28/en?format=image");
            put("BLC/Goat", "https://api.scryfall.com/cards/tblc/5/en?format=image");
            put("BLC/Goblin", "https://api.scryfall.com/cards/tblc/21/en?format=image");
            put("BLC/Hamster", "https://api.scryfall.com/cards/tblc/22/en?format=image");
            put("BLC/Human", "https://api.scryfall.com/cards/tblc/6/en?format=image");
            put("BLC/Human Soldier", "https://api.scryfall.com/cards/tblc/7/en?format=image");
            put("BLC/Illusion", "https://api.scryfall.com/cards/tblc/13/en?format=image");
            put("BLC/Octopus", "https://api.scryfall.com/cards/tblc/14/en?format=image");
            put("BLC/Pest", "https://api.scryfall.com/cards/tblc/34/en?format=image");
            put("BLC/Phyrexian Golem", "https://api.scryfall.com/cards/tblc/38/en?format=image");
            put("BLC/Raccoon", "https://api.scryfall.com/cards/tblc/29/en?format=image");
            put("BLC/Rat", "https://api.scryfall.com/cards/tblc/19/en?format=image");
            put("BLC/Saproling", "https://api.scryfall.com/cards/tblc/30/en?format=image");
            put("BLC/Shapeshifter", "https://api.scryfall.com/cards/tblc/15/en?format=image");
            put("BLC/Shark", "https://api.scryfall.com/cards/tblc/16/en?format=image");
            put("BLC/Soldier", "https://api.scryfall.com/cards/tblc/8/en?format=image");
            put("BLC/Spider", "https://api.scryfall.com/cards/tblc/31/en?format=image");
            put("BLC/Spirit", "https://api.scryfall.com/cards/tblc/9/en?format=image");
            put("BLC/Squid", "https://api.scryfall.com/cards/tblc/17/en?format=image");
            put("BLC/Storm Crow", "https://api.scryfall.com/cards/tblc/18/en?format=image");
            put("BLC/Thopter", "https://api.scryfall.com/cards/tblc/39/en?format=image");
            put("BLC/Wolf/1", "https://api.scryfall.com/cards/tblc/35/en?format=image");
            put("BLC/Wolf/2", "https://api.scryfall.com/cards/tblc/32/en?format=image");

            // DSK
            put("DSK/Beast", "https://api.scryfall.com/cards/tdsk/3?format=image");
            put("DSK/Demon", "https://api.scryfall.com/cards/tdsk/9?format=image");
            put("DSK/Emblem Kaito", "https://api.scryfall.com/cards/tdsk/17/en?format=image");
            put("DSK/Everywhere", "https://api.scryfall.com/cards/tdsk/16?format=image");
            put("DSK/Glimmer", "https://api.scryfall.com/cards/tdsk/4?format=image");
            put("DSK/Gremlin", "https://api.scryfall.com/cards/tdsk/11?format=image");
            put("DSK/Horror", "https://api.scryfall.com/cards/tdsk/10?format=image");
            put("DSK/Insect/1", "https://api.scryfall.com/cards/tdsk/13?format=image");
            put("DSK/Insect/2", "https://api.scryfall.com/cards/tdsk/5?format=image");
            put("DSK/Primo, the Indivisible", "https://api.scryfall.com/cards/tdsk/14?format=image");
            put("DSK/Shard", "https://api.scryfall.com/cards/tdsk/2?format=image");
            put("DSK/Spider", "https://api.scryfall.com/cards/tdsk/12?format=image");
            put("DSK/Spirit/1", "https://api.scryfall.com/cards/tdsk/6?format=image");
            put("DSK/Spirit/2", "https://api.scryfall.com/cards/tdsk/8?format=image");
            put("DSK/Toy", "https://api.scryfall.com/cards/tdsk/7?format=image");
            put("DSK/Treasure", "https://api.scryfall.com/cards/tdsk/15?format=image");

            // DSC
            put("DSC/Angel", "https://api.scryfall.com/cards/tdsc/2/en?format=image");
            put("DSC/Beast", "https://api.scryfall.com/cards/tdsc/8/en?format=image");
            put("DSC/Bird", "https://api.scryfall.com/cards/tdsc/5/en?format=image");
            put("DSC/Demon", "https://api.scryfall.com/cards/tdsc/6/en?format=image");
            put("DSC/Devil", "https://api.scryfall.com/cards/tdsc/7/en?format=image");
            put("DSC/Elemental", "https://api.scryfall.com/cards/tdsc/9/en?format=image");
            put("DSC/Fractal", "https://api.scryfall.com/cards/tdsc/20/en?format=image");
            put("DSC/Human Soldier", "https://api.scryfall.com/cards/tdsc/3/en?format=image");
            put("DSC/Inkling", "https://api.scryfall.com/cards/tdsc/21/en?format=image");
            put("DSC/Insect/1", "https://api.scryfall.com/cards/tdsc/22/en?format=image");
            put("DSC/Insect/2", "https://api.scryfall.com/cards/tdsc/10/en?format=image");
            put("DSC/Insect/3", "https://api.scryfall.com/cards/tdsc/11/en?format=image");
            put("DSC/Insect/4", "https://api.scryfall.com/cards/tdsc/12/en?format=image");
            put("DSC/Ooze/1", "https://api.scryfall.com/cards/tdsc/14/en?format=image");
            put("DSC/Ooze/2", "https://api.scryfall.com/cards/tdsc/13/en?format=image");
            put("DSC/Phyrexian Beast", "https://api.scryfall.com/cards/tdsc/15/en?format=image");
            put("DSC/Shapeshifter", "https://api.scryfall.com/cards/tdsc/1/en?format=image");
            put("DSC/Shark", "https://api.scryfall.com/cards/tdsc/4/en?format=image");
            put("DSC/Spider", "https://api.scryfall.com/cards/tdsc/16/en?format=image");
            put("DSC/Treefolk", "https://api.scryfall.com/cards/tdsc/17/en?format=image");
            put("DSC/Wurm/1", "https://api.scryfall.com/cards/tdsc/18/en?format=image");
            put("DSC/Wurm/2", "https://api.scryfall.com/cards/tdsc/19/en?format=image");

            // FDN
            put("FDN/Beast/1", "https://api.scryfall.com/cards/tfdn/32/en?format=image");
            put("FDN/Beast/2", "https://api.scryfall.com/cards/tfdn/33/en?format=image");
            put("FDN/Cat/1", "https://api.scryfall.com/cards/tfdn/1/en?format=image");
            put("FDN/Cat/2", "https://api.scryfall.com/cards/tfdn/27/en?format=image");
            put("FDN/Cat/3", "https://api.scryfall.com/cards/tfdn/2/en?format=image");
            put("FDN/Cat Beast", "https://api.scryfall.com/cards/tfdn/28/en?format=image");
            put("FDN/Dog", "https://api.scryfall.com/cards/tfdn/29/en?format=image");
            put("FDN/Dragon/1", "https://api.scryfall.com/cards/tfdn/16/en?format=image");
            put("FDN/Dragon/2", "https://api.scryfall.com/cards/tfdn/17/en?format=image");
            put("FDN/Drake", "https://api.scryfall.com/cards/tfdn/8/en?format=image");
            put("FDN/Elf Warrior", "https://api.scryfall.com/cards/tfdn/19/en?format=image");
            put("FDN/Faerie", "https://api.scryfall.com/cards/tfdn/9/en?format=image");
            put("FDN/Fish", "https://api.scryfall.com/cards/tfdn/10/en?format=image");
            put("FDN/Food", "https://api.scryfall.com/cards/tfdn/22/en?format=image");
            put("FDN/Goblin", "https://api.scryfall.com/cards/tfdn/18/en?format=image");
            put("FDN/Human", "https://api.scryfall.com/cards/tfdn/3/en?format=image");
            put("FDN/Insect", "https://api.scryfall.com/cards/tfdn/21/en?format=image");
            put("FDN/Emblem Kaito", "https://api.scryfall.com/cards/tfdn/24/en?format=image");
            put("FDN/Knight", "https://api.scryfall.com/cards/tfdn/4/en?format=image");
            put("FDN/Koma's Coil", "https://api.scryfall.com/cards/tfdn/11/en?format=image");
            put("FDN/Ninja", "https://api.scryfall.com/cards/tfdn/12/en?format=image");
            put("FDN/Phyrexian Goblin", "https://api.scryfall.com/cards/tfdn/31/en?format=image");
            put("FDN/Rabbit", "https://api.scryfall.com/cards/tfdn/5/en?format=image");
            put("FDN/Raccoon", "https://api.scryfall.com/cards/tfdn/20/en?format=image");
            put("FDN/Rat/1", "https://api.scryfall.com/cards/tfdn/14/en?format=image");
            put("FDN/Rat/2", "https://api.scryfall.com/cards/tfdn/30/en?format=image");
            put("FDN/Scion of the Deep", "https://api.scryfall.com/cards/tfdn/13/en?format=image");
            put("FDN/Soldier", "https://api.scryfall.com/cards/tfdn/6/en?format=image");
            put("FDN/Spirit", "https://api.scryfall.com/cards/tfdn/7/en?format=image");
            put("FDN/Treasure", "https://api.scryfall.com/cards/tfdn/23/en?format=image");
            put("FDN/Emblem Vivien", "https://api.scryfall.com/cards/tfdn/25/en?format=image");
            put("FDN/Zombie", "https://api.scryfall.com/cards/tfdn/15/en?format=image");

            // H17
            put("H17/Dragon", "https://api.scryfall.com/cards/h17/4/en?format=image");

            // INR
            put("INR/Emblem Arlinn", "https://api.scryfall.com/cards/tinr/23/en?format=image");
            put("INR/Blood", "https://api.scryfall.com/cards/tinr/21/en?format=image");
            put("INR/Emblem Chandra", "https://api.scryfall.com/cards/tinr/24/en?format=image");
            put("INR/Clue", "https://api.scryfall.com/cards/tinr/22/en?format=image");
            put("INR/Demon", "https://api.scryfall.com/cards/tinr/6/en?format=image");
            put("INR/Eldrazi Horror", "https://api.scryfall.com/cards/tinr/1/en?format=image");
            put("INR/Elemental", "https://api.scryfall.com/cards/tinr/13/en?format=image");
            put("INR/Human/1", "https://api.scryfall.com/cards/tinr/14/en?format=image");
            put("INR/Human/2", "https://api.scryfall.com/cards/tinr/2/en?format=image");
            put("INR/Human Cleric", "https://api.scryfall.com/cards/tinr/19/en?format=image");
            put("INR/Human Soldier/1", "https://api.scryfall.com/cards/tinr/3/en?format=image");
            put("INR/Human Soldier/2", "https://api.scryfall.com/cards/tinr/20/en?format=image");
            put("INR/Human Wizard", "https://api.scryfall.com/cards/tinr/5/en?format=image");
            put("INR/Insect", "https://api.scryfall.com/cards/tinr/15/en?format=image");
            put("INR/Emblem Jace", "https://api.scryfall.com/cards/tinr/25/en?format=image");
            put("INR/Spider", "https://api.scryfall.com/cards/tinr/16/en?format=image");
            put("INR/Spirit", "https://api.scryfall.com/cards/tinr/4/en?format=image");
            put("INR/Emblem Tamiyo", "https://api.scryfall.com/cards/tinr/26/en?format=image");
            put("INR/Treefolk", "https://api.scryfall.com/cards/tinr/17/en?format=image");
            put("INR/Vampire/1", "https://api.scryfall.com/cards/tinr/7/en?format=image");
            put("INR/Vampire/2", "https://api.scryfall.com/cards/tinr/8/en?format=image");
            put("INR/Wolf/1", "https://api.scryfall.com/cards/tinr/9/en?format=image");
            put("INR/Wolf/2", "https://api.scryfall.com/cards/tinr/18/en?format=image");
            put("INR/Emblem Wrenn", "https://api.scryfall.com/cards/tinr/27/en?format=image");
            put("INR/Zombie/1", "https://api.scryfall.com/cards/tinr/12/en?format=image");
            put("INR/Zombie/2", "https://api.scryfall.com/cards/tinr/10/en?format=image");
            put("INR/Zombie/3", "https://api.scryfall.com/cards/tinr/11/en?format=image");

            // DFT
            put("DFT/Cat", "https://api.scryfall.com/cards/tdft/2/en?format=image");
            put("DFT/Emblem Chandra", "https://api.scryfall.com/cards/tdft/13/en?format=image");
            put("DFT/Dinosaur Dragon", "https://api.scryfall.com/cards/tdft/4/en?format=image");
            put("DFT/Elephant", "https://api.scryfall.com/cards/tdft/6/en?format=image");
            put("DFT/Goblin", "https://api.scryfall.com/cards/tdft/5/en?format=image");
            put("DFT/Insect", "https://api.scryfall.com/cards/tdft/7/en?format=image");
            put("DFT/Pilot", "https://api.scryfall.com/cards/tdft/1/en?format=image");
            put("DFT/Servo", "https://api.scryfall.com/cards/tdft/8/en?format=image");
            put("DFT/Thopter/1", "https://api.scryfall.com/cards/tdft/9/en?format=image");
            put("DFT/Thopter/2", "https://api.scryfall.com/cards/tdft/10/en?format=image");
            put("DFT/Treasure", "https://api.scryfall.com/cards/tdft/11/en?format=image");
            put("DFT/Vehicle", "https://api.scryfall.com/cards/tdft/12/en?format=image");
            put("DFT/Zombie", "https://api.scryfall.com/cards/tdft/3/en?format=image");

            // DRC
            put("DRC/Beast/1", "https://api.scryfall.com/cards/tdrc/10/en?format=image");
            put("DRC/Beast/2", "https://api.scryfall.com/cards/tdrc/11/en?format=image");
            put("DRC/Construct", "https://api.scryfall.com/cards/tdrc/12/en?format=image");
            put("DRC/Golem/1", "https://api.scryfall.com/cards/tdrc/13/en?format=image");
            put("DRC/Golem/2", "https://api.scryfall.com/cards/tdrc/14/en?format=image");
            put("DRC/Golem/3", "https://api.scryfall.com/cards/tdrc/15/en?format=image");
            put("DRC/Nalaar Aetherjet", "https://api.scryfall.com/cards/tdrc/16/en?format=image");
            put("DRC/Shapeshifter", "https://api.scryfall.com/cards/tdrc/4/en?format=image");
            put("DRC/Zombie/1", "https://api.scryfall.com/cards/tdrc/3/en?format=image");
            put("DRC/Zombie/2", "https://api.scryfall.com/cards/tdrc/7/en?format=image");
            put("DRC/Zombie Army", "https://api.scryfall.com/cards/tdrc/8/en?format=image");
            put("DRC/Zombie Warrior", "https://api.scryfall.com/cards/tdrc/9/en?format=image");

            // TDM
            put("TDM/Bird", "https://api.scryfall.com/cards/ttdm/2/en?format=image");
            put("TDM/Dragon", "https://api.scryfall.com/cards/ttdm/11/en?format=image");
            put("TDM/Elephant", "https://api.scryfall.com/cards/ttdm/14/en?format=image");
            put("TDM/Goblin", "https://api.scryfall.com/cards/ttdm/12/en?format=image");
            put("TDM/Monk", "https://api.scryfall.com/cards/ttdm/3/en?format=image");
            put("TDM/Reliquary Dragon", "https://api.scryfall.com/cards/ttdm/15/en?format=image");
            put("TDM/Soldier/1", "https://api.scryfall.com/cards/ttdm/4/en?format=image");
            put("TDM/Soldier/2", "https://api.scryfall.com/cards/ttdm/5/en?format=image");
            put("TDM/Spirit/1", "https://api.scryfall.com/cards/ttdm/9/en?format=image");
            put("TDM/Spirit/2", "https://api.scryfall.com/cards/ttdm/6/en?format=image");
            // TODO: 2/2 and 3/3 Spirit tokens (no relevant cards revealed, token not implemented)
            put("TDM/Treasure", "https://api.scryfall.com/cards/ttdm/16/en?format=image");
            put("TDM/Warrior", "https://api.scryfall.com/cards/ttdm/13/en?format=image");
            put("TDM/Zombie Druid", "https://api.scryfall.com/cards/ttdm/10/en?format=image");

            // TDC
            put("TDC/Angel", "https://api.scryfall.com/cards/ttdc/2/en?format=image");
            put("TDC/Beast", "https://api.scryfall.com/cards/ttdc/20?format=image");
            put("TDC/Citizen", "https://api.scryfall.com/cards/ttdc/26/en?format=image");
            put("TDC/Dog", "https://api.scryfall.com/cards/ttdc/3/en?format=image");
            put("TDC/Dragon/1", "https://api.scryfall.com/cards/ttdc/13?format=image");
            put("TDC/Dragon/2", "https://api.scryfall.com/cards/ttdc/14?format=image");
            put("TDC/Dragon Egg", "https://api.scryfall.com/cards/ttdc/12?format=image");
            put("TDC/Dragon Illusion", "https://api.scryfall.com/cards/ttdc/15/en?format=image");
            put("TDC/Eldrazi", "https://api.scryfall.com/cards/ttdc/1/en?format=image");
            put("TDC/Elemental/1", "https://api.scryfall.com/cards/ttdc/16/en?format=image");
            put("TDC/Elemental/2", "https://api.scryfall.com/cards/ttdc/17/en?format=image");
            put("TDC/Elemental/3", "https://api.scryfall.com/cards/ttdc/27/en?format=image");
            put("TDC/First Mate Ragavan", "https://api.scryfall.com/cards/ttdc/18/en?format=image");
            put("TDC/Frog Lizard", "https://api.scryfall.com/cards/ttdc/21?format=image");
            put("TDC/Goat", "https://api.scryfall.com/cards/ttdc/4/en?format=image");
            put("TDC/Gold", "https://api.scryfall.com/cards/ttdc/29/en?format=image");
            put("TDC/Human", "https://api.scryfall.com/cards/ttdc/5/en?format=image");
            put("TDC/Inkling", "https://api.scryfall.com/cards/ttdc/28?format=image");
            put("TDC/Insect/1", "https://api.scryfall.com/cards/ttdc/22/en?format=image");
            put("TDC/Insect/2", "https://api.scryfall.com/cards/ttdc/23/en?format=image");
            put("TDC/Karox Bladewing", "https://api.scryfall.com/cards/ttdc/19?format=image");
            put("TDC/Myr", "https://api.scryfall.com/cards/ttdc/30/en?format=image");
            put("TDC/Plant", "https://api.scryfall.com/cards/ttdc/24/en?format=image");
            put("TDC/Rat", "https://api.scryfall.com/cards/ttdc/9/en?format=image");
            put("TDC/Salamander Warrior", "https://api.scryfall.com/cards/ttdc/8/en?format=image");
            put("TDC/Servo", "https://api.scryfall.com/cards/ttdc/31/en?format=image");
            put("TDC/Snake", "https://api.scryfall.com/cards/ttdc/10/en?format=image");
            put("TDC/Soldier", "https://api.scryfall.com/cards/ttdc/32/en?format=image");
            put("TDC/Spider", "https://api.scryfall.com/cards/ttdc/25?format=image");
            put("TDC/Spirit", "https://api.scryfall.com/cards/ttdc/6/en?format=image");
            put("TDC/Thopter", "https://api.scryfall.com/cards/ttdc/33/en?format=image");
            put("TDC/Wall", "https://api.scryfall.com/cards/ttdc/7/en?format=image");

            // ACR
            put("ACR/Assassin", "https://api.scryfall.com/cards/tacr/4?format=image");
            put("ACR/Emblem Capitoline Triad", "https://api.scryfall.com/cards/tacr/7/en?format=image");
            put("ACR/Human Rogue", "https://api.scryfall.com/cards/tacr/3?format=image");
            put("ACR/Phobos", "https://api.scryfall.com/cards/tacr/5?format=image");
            put("ACR/Shapeshifter", "https://api.scryfall.com/cards/tacr/2?format=image");
            put("ACR/Treasure", "https://api.scryfall.com/cards/tacr/6?format=image");

            // DD2
            put("DD2/Elemental Shaman", "https://api.scryfall.com/cards/tdd2/1?format=image");            

            // FIN
            put("FIN/Hero/1", "https://api.scryfall.com/cards/tfin/2/en?format=image");
            put("FIN/Hero/2", "https://api.scryfall.com/cards/tfin/3/en?format=image");
            put("FIN/Hero/3", "https://api.scryfall.com/cards/tfin/4/en?format=image");
            put("FIN/Hero/4", "https://api.scryfall.com/cards/tfin/5/en?format=image");
            put("FIN/Hero/5", "https://api.scryfall.com/cards/tfin/6/en?format=image");
            put("FIN/Hero/6", "https://api.scryfall.com/cards/tfin/7/en?format=image");
            put("FIN/Hero/7", "https://api.scryfall.com/cards/tfin/8/en?format=image");
            put("FIN/Hero/8", "https://api.scryfall.com/cards/tfin/9/en?format=image");
            put("FIN/Hero/9", "https://api.scryfall.com/cards/tfin/26/en?format=image");
            put("FIN/Hero/10", "https://api.scryfall.com/cards/tfin/27/en?format=image");
            put("FIN/Hero/11", "https://api.scryfall.com/cards/tfin/28/en?format=image");
            put("FIN/Hero/12", "https://api.scryfall.com/cards/tfin/29/en?format=image");
            put("FIN/Hero/13", "https://api.scryfall.com/cards/tfin/30/en?format=image");
            put("FIN/Hero/14", "https://api.scryfall.com/cards/tfin/31/en?format=image");
            put("FIN/Hero/15", "https://api.scryfall.com/cards/tfin/32/en?format=image");
            put("FIN/Hero/16", "https://api.scryfall.com/cards/tfin/33/en?format=image");
            put("FIN/Knight", "https://api.scryfall.com/cards/tfin/10/en?format=image");
            put("FIN/Moogle/1", "https://api.scryfall.com/cards/tfin/11/en?format=image");
            put("FIN/Moogle/2", "https://api.scryfall.com/cards/tfin/34/en?format=image");
            put("FIN/Robot", "https://api.scryfall.com/cards/tfin/12/en?format=image");
            put("FIN/Horror", "https://api.scryfall.com/cards/tfin/13/en?format=image");
            put("FIN/Wizard/1", "https://api.scryfall.com/cards/tfin/14/en?format=image");
            put("FIN/Wizard/2", "https://api.scryfall.com/cards/tfin/15/en?format=image");
            put("FIN/Wizard/3", "https://api.scryfall.com/cards/tfin/35/en?format=image");
            put("FIN/Bird/1", "https://api.scryfall.com/cards/tfin/16/en?format=image");
            put("FIN/Bird/2", "https://api.scryfall.com/cards/tfin/17/en?format=image");
            put("FIN/Frog", "https://api.scryfall.com/cards/tfin/18/en?format=image");
            put("FIN/Angelo", "https://api.scryfall.com/cards/tfin/19/en?format=image");
            put("FIN/Darkstar", "https://api.scryfall.com/cards/tfin/20/en?format=image");
            put("FIN/Elemental", "https://api.scryfall.com/cards/tfin/21/en?format=image");
            put("FIN/Food", "https://api.scryfall.com/cards/tfin/22?format=image");
            put("FIN/Treasure/1", "https://api.scryfall.com/cards/tfin/23/en?format=image");
            put("FIN/Treasure/2", "https://api.scryfall.com/cards/tfin/36/en?format=image");
            put("FIN/Emblem Sephiroth", "https://api.scryfall.com/cards/tfin/24/en?format=image");

            // FIC
            put("FIC/Human Soldier", "https://api.scryfall.com/cards/tfic/1/en?format=image");
            put("FIC/Soldier", "https://api.scryfall.com/cards/tfic/2/en?format=image");
            put("FIC/Spirit", "https://api.scryfall.com/cards/tfic/3/en?format=image");
            put("FIC/Bird", "https://api.scryfall.com/cards/tfic/4/en?format=image");
            put("FIC/Squid", "https://api.scryfall.com/cards/tfic/5/en?format=image");
            put("FIC/Zombie", "https://api.scryfall.com/cards/tfic/6/en?format=image");
            put("FIC/Rebel", "https://api.scryfall.com/cards/tfic/7/en?format=image");
            put("FIC/The Blackjack", "https://api.scryfall.com/cards/tfic/8/en?format=image");
            put("FIC/Clue", "https://api.scryfall.com/cards/tfic/9/en?format=image");

            // EOE
            put("EOE/Drone", "https://api.scryfall.com/cards/teoe/3?format=image");
            put("EOE/Emblem Tezzeret", "https://api.scryfall.com/cards/teoe/11?format=image");
            put("EOE/Human Soldier", "https://api.scryfall.com/cards/teoe/2?format=image");
            put("EOE/Lander/1", "https://api.scryfall.com/cards/teoe/4?format=image");
            put("EOE/Lander/2", "https://api.scryfall.com/cards/teoe/5?format=image");
            put("EOE/Lander/3", "https://api.scryfall.com/cards/teoe/6?format=image");
            put("EOE/Lander/4", "https://api.scryfall.com/cards/teoe/7?format=image");
            put("EOE/Lander/5", "https://api.scryfall.com/cards/teoe/8?format=image");
            put("EOE/Munitions", "https://api.scryfall.com/cards/teoe/9?format=image");
            put("EOE/Robot", "https://api.scryfall.com/cards/teoe/10?format=image");
            put("EOE/Sliver", "https://api.scryfall.com/cards/teoe/1?format=image");

            // EOC
            put("EOC/Beast/1", "https://api.scryfall.com/cards/teoc/5/en?format=image");
            put("EOC/Beast/2", "https://api.scryfall.com/cards/teoc/6/en?format=image");
            put("EOC/Bird", "https://api.scryfall.com/cards/teoc/3/en?format=image");
            put("EOC/Clue", "https://api.scryfall.com/cards/teoc/10/en?format=image");
            put("EOC/Elemental/1", "https://api.scryfall.com/cards/teoc/7/en?format=image");
            put("EOC/Elemental/2", "https://api.scryfall.com/cards/teoc/8/en?format=image");
            put("EOC/Gnome", "https://api.scryfall.com/cards/teoc/11/en?format=image");
            put("EOC/Golem/1", "https://api.scryfall.com/cards/teoc/12/en?format=image");
            put("EOC/Golem/2", "https://api.scryfall.com/cards/teoc/13/en?format=image");
            put("EOC/Golem/3", "https://api.scryfall.com/cards/teoc/14/en?format=image");
            put("EOC/Incubator", "https://api.scryfall.com/cards/teoc/15/en?format=image&face=front");
            put("EOC/Insect", "https://api.scryfall.com/cards/teoc/4/en?format=image");
            put("EOC/Pest", "https://api.scryfall.com/cards/teoc/9/en?format=image");
            put("EOC/Phyrexian", "https://api.scryfall.com/cards/teoc/15/en?format=image&face=back");
            put("EOC/Shapeshifter", "https://api.scryfall.com/cards/teoc/2/en?format=image");
            put("EOC/Thopter", "https://api.scryfall.com/cards/teoc/16/en?format=image");

            // SPM
            put("SPM/Food", "https://api.scryfall.com/cards/tspm/5?format=image");
            put("SPM/Human", "https://api.scryfall.com/cards/tspm/4?format=image");
            put("SPM/Illusion", "https://api.scryfall.com/cards/tspm/2?format=image");
            put("SPM/Robot", "https://api.scryfall.com/cards/tspm/6?format=image");
            put("SPM/Spider", "https://api.scryfall.com/cards/tspm/3?format=image");
            put("SPM/Treasure", "https://api.scryfall.com/cards/tspm/7?format=image");

            // JVC
            put("JVC/Elemental Shaman", "https://api.scryfall.com/cards/tjvc/4?format=image");

            // PIP
            put("PIP/Alien", "https://api.scryfall.com/cards/tpip/6?format=image");
            put("PIP/Clue", "https://api.scryfall.com/cards/tpip/11?format=image");
            put("PIP/Food/1", "https://api.scryfall.com/cards/tpip/12?format=image");
            put("PIP/Food/2", "https://api.scryfall.com/cards/tpip/13?format=image");
            put("PIP/Food/3", "https://api.scryfall.com/cards/tpip/14?format=image");
            put("PIP/Human Knight", "https://api.scryfall.com/cards/tpip/2?format=image");
            put("PIP/Human Soldier", "https://api.scryfall.com/cards/tpip/3?format=image");
            put("PIP/Junk", "https://api.scryfall.com/cards/tpip/15?format=image");
            put("PIP/Robot", "https://api.scryfall.com/cards/tpip/16?format=image");
            put("PIP/Settlement", "https://api.scryfall.com/cards/tpip/8?format=image");
            put("PIP/Soldier/1", "https://api.scryfall.com/cards/tpip/10?format=image");
            put("PIP/Soldier/2", "https://api.scryfall.com/cards/tpip/4?format=image");
            put("PIP/Squirrel", "https://api.scryfall.com/cards/tpip/9?format=image");
            put("PIP/Thopter", "https://api.scryfall.com/cards/tpip/17?format=image");
            put("PIP/Treasure/1", "https://api.scryfall.com/cards/tpip/18?format=image");
            put("PIP/Treasure/2", "https://api.scryfall.com/cards/tpip/19?format=image");
            put("PIP/Warrior", "https://api.scryfall.com/cards/tpip/5?format=image");
            put("PIP/Wasteland Survival Guide", "https://api.scryfall.com/cards/tpip/20?format=image");
            put("PIP/Zombie Mutant", "https://api.scryfall.com/cards/tpip/7?format=image");

            // REX
            put("REX/Dinosaur", "https://api.scryfall.com/cards/trex/1?format=image");
            put("REX/Treasure", "https://api.scryfall.com/cards/trex/2?format=image");

            // UGL
            put("UGL/Goblin", "https://api.scryfall.com/cards/tugl/4?format=image");
            put("UGL/Pegasus", "https://api.scryfall.com/cards/tugl/1?format=image");
            put("UGL/Rabid Sheep", "https://api.scryfall.com/cards/tugl/5?format=image");
            put("UGL/Soldier", "https://api.scryfall.com/cards/tugl/2?format=image");
            put("UGL/Squirrel", "https://api.scryfall.com/cards/tugl/6?format=image");
            put("UGL/Zombie", "https://api.scryfall.com/cards/tugl/3?format=image");

            // UST
            put("UST/Angel", "https://api.scryfall.com/cards/tust/1?format=image");
            put("UST/Beast", "https://api.scryfall.com/cards/tust/13?format=image");
            put("UST/Brainiac", "https://api.scryfall.com/cards/tust/10?format=image");
            put("UST/Clue", "https://api.scryfall.com/cards/tust/18?format=image");
            put("UST/Dragon", "https://api.scryfall.com/cards/tust/16?format=image");
            put("UST/Elemental/1", "https://api.scryfall.com/cards/tust/11?format=image");
            put("UST/Elemental/2", "https://api.scryfall.com/cards/tust/17?format=image");
            put("UST/Gnome", "https://api.scryfall.com/cards/tust/20?format=image");
            put("UST/Goat", "https://api.scryfall.com/cards/tust/2?format=image");
            put("UST/Goblin", "https://api.scryfall.com/cards/tust/12?format=image");
            put("UST/Saproling", "https://api.scryfall.com/cards/tust/14?format=image");
            put("UST/Spirit", "https://api.scryfall.com/cards/tust/3?format=image");
            put("UST/Squirrel", "https://api.scryfall.com/cards/tust/15?format=image");
            put("UST/Storm Crow", "https://api.scryfall.com/cards/tust/5?format=image");
            put("UST/Thopter", "https://api.scryfall.com/cards/tust/6?format=image");
            put("UST/Vampire", "https://api.scryfall.com/cards/tust/8?format=image");
            put("UST/Zombie", "https://api.scryfall.com/cards/tust/9?format=image");

            // F12
            put("F12/Human", "https://api.scryfall.com/cards/f12/1a?format=image");
            put("F12/Wolf", "https://api.scryfall.com/cards/f12/1a?format=image&face=back");

            // F17
            put("F17/Dinosaur", "https://api.scryfall.com/cards/f17/11?format=image");
            put("F17/Pirate", "https://api.scryfall.com/cards/f17/12?format=image");
            put("F17/Vampire", "https://api.scryfall.com/cards/f17/10?format=image");
            put("F17/Treasure/1", "https://api.scryfall.com/cards/f17/11?format=image&face=back");
            put("F17/Treasure/2", "https://api.scryfall.com/cards/f17/12?format=image&face=back");
            put("F17/Treasure/3", "https://api.scryfall.com/cards/f17/10?format=image&face=back");

            // HHO
            put("HHO/Treasure", "https://api.scryfall.com/cards/hho/21★?format=image");

            // J12
            put("J12/Centaur", "https://api.scryfall.com/cards/j12/9?format=image");

            // J13
            put("J13/Golem", "https://api.scryfall.com/cards/j13/9?format=image");

            // MPR
            put("MPR/Bear", "https://api.scryfall.com/cards/mpr/7?format=image");
            put("MPR/Beast", "https://api.scryfall.com/cards/mpr/8?format=image");
            put("MPR/Bird", "https://api.scryfall.com/cards/mpr/4?format=image");
            put("MPR/Elephant", "https://api.scryfall.com/cards/mpr/3?format=image");
            put("MPR/Goblin Soldier", "https://api.scryfall.com/cards/mpr/6?format=image");
            put("MPR/Saproling", "https://api.scryfall.com/cards/mpr/2?format=image");
            put("MPR/Spirit", "https://api.scryfall.com/cards/mpr/5?format=image");

            // P03
            put("P03/Bear", "https://api.scryfall.com/cards/p03/4?format=image");
            put("P03/Demon", "https://api.scryfall.com/cards/p03/6?format=image");
            put("P03/Goblin", "https://api.scryfall.com/cards/p03/5?format=image");
            put("P03/Insect", "https://api.scryfall.com/cards/p03/2?format=image");
            put("P03/Bird", "https://api.scryfall.com/cards/p03/7?format=image");
            put("P03/Sliver", "https://api.scryfall.com/cards/p03/3?format=image");

            // P04
            put("P04/Angel", "https://api.scryfall.com/cards/p04/2?format=image");
            put("P04/Beast", "https://api.scryfall.com/cards/p04/5?format=image");
            put("P04/Myr", "https://api.scryfall.com/cards/p04/4?format=image");
            put("P04/Pentavite", "https://api.scryfall.com/cards/p04/3?format=image");
            put("P04/Spirit", "https://api.scryfall.com/cards/p04/6?format=image");

            // PEMN
            put("PEMN/Zombie/1", "https://api.scryfall.com/cards/pemn/1Z?format=image");
            put("PEMN/Zombie/2", "https://api.scryfall.com/cards/pemn/1Z?format=image&face=back");

            // PHEL
            put("PHEL/Angel", "https://api.scryfall.com/cards/phel/1★?format=image");

            // PL21
            put("PL21/Minotaur", "https://api.scryfall.com/cards/pl21/2★?format=image");

            // PL23
            put("PL23/Food", "https://api.scryfall.com/cards/pl23/2?format=image");

            // PL24
            put("PL24/Dragon", "https://api.scryfall.com/cards/pl24/3?format=image");

            // PL25
            put("PL25/Snake", "https://api.scryfall.com/cards/pl25/2?format=image");

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

    public static String findTokenLink(String setCode, String tokenName, Integer imageNumber) {
        String search = setCode + "/" + tokenName + (!imageNumber.equals(0) ? "/" + imageNumber : "");
        return supportedCards.getOrDefault(search, null);
    }
}
