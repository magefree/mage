package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
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
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null) {
            return sourcePermanent.getToughness().getValue();
        }
        return 0;
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
