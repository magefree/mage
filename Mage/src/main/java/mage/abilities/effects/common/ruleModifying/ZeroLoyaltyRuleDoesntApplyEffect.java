package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author DarioBarrabinoDev
 */
public class ZeroLoyaltyRuleDoesntApplyEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;

    public ZeroLoyaltyRuleDoesntApplyEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = filter.getMessage() + " aren't put into their owners' graveyards for having 0 loyalty";
        this.filter = filter;
    }

    private ZeroLoyaltyRuleDoesntApplyEffect(final ZeroLoyaltyRuleDoesntApplyEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public ZeroLoyaltyRuleDoesntApplyEffect copy() {
        return new ZeroLoyaltyRuleDoesntApplyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            permanent.setZeroLoyaltyRuleApplies(false);
        }
        return true;
    }
}
