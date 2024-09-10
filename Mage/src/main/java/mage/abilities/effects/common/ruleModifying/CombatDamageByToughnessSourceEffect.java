package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CombatDamageByToughnessSourceEffect extends ContinuousEffectImpl {
    
    public CombatDamageByToughnessSourceEffect(Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Neutral);
        this.staticText = "{this} assigns combat damage equal to its toughness rather than its power";
    }

    private CombatDamageByToughnessSourceEffect(final CombatDamageByToughnessSourceEffect effect) {
        super(effect);
    }

    @Override
    public CombatDamageByToughnessSourceEffect copy() {
        return new CombatDamageByToughnessSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }

        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new MageObjectReferencePredicate(permanent.getId(), game));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);

        return true;
    }

}
