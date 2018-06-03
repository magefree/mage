

package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ShroudAbility extends StaticAbility implements MageSingleton {

    private static final ShroudAbility instance =  new ShroudAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ShroudAbility getInstance() {
        return instance;
    }

    private ShroudAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "shroud";
    }

    @Override
    public ShroudAbility copy() {
        return instance;
    }

}
