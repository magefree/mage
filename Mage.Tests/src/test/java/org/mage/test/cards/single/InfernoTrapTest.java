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
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class InfernoTrapTest extends CardTestPlayerBase {

    @Test
    public void testTwoDamageStepsCountOnlyAsOneCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // If you've been dealt damage by two or more creatures this turn, you may pay {R} rather than pay Inferno Trap's mana cost.
        // Inferno Trap deals 4 damage to target creature.
        addCard(Zone.HAND, playerA, "Inferno Trap"); // Instant {3}{R}

        // Flying, double strike
        addCard(Zone.BATTLEFIELD, playerB, "Skyhunter Skirmisher"); // 1/1

        attack(2, playerB, "Skyhunter Skirmisher");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Inferno Trap", "Skyhunter Skirmisher");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Inferno Trap", 0);
        assertGraveyardCount(playerB, "Skyhunter Skirmisher", 0);
    }

    @Test
    public void testPlayByAlternateCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // If you've been dealt damage by two or more creatures this turn, you may pay {R} rather than pay Inferno Trap's mana cost.
        // Inferno Trap deals 4 damage to target creature.
        addCard(Zone.HAND, playerA, "Inferno Trap"); // Instant {3}{R}

        // Flying, double strike
        addCard(Zone.BATTLEFIELD, playerB, "Skyhunter Skirmisher"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2

        attack(2, playerB, "Skyhunter Skirmisher");
        attack(2, playerB, "Silvercoat Lion");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Inferno Trap", "Skyhunter Skirmisher");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 16);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Inferno Trap", 1);
        assertGraveyardCount(playerB, "Skyhunter Skirmisher", 1);
    }

}
