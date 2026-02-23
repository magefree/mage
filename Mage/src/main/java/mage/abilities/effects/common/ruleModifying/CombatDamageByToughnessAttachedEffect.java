package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class CombatDamageByToughnessAttachedEffect extends ContinuousEffectImpl {

    private final Condition condition;

    public CombatDamageByToughnessAttachedEffect(Condition condition, String text) {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.condition = condition;
        this.staticText = text;
    }

    private CombatDamageByToughnessAttachedEffect(final CombatDamageByToughnessAttachedEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public CombatDamageByToughnessAttachedEffect copy() {
        return new CombatDamageByToughnessAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (condition != null && !condition.apply(game, source)) {
            return false;
        }
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PermanentIdPredicate(permanent.getId()));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);
        return true;
    }
}
