package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

import java.util.Arrays;
import java.util.List;

/**
 * @author JayDi85
 */
public class SimulationPerformanceAITest extends CardTestPlayerBaseAI {

    @Override
    public List<String> getFullSimulatedPlayers() {
        return Arrays.asList("PlayerA", "PlayerB");
    }

    @Test
    public void test_AIvsAI_Simple() {
        // both must kill x2 bears by x2 bolts
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Balduvian Bears", 2);
        assertGraveyardCount(playerB, "Balduvian Bears", 2);
    }

    @Test
    public void test_AIvsAI_LongGame() {
        // many bears and bolts must help to end game fast
        int maxTurn = 50;
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerA, "Mountain", 10);
        addCard(Zone.LIBRARY, playerA, "Forest", 10);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 20);
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 10);
        //
        addCard(Zone.LIBRARY, playerB, "Mountain", 10);
        addCard(Zone.LIBRARY, playerA, "Forest", 10);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 20);
        addCard(Zone.LIBRARY, playerB, "Balduvian Bears", 10);

        // full ai simulation
        setStrictChooseMode(true);
        setStopAt(maxTurn, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue("One of player must won a game before turn " + maxTurn + ", but it ends on " + currentGame, currentGame.hasEnded());
    }
}
