
package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.Mode;
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
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        this.filter = filter;
        this.onlyControlled = onlyControlled;
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
    public boolean apply(Game game, Ability source) {
        // Change the rule
        FilterCreaturePermanent filterPermanent;
        if (onlyControlled) {
            filterPermanent = filter.copy();
            filterPermanent.add(new ControllerIdPredicate(source.getControllerId()));
        } else {
            filterPermanent = filter;
        }
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filterPermanent);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("Each ");
        sb.append(filter.getMessage());
        if (onlyControlled && !filter.getMessage().contains("you control")) {
            sb.append(" you control");
        }
        sb.append(" assigns combat damage equal to its toughness rather than its power");
        return sb.toString();
    }
}
