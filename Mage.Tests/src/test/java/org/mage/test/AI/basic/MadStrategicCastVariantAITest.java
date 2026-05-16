package org.mage.test.AI.basic;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.CastCostPlan;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.keyword.ReplicateAbility;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.player.ai.ComputerPlayerMadStrategic;
import mage.player.ai.SimulatedPlayer2;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;
import java.util.UUID;

/**
 * Regression coverage for strategic-only cast cost variants.
 */
public class MadStrategicCastVariantAITest extends CardTestPlayerBase {

    private static final String HAZE_OF_RAGE = "Haze of Rage";
    private static final String ELECTRICKERY = "Electrickery";
    private static final String TRAIN_OF_THOUGHT = "Train of Thought";
    private static final String STORM_KILN_ARTIST = "Storm-Kiln Artist";
    private static final String[] CAST_VARIANT_PROPERTIES = {
            "xmage.ai.strategy",
            "xmage.ai.strategy.traceOnly",
            "xmage.ai.strategy.castVariants.enabled",
            "xmage.ai.strategy.castVariants.apply"
    };

    @Test
    public void strategicCastVariantFlagIsEvaluatedAtStrategicPlayerBoundary() {
        ExposedStrategicPlayer strategicPlayer = new ExposedStrategicPlayer();
        String strategy = System.getProperty("xmage.ai.strategy");
        String traceOnly = System.getProperty("xmage.ai.strategy.traceOnly");
        String enabled = System.getProperty("xmage.ai.strategy.castVariants.enabled");
        String apply = System.getProperty("xmage.ai.strategy.castVariants.apply");
        try {
            System.setProperty("xmage.ai.strategy", "true");
            System.setProperty("xmage.ai.strategy.traceOnly", "false");
            System.setProperty("xmage.ai.strategy.castVariants.enabled", "true");

            System.setProperty("xmage.ai.strategy.castVariants.apply", "false");
            Assert.assertFalse("Strategic cast variants should be disabled at the strategic player boundary",
                    strategicPlayer.usesStrategicCastVariants());

            System.setProperty("xmage.ai.strategy.castVariants.apply", "true");
            Assert.assertTrue("Strategic cast variants should be enabled at the strategic player boundary",
                    strategicPlayer.usesStrategicCastVariants());
        } finally {
            restoreProperty("xmage.ai.strategy", strategy);
            restoreProperty("xmage.ai.strategy.traceOnly", traceOnly);
            restoreProperty("xmage.ai.strategy.castVariants.enabled", enabled);
            restoreProperty("xmage.ai.strategy.castVariants.apply", apply);
        }
    }

