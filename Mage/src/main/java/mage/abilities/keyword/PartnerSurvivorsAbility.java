package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author LevelX2
 */
public class PartnerSurvivorsAbility extends StaticAbility implements MageSingleton {

    private static final PartnerSurvivorsAbility instance = new PartnerSurvivorsAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static PartnerSurvivorsAbility getInstance() {
        return instance;
    }

    private PartnerSurvivorsAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "Partner&mdash;Survivors <i>(You can have two commanders if both have this ability.)</i>";
    }

    @Override
    public PartnerSurvivorsAbility copy() {
        return instance;
    }

}
