package org.mage.test.cards.requirement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithMonteCarloAIHelps;

/**
 * @author JayDi85
 */
public class BecomeBlockTriggersMonteCarloAITest extends CardTestPlayerBaseWithMonteCarloAIHelps {

    // continue from BecomeBlockTriggersTest
    @Test
    public void test_AI_CantBlockAgain() {
        // Monte Carlo bug: Triggered ability triggered twice (should be once), see https://github.com/magefree/mage/issues/6367

        removeAllCardsFromHand(playerA);
        removeAllCardsFromHand(playerB);

        // All creatures able to block Nessian Boar do so.
        // Whenever Nessian Boar becomes blocked by a creature, that creature’s controller draws a card.
        addCard(Zone.BATTLEFIELD, playerA, "Nessian Boar", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // auto-block by requirement effect
        attack(1, playerA, "Nessian Boar");
        // AI can't block same creature twice
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 1);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1);
    }
}
