

package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DefenderAbility extends StaticAbility implements MageSingleton {

    private static final DefenderAbility instance =  new DefenderAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DefenderAbility getInstance() {
        return instance;
    }

    private DefenderAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "defender";
    }

    @Override
    public DefenderAbility copy() {
        return instance;
    }

}
