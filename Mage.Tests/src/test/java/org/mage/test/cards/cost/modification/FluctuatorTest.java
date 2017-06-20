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
package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class FluctuatorTest extends CardTestPlayerBase {

    /**
     * NOTE: As of 4/19/2017 this test is failing due to a bug in code. 
     * See issue #3148
     *
     * Fluctuator makes 'Akroma's Vengeance' cyclic cost reduced to {1} Test it
     * with one Plains on battlefield.
     */
    @Test
    public void testFluctuatorReducedBy2() {

        // Destroy all artifacts, creatures, and enchantments.
        // Cycling ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerA, "Akroma's Vengeance");

        // Cycling abilities you activate cost you up to {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fluctuator");

        // One mana should be enough
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, "2"); // reduce 2 generic mana

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Akroma's Vengeance", 1);
        assertHandCount(playerA, 1);
    }

    /**
     * Fluctuator makes 'Akroma's Vengeance' cyclic cost reduced to {1}
     *
     * Make sure it wasn't reduced more than by two.
     */
    @Test
    public void testFluctuatorReducedNotBy3() {

        // Destroy all artifacts, creatures, and enchantments.
        // Cycling ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerA, "Akroma's Vengeance");

        // Cycling abilities you activate cost you up to {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fluctuator");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, "2"); // reduce 1 generic mana

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Akroma's Vengeance", 0);
        assertHandCount(playerA, 1);
    }

    /**
     * NOTE: As of 4/19/2017 this test is failing due to a bug in code. 
     * See issue #3148
     *
     * Test 2 Fluctuators reduce cycling cost up to 4.
     */
    @Test
    public void testTwoFluctuatorsReduceBy4() {

        // Destroy all artifacts, creatures, and enchantments.
        // Cycling ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerA, "Akroma's Vengeance");

        // Cycling abilities you activate cost you up to {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fluctuator", 2); // 2 copies

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, "2"); // reduce 2 generic mana
        setChoice(playerA, "1"); // reduce 1 generic mana

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Akroma's Vengeance", 1);
        assertHandCount(playerA, 1);
    }
}
