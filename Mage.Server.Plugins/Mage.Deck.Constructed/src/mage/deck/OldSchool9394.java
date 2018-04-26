/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.SetType;

/**
 * This class validates a deck for the Old School 93/94 format.
 *
 * This was originally made to follow the deck construction rules found at the
 * Old School Mtg blog found at:
 * http://oldschool-mtg.blogspot.com/p/banrestriction.html
 *
 * There is no mana burn in this version of old school.
 *
 * If there are any questions or corrections, feel free to contact me.
 *
 * @author Marthinwurer (at gmail.com)
 */
public class OldSchool9394 extends Constructed {

    public OldSchool9394() {
        super("Constructed - Old School 93/94");

        // use the set instances to make sure that we get the correct set codes
        setCodes.add(mage.sets.LimitedEditionAlpha.getInstance().getCode());
        setCodes.add(mage.sets.LimitedEditionBeta.getInstance().getCode());
        setCodes.add(mage.sets.UnlimitedEdition.getInstance().getCode());
        setCodes.add(mage.sets.ArabianNights.getInstance().getCode());
        setCodes.add(mage.sets.Antiquities.getInstance().getCode());
        setCodes.add(mage.sets.Legends.getInstance().getCode());
        setCodes.add(mage.sets.TheDark.getInstance().getCode());

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
