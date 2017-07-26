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
package org.mage.test.game.ends;

import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LeveöX
 */
public class PhageTheUntouchableTest extends CardTestPlayerBase {

    @Test
    public void TestWithEndlessWhispers() {
        // Each creature has "When this creature dies, choose target opponent.
        // That player puts this card from its owner's graveyard onto the battlefield
        // under his or her control at the beginning of the next end step."
        addCard(Zone.BATTLEFIELD, playerA, "Endless Whispers");

        // Destroy target creature or planeswalker..
        addCard(Zone.HAND, playerA, "Hero's Downfall"); // {1}{B}{B}

        // When Phage the Untouchable enters the battlefield, if you didn't cast it from your hand, you lose the game.
        // Whenever Phage deals combat damage to a creature, destroy that creature. It can't be regenerated.
        // Whenever Phage deals combat damage to a player, that player loses the game.
        addCard(Zone.HAND, playerA, "Phage the Untouchable"); // Creature {3}{B}{B}{B}{B} 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phage the Untouchable");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hero's Downfall", "Phage the Untouchable");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Hero's Downfall", 1);
        assertPermanentCount(playerB, "Phage the Untouchable", 1);

        Assert.assertTrue("Game has ended.", currentGame.hasEnded());
        Assert.assertTrue("Player A has won.", playerA.hasWon());
        Assert.assertTrue("Game ist At end phase", currentGame.getPhase().getType().equals(TurnPhase.END));

    }

}
