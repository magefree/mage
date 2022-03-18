package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Vintage extends Constructed {

    public Vintage() {
        super("Constructed - Vintage");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
        restricted.add("Ancestral Recall");
        restricted.add("Balance");
        restricted.add("Black Lotus");
        restricted.add("Brainstorm");
        restricted.add("Channel");
        restricted.add("Chalice of the Void");
        restricted.add("Demonic Consultation");
        restricted.add("Demonic Tutor");
        restricted.add("Dig Through Time");
        restricted.add("Flash");
        restricted.add("Gitaxian Probe");
        restricted.add("Golgari Grave-Troll");
        restricted.add("Gush");
        restricted.add("Imperial Seal");
        restricted.add("Karn, the Great Creator");
        restricted.add("Library of Alexandria");
        restricted.add("Lion's Eye Diamond");
        restricted.add("Lodestone Golem");
        restricted.add("Lotus Petal");
        restricted.add("Mana Crypt");
        restricted.add("Mana Vault");
        restricted.add("Memory Jar");
        restricted.add("Mental Misstep");
        restricted.add("Merchant Scroll");
        restricted.add("Mind's Desire");
        restricted.add("Monastery Mentory");
        restricted.add("Mox Emerald");
        restricted.add("Mox Jet");
        restricted.add("Mox Pearl");
        restricted.add("Mox Ruby");
        restricted.add("Mox Sapphire");
        restricted.add("Mystic Forge");
        restricted.add("Mystical Tutor");
        restricted.add("Narset, Parter of Veils");
        restricted.add("Necropotence");
        restricted.add("Ponder");
        restricted.add("Sol Ring");
        restricted.add("Strip Mine");
        restricted.add("Thorn of Amethyst");
        restricted.add("Time Vault");
        restricted.add("Time Walk");
        restricted.add("Timetwister");
        restricted.add("Tinker");
        restricted.add("Tolarian Academy");
        restricted.add("Treasure Cruise");
        restricted.add("Trinisphere");
        restricted.add("Vampiric Tutor");
        restricted.add("Wheel of Fortune");
        restricted.add("Windfall");
        restricted.add("Yawgmoth's Will");
    }
}
