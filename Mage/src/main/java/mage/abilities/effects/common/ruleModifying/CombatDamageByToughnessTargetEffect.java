package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CombatDamageByToughnessTargetEffect extends ContinuousEffectImpl {
    
    public CombatDamageByToughnessTargetEffect(Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Neutral);
    }

    private CombatDamageByToughnessTargetEffect(final CombatDamageByToughnessTargetEffect effect) {
        super(effect);
    }

    @Override
    public CombatDamageByToughnessTargetEffect copy() {
        return new CombatDamageByToughnessTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }

        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PermanentIdPredicate(permanent.getId()));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);

        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "this creature assigns combat damage equal to its toughness rather than its power";
    }

}