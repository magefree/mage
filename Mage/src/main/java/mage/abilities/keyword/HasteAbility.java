
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HasteAbility extends StaticAbility implements MageSingleton {

    private static final HasteAbility instance = new HasteAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HasteAbility getInstance() {
        return instance;
    }

    private HasteAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "haste";
    }

    @Override
    public HasteAbility copy() {
        return instance;
    }

}
