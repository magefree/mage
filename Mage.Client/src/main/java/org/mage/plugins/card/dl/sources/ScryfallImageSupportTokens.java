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
