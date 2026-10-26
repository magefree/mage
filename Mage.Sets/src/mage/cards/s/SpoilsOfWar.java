package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.EarlyTargetCost;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.common.TargetOpponent;

import java.util.IntSummaryStatistics;
import java.util.Objects;
import java.util.UUID;

public final class SpoilsOfWar extends CardImpl {

    public SpoilsOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // X is the number of artifact and/or creature cards in an opponent's graveyard as you cast this spell.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("X is the number of artifact and/or creature cards in an opponents graveyard as you cast this spell.")
        ).setRuleAtTheTop(true));
        this.getSpellAbility().addCost(new SpoilsOfWarXCost());
        this.getSpellAbility().setCostAdjuster(SpoilsOfWarCostAdjuster.instance);

        // Distribute X +1/+1 counters among any number of target creatures.
        this.getSpellAbility().addEffect(new DistributeCountersEffect().setText("distribute X +1/+1 counters among any number of target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(GetXValue.instance, StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private SpoilsOfWar(final SpoilsOfWar card) {
        super(card);
    }

    @Override
    public SpoilsOfWar copy() {
        return new SpoilsOfWar(this);
    }
}

class SpoilsOfWarXCost extends VariableManaCost implements EarlyTargetCost {

    public SpoilsOfWarXCost() {
        super(VariableCostType.NORMAL, 1);
        this.setText("{X}");
    }

    public SpoilsOfWarXCost(final SpoilsOfWarXCost cost) {
        super(cost);
    }

    @Override
    public void chooseTarget(Game game, Ability source, Player controller) {
        final Target targetOpponent = new TargetOpponent(true).withNotTarget(true);
        controller.choose(Outcome.Benefit, targetOpponent, source, game);
        this.addTarget(targetOpponent);
    }

    @Override
    public SpoilsOfWarXCost copy() {
        return new SpoilsOfWarXCost(this);
    }
}

enum SpoilsOfWarCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void prepareX(Ability ability, Game game) {
        final SpoilsOfWarXCost cost = ability.getManaCostsToPay().getVariableCosts().stream()
                .filter(SpoilsOfWarXCost.class::isInstance)
                .map(SpoilsOfWarXCost.class::cast)
                .findFirst()
                .orElse(null);
        if (cost == null) {
            return;
        }

        if (game.inCheckPlayableState()) {
            // possible X
            final IntSummaryStatistics stats = game.getOpponents(ability.getControllerId(), true).stream()
                    .map(game::getPlayer)
                    .filter(Objects::nonNull)
                    .mapToInt(player -> player.getGraveyard().count(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE, ability.getControllerId(), ability, game))
                    .summaryStatistics();
            ability.setVariableCostsMinMax(stats.getCount() > 0 ? stats.getMin() : 0, stats.getCount() > 0 ? stats.getMax() : 0);
        } else {
            // real X
            final Player opponent = game.getPlayer(cost.getTargets().getFirstTarget());
            if (opponent == null) {
                return;
            }
            ability.setVariableCostsValue(opponent.getGraveyard().count(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE, ability.getControllerId(), ability, game));
        }
    }
}
