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

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Vintage extends Constructed {

	public Vintage() {
		super("Constructed - Vintage");
        
        banned.add("Amulet of Quoz");
        banned.add("Bronze Tablet");
        banned.add("Chaos Orb");
        banned.add("Contract from Below");
        banned.add("Darkpact");
        banned.add("Demonic Attorney");
        banned.add("Falling Star");
        banned.add("Jeweled Bird");
        banned.add("Rebirth");
        banned.add("Shahrazad");
        banned.add("Tempest Efreet");
        banned.add("Timmerian Fiends");

        restricted.add("Ancestral Recall");
        restricted.add("Balance");
        restricted.add("Black Lotus");
        restricted.add("Brainstorm");
        restricted.add("Burning Wish");
        restricted.add("Channel");
        restricted.add("Demonic Consultation");
        restricted.add("Demonic Tutor");
        restricted.add("Fact or Fiction");
        restricted.add("Fastbond");
        restricted.add("Flash");
        restricted.add("Gifts Ungiven");
        restricted.add("Imperial Seal");
        restricted.add("Library of Alexandria");
        restricted.add("Lion's Eye Diamond");
        restricted.add("Lotus Petal");
        restricted.add("Mana Crypt");
        restricted.add("Mana Vault");
        restricted.add("Memory Jar");
        restricted.add("Merchant Scroll");
        restricted.add("Mind's Desire");
        restricted.add("Mox Emerald");
        restricted.add("Mox Jet");
        restricted.add("Mox Pearl");
        restricted.add("Mox Ruby");
        restricted.add("Mox Sapphire");
        restricted.add("Mystical Tutor");
        restricted.add("Necropotence");
        restricted.add("Ponder");
        restricted.add("Regrowth");
        restricted.add("Sol Ring");
        restricted.add("Strip Mine");
        restricted.add("Thirst for Knowledge");
        restricted.add("Time Vault");
        restricted.add("Time Walk");
        restricted.add("Timetwister");
        restricted.add("Tinker");
        restricted.add("Tolarian Academy");
        restricted.add("Trinisphere");
        restricted.add("Vampiric Tutor");
        restricted.add("Wheel of Fortune");
        restricted.add("Windfall");
        restricted.add("Yawgmoth's Bargain");
        restricted.add("Yawgmoth's Will");

	}
}
