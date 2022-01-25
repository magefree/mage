package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

/**
 * LevelX2
 */
public class Legacy extends Constructed {

    public Legacy() {
        super("Constructed - Legacy");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
        banned.add("Advantageous Proclamation");
        banned.add("Arcum's Astrolabe");
        banned.add("Amulet of Quoz");
        banned.add("Ancestral Recall");
        banned.add("Backup Plan");
        banned.add("Balance");
        banned.add("Bazaar of Baghdad");
        banned.add("Black Lotus");
        banned.add("Brago's Favor");
        banned.add("Bronze Tablet");
        banned.add("Channel");
        banned.add("Chaos Orb");
        banned.add("Contract from Below");
        banned.add("Darkpact");
        banned.add("Deathrite Shaman");
        banned.add("Demonic Attorney");
        banned.add("Demonic Consultation");
        banned.add("Demonic Tutor");
        banned.add("Dig Through Time");
        banned.add("Double Stroke");
        banned.add("Dreadhorde Arcanist");
        banned.add("Earthcraft");
        banned.add("Falling Star");
        banned.add("Fastbond");
        banned.add("Flash");
        banned.add("Frantic Search");
        banned.add("Gitaxian Probe");
        banned.add("Goblin Recruiter");
        banned.add("Gush");
        banned.add("Hermit Druid");
        banned.add("Immediate Action");
        banned.add("Imperial Seal");
        banned.add("Iterative Analysis");
        banned.add("Jeweled Bird");
        banned.add("Library of Alexandria");
        banned.add("Lurrus of the Dream-Den");
        banned.add("Mana Crypt");
        banned.add("Mana Drain");
        banned.add("Mana Vault");
        banned.add("Memory Jar");
        banned.add("Mental Misstep");
        banned.add("Mind Twist");
        banned.add("Mind's Desire");
        banned.add("Mishra's Workshop");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Muzzio's Preparations");
        banned.add("Mystical Tutor");
        banned.add("Necropotence");
        banned.add("Oath of Druids");
        banned.add("Oko, Thief of Crowns");
        banned.add("Power Play");
        banned.add("Ragavan, Nimble Pilferer");
        banned.add("Rebirth");
        banned.add("Secret Summoning");
        banned.add("Secrets of Paradise");
        banned.add("Sensei's Divining Top");
        banned.add("Sentinel Dispatch");
        banned.add("Shahrazad");
        banned.add("Skullclamp");
        banned.add("Sol Ring");
        banned.add("Strip Mine");
        banned.add("Survival of the Fittest");
        banned.add("Tempest Efreet");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Timetwister");
        banned.add("Timmerian Fiends");
        banned.add("Tinker");
        banned.add("Tolarian Academy");
        banned.add("Treasure Cruise");
        banned.add("Underworld Breach");
        banned.add("Unexpected Potential");
        banned.add("Vampiric Tutor");
        banned.add("Wheel of Fortune");
        banned.add("Windfall");
        banned.add("Worldknit");
        banned.add("Wrenn and Six");
        banned.add("Yawgmoth's Bargain");
        banned.add("Yawgmoth's Will");
        banned.add("Zirda, the Dawnwaker");
    }
}
