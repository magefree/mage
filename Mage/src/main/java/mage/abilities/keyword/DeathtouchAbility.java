
package mage.abilities.keyword;

import java.io.ObjectStreamException;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DeathtouchAbility extends StaticAbility implements MageSingleton {

    private static final DeathtouchAbility instance = new DeathtouchAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DeathtouchAbility getInstance() {
        return instance;
    }

    private DeathtouchAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "deathtouch";
    }

    @Override
    public DeathtouchAbility copy() {
        return instance;
    }

}
