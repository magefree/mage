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
package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LightningStormTest extends CardTestPlayerBase {

    /**
     * So, this just happened to me. My opponent cast Lightning Storm and while
     * it was on the stack I couldn't use the ability despite having land in
     * hand which isn't something I've had an issue with before.
     *
     * My opponent had a Leyline of Sanctity in play, so perhaps that was
     * causing the issue somehow? Does anyone want to try and replicate it?
     *
     */
    @Test
    public void ActivateByBothPlayersTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Lightning Storm deals X damage to target creature or player, where X is 3 plus the number of charge counters on it.
        // Discard a land card: Put two charge counters on Lightning Storm. You may choose a new target for it. Any player may activate this ability but only if Lightning Storm is on the stack.
        addCard(Zone.HAND, playerA, "Lightning Storm"); // {1}{R}{R}

        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.HAND, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Storm", playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Discard");
        setChoice(playerB, "playerA");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, "playerB");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);

        execute();

        assertGraveyardCount(playerA, "Lightning Storm", 1);
        assertGraveyardCount(playerB, "Mountain", 1);
        assertGraveyardCount(playerA, "Mountain", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 13);
    }

}
