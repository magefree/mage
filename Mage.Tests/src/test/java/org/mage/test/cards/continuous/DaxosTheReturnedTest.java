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
package org.mage.test.cards.continuous;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DaxosTheReturnedTest extends CardTestPlayerBase {

    /**
     * Daxos the Returned 's spirit tokens are not counted as enchantment tokens
     * and do not count towards experience counters like they should.
     */
    @Test
    public void testCounterAddAndTokenStates() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Whenever you cast an enchantment spell, you get an experience counter.
        // {1}{W}{B}: Create a white and black Spirit enchantment creature token onto the battlefield. It has
        // "This creature's power and toughness are each equal to the number of experience counters you have."
        addCard(Zone.BATTLEFIELD, playerA, "Daxos the Returned");

        // Whenever an opponent draws a card, Underworld Dreams deals 1 damage to him or her.
        addCard(Zone.HAND, playerA, "Underworld Dreams", 2); // {B}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Underworld Dreams");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Underworld Dreams");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}{W}{B}");
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);

        assertPermanentCount(playerA, "Underworld Dreams", 2);
        assertCounterCount(playerA, CounterType.EXPERIENCE, 2);
        assertPowerToughness(playerA, "Spirit", 2, 2, Filter.ComparisonScope.All);
        assertType("Spirit", CardType.ENCHANTMENT, "Spirit");

    }

}
