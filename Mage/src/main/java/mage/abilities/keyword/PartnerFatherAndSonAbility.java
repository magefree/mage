package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 *
 * @author LevelX2
 */
public class PartnerFatherAndSonAbility extends StaticAbility implements MageSingleton {

    private static final PartnerFatherAndSonAbility instance = new PartnerFatherAndSonAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static PartnerFatherAndSonAbility getInstance() {
        return instance;
    }

    private PartnerFatherAndSonAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "Partner&mdash;Father & son <i>(You can have two commanders if both have this ability.)</i>";
    }

    @Override
    public PartnerFatherAndSonAbility copy() {
        return instance;
    }

}
