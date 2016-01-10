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
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AjaniTest extends CardTestPlayerBase {

    @Test
    public void CastAjani() {
        // +1: You gain 2 life.
        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        // -6: Put a white Avatar creature token onto the battlefield. It has "This creature's power and toughness are each equal to your life total."
        addCard(Zone.HAND, playerA, "Ajani Goldmane"); // {2}{W}{W} starts with 4 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani Goldmane");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: You gain 2 life");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ajani Goldmane", 1);
        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 5);  // 4 + 1 = 5

        assertLife(playerA, 22);
        assertLife(playerB, 20);
    }

    @Test
    public void CastAjaniWithOathOfGideon() {
        // +1: You gain 2 life.
        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        // -6: Put a white Avatar creature token onto the battlefield. It has "This creature's power and toughness are each equal to your life total."
        addCard(Zone.HAND, playerA, "Ajani Goldmane"); // {2}{W}{W} starts with 4 Loyality counters
        // When Oath of Gideon enters the battlefield, put two 1/1 Kor Ally creature tokens onto the battlefield.
        // Each planeswalker you control enters the battlefield with an additional loyalty counter on it.
        addCard(Zone.HAND, playerA, "Oath of Gideon"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oath of Gideon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani Goldmane");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: You gain 2 life");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kor Ally", 2);
        assertPermanentCount(playerA, "Oath of Gideon", 1);
        assertPermanentCount(playerA, "Ajani Goldmane", 1);
        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 6);  // 5 + 1 = 5

        assertLife(playerA, 22);
        assertLife(playerB, 20);
    }

}
