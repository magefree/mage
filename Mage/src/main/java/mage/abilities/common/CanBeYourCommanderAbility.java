
package mage.abilities.common;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */

public class CanBeYourCommanderAbility extends StaticAbility implements MageSingleton {

    private static final CanBeYourCommanderAbility instance =  new CanBeYourCommanderAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static CanBeYourCommanderAbility getInstance() {
        return instance;
    }

    private CanBeYourCommanderAbility() {
        super(Zone.ALL, new InfoEffect("{this} can be your commander"));
    }

    @Override
    public CanBeYourCommanderAbility copy() {
        return instance;
    }

}
