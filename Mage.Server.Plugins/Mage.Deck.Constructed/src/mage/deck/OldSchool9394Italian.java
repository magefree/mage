
package mage.deck;

import mage.cards.decks.Constructed;

/**
 * This class validates a deck for the Old School 93/94 format, specifically for
 * the Italian Rules.
 *
 * This was originally made to follow the deck construction rules found at the
 * Old School Mtg blog found at:
 * http://oldschool-mtg.blogspot.com/p/banrestriction.html
 *
 * There is no mana burn in this version of old school
 *
 * @author jmharmon
 */
public class OldSchool9394Italian extends Constructed{
    
    public OldSchool9394Italian() {
        super("Constructed - Old School 93/94 - Italian Rules");
        
        // use the set instances to make sure that we get the correct set codes
        setCodes.add(mage.sets.LimitedEditionAlpha.getInstance().getCode());
        setCodes.add(mage.sets.LimitedEditionBeta.getInstance().getCode());
        setCodes.add(mage.sets.UnlimitedEdition.getInstance().getCode());
        setCodes.add(mage.sets.ArabianNights.getInstance().getCode());
        setCodes.add(mage.sets.Antiquities.getInstance().getCode());
        setCodes.add(mage.sets.Legends.getInstance().getCode());
        setCodes.add(mage.sets.TheDark.getInstance().getCode());
        setCodes.add(mage.sets.RevisedEdition.getInstance().getCode());
        
        // ante cards and conspiracies banned, with specifically mentioned ones called out.
        banned.add("Advantageous Proclamation");
        banned.add("Amulet of Quoz");
        banned.add("Backup Plan");
        banned.add("Brago's Favor");
        banned.add("Bronze Tablet"); ///
        banned.add("Contract from Below"); ///
        banned.add("Darkpact"); ///
        banned.add("Demonic Attorney"); ///
        banned.add("Double Stroke");
        banned.add("Immediate Action");
        banned.add("Iterative Analysis");
        banned.add("Jeweled Bird"); ///
        banned.add("Muzzio's Preparations");
        banned.add("Power Play");
        banned.add("Rebirth"); ///
        banned.add("Secret Summoning");
        banned.add("Secrets of Paradise");
        banned.add("Sentinel Dispatch");
        banned.add("Shahrazad");
        banned.add("Tempest Efreet"); ///
        banned.add("Timmerian Fiends");
        banned.add("Unexpected Potential");
        banned.add("Worldknit");
        
        restricted.add("Ancestral Recall");
        restricted.add("Balance");
        restricted.add("Black Lotus");
        restricted.add("Braingeyser");
        restricted.add("Channel");
        restricted.add("Chaos Orb");
        restricted.add("Demonic Tutor");
        restricted.add("Library of Alexandria");
        restricted.add("Mana Drain");
        restricted.add("Mind Twist");
        restricted.add("Mishra's Workshop");
        restricted.add("Mox Emerald");
        restricted.add("Mox Jet");
        restricted.add("Mox Pearl");
        restricted.add("Mox Ruby");
        restricted.add("Mox Sapphire");
        restricted.add("Regrowth");
        restricted.add("Shahrazad");
        restricted.add("Sol Ring");
        restricted.add("Strip Mine");
        restricted.add("Time Vault");
        restricted.add("Time Walk");
        restricted.add("Timetwister");
        restricted.add("Wheel of Fortune");
        
    }
}
