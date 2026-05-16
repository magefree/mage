package org.mage.test.AI.basic;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.costs.CastCostPlan;
import mage.abilities.keyword.BuybackAbility;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.player.ai.AiStrategyScore;
import mage.player.ai.SimulatedPlayer2;
import mage.player.ai.scoring.AiScoreModuleConfig;
import mage.player.ai.scoring.AiScoringContext;
import mage.player.ai.scoring.ComboProgressScoreModule;
import mage.players.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.player.TestComputerPlayerMadStrategic;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import java.util.List;
import java.util.UUID;

/**
 * Headless combo benchmarks for the experimental Mad strategic AI.
 * <p>
 * Combo source: Commander Spellbook 3940-5195, Storm-Kiln Artist + Haze of Rage.
 * Haze of Rage with buyback and storm creates enough Storm-Kiln Artist magecraft
 * triggers to keep recasting Haze, then attacks for lethal after enough loops.
 */
public class MadStrategicInfiniteComboAITest extends CardTestPlayerBaseWithAIHelps {

    private static final String STORM_KILN_ARTIST = "Storm-Kiln Artist";
    private static final String HAZE_OF_RAGE = "Haze of Rage";
    private static final int SETUP_SPELLS = 3;

    private static final String[] STRATEGY_PROPERTIES = {
            "xmage.ai.strategy",
            "xmage.ai.strategy.maxScoreModifier",
            "xmage.ai.strategy.castVariants.enabled",
            "xmage.ai.strategy.castVariants.apply",
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

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        TestPlayer testPlayer = new TestPlayer(new TestComputerPlayerMadStrategic(name, rangeOfInfluence, 6));
        testPlayer.setAIPlayer(false);
        return testPlayer;
    }

    @After
    public void restoreStrategicProperties() {
        if (previousProperties == null) {
            return;
        }
        for (int i = 0; i < STRATEGY_PROPERTIES.length; i++) {
            if (previousProperties[i] == null) {
                System.clearProperty(STRATEGY_PROPERTIES[i]);
            } else {
                System.setProperty(STRATEGY_PROPERTIES[i], previousProperties[i]);
            }
        }
    }

    @Test
    public void manualStormKilnHazeLoopCanBeExecutedTenTimesForLethalCombat() {
        setupStormKilnHazeCombo(125);

        castSetupSpells();
        manuallyCastHazeWithBuyback(10);

        checkHandCardCount("buyback keeps Haze available after ten loops", 1, PhaseStep.BEGIN_COMBAT, playerA, HAZE_OF_RAGE, 1);
        attack(1, playerA, STORM_KILN_ARTIST, playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
    }

    @Test
    public void comboProgressScoresRepeatableHazeLoopUntilVisibleCombatGoal() {
        setupStrategicApplyDefaults();
        setupStormKilnHazeCombo(18);
        castSetupSpells();

        runCode("score strategic Haze loop progress", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Game beforeFirst = game.createSimulationForAI();
            LoopCastResult first = castStrategicHazeWithBuyback(beforeFirst, SETUP_SPELLS, false, player.getId());
            AiStrategyScore firstScore = evaluateComboProgress(beforeFirst, first.after, player.getId(), first.action);
            assertContribution(firstScore, "combo-progress:repeatable-spell-preserved");
            assertContribution(firstScore, "combo-progress:loop-progress");
            assertNoContribution(firstScore, "combo-progress:resource-goal-met");

            LoopCastResult second = castStrategicHazeWithBuyback(first.after, SETUP_SPELLS + 1, true, player.getId());
            AiStrategyScore secondScore = evaluateComboProgress(first.after, second.after, player.getId(), second.action);
            assertContribution(secondScore, "combo-progress:resource-goal-met");

            LoopCastResult third = castStrategicHazeWithBuyback(second.after, SETUP_SPELLS + 2, true, player.getId());
            AiStrategyScore thirdScore = evaluateComboProgress(second.after, third.after, player.getId(), third.action);
            assertContribution(thirdScore, "combo-progress:loop-over-goal");
            Assert.assertTrue("Looping after the visible combat goal is met should be penalized",
                    thirdScore.getRawModifier() < 0);
        });

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void strategicAiShouldExecuteTwoStormKilnHazeLoopsForLethalCombat() {
        setupStrategicApplyDefaults();
        setupStormKilnHazeCombo(18);
        castSetupSpells();

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.BEGIN_COMBAT, playerA);
        runCode("strategic loop should reach lethal Storm-Kiln attack power",
                1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> {
                    Permanent artist = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                            .filter(permanent -> permanent.getName().equals(STORM_KILN_ARTIST))
                            .findFirst()
                            .orElse(null);
                    Assert.assertNotNull("Expected Storm-Kiln Artist on the battlefield", artist);
                    Assert.assertTrue("Storm-Kiln Artist should be large enough to attack for lethal",
                            artist.getPower().getValue() >= game.getPlayer(playerB.getId()).getLife());
                });
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);

        setStopAt(1, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
    }

