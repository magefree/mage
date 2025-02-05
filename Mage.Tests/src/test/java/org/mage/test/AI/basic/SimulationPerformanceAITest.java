package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Ignore;
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

    private void runManyTargetOptionsTest(String info, int totalCreatures, int needDiedCreatures, int needPlayerLife) {
        // When Bogardan Hellkite enters, it deals 5 damage divided as you choose among any number of targets.
        addCard(Zone.HAND, playerA, "Bogardan Hellkite", 1); // {6}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", totalCreatures);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Bogardan Hellkite", 1); // if fail then AI stops before all sims ends
        assertGraveyardCount(playerB, "Balduvian Bears", needDiedCreatures);
        assertLife(playerB, needPlayerLife);
    }

    @Test
    public void test_AIvsAI_ManyTargetOptions_Simple() {
        // 2 damage to bear and 3 damage to player B
        runManyTargetOptionsTest("1 target creature", 1, 1, 20 - 3);
    }

    @Test
    public void test_AIvsAI_ManyTargetOptions_Few() {
        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsTest("2 target creatures", 2, 2, 20 - 1);
    }

    @Test
    public void test_AIvsAI_ManyTargetOptions_Many() {
        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsTest("5 target creatures", 2, 2, 20 - 1);
    }

    @Test
    @Ignore // AI code must be improved
    // TODO: need memory optimization
    // TODO: need sims/options amount optimization (example: target name + score as unique param to reduce possible options)
    // TODO: need best choice selection on timeout (AI must make any good/bad choice on timeout with game log - not a skip)
    public void test_AIvsAI_ManyTargetOptions_TooMuch() {
        // possible problems:
        // - big memory consumption on sims prepare (memory overflow)
        // - too many sims to calculate (AI fail on time out and do nothing)

        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsTest("50 target creatures", 50, 2, 20 - 1);
    }
}
