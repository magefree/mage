package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class CanBlockSpaceflightAbility extends StaticAbility implements MageSingleton {

    private static final CanBlockSpaceflightAbility instance = new CanBlockSpaceflightAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static CanBlockSpaceflightAbility getInstance() {
        return instance;
    }

    private CanBlockSpaceflightAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "{this} can block creatures with spaceflight.";
    }

    @Override
    public CanBlockSpaceflightAbility copy() {
        return instance;
    }

}
