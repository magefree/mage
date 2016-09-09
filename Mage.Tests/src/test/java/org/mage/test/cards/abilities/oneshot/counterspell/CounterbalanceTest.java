/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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

package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Counterbalance
 * Enchantment, UU
 * Whenever an opponent casts a spell, you may reveal the top card of your library. If you do, counter that spell
 * if it has the same converted mana cost as the revealed card.
 *
 * @author LevelX2
 */
public class CounterbalanceTest extends CardTestPlayerBase {

    /**
     * Test that X mana costs from spells are taken into account to calculate the converted mana costs
     * of stack objects
     */
    @Test
    public void testCommand() {
        addCard(Zone.HAND, playerA, "Death Grasp");
        // Sorcery {X}{W}{B}
        // Death Grasp deals X damage to target creature or player. You gain X life.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Counterbalance");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        addCard(Zone.LIBRARY, playerB, "Desert Twister"); // cmc = 6 ({G}{G}{4} because DeathGrasp = 2 + 4 (of X) = 6
        skipInitShuffling(); // so the set to top card stays at top

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Death Grasp", "targetPlayer=PlayerB");
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Death Grasp", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 0);

    }

    /**
     * Test that if the top card is a split card, both casting costs of the split cards
     * count to counter the spell. If one of the split cards halves has the equal casting
     * cost, the spell is countered.
     *
     */

    @Test
    public void testSplitCard() {
        addCard(Zone.HAND, playerA, "Typhoid Rats");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Counterbalance");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        addCard(Zone.LIBRARY, playerB, "Wear // Tear"); // CMC 2 and 1
        skipInitShuffling(); // so the set to top card stays at top

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Typhoid Rats");
        setChoice(playerB, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Typhoid Rats", 0);
        assertGraveyardCount(playerA, "Typhoid Rats", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 0);

    }
}