    @Test
    public void strategicCastVariantsExposeBuybackWithoutChangingDefaultActionSurface() {
        addCard(Zone.HAND, playerA, HAZE_OF_RAGE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        runCode("compare default and strategic cast variants", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<Ability> defaultActions = new SimulatedPlayer2(player, true, 0, 0)
                    .simulatePriority(game);
            Assert.assertEquals("Default simulated player should see only the normal Haze cast",
                    1, countHazeActions(defaultActions));
            Assert.assertEquals("Default simulated player must not expose the buyback variant",
                    0, countHazeBuybackActions(defaultActions));

            List<Ability> strategicActions = new SimulatedPlayer2(player, true, 0, 0, false, true)
                    .simulatePriority(game);
            Assert.assertEquals("Strategic simulated player should see normal Haze plus buyback Haze",
                    2, countHazeActions(strategicActions));
            Ability buybackAction = findHazeBuybackAction(strategicActions);
            Assert.assertTrue("Buyback cost-plan tag must survive ability copies",
                    CastCostPlan.isOptionalAdditionalCostSelected(buybackAction.copy(), BuybackAbility.BUYBACK_COST_PLAN_KEY));
            Assert.assertTrue("Buyback variant should advertise that it preserves the source card",
                    CastCostPlan.preservesSource(buybackAction.copy()));
            Assert.assertEquals("with-buyback", CastCostPlan.getVariantLabel(buybackAction));
        });

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void overloadIsAlreadyRepresentedAsAnAlternateSpellAction() {
        addCard(Zone.HAND, playerA, ELECTRICKERY);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");

        runCode("compare overload action surface", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<Ability> defaultActions = new SimulatedPlayer2(player, true, 0, 0)
                    .simulatePriority(game);
            Assert.assertEquals("Default simulated player should already see normal Electrickery plus overload",
                    2, countElectrickeryActions(defaultActions));

            List<Ability> strategicActions = new SimulatedPlayer2(player, true, 0, 0, false, true)
                    .simulatePriority(game);
            Assert.assertEquals("Strategic cast variants should not duplicate overload actions",
                    2, countElectrickeryActions(strategicActions));
        });

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void strategicCastVariantsExposeAffordableReplicateCountsWithoutChangingDefaultActionSurface() {
        addCard(Zone.HAND, playerA, TRAIN_OF_THOUGHT);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        runCode("compare default and strategic replicate variants", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<Ability> defaultActions = new SimulatedPlayer2(player, true, 0, 0)
                    .simulatePriority(game);
            Assert.assertEquals("Default simulated player should see only the normal Train of Thought cast",
                    1, countTrainActions(defaultActions));
            Assert.assertEquals("Default simulated player must not expose replicate count variants",
                    0, countTrainReplicateActions(defaultActions, 1));

            List<Ability> strategicActions = new SimulatedPlayer2(player, true, 0, 0, false, true)
                    .simulatePriority(game);
            Assert.assertEquals("Strategic simulated player should see normal Train plus two affordable replicate counts",
                    3, countTrainActions(strategicActions));
            Assert.assertEquals(1, countTrainReplicateActions(strategicActions, 1));
            Assert.assertEquals(1, countTrainReplicateActions(strategicActions, 2));
            Assert.assertEquals("Three replicate payments cost more mana than this setup can produce",
                    0, countTrainReplicateActions(strategicActions, 3));
        });

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void strategicCastVariantsCapRepeatableReplicateCounts() {
        addCard(Zone.HAND, playerA, TRAIN_OF_THOUGHT);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);

        runCode("verify replicate variant cap", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<Ability> strategicActions = new SimulatedPlayer2(player, true, 0, 0, false, true)
                    .simulatePriority(game);
            Assert.assertEquals("Strategic replicate count generation should stop at the configured safety cap",
                    CastCostPlan.DEFAULT_MAX_REPEAT_COUNT, maxTrainReplicateCount(strategicActions));
            Assert.assertEquals(1, countTrainReplicateActions(strategicActions, CastCostPlan.DEFAULT_MAX_REPEAT_COUNT));
            Assert.assertEquals(0, countTrainReplicateActions(strategicActions, CastCostPlan.DEFAULT_MAX_REPEAT_COUNT + 1));
        });

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void preselectedBuybackVariantCastsWithoutPromptAndReturnsSpellToHand() {
        addCard(Zone.HAND, playerA, HAZE_OF_RAGE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        runCode("cast preselected buyback variant", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Game sim = game.createSimulationForAI();
            Player simPlayer = sim.getPlayer(player.getId());
            List<Ability> strategicActions = new SimulatedPlayer2(simPlayer, true, 0, 0, false, true)
                    .simulatePriority(sim);
            Ability buybackAction = findHazeBuybackAction(strategicActions);
            Assert.assertTrue("Preselected buyback action should activate",
                    simPlayer.activateAbility((ActivatedAbility) buybackAction, sim));
            resolveFirstStackObject(sim);
            Assert.assertEquals("Buyback should return Haze to hand in the simulated activation",
                    1, countCardsInHand(simPlayer, HAZE_OF_RAGE, sim));
            Assert.assertEquals("Buyback should keep Haze out of the graveyard in the simulated activation",
                    0, countCardsInGraveyard(simPlayer, HAZE_OF_RAGE, sim));
        });

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void preselectedReplicateVariantCastsWithoutPromptAndCreatesCopies() {
        addCard(Zone.HAND, playerA, TRAIN_OF_THOUGHT);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        runCode("cast preselected replicate variant", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Game sim = game.createSimulationForAI();
            Player simPlayer = sim.getPlayer(player.getId());
            List<Ability> strategicActions = new SimulatedPlayer2(simPlayer, true, 0, 0, false, true)
                    .simulatePriority(sim);
            Ability replicateAction = findTrainReplicateAction(strategicActions, 2);
            Assert.assertEquals("Replicate count tag must survive ability copies",
                    2, ReplicateAbility.getSelectedReplicateCount(replicateAction.copy()));
            Assert.assertFalse("Replicate creates spell copies but does not preserve the source card",
                    CastCostPlan.preservesSource(replicateAction.copy()));

            Assert.assertTrue("Preselected replicate action should activate",
                    simPlayer.activateAbility((ActivatedAbility) replicateAction, sim));
            sim.checkStateAndTriggered();
            sim.applyEffects();
            Assert.assertEquals("Original Train plus its replicate trigger should be on the stack",
                    2, sim.getStack().size());

            resolveFirstStackObject(sim);
            Assert.assertEquals("Resolving the replicate trigger should create two spell copies",
                    3, sim.getStack().size());

            resolveStack(sim);
            Assert.assertEquals("Original Train plus two copies should draw three cards",
                    3, countCardsInHand(simPlayer, null, sim));
            Assert.assertEquals("The original Train card should resolve to the graveyard",
                    1, countCardsInGraveyard(simPlayer, TRAIN_OF_THOUGHT, sim));
        });

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void sourcePreservingVariantPrunesBaseCastAndPassBeforeVisibleCombatLethal() {
        String[] previousProperties = enableStrategicCastVariants();
        try {
            setLife(playerB, 18);
            addCard(Zone.BATTLEFIELD, playerA, STORM_KILN_ARTIST);
            addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
            addCard(Zone.HAND, playerA, HAZE_OF_RAGE);

            runCode("precombat source-preserving variant should dominate",
                    1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
                        List<Ability> actions = strategicActions(player, game);
                        Assert.assertEquals("Sanity check: normal Haze plus buyback Haze before pruning",
                                2, countHazeActions(actions));
                        Assert.assertEquals("Sanity check: pass exists before pruning",
                                1, countPassActions(actions));

                        new ExposedStrategicPlayer(player.getId()).optimizeActions(game, actions);

                        Assert.assertEquals("Before visible lethal, only source-preserving Haze should remain",
                                1, countHazeActions(actions));
                        Assert.assertEquals("The remaining Haze action should be the buyback variant",
                                1, countHazeBuybackActions(actions));
                        Assert.assertEquals("Pass should be pruned while source-preserving progress is available",
                                0, countPassActions(actions));
                    });

            setStopAt(1, PhaseStep.BEGIN_COMBAT);
            setStrictChooseMode(true);
            execute();
        } finally {
            restoreProperties(previousProperties);
        }
    }

