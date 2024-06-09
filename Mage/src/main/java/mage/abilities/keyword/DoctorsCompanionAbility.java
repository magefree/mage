package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 */
public class DoctorsCompanionAbility extends StaticAbility implements MageSingleton {

    private static final DoctorsCompanionAbility instance = new DoctorsCompanionAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DoctorsCompanionAbility getInstance() {
        return instance;
    }

    private DoctorsCompanionAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "Doctor's companion <i>(You can have two commanders if the other is the Doctor.)</i>";
    }

    @Override
    public DoctorsCompanionAbility copy() {
        return instance;
    }

}
