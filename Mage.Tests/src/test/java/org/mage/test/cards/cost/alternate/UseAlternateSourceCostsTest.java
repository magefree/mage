/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class UseAlternateSourceCostsTest extends CardTestPlayerBase {

    @Test
    public void DreamHallsCastColoredSpell() {
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // Add the mountains so the spell is included in teh available spells

        addCard(Zone.HAND, playerA, "Gray Ogre", 1); // Creature 3/1
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre"); // Cast Orgre by discarding the Lightning Bolt

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //Gray Ogre is cast with the discard
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertTapped("Mountain", false);
    }

    @Test
    public void DreamHallsCantCastColorlessSpell() {
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4); // Add the mountains so the spell is included in teh available spells

        addCard(Zone.HAND, playerA, "Juggernaut", 1); // Creature 5/3 - {4}
        addCard(Zone.HAND, playerA, "Haunted Plate Mail", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Juggernaut"); // Cast Juggernaut by discarding Haunted Plate Mail may not work

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Haunted Plate Mail", 0);
        assertTapped("Mountain", true);
        //Juggernaut is not cast by alternate casting costs
        assertPermanentCount(playerA, "Juggernaut", 1);
    }

    @Test
    public void DreamHallsCastWithFutureSight() {
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);
        // Play with the top card of your library revealed.
        // You may play the top card of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Future Sight", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // Add the mountains so the spell is included in teh available spells

        addCard(Zone.LIBRARY, playerA, "Gray Ogre", 1); // Creature 3/1
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre"); // Cast Orgre by discarding the Lightning Bolt

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Mountain", false);
        //Gray Ogre is cast with the discard
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }
}
