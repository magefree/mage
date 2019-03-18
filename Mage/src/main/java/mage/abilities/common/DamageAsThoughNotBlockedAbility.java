
package mage.abilities.common;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 * @author BetaSteward
 */
public class DamageAsThoughNotBlockedAbility extends StaticAbility implements MageSingleton {

    private static final DamageAsThoughNotBlockedAbility instance =  new DamageAsThoughNotBlockedAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DamageAsThoughNotBlockedAbility getInstance() {
        return instance;
    }

    private DamageAsThoughNotBlockedAbility() {
        super(AbilityType.STATIC, Zone.BATTLEFIELD);
    }

    @Override
    public String getRule() {
        return "You may have {this} assign its combat damage as though it weren't blocked.";
    }

    @Override
    public DamageAsThoughNotBlockedAbility copy() {
        return instance;
    }

}