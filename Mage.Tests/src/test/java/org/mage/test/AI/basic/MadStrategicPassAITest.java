package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.player.ai.ComputerPlayerMadStrategic;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.player.TestComputerPlayerMadStrategic;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * Regression coverage for the experimental Mad strategic AI passing priority
 * instead of taking clearly productive main-phase actions.
 */
public class MadStrategicPassAITest extends CardTestPlayerBaseWithAIHelps {

    private static final String[] STRATEGY_PROPERTIES = {
            "xmage.ai.strategy",
            "xmage.ai.strategy.maxScoreModifier",
            "xmage.ai.strategy.searchBudget.heapGuard.minFreeMb",
            "xmage.ai.strategy.alternateLoss.apply",
            "xmage.ai.strategy.boardRole.apply",
            "xmage.ai.strategy.colorAccess.apply",
            "xmage.ai.strategy.combatPressure.apply",
            "xmage.ai.strategy.comboProgress.apply",
            "xmage.ai.strategy.commanderLifecycle.apply",
            "xmage.ai.strategy.defenseReserve.apply",
            "xmage.ai.strategy.ffaTarget.apply",
            "xmage.ai.strategy.ffaThreat.apply",
            "xmage.ai.strategy.gameStage.apply",
            "xmage.ai.strategy.handPlan.apply",
            "xmage.ai.strategy.handQuality.apply",
            "xmage.ai.strategy.interactionTiming.apply",
            "xmage.ai.strategy.phaseStrategy.apply",
            "xmage.ai.strategy.politicalMemory.apply",
            "xmage.ai.strategy.resourceDevelopment.apply",
            "xmage.ai.strategy.sacrificeCost.apply",
            "xmage.ai.strategy.searchTutor.apply",
            "xmage.ai.strategy.targetQuality.apply",
            "xmage.ai.strategy.threatProjection.apply",
            "xmage.ai.strategy.tokenSwarm.apply",
            "xmage.ai.strategy.winClosure.apply"
    };

    private String[] previousProperties;

    @Before
    public void enablePackagedStrategicApplyDefaults() {
        previousProperties = new String[STRATEGY_PROPERTIES.length];
        for (int i = 0; i < STRATEGY_PROPERTIES.length; i++) {
            previousProperties[i] = System.getProperty(STRATEGY_PROPERTIES[i]);
        }
        System.setProperty("xmage.ai.strategy", "true");
        System.setProperty("xmage.ai.strategy.maxScoreModifier", "120");
        for (String property : STRATEGY_PROPERTIES) {
            if (property.endsWith(".apply")) {
                System.setProperty(property, "true");
            }
        }
    }

    @After
    public void restoreStrategicProperties() {
        for (int i = 0; i < STRATEGY_PROPERTIES.length; i++) {
            if (previousProperties[i] == null) {
                System.clearProperty(STRATEGY_PROPERTIES[i]);
            } else {
                System.setProperty(STRATEGY_PROPERTIES[i], previousProperties[i]);
            }
        }
    }

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        TestPlayer testPlayer = new TestPlayer(new TestComputerPlayerMadStrategic(name, rangeOfInfluence, 6));
        testPlayer.setAIPlayer(false);
        return testPlayer;
    }

    @Test
    public void strategicAiCastsSimpleCreatureInsteadOfPassingMainPhase() {
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void strategicAiCastsRemovalInsteadOfPassingMainPhase() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 0);
    }

    @Test
    public void strategicAiStillEvaluatesRootActionsWhenHeapGuardTrips() {
        System.setProperty("xmage.ai.strategy.searchBudget.heapGuard.minFreeMb", "999999999");

        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void strategicNodeDebugLimitScalesWithRaisedNodeBudget() {
        ExposedStrategicPlayer ai = new ExposedStrategicPlayer();

        Assert.assertTrue("strategic debug node limit must not trip before the configured strategic maxNodes",
                ai.getNodeErrorLimitForMaxNodes(12000) > 12000);
    }

    private static final class ExposedStrategicPlayer extends ComputerPlayerMadStrategic {
        private ExposedStrategicPlayer() {
            super("strategic", RangeOfInfluence.ONE, 6);
        }

        private int getNodeErrorLimitForMaxNodes(int maxNodes) {
            this.maxNodes = maxNodes;
            return getMaxSimulatedNodesPerError();
        }
    }
}
