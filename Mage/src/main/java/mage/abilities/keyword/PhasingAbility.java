

package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PhasingAbility extends StaticAbility implements MageSingleton {

    private static final PhasingAbility instance =  new PhasingAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static PhasingAbility getInstance() {
        return instance;
    }

    private PhasingAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "phasing <i>(This phases in or out before you untap during each of your untap steps. While it's phased out, it's treated as though it doesn't exist.)</i>";
    }

    @Override
    public PhasingAbility copy() {
        return instance;
    }

}
