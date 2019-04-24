
package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class CombatDamageByToughnessEffect extends ContinuousEffectImpl {

    private final FilterCreaturePermanent filter;
    private final boolean onlyControlled;

    public CombatDamageByToughnessEffect(FilterCreaturePermanent filter, boolean onlyControlled) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.filter = filter;
        this.onlyControlled = onlyControlled;
        staticText = "Each " + filter.getMessage() + (onlyControlled ? " you control" : "") +
                " assigns combat damage equal to its toughness rather than its power";
    }

    private CombatDamageByToughnessEffect(final CombatDamageByToughnessEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.onlyControlled = effect.onlyControlled;
    }

    @Override
    public CombatDamageByToughnessEffect copy() {
        return new CombatDamageByToughnessEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        // Change the rule
        FilterCreaturePermanent filterPermanent = filter.copy();
        if (onlyControlled) {
            filterPermanent.add(new ControllerIdPredicate(source.getControllerId()));
        }
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filterPermanent);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
