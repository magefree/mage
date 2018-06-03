

package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

/**
 * @author magenoxx_at_googlemail.com
 */
public class CantBlockAloneAbility extends StaticAbility implements MageSingleton {

    private static final CantBlockAloneAbility instance = new CantBlockAloneAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static CantBlockAloneAbility getInstance() {
        return instance;
    }

    private CantBlockAloneAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "{this} can't block alone.";
    }

    @Override
    public CantBlockAloneAbility copy() {
        return instance;
    }

}
