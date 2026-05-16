package org.mage.test.AI.basic;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.player.ai.ComputerPlayerMadStrategic;
import mage.player.ai.PossibleTargetsSelector;
import mage.player.ai.score.AiHandCardValue;
import mage.player.ai.score.GameStateEvaluator2;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;
import mage.target.common.TargetPermanentOrPlayer;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class PossibleTargetsSelectorAITest extends CardTestPlayerBase {

    private static final String STRATEGY_ENABLED_PROPERTY = "xmage.ai.strategy";
    private static final String STRATEGY_TRACE_ONLY_PROPERTY = "xmage.ai.strategy.traceOnly";
    private static final String HAND_QUALITY_ENABLED_PROPERTY = "xmage.ai.strategy.handQuality.enabled";
    private static final String HAND_QUALITY_APPLY_PROPERTY = "xmage.ai.strategy.handQuality.apply";

    @Test
    public void test_SortByOutcome() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Spectral Bears", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1); // land
        addCard(Zone.BATTLEFIELD, playerA, "Gideon, Martial Paragon", 1);  // planeswalker
        //
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1); // land
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Brigand", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 1); // 4/4


        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // most valuable (planeswalker -> player -> bigger -> smaller)

            // good effect
            PossibleTargetsSelector selector = prepareAnyTargetSelector(Outcome.Benefit);
            selector.findNewTargets(null);
            assertTargets("good effect must return my most valuable and biggest as priority", selector.getGoodTargets(), Arrays.asList(
                    "Gideon, Martial Paragon", // pw
                    "PlayerA", // p
                    "Spectral Bears", // 3/3
                    "Balduvian Bears", // 2/2
                    "Arbor Elf", // 1/1
                    "Forest" // l
            ));
            assertTargets("good effect must return opponent's lowest as optional", selector.getBadTargets(), Arrays.asList(
                    "Forest", // l
                    "Goblin Brigand", // 2/2
                    "Battering Sliver", // 4/4
                    "PlayerB" // p
            ));

            // bad effect - must be inverted
            selector = prepareAnyTargetSelector(Outcome.Detriment);
            selector.findNewTargets(null);
            assertTargets("bad effect must return opponent's most valuable and biggest as priority", selector.getGoodTargets(), Arrays.asList(
                    "PlayerB", // p
                    "Battering Sliver", // 4/4
                    "Goblin Brigand", // 1/1
                    "Forest" // l
            ));
            assertTargets("bad effect must return my lowest as optional", selector.getBadTargets(), Arrays.asList(
                    "Forest", // l
                    "Arbor Elf", // 1/1
                    "Balduvian Bears", // 2/2
                    "Spectral Bears", // 3/3
                    "PlayerA", // p
                    "Gideon, Martial Paragon" // pw
            ));
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_SortByPlayable() {
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1); // 2/2, {1}{G}
        addCard(Zone.HAND, playerA, "Arbor Elf", 1); // 1/1, {G}, playable
        addCard(Zone.HAND, playerA, "Spectral Bears", 1); // 3/3, {1}{G}
        addCard(Zone.HAND, playerA, "Forest", 1); // land
        addCard(Zone.HAND, playerA, "Gideon, Martial Paragon", 1);  // planeswalker, {4}{W}
        //
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // default discard logic preserves the original Mad AI control ordering

            PossibleTargetsSelector selector = prepareDiscardCardSelector();
            selector.findNewTargets(null);
            assertTargets("default discard must return original unplayable ordering", selector.getGoodTargets(), Arrays.asList(
                    "Gideon, Martial Paragon", // pw
                    "Spectral Bears", // 3/3
                    "Balduvian Bears", // 2/2
                    "Arbor Elf", // 1/1 - playable
                    "Forest" // l
            ));
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_StrategicHandQualityDiscardTargetingRequiresApplyConfig() {
        withStrategyProperties(null, null, null, null, () ->
                Assert.assertFalse("hand-quality targeting must be monitor-only by default",
                        strategicHandQualityDiscardTargetingEnabled()));

        withStrategyProperties("true", null, "true", "true", () ->
                Assert.assertTrue("ApplyHandQuality should enable strategic hand-quality targeting",
                        strategicHandQualityDiscardTargetingEnabled()));

        withStrategyProperties("true", "true", "true", "true", () ->
                Assert.assertFalse("TraceOnly must not change discard targeting behavior",
                        strategicHandQualityDiscardTargetingEnabled()));

        withStrategyProperties("true", null, "false", "true", () ->
                Assert.assertFalse("disabled hand-quality module must not change discard targeting behavior",
                        strategicHandQualityDiscardTargetingEnabled()));

        withStrategyProperties("false", null, "true", "true", () ->
                Assert.assertFalse("disabled strategic AI must not change discard targeting behavior",
                        strategicHandQualityDiscardTargetingEnabled()));
    }

    @Test
    public void test_DefaultDiscardSortPreservesOriginalMadDynamicOrdering() {
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);
        addCard(Zone.HAND, playerA, "Lord of Extinction", 1);
        addCard(Zone.HAND, playerA, "Forest", 1);

        runCode("check original mad dynamic ordering", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            PossibleTargetsSelector selector = prepareDiscardCardSelector();
            selector.findNewTargets(null);
            assertTargets("default discard must keep original Mad AI ordering for dynamic cards", selector.getGoodTargets(), Arrays.asList(
                    "Lord of Extinction",
                    "Balduvian Bears",
                    "Forest"
            ));
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_StrategicDiscardSortKeepsDynamicGraveyardThreat() {
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1); // 2/2, ordinary discard candidate
        addCard(Zone.HAND, playerA, "Lord of Extinction", 1); // 0/0 printed, dynamic in all zones
        addCard(Zone.HAND, playerA, "Forest", 1); // land should remain last
        addCard(Zone.GRAVEYARD, playerA, "Memnite", 5);
        addCard(Zone.GRAVEYARD, playerB, "Memnite", 5);

        runCode("check dynamic hand threat", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card lord = player.getHand().getCards(game).stream()
                    .filter(card -> card.getName().equals("Lord of Extinction"))
                    .findFirst()
                    .orElse(null);
            Card bear = player.getHand().getCards(game).stream()
                    .filter(card -> card.getName().equals("Balduvian Bears"))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull("Lord of Extinction must be in hand for the regression", lord);
            Assert.assertNotNull("Balduvian Bears must be in hand for comparison", bear);

            AiHandCardValue.CardValue lordValue = AiHandCardValue.estimateKeepValue(game, lord, player.getId());
            AiHandCardValue.CardValue bearValue = AiHandCardValue.estimateKeepValue(game, bear, player.getId());
            Assert.assertTrue("Lord of Extinction hand valuation must use dynamic stats", lordValue.isDynamicStats());
            Assert.assertEquals("Lord of Extinction should count all graveyard cards as power", 10, lordValue.getEstimatedPower());
            Assert.assertEquals("Lord of Extinction should count all graveyard cards as toughness", 10, lordValue.getEstimatedToughness());
            Assert.assertTrue("Dynamic 10/10 threat must keep higher than an ordinary 2/2",
                    lordValue.getKeepScore() > bearValue.getKeepScore());
            Assert.assertTrue("The dynamic hand-card score should exceed the old flat card score",
                    AiHandCardValue.estimateHandScore(game, lord, player.getId()) > GameStateEvaluator2.HAND_CARD_SCORE);

            PossibleTargetsSelector selector = prepareStrategicDiscardCardSelector();
            selector.findNewTargets(null);
            assertTargets("strategic discard must not treat dynamic 0/0 hand threats as fodder", selector.getGoodTargets(), Arrays.asList(
                    "Balduvian Bears",
                    "Lord of Extinction",
                    "Forest"
            ));
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_StrategicDiscardSortKeepsEmptyGraveyardScalerForFutureValue() {
        addCard(Zone.HAND, playerA, "Sengir Vampire", 1); // large ordinary creature
        addCard(Zone.HAND, playerA, "Lord of Extinction", 1); // current 0/0, future graveyard scaler
        addCard(Zone.HAND, playerA, "Forest", 1);

        runCode("check empty graveyard future value", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card lord = player.getHand().getCards(game).stream()
                    .filter(card -> card.getName().equals("Lord of Extinction"))
                    .findFirst()
                    .orElse(null);
            Card vampire = player.getHand().getCards(game).stream()
                    .filter(card -> card.getName().equals("Sengir Vampire"))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull("Lord of Extinction must be in hand for the regression", lord);
            Assert.assertNotNull("Sengir Vampire must be in hand for comparison", vampire);

            AiHandCardValue.CardValue lordValue = AiHandCardValue.estimateKeepValue(game, lord, player.getId());
            AiHandCardValue.CardValue vampireValue = AiHandCardValue.estimateKeepValue(game, vampire, player.getId());
            Assert.assertTrue("Empty-graveyard Lord should still be recognized as dynamic", lordValue.isDynamicStats());
            Assert.assertEquals("Empty graveyards mean current Lord power is still zero", 0, lordValue.getEstimatedPower());
            Assert.assertTrue("Empty-graveyard Lord should receive future graveyard-scaling value",
                    lordValue.getDetail().contains("future potential"));
            Assert.assertTrue("Future graveyard scaler should not be treated as worse than an ordinary five-drop",
                    lordValue.getKeepScore() > vampireValue.getKeepScore());

            PossibleTargetsSelector selector = prepareStrategicDiscardCardSelector();
            selector.findNewTargets(null);
            assertTargets("strategic discard must preserve empty-graveyard future scalers", selector.getGoodTargets(), Arrays.asList(
                    "Sengir Vampire",
                    "Lord of Extinction",
                    "Forest"
            ));
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_StrategicDiscardSortKeepsBoardCountScalersForFutureValue() {
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1); // ordinary 2/2
        addCard(Zone.HAND, playerA, "Wayfaring Temple", 1); // scales with creatures you control
        addCard(Zone.HAND, playerA, "Zendikar Incarnate", 1); // scales with lands you control
        addCard(Zone.HAND, playerA, "Forest", 1);

        runCode("check board scaler future value", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card bear = findHandCard(player, game, "Balduvian Bears");
            Card temple = findHandCard(player, game, "Wayfaring Temple");
            Card zendikar = findHandCard(player, game, "Zendikar Incarnate");
            Assert.assertNotNull("Balduvian Bears must be in hand for comparison", bear);
            Assert.assertNotNull("Wayfaring Temple must be in hand for the regression", temple);
            Assert.assertNotNull("Zendikar Incarnate must be in hand for the regression", zendikar);

            AiHandCardValue.CardValue bearValue = AiHandCardValue.estimateKeepValue(game, bear, player.getId());
            AiHandCardValue.CardValue templeValue = AiHandCardValue.estimateKeepValue(game, temple, player.getId());
            AiHandCardValue.CardValue zendikarValue = AiHandCardValue.estimateKeepValue(game, zendikar, player.getId());
            Assert.assertTrue("Creature-count scaler should receive future board-scaling value",
                    templeValue.getDetail().contains("future potential"));
            Assert.assertTrue("Land-count scaler should receive future board-scaling value",
                    zendikarValue.getDetail().contains("future potential"));
            Assert.assertTrue("Creature-count scaler should keep higher than an ordinary 2/2",
                    templeValue.getKeepScore() > bearValue.getKeepScore());
            Assert.assertTrue("Land-count scaler should keep higher than an ordinary 2/2",
                    zendikarValue.getKeepScore() > bearValue.getKeepScore());

            PossibleTargetsSelector selector = prepareStrategicDiscardCardSelector();
            selector.findNewTargets(null);
            List<String> names = targetNames(selector.getGoodTargets());
            Assert.assertEquals("ordinary 2/2 should be discarded before future scalers",
                    "Balduvian Bears", names.get(0));
            Assert.assertEquals("lands should still be preserved to the end of discard sorting",
                    "Forest", names.get(names.size() - 1));
            Assert.assertTrue("Wayfaring Temple should not be first discard",
                    names.indexOf("Wayfaring Temple") > 0);
            Assert.assertTrue("Zendikar Incarnate should not be first discard",
                    names.indexOf("Zendikar Incarnate") > 0);
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    private PossibleTargetsSelector prepareAnyTargetSelector(Outcome outcome) {
        Target target = new TargetPermanentOrPlayer();
        Ability fakeAbility = new SimpleStaticAbility(new InfoEffect("fake"));
        return new PossibleTargetsSelector(outcome, target, playerA.getId(), fakeAbility, currentGame);
    }

    private PossibleTargetsSelector prepareDiscardCardSelector() {
        return prepareDiscardCardSelector(false);
    }

    private PossibleTargetsSelector prepareStrategicDiscardCardSelector() {
        return prepareDiscardCardSelector(true);
    }

    private PossibleTargetsSelector prepareDiscardCardSelector(boolean useHandQualityDiscardSort) {
        Target target = new TargetDiscard(playerA.getId());
        Ability fakeAbility = new SimpleStaticAbility(new InfoEffect("fake"));
        // discard sorting do not use outcome
        return new PossibleTargetsSelector(Outcome.Benefit, target, playerA.getId(), fakeAbility, currentGame,
                useHandQualityDiscardSort);
    }

    private Card findHandCard(Player player, mage.game.Game game, String name) {
        return player.getHand().getCards(game).stream()
                .filter(card -> card.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void assertTargets(String info, List<MageItem> targets, List<String> needTargets) {
        List<String> currentTargets = targetNames(targets);
        String current = String.join("\n", currentTargets);
        String need = String.join("\n", needTargets);
        if (!current.equals(need)) {
            Assert.fail(info + "\n\n"
                    + "NEED targets:\n" + need + "\n\n"
                    + "FOUND targets:\n" + current + "\n");
        }
    }

    private List<String> targetNames(List<MageItem> targets) {
        return targets.stream()
                .map(item -> {
                    if (item instanceof Player) {
                        return ((Player) item).getName();
                    } else if (item instanceof MageObject) {
                        return ((MageObject) item).getName();
                    } else {
                        return "unknown item";
                    }
                })
                .collect(Collectors.toList());
    }

    private boolean strategicHandQualityDiscardTargetingEnabled() {
        return new ExposedMadStrategicPlayer().usesHandQualityDiscardTargeting();
    }

    private void withStrategyProperties(String strategyEnabled, String traceOnly, String handQualityEnabled,
                                        String handQualityApply, Runnable runnable) {
        String originalStrategyEnabled = System.getProperty(STRATEGY_ENABLED_PROPERTY);
        String originalTraceOnly = System.getProperty(STRATEGY_TRACE_ONLY_PROPERTY);
        String originalHandQualityEnabled = System.getProperty(HAND_QUALITY_ENABLED_PROPERTY);
        String originalHandQualityApply = System.getProperty(HAND_QUALITY_APPLY_PROPERTY);
        try {
            setOrClearProperty(STRATEGY_ENABLED_PROPERTY, strategyEnabled);
            setOrClearProperty(STRATEGY_TRACE_ONLY_PROPERTY, traceOnly);
            setOrClearProperty(HAND_QUALITY_ENABLED_PROPERTY, handQualityEnabled);
            setOrClearProperty(HAND_QUALITY_APPLY_PROPERTY, handQualityApply);
            runnable.run();
        } finally {
            setOrClearProperty(STRATEGY_ENABLED_PROPERTY, originalStrategyEnabled);
            setOrClearProperty(STRATEGY_TRACE_ONLY_PROPERTY, originalTraceOnly);
            setOrClearProperty(HAND_QUALITY_ENABLED_PROPERTY, originalHandQualityEnabled);
            setOrClearProperty(HAND_QUALITY_APPLY_PROPERTY, originalHandQualityApply);
        }
    }

    private static void setOrClearProperty(String property, String value) {
        if (value == null) {
            System.clearProperty(property);
        } else {
            System.setProperty(property, value);
        }
    }

    private static final class ExposedMadStrategicPlayer extends ComputerPlayerMadStrategic {

        private ExposedMadStrategicPlayer() {
            super("strategic", RangeOfInfluence.ONE, 6);
        }

        private boolean usesHandQualityDiscardTargeting() {
            return shouldUseHandQualityDiscardTargeting();
        }
    }
}
