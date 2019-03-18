
package mage.abilities.common;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 * @author L_J
 */
public class ControllerAssignCombatDamageToBlockersAbility extends StaticAbility implements MageSingleton {

    private static final ControllerAssignCombatDamageToBlockersAbility instance =  new ControllerAssignCombatDamageToBlockersAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ControllerAssignCombatDamageToBlockersAbility getInstance() {
        return instance;
    }

    private ControllerAssignCombatDamageToBlockersAbility() {
        super(AbilityType.STATIC, Zone.BATTLEFIELD);
    }

    @Override
    public String getRule() {
        return "Rather than the attacking player, you assign the combat damage of each creature attacking you. You can divide that creature's combat damage as you choose among any of the creatures blocking it.";
    }

    @Override
    public ControllerAssignCombatDamageToBlockersAbility copy() {
        return instance;
    }

}