    @Test
    public void sourcePreservingVariantDoesNotPruneAfterVisibleCombatLethal() {
        String[] previousProperties = enableStrategicCastVariants();
        try {
            setLife(playerB, 2);
            addCard(Zone.BATTLEFIELD, playerA, STORM_KILN_ARTIST);
            addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
            addCard(Zone.HAND, playerA, HAZE_OF_RAGE);

            runCode("precombat source-preserving variant should not dominate after lethal",
                    1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
                        List<Ability> actions = strategicActions(player, game);
                        new ExposedStrategicPlayer(player.getId()).optimizeActions(game, actions);

                        Assert.assertEquals("After visible lethal, normal Haze and buyback Haze should remain",
                                2, countHazeActions(actions));
                        Assert.assertEquals("Buyback should still be available, just not forced",
                                1, countHazeBuybackActions(actions));
                        Assert.assertEquals("Pass should remain available once combat lethal is visible",
                                1, countPassActions(actions));
                    });

            setStopAt(1, PhaseStep.BEGIN_COMBAT);
            setStrictChooseMode(true);
            execute();
        } finally {
            restoreProperties(previousProperties);
        }
    }

    @Test
    public void sourcePreservingVariantDoesNotPruneOutsidePrecombatMain() {
        String[] previousProperties = enableStrategicCastVariants();
        try {
            setLife(playerB, 18);
            addCard(Zone.BATTLEFIELD, playerA, STORM_KILN_ARTIST);
            addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
            addCard(Zone.HAND, playerA, HAZE_OF_RAGE);

            runCode("postcombat source-preserving variant should not dominate",
                    1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
                        List<Ability> actions = strategicActions(player, game);
                        new ExposedStrategicPlayer(player.getId()).optimizeActions(game, actions);

                        Assert.assertEquals("Outside precombat main, normal Haze and buyback Haze should remain",
                                2, countHazeActions(actions));
                        Assert.assertEquals("Buyback should still be available, just not forced",
                                1, countHazeBuybackActions(actions));
                        Assert.assertEquals("Pass should remain available outside the precombat combo-progress window",
                                1, countPassActions(actions));
                    });

            setStopAt(1, PhaseStep.END_TURN);
            setStrictChooseMode(true);
            execute();
        } finally {
            restoreProperties(previousProperties);
        }
    }

    private static long countHazeActions(List<Ability> actions) {
        return actions.stream()
                .filter(MadStrategicCastVariantAITest::isHazeAction)
                .count();
    }

    private static long countPassActions(List<Ability> actions) {
        return actions.stream()
                .filter(action -> action instanceof PassAbility)
                .count();
    }

    private static long countHazeBuybackActions(List<Ability> actions) {
        return actions.stream()
                .filter(MadStrategicCastVariantAITest::isHazeBuybackAction)
                .count();
    }

    private static long countElectrickeryActions(List<Ability> actions) {
        return actions.stream()
                .filter(action -> action.toString().contains(ELECTRICKERY))
                .count();
    }

    private static long countTrainActions(List<Ability> actions) {
        return actions.stream()
                .filter(MadStrategicCastVariantAITest::isTrainAction)
                .count();
    }

    private static long countTrainReplicateActions(List<Ability> actions, int replicateCount) {
        return actions.stream()
                .filter(action -> isTrainReplicateAction(action, replicateCount))
                .count();
    }

    private static int maxTrainReplicateCount(List<Ability> actions) {
        return actions.stream()
                .filter(MadStrategicCastVariantAITest::isTrainAction)
                .mapToInt(ReplicateAbility::getSelectedReplicateCount)
                .max()
                .orElse(0);
    }

    private static Ability findHazeBuybackAction(List<Ability> actions) {
        for (Ability action : actions) {
            if (isHazeBuybackAction(action)) {
                return action;
            }
        }
        Assert.fail("Expected a Haze of Rage buyback cast variant");
        return null;
    }

    private static Ability findTrainReplicateAction(List<Ability> actions, int replicateCount) {
        for (Ability action : actions) {
            if (isTrainReplicateAction(action, replicateCount)) {
                return action;
            }
        }
        Assert.fail("Expected a Train of Thought replicate " + replicateCount + " cast variant");
        return null;
    }

    private static boolean isHazeBuybackAction(Ability action) {
        return isHazeAction(action)
                && CastCostPlan.isOptionalAdditionalCostSelected(action, BuybackAbility.BUYBACK_COST_PLAN_KEY);
    }

    private static boolean isHazeAction(Ability action) {
        return action.toString().contains(HAZE_OF_RAGE);
    }

    private static boolean isTrainReplicateAction(Ability action, int replicateCount) {
        return isTrainAction(action)
                && ReplicateAbility.getSelectedReplicateCount(action) == replicateCount;
    }

    private static boolean isTrainAction(Ability action) {
        return action.toString().contains(TRAIN_OF_THOUGHT);
    }

    private static void resolveFirstStackObject(Game game) {
        StackObject stackObject = game.getStack().getFirstOrNull();
        Assert.assertNotNull("Expected the preselected buyback cast on the stack", stackObject);
        stackObject.resolve(game);
        game.getStack().remove(stackObject, game);
        game.applyEffects();
    }

    private static void resolveStack(Game game) {
        while (game.getStack().getFirstOrNull() != null) {
            resolveFirstStackObject(game);
        }
    }

    private static long countCardsInHand(Player player, String cardName, Game game) {
        return player.getHand().getCards(game).stream()
                .filter(card -> cardName == null || card.getName().equals(cardName))
                .count();
    }

    private static long countCardsInGraveyard(Player player, String cardName, Game game) {
        return player.getGraveyard().getCards(game).stream()
                .filter(card -> card.getName().equals(cardName))
                .count();
    }

    private static List<Ability> strategicActions(Player player, Game game) {
        return new SimulatedPlayer2(player, true, 0, 0, false, true)
                .simulatePriority(game);
    }

    private static String[] enableStrategicCastVariants() {
        String[] previousProperties = new String[CAST_VARIANT_PROPERTIES.length];
        for (int i = 0; i < CAST_VARIANT_PROPERTIES.length; i++) {
            previousProperties[i] = System.getProperty(CAST_VARIANT_PROPERTIES[i]);
        }
        System.setProperty("xmage.ai.strategy", "true");
        System.setProperty("xmage.ai.strategy.traceOnly", "false");
        System.setProperty("xmage.ai.strategy.castVariants.enabled", "true");
        System.setProperty("xmage.ai.strategy.castVariants.apply", "true");
        return previousProperties;
    }

    private static void restoreProperties(String[] previousProperties) {
        for (int i = 0; i < CAST_VARIANT_PROPERTIES.length; i++) {
            restoreProperty(CAST_VARIANT_PROPERTIES[i], previousProperties[i]);
        }
    }

    private static void restoreProperty(String property, String value) {
        if (value == null) {
            System.clearProperty(property);
        } else {
            System.setProperty(property, value);
        }
    }

    private static final class ExposedStrategicPlayer extends ComputerPlayerMadStrategic {

        private ExposedStrategicPlayer() {
            super("strategic", RangeOfInfluence.ONE, 6);
        }

        private boolean usesStrategicCastVariants() {
            return shouldUseStrategicCastVariants();
        }

        private ExposedStrategicPlayer(UUID playerId) {
            super(playerId, 6);
        }

        private void optimizeActions(Game game, List<Ability> actions) {
            optimize(game, actions);
        }
    }
}
