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
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX
 */
public class BronzeBombshellTest extends CardTestPlayerBase {

    @Test
    public void testEndlessWhispers() {
        // When a player other than Bronze Bombshell's owner controls it, that player sacrifices it.
        // If the player does, Bronze Bombshell deals 7 damage to him or her.
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Bombshell", 1);

        // Each creature has "When this creature dies, choose target opponent.
        // That player puts this card from its owner's graveyard onto the battlefield
        // under his or her control at the beginning of the next end step."
        addCard(Zone.BATTLEFIELD, playerA, "Endless Whispers", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Destroy target creature or planeswalker..
        addCard(Zone.HAND, playerA, "Hero's Downfall"); // {1}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hero's Downfall", "Bronze Bombshell");
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Hero's Downfall", 1);
        assertGraveyardCount(playerA, "Bronze Bombshell", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 13);

    }

}
