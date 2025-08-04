package org.mage.test.cards.targets;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Helper class for target selection tests in diff use cases like selection on targets declare or on resolve
 *
 * @author JayDi85
 */
public class TargetsSelectionBaseTest extends CardTestPlayerBaseWithAIHelps {

    static final boolean DEBUG_ENABLE_DETAIL_LOGS = true;

    protected void run_PlayerChooseTarget_OnActivate(int chooseCardsCount, int availableCardsCount) {
        // custom effect - exile and gain life due selected targets
        int minTarget = 0;
        int maxTarget = 3;
        String startingText = "exile and gain";
        Ability ability = new SimpleActivatedAbility(
                new ExileTargetEffect().setText("exile"),
                new ManaCostsImpl<>("")
        );
        ability.addEffect(new GainLifeEffect(TotalTargetsValue.instance).concatBy("and").setText("gain life"));
        ability.addTarget(new TargetCardInHand(minTarget, maxTarget, StaticFilters.FILTER_CARD).withNotTarget(false));
        addCustomCardWithAbility("test choice", playerA, ability);

        addCard(Zone.HAND, playerA, "Swamp", availableCardsCount);

        checkHandCardCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp", availableCardsCount);
        checkExileCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp", 0);

        // cause minTarget = 0, so ability can be activated at any time
        checkPlayableAbility("can activate on any targets", 1, PhaseStep.PRECOMBAT_MAIN, playerA, startingText, true);
        if (availableCardsCount > 0) {
            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, startingText);
        }

        if (chooseCardsCount > 0) {
            // need selection
            List<String> targetCards = new ArrayList<>();
            IntStream.rangeClosed(1, chooseCardsCount).forEach(x -> {
                targetCards.add("Swamp");
            });
            addTarget(playerA, String.join("^", targetCards));
            // end selection:
            // - x of 0 - no
            // - 1 of 3 - yes
            // - 2 of 3 - yes
            // - 3 of 3 - no, it's auto-finish on last select
            // - 3 of 5 - no, it's auto-finish on last select
            int maxPossibleChoose = Math.min(availableCardsCount, maxTarget);
            if (chooseCardsCount < maxPossibleChoose) {
                addTarget(playerA, TestPlayer.TARGET_SKIP);
            }
        } else {
            // need skip
            // on 0 cards there are not valid targets, so no any dialogs
            if (availableCardsCount > 0) {
                addTarget(playerA, TestPlayer.TARGET_SKIP);
            }
        }

        if (DEBUG_ENABLE_DETAIL_LOGS) {
            System.out.println("planning actions:");
            playerA.getActions().forEach(System.out::println);
            System.out.println("planning targets:");
            playerA.getTargets().forEach(System.out::println);
            System.out.println("planning choices:");
            playerA.getChoices().forEach(System.out::println);
        }

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Swamp", chooseCardsCount);
        assertLife(playerA, 20 + chooseCardsCount);
    }

    protected void run_PlayerChoose_OnResolve(int chooseCardsCount, int availableCardsCount) {
        // custom effect - select, exile and gain life
        int minTarget = 0;
        int maxTarget = 3;
        String startingText = "select, exile and gain life";
        Ability ability = new SimpleActivatedAbility(
                new SelectExileAndGainLifeCustomEffect(minTarget, maxTarget, Outcome.Benefit),
                new ManaCostsImpl<>("")
        );
        addCustomCardWithAbility("test choice", playerA, ability);

        addCard(Zone.HAND, playerA, "Swamp", availableCardsCount);

        checkHandCardCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp", availableCardsCount);
        checkExileCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp", 0);

        checkPlayableAbility("can activate any time (even with zero cards)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, startingText, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, startingText);

        if (chooseCardsCount > 0) {
            // need selection
            List<String> targetCards = new ArrayList<>();
            IntStream.rangeClosed(1, chooseCardsCount).forEach(x -> {
                targetCards.add("Swamp");
            });
            setChoice(playerA, String.join("^", targetCards));
        }

        // choose skip
        // end selection condition:
        // - x of 0 - yes
        // - 1 of 3 - yes
        // - 2 of 3 - yes
        // - 3 of 3 - no, it's auto-finish on last select
        // - 3 of 5 - no, it's auto-finish on last select and nothing to choose
        int canSelectCount = Math.min(maxTarget, availableCardsCount);
        if (canSelectCount > 0 && chooseCardsCount < canSelectCount) {
            setChoice(playerA, TestPlayer.CHOICE_SKIP);
        }

        if (DEBUG_ENABLE_DETAIL_LOGS) {
            System.out.println("planning actions:");
            playerA.getActions().forEach(System.out::println);
            System.out.println("planning targets:");
            playerA.getTargets().forEach(System.out::println);
            System.out.println("planning choices:");
            playerA.getChoices().forEach(System.out::println);
        }

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Swamp", chooseCardsCount);
        assertLife(playerA, 20 + chooseCardsCount);
    }

    protected void run_PlayerChoose_OnResolve_AI(Outcome outcome, int minTargets, int maxTargets, int aiMustChooseCardsCount, int availableCardsCount) {
        // custom effect - select, exile and gain life
        String startingText = "{T}: Select, exile and gain life";
        Ability ability = new SimpleActivatedAbility(
                new SelectExileAndGainLifeCustomEffect(minTargets, maxTargets, outcome),
                new ManaCostsImpl<>("")
        );
        ability.addCost(new TapSourceCost());
        addCustomCardWithAbility("test choice", playerA, ability);

        addCard(Zone.HAND, playerA, "Swamp", availableCardsCount);
        addCard(Zone.HAND, playerA, "Forest", 1);

        // Forest play is workaround to disable lands play in ai priority
        // {T} cost is workaround to disable multiple calls of the ability
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        checkPlayableAbility("can activate any time (even with zero cards)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, startingText, true);

        // AI must see bad effect and select only halves of the max targets, e.g. 1 of 3
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Swamp", aiMustChooseCardsCount);
        assertLife(playerA, 20 + aiMustChooseCardsCount);
    }
}

enum TotalTargetsValue implements DynamicValue {
    instance;

    @Override
    public TotalTargetsValue copy() {
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return effect.getTargetPointer().getTargets(game, sourceAbility).size();
    }

    @Override
    public String getMessage() {
        return "total targets";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class SelectExileAndGainLifeCustomEffect extends OneShotEffect {

    private final int minTargets;
    private final int maxTargets;

    SelectExileAndGainLifeCustomEffect(int minTargets, int maxTargets, Outcome outcome) {
        super(outcome);
        this.minTargets = minTargets;
        this.maxTargets = maxTargets;
        staticText = "select, exile and gain life";
    }

    private SelectExileAndGainLifeCustomEffect(final SelectExileAndGainLifeCustomEffect effect) {
        super(effect);
        this.minTargets = effect.minTargets;
        this.maxTargets = effect.maxTargets;
    }

    @Override
    public SelectExileAndGainLifeCustomEffect copy() {
        return new SelectExileAndGainLifeCustomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Target target = new TargetCardInHand(this.minTargets, this.maxTargets, StaticFilters.FILTER_CARD).withNotTarget(true);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }

        player.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game), source, game, false, source.getSourceId(), player.getLogName());
        player.gainLife(target.getTargets().size(), game, source);
        return true;
    }
}
