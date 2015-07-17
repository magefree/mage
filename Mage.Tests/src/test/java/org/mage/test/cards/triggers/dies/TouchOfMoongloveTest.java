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
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TouchOfMoongloveTest extends CardTestPlayerBase {

    /**
     * I blocked my opponent's Pharika's Disciple with a Cleric of the Forward
     * Order and Guardian Automaton. He cast Touch of Moonglove on his Pharika's
     * Disciple and both of my creatures were killed, but I only lost 2 life
     * instead of 4.(and gained 3 from Guardian Automaton dying).
     *
     */
    @Test
    public void testDiesAndControllerDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // When Cleric of the Forward Order enters the battlefield, you gain 2 life for each creature you control named Cleric of the Forward Order.
        addCard(Zone.HAND, playerA, "Cleric of the Forward Order", 1);
        // When Guardian Automaton dies, you gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Guardian Automaton", 1); // 3/3

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        // Target creature you control gets +1/+0 and gains deathtouch until end of turn.
        // Whenever a creature dealt damage by that creature this turn dies, its controller loses 2 life.
        addCard(Zone.HAND, playerB, "Touch of Moonglove"); // {B}
        addCard(Zone.BATTLEFIELD, playerB, "Pharika's Disciple", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cleric of the Forward Order");
        attack(2, playerB, "Pharika's Disciple");
        block(2, playerA, "Cleric of the Forward Order", "Pharika's Disciple");
        block(2, playerA, "Guardian Automaton", "Pharika's Disciple");

        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerB, "Touch of Moonglove", "Pharika's Disciple");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Touch of Moonglove", 1);
        assertGraveyardCount(playerA, "Cleric of the Forward Order", 1);
        assertGraveyardCount(playerB, "Pharika's Disciple", 1);

        assertGraveyardCount(playerA, "Guardian Automaton", 1);

        assertLife(playerA, 21); // +2 by Cleric + 2x -2 by Touch +3 by Guardian Automation
        assertLife(playerB, 20);

    }

}
