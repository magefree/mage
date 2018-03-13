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
public class DemonicPactTest extends CardTestPlayerBase {

    @Test
    public void testModes() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // At the beginning of your upkeep, choose one that hasn't been chosen
        // (1) - Demonic Pact deals 4 damage to target creature or player and you gain 4 life;
        // (2) - Target opponent discards two cards
        // (3) - Draw two cards
        // (4) - You lose the game.
        addCard(Zone.HAND, playerA, "Demonic Pact"); // Enchantment {2}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Pact");

        setModeChoice(playerA, "3");

        setModeChoice(playerA, "2");
        addTarget(playerA, playerB);

        setModeChoice(playerA, "1");
        addTarget(playerA, playerB);

        setStopAt(7, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Demonic Pact", 1);
        assertLife(playerA, 24);
        assertLife(playerB, 16);
        assertHandCount(playerB, 1); // discard 2 + 3 from regular draws

        assertHandCount(playerA, 5); // two from Demonic Pact + 3 from regular draws

    }

    /*
        The rollback to the start of the turn does not correctly reset the already selected choices from Demonic Pact. They are not available again.
     */
    @Test
    public void testModeAfterRollback() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // At the beginning of your upkeep, choose one that hasn't been chosen
        // (1) - Demonic Pact deals 4 damage to target creature or player and you gain 4 life;
        // (2) - Target opponent discards two cards
        // (3) - Draw two cards
        // (4) - You lose the game.
        addCard(Zone.HAND, playerA, "Demonic Pact"); // Enchantment {2}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Pact");

        setModeChoice(playerA, "1");
        addTarget(playerA, playerB);

        rollbackTurns(3, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1); // 1 from regular draws
        assertHandCount(playerB, 1); // 1 from regular draw

        assertLife(playerA, 24);
        assertLife(playerB, 16);

    }
}
