package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */
public enum SourceMutatedCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = sourceAbility.getSourcePermanentOrLKI(game);
        return (sourcePermanent == null) ? 0 : sourcePermanent.getMutateCount();
    }

    @Override
    public SourceMutatedCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of times this creature has mutated";
    }
}
