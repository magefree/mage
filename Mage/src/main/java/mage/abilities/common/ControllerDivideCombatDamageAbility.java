
package mage.abilities.common;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 * @author L_J
 */
public class ControllerDivideCombatDamageAbility extends StaticAbility implements MageSingleton {

    private static final ControllerDivideCombatDamageAbility instance =  new ControllerDivideCombatDamageAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ControllerDivideCombatDamageAbility getInstance() {
        return instance;
    }

    private ControllerDivideCombatDamageAbility() {
        super(AbilityType.STATIC, Zone.BATTLEFIELD);
    }

    @Override
    public String getRule() {
        return "You may assign {this}'s combat damage divided as you choose among defending player and/or any number of creatures they control.";
    }

    @Override
    public ControllerDivideCombatDamageAbility copy() {
        return instance;
    }

}
