package org.mage.test.cards.requirement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithMonteCarloAIHelps;

/**
 * @author JayDi85
 */
@Ignore // TODO: research and fix attack/block simulations
public class BecomeBlockTriggersMonteCarloAITest extends CardTestPlayerBaseWithMonteCarloAIHelps {

    @Test
    public void test_AI_Block_NoBlockers() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2

        attack(1, playerA, "Grizzly Bears");

        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, 1);
    }

    @Test
    public void test_AI_Block_KillAttackerAndAlive() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Apex Devastator", 1); // 10/10

        attack(1, playerA, "Grizzly Bears");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, 0);
        assertPermanentCount(playerB, 1);
    }

    @Test
    public void test_AI_Block_KillAttackerAndDie() {
        addCard(Zone.BATTLEFIELD, playerA, "Atarka Efreet", 1); // 5/1
        //
        addCard(Zone.BATTLEFIELD, playerB, "Aarakocra Sneak", 1); // 1/4

        attack(1, playerA, "Atarka Efreet");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerB, 1);
    }

    @Test
    public void test_AI_Block_BlockAttackerAndDie() {
        addCard(Zone.BATTLEFIELD, playerA, "Apex Devastator", 1);// 10/10
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1); // 2/2

        attack(1, playerA, "Apex Devastator");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerB, 0);
    }

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
