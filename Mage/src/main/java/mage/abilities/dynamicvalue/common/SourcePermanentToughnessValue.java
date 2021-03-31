package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 * @author LevelX2
 */

public class SourcePermanentToughnessValue implements DynamicValue {

    private static final SourcePermanentToughnessValue instance = new SourcePermanentToughnessValue();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static SourcePermanentToughnessValue getInstance() {
        return instance;
    }

    private SourcePermanentToughnessValue() {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return 0;
        }
        return sourcePermanent.getToughness().getValue();
    }

    @Override
    public SourcePermanentToughnessValue copy() {
        return new SourcePermanentToughnessValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "{this}'s toughness";
    }
}
