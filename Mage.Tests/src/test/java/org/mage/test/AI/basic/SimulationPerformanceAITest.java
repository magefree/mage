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

    @Test
    public void test_card_order() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, "Monastery Swiftspear", 4);
        addCard(Zone.LIBRARY, playerA, "Goblin Guide", 4);
        addCard(Zone.LIBRARY, playerA, "Slickshot Show-Off", 4);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 4);
        addCard(Zone.LIBRARY, playerA, "Skullcrack", 2);
        addCard(Zone.LIBRARY, playerA, "Boros Charm", 4);
        addCard(Zone.LIBRARY, playerA, "Searing Blaze", 3);
        addCard(Zone.LIBRARY, playerA, "Flare of Duplication", 4);
        addCard(Zone.LIBRARY, playerA, "Lava Spike", 4);
        addCard(Zone.LIBRARY, playerA, "Rift Bolt", 4);
        addCard(Zone.LIBRARY, playerA, "Skewer the Critics", 4);
        addCard(Zone.LIBRARY, playerA, "Underworld Breach", 4);
        addCard(Zone.LIBRARY, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, "Inspiring Vantage", 4);
        addCard(Zone.LIBRARY, playerA, "Sacred Foundry", 2);
        addCard(Zone.LIBRARY, playerA, "Bloodstained Mire", 2);
        addCard(Zone.LIBRARY, playerA, "Wooded Foothills", 4);
        addCard(Zone.LIBRARY, playerA, "Barbarian Ring", 1);
        addCard(Zone.LIBRARY, playerA, "Sunbaked Canyon", 3);


        addCard(Zone.LIBRARY, playerB, "Monastery Swiftspear", 4);
        addCard(Zone.LIBRARY, playerB, "Goblin Guide", 4);
        addCard(Zone.LIBRARY, playerB, "Slickshot Show-Off", 4);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 4);
        addCard(Zone.LIBRARY, playerB, "Skullcrack", 2);
        addCard(Zone.LIBRARY, playerB, "Boros Charm", 4);
        addCard(Zone.LIBRARY, playerB, "Searing Blaze", 3);
        addCard(Zone.LIBRARY, playerB, "Flare of Duplication", 4);
        addCard(Zone.LIBRARY, playerB, "Lava Spike", 4);
        addCard(Zone.LIBRARY, playerB, "Rift Bolt", 4);
        addCard(Zone.LIBRARY, playerB, "Skewer the Critics", 4);
        addCard(Zone.LIBRARY, playerB, "Underworld Breach", 4);
        addCard(Zone.LIBRARY, playerB, "Mountain", 4);
        addCard(Zone.LIBRARY, playerB, "Inspiring Vantage", 4);
        addCard(Zone.LIBRARY, playerB, "Sacred Foundry", 2);
        addCard(Zone.LIBRARY, playerB, "Bloodstained Mire", 2);
        addCard(Zone.LIBRARY, playerB, "Wooded Foothills", 4);
        addCard(Zone.LIBRARY, playerB, "Barbarian Ring", 1);
        addCard(Zone.LIBRARY, playerB, "Sunbaked Canyon", 3);

        setStrictChooseMode(true);
        setStopAt(20, PhaseStep.END_TURN);
        execute();
    }
}
