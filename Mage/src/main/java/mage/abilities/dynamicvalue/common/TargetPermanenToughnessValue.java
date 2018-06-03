

package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 *
 * @author LevelX2
 */

public class TargetPermanenToughnessValue implements DynamicValue {
    
    private static final TargetPermanenToughnessValue instance =  new TargetPermanenToughnessValue();
    
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }   
    
    public static TargetPermanenToughnessValue getInstance() {
        return instance;
    }
    
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getFirstTarget(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null) {
            return sourcePermanent.getToughness().getValue();
        }
        return 0;
    }

    @Override
    public TargetPermanenToughnessValue copy() {
        return new TargetPermanenToughnessValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "target creature's toughness";
    }
}
