
package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.SetType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Vintage extends Constructed {

    public Vintage() {
        super("Constructed - Vintage");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType() != SetType.CUSTOM_SET) {
                setCodes.add(set.getCode());
            }
        }
        banned.add("Advantageous Proclamation");
        banned.add("Amulet of Quoz");
        banned.add("Backup Plan");
        banned.add("Brago's Favor");
        banned.add("Bronze Tablet");
        banned.add("Chaos Orb");
        banned.add("Contract from Below");
        banned.add("Darkpact");
        banned.add("Demonic Attorney");
        banned.add("Double Stroke");
        banned.add("Falling Star");
        banned.add("Immediate Action");
        banned.add("Iterative Analysis");
        banned.add("Jeweled Bird");
        banned.add("Muzzio's Preparations");
        banned.add("Power Play");
        banned.add("Rebirth");
        banned.add("Secret Summoning");
        banned.add("Secrets of Paradise");
        banned.add("Sentinel Dispatch");
        banned.add("Shahrazad");
        banned.add("Tempest Efreet");
        banned.add("Timmerian Fiends");
        banned.add("Unexpected Potential");
        banned.add("Worldknit");

        restricted.add("Ancestral Recall");
        restricted.add("Balance");
        restricted.add("Black Lotus");
        restricted.add("Brainstorm");
        restricted.add("Channel");
        restricted.add("Chalice of the Void");
        restricted.add("Demonic Consultation");
        restricted.add("Demonic Tutor");
        restricted.add("Dig Through Time");
        restricted.add("Fastbond");
        restricted.add("Flash");
        restricted.add("Gitaxian Probe");
        restricted.add("Gush");
        restricted.add("Imperial Seal");
        restricted.add("Library of Alexandria");
        restricted.add("Lion's Eye Diamond");
        restricted.add("Lodestone Golem");
        restricted.add("Lotus Petal");
        restricted.add("Mana Crypt");
        restricted.add("Mana Vault");
        restricted.add("Memory Jar");
        restricted.add("Merchant Scroll");
        restricted.add("Mind's Desire");
        restricted.add("Monastery Mentory");
        restricted.add("Mox Emerald");
        restricted.add("Mox Jet");
        restricted.add("Mox Pearl");
        restricted.add("Mox Ruby");
        restricted.add("Mox Sapphire");
        restricted.add("Mystical Tutor");
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
