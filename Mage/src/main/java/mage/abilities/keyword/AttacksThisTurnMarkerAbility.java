

package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

/**
 * This is marker ability that is used only for rule display.
 * You should use {@see AttacksIfAbleTargetEffect} for real effect.
 *
 * @author magenoxx_at_googlemail.com
 */
public class AttacksThisTurnMarkerAbility extends StaticAbility implements MageSingleton {

    private static final AttacksThisTurnMarkerAbility instance =  new AttacksThisTurnMarkerAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static AttacksThisTurnMarkerAbility getInstance() {
        return instance;
    }

    private AttacksThisTurnMarkerAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "{this} attacks this turn if able";
    }

    @Override
    public AttacksThisTurnMarkerAbility copy() {
        return instance;
    }

}
