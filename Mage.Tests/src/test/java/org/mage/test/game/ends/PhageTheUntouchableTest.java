
package org.mage.test.game.ends;

import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PhageTheUntouchableTest extends CardTestPlayerBase {

    @Test
    public void TestWithEndlessWhispers() {
        // Each creature has "When this creature dies, choose target opponent.
        // That player puts this card from its owner's graveyard onto the battlefield
        // under their control at the beginning of the next end step."
        addCard(Zone.BATTLEFIELD, playerA, "Endless Whispers");

        // Destroy target creature or planeswalker..
        addCard(Zone.HAND, playerA, "Hero's Downfall"); // {1}{B}{B}

        // When Phage the Untouchable enters the battlefield, if you didn't cast it from your hand, you lose the game.
        // Whenever Phage deals combat damage to a creature, destroy that creature. It can't be regenerated.
        // Whenever Phage deals combat damage to a player, that player loses the game.
        addCard(Zone.HAND, playerA, "Phage the Untouchable"); // Creature {3}{B}{B}{B}{B} 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phage the Untouchable", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hero's Downfall", "Phage the Untouchable");
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Hero's Downfall", 1);
        assertPermanentCount(playerB, "Phage the Untouchable", 1);

        Assert.assertTrue("Game has ended.", currentGame.hasEnded());
        assertWonTheGame(playerA);
        Assert.assertTrue("Game ist At end phase", currentGame.getTurnPhaseType() == TurnPhase.END);
    }
}
