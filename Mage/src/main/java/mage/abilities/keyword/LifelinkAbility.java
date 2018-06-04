

package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LifelinkAbility extends StaticAbility implements MageSingleton {

    private static final LifelinkAbility instance =  new LifelinkAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static LifelinkAbility getInstance() {
        return instance;
    }

    private LifelinkAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "lifelink <i>(Damage dealt by this creature also causes you to gain that much life.)</i>";
    }

    @Override
    public LifelinkAbility copy() {
        return instance;
    }

}
