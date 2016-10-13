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
package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class StateValuesTest extends CardTestPlayerBase {

    @Test
    public void testDragonWhelpActivatedFourTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Flying
        // {R}: Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon Whelp", 1); // 2/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        attack(1, playerA, "Dragon Whelp");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");

        attack(3, playerA, "Dragon Whelp");

        rollbackTurns(3, PhaseStep.BEGIN_COMBAT, playerA, 0);

        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 12);

        assertPermanentCount(playerA, "Dragon Whelp", 1);
        assertGraveyardCount(playerA, "Dragon Whelp", 0);

    }

    @Test
    public void testBriarbridgePatrol() {
        // Whenever Briarbridge Patrol deals damage to one or more creatures, investigate (Put a colorless Clue artifact token onto the battlefield with "2, Sacrifice this artifact: Draw a card.").
        // At the beginning of each end step, if you sacrificed three or more Clues this turn, you may put a creature card from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Briarbridge Patrol", 1); // 3/3

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1); // 2/2

        attack(1, playerA, "Briarbridge Patrol");
        block(1, playerB, "Pillarfield Ox", "Briarbridge Patrol");

        attack(3, playerA, "Briarbridge Patrol");
        block(3, playerB, "Pillarfield Ox", "Briarbridge Patrol");
        rollbackTurns(3, PhaseStep.POSTCOMBAT_MAIN, playerA, 0);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Clue", 2);

    }
}
