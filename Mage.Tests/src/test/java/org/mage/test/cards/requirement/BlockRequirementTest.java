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
package org.mage.test.cards.requirement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BlockRequirementTest extends CardTestPlayerBase {

    @Test
    public void testPrizedUnicorn() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2

        // All creatures able to block Prized Unicorn do so.
        addCard(Zone.BATTLEFIELD, playerB, "Prized Unicorn"); // 2/2

        // Silvercoat Lion should be forced to block
        attack(2, playerB, "Prized Unicorn");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Prized Unicorn", 1);
    }

    @Test
    public void testPrizedUnicornAndOppressiveRays() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Oppressive Rays");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2

        // All creatures able to block Prized Unicorn do so.
        addCard(Zone.BATTLEFIELD, playerB, "Prized Unicorn"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oppressive Rays", "Silvercoat Lion");

        // Silvercoat Lion has not to block because it has to pay {3} to block
        attack(2, playerB, "Prized Unicorn");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Oppressive Rays", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Prized Unicorn", 1);
    }

    /**
     * Joraga Invocation is bugged big time. He cast it with 2 creatures out. I
     * only had one untapped creature. Blocked one of his, hit Done, error
     * message popped up saying the other one needed to be blocked in an
     * infinite loop. Had to shut down the program via Task Manager.
     */
    @Test
    public void testJoragaInvocationTest() {
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 6);
        // Each creature you control gets +3/+3 until end of turn and must be blocked this turn if able.
        addCard(Zone.HAND, playerB, "Joraga Invocation");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox"); // 2/4

        // Swampwalk
        addCard(Zone.BATTLEFIELD, playerA, "Bog Wraith"); // 3/3

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Joraga Invocation");

        // Silvercoat Lion has not to block because it has to pay {3} to block
        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Pillarfield Ox");
        block(2, playerA, "Bog Wraith", "Pillarfield Ox");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 15);

        assertGraveyardCount(playerB, "Joraga Invocation", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 5, 5);
        assertPowerToughness(playerB, "Pillarfield Ox", 5, 7);
        assertGraveyardCount(playerA, "Bog Wraith", 1);
    }

}
