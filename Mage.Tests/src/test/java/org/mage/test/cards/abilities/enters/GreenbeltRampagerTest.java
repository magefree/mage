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

package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JRHerlehy
 */
public class GreenbeltRampagerTest extends CardTestPlayerBase {

    @Test
    public void testFirstCast() {
        addCard(Zone.HAND, playerA, "Greenbelt Rampager");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Greenbelt Rampager", 1);
        assertPermanentCount(playerA, "Greenbelt Rampager", 0);
        assertCounterCount(playerA, CounterType.ENERGY, 1);
    }

    @Test
    public void testScondCast() {
        addCard(Zone.HAND, playerA, "Greenbelt Rampager");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Greenbelt Rampager", 1);
        assertPermanentCount(playerA, "Greenbelt Rampager", 0);
        assertCounterCount(playerA, CounterType.ENERGY, 2);
    }

    @Test
    public void testThirdCast() {
        addCard(Zone.HAND, playerA, "Greenbelt Rampager");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Greenbelt Rampager", 0);
        assertPermanentCount(playerA, "Greenbelt Rampager", 1);
        assertCounterCount(playerA, CounterType.ENERGY, 0);
    }

    @Test
    public void testCastNotOwned() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.HAND, playerA, "Gonti, Lord of Luxury");

        addCard(Zone.LIBRARY, playerB, "Greenbelt Rampager");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gonti, Lord of Luxury");
        addTarget(playerA, playerB);
        setChoice(playerA, "Greenbelt Rampager");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, CounterType.ENERGY, 1);
        assertPermanentCount(playerA, "Greenbelt Rampager", 0);
        assertHandCount(playerA, "Greenbelt Rampager", 0);
        assertHandCount(playerB, "Greenbelt Rampager", 1);
    }
}
