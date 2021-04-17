package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class PartnerAbility extends StaticAbility implements MageSingleton {

    private static final PartnerAbility instance = new PartnerAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static PartnerAbility getInstance() {
        return instance;
    }

    private PartnerAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "Partner <i>(You can have two commanders if both have partner.)</i>";
    }

    @Override
    public PartnerAbility copy() {
        return instance;
    }

}