    @Test
    public void strategicAiShouldExecuteTenStormKilnHazeLoopsForLethalCombat() {
        setupStrategicApplyDefaults();
        setupStormKilnHazeCombo(125);
        castSetupSpells();

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.BEGIN_COMBAT, playerA);
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);

        setStopAt(1, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
    }

    @Test
    @Ignore("Stress placeholder: ten-loop strategic execution is active; keep twenty-loop validation out of the default suite until runtime is characterized.")
    public void strategicAiShouldExecuteTwentyStormKilnHazeLoopsForLethalCombat() {
        setupStrategicApplyDefaults();
        setupStormKilnHazeCombo(450);
        castSetupSpells();

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.BEGIN_COMBAT, playerA);
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);

        setStopAt(1, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
    }

    private void setupStrategicApplyDefaults() {
        previousProperties = new String[STRATEGY_PROPERTIES.length];
        for (int i = 0; i < STRATEGY_PROPERTIES.length; i++) {
            previousProperties[i] = System.getProperty(STRATEGY_PROPERTIES[i]);
        }
        System.setProperty("xmage.ai.strategy", "true");
        System.setProperty("xmage.ai.strategy.maxScoreModifier", "120");
        System.setProperty("xmage.ai.strategy.castVariants.enabled", "true");
        System.setProperty("xmage.ai.strategy.castVariants.apply", "true");
        for (String property : STRATEGY_PROPERTIES) {
            if (property.endsWith(".apply")) {
                System.setProperty(property, "true");
            }
        }
    }

    private void setupStormKilnHazeCombo(int opponentLife) {
        setLife(playerB, opponentLife);
        addCard(Zone.BATTLEFIELD, playerA, STORM_KILN_ARTIST);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Memnite", SETUP_SPELLS);
        addCard(Zone.HAND, playerA, HAZE_OF_RAGE);
    }

    private void castSetupSpells() {
        for (int i = 0; i < SETUP_SPELLS; i++) {
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", true);
        }
    }

    private void manuallyCastHazeWithBuyback(int loopCount) {
        for (int i = 0; i < loopCount; i++) {
            int stormCopyCount = SETUP_SPELLS + i;
            setChoice(playerA, true); // pay buyback
            if (i > 0) {
                setChoice(playerA, "Red", 4); // pay Haze plus buyback from Treasure tokens
            }
            setChoice(playerA, HAZE_OF_RAGE); // put storm and magecraft cast triggers on the stack deterministically
            setChoice(playerA, STORM_KILN_ARTIST, stormCopyCount - 1); // order identical magecraft triggers from storm copies
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, HAZE_OF_RAGE, true);
        }
    }

    private static LoopCastResult castStrategicHazeWithBuyback(Game before,
                                                               int previousSpellCount,
                                                               boolean payWithTreasures,
                                                               UUID playerId) {
        Game after = before.createSimulationForAI();
        Player simPlayer = after.getPlayer(playerId);
        queueHazeChoices(simPlayer, previousSpellCount, payWithTreasures);
        List<Ability> actions = new SimulatedPlayer2(simPlayer, true, 0, 0, false, true)
                .simulatePriority(after);
        Ability action = findHazeBuybackAction(actions);
        Assert.assertTrue("Strategic Haze buyback action should activate",
                simPlayer.activateAbility((ActivatedAbility) action, after));
        after.applyEffects();
        after.checkStateAndTriggered();
        after.applyEffects();
        resolveStack(after);
        return new LoopCastResult(action, after);
    }

    private static void queueHazeChoices(Player player, int previousSpellCount, boolean payWithTreasures) {
        if (!(player instanceof TestPlayer)) {
            return;
        }
        TestPlayer testPlayer = (TestPlayer) player;
        if (payWithTreasures) {
            for (int i = 0; i < 4; i++) {
                testPlayer.addChoice("Red");
            }
        }
        testPlayer.addChoice(HAZE_OF_RAGE);
        for (int i = 1; i < previousSpellCount; i++) {
            testPlayer.addChoice(STORM_KILN_ARTIST);
        }
    }

    private static Ability findHazeBuybackAction(List<Ability> actions) {
        for (Ability action : actions) {
            if (action.toString().contains(HAZE_OF_RAGE)
                    && CastCostPlan.isOptionalAdditionalCostSelected(action, BuybackAbility.BUYBACK_COST_PLAN_KEY)) {
                return action;
            }
        }
        Assert.fail("Expected a strategic Haze of Rage buyback variant");
        return null;
    }

    private static AiStrategyScore evaluateComboProgress(Game before, Game after, UUID playerId, Ability action) {
        return new ComboProgressScoreModule(new AiScoreModuleConfig(playerId, 120, true))
                .evaluate(AiScoringContext.candidate(before, after, playerId, action, 0));
    }

    private static void assertContribution(AiStrategyScore score, String label) {
        Assert.assertTrue("Expected contribution " + label,
                score.getContributions().stream().anyMatch(contribution -> label.equals(contribution.getLabel())));
    }

    private static void assertNoContribution(AiStrategyScore score, String label) {
        Assert.assertFalse("Did not expect contribution " + label,
                score.getContributions().stream().anyMatch(contribution -> label.equals(contribution.getLabel())));
    }

    private static void resolveStack(Game game) {
        int guard = 0;
        while (game.getStack().getFirstOrNull() != null) {
            Assert.assertTrue("Stack resolution guard exceeded", guard++ < 100);
            StackObject stackObject = game.getStack().getFirstOrNull();
            stackObject.resolve(game);
            game.getStack().remove(stackObject, game);
            game.applyEffects();
            game.checkStateAndTriggered();
            game.applyEffects();
        }
    }

    private static final class LoopCastResult {
        private final Ability action;
        private final Game after;

        private LoopCastResult(Ability action, Game after) {
            this.action = action;
            this.after = after;
        }
    }
}
