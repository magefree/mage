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
package org.mage.test.cards.single.rtr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Jace, Architect of Thought {2}{U}{U} Planeswalker — Jace Loyalty 4 +1: Until
 * your next turn, whenever a creature an opponent controls attacks, it gets
 * -1/-0 until end of turn. -2: Reveal the top three cards of your library. An
 * opponent separates those cards into two piles. Put one pile into your hand
 * and the other on the bottom of your library in any order. -8: For each
 * player, search that player's library for a nonland card and exile it, then
 * that player shuffles his or her library. You may cast those cards without
 * paying their mana costs.
 *
 * @author LevelX2
 */
public class JaceArchitectOfThoughtTest extends CardTestPlayerBase {

    @Test
    public void testAbility1normal() {
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Architect of Thought");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.");

        attack(2, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount("Jace, Architect of Thought", CounterType.LOYALTY, 5);
        assertPowerToughness(playerB, "Silvercoat Lion", 1, 2);

        assertLife(playerA, 19);
        assertLife(playerB, 20);

    }

    @Test
    public void testAbilit1lastOnlyUntilNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Architect of Thought");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.");

        attack(2, playerB, "Silvercoat Lion");
        attack(4, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount("Jace, Architect of Thought", CounterType.LOYALTY, 5);
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }
    /*
     Ability 1 has still to trigger next turn if used also if Jace left the battlefield.
     */

    @Test
    public void testAbility1AfterJacesWasExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Architect of Thought");

        // Sorcery {R}{B}
        // Destroy target creature or planeswalker.
        addCard(Zone.HAND, playerB, "Dreadbore");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // +1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until your next turn");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Dreadbore", "Jace, Architect of Thought");
        attack(2, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Jace, Architect of Thought", 0);
        assertPowerToughness(playerB, "Silvercoat Lion", 1, 2);

    }

}
