
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author muz
 */
public enum TargetPermanentToughnessCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent targetPermanent = effect.getTargetPointer().getFirstTargetPermanentOrLKI(game, sourceAbility);
        if (targetPermanent != null) {
            return targetPermanent.getToughness().getValue();
        }

        return 0;
    }

    @Override
    public TargetPermanentToughnessCount copy() {
        return TargetPermanentToughnessCount.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "its toughness";
    }
}
