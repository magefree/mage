package org.mage.test.cards.requirement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class BecomeBlockTriggersAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Manual_AutoBlock() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromHand(playerB);

        // All creatures able to block Nessian Boar do so.
        // Whenever Nessian Boar becomes blocked by a creature, that creature’s controller draws a card.
        addCard(Zone.BATTLEFIELD, playerA, "Nessian Boar", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // auto-block by requirement effect
        attack(1, playerA, "Nessian Boar");
        //block(1, playerB, "Balduvian Bears", "Nessian Boar");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 1);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1);
    }

    @Test
    public void test_Manual_CantBlockAgain() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromHand(playerB);

        // All creatures able to block Nessian Boar do so.
        // Whenever Nessian Boar becomes blocked by a creature, that creature’s controller draws a card.
        addCard(Zone.BATTLEFIELD, playerA, "Nessian Boar", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // auto-block by requirement effect
        attack(1, playerA, "Nessian Boar");
        // try to block manually, but it must raise error
        block(1, playerB, "Balduvian Bears", "Nessian Boar");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        try {
            execute();
            Assert.fail("Expected exception, but not raise");
        } catch (UnsupportedOperationException ue) {
            Assert.assertEquals("Balduvian Bears cannot block Nessian Boar it is already blocking the maximum amount of creatures.", ue.getMessage());
        }
    }

    @Test
    public void test_AI_CantBlockAgain() {
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
