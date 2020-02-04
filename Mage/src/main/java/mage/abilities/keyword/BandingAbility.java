
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author L_J
 */
public class BandingAbility extends StaticAbility implements MageSingleton {

    private static final BandingAbility instance = new BandingAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static BandingAbility getInstance() {
        return instance;
    }

    private BandingAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "banding";
    }

    @Override
    public BandingAbility copy() {
        return instance;
    }

}
