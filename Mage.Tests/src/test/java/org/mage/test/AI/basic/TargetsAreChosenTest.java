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
package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 *
 * @author LevelX2
 */
public class TargetsAreChosenTest extends CardTestPlayerBaseAI {

    /**
     * Check that the AI selects a target from the own artifacts and also an
     * artifact from the opponent artficats
     */
    @Test
    public void testRackAndRuin() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Destroy two target artifacts.
        addCard(Zone.HAND, playerA, "Rack and Ruin"); // {2}{R}

        addCard(Zone.BATTLEFIELD, playerA, "Mox Emerald", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rack and Ruin", 1);
        assertGraveyardCount(playerA, "Mox Emerald", 1);
        assertGraveyardCount(playerB, "Juggernaut", 1);
    }

    /**
     * Check that the AI does not cast Rack and Ruin if it would destroy the
     * owly creature on the battlefield owned by the AI
     */
    @Test
    public void testRackAndRuin2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Destroy two target artifacts.
        addCard(Zone.HAND, playerA, "Rack and Ruin"); // {2}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Mox Emerald");
        addCard(Zone.BATTLEFIELD, playerA, "Juggernaut");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rack and Ruin", 0);
    }

    /**
     * Check that the AI does cast Rack and Ruin if it would destroy two targets
     * of the opponent
     */
    @Test
    public void testRackAndRuin3() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Destroy two target artifacts.
        addCard(Zone.HAND, playerA, "Rack and Ruin"); // {2}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Mox Emerald", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Juggernaut");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rack and Ruin", 1);
        assertGraveyardCount(playerB, "Mox Emerald", 2);

    }

}
