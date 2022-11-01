package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class LegendRuleDoesntApplyEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;

    public LegendRuleDoesntApplyEffect() {
        this(StaticFilters.FILTER_PERMANENTS);
        this.staticText = "the \"legend rule\" doesn't apply";
    }

    public LegendRuleDoesntApplyEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "the \"legend rule\" doesn't apply to " + filter.getMessage();
        this.filter = filter;
    }

    private LegendRuleDoesntApplyEffect(final LegendRuleDoesntApplyEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public LegendRuleDoesntApplyEffect copy() {
        return new LegendRuleDoesntApplyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            permanent.setLegendRuleApplies(false);
        }
        return true;
    }
}