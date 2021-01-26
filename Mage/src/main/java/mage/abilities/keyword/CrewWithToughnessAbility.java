package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 *
 * @author varaghar
 */
public class CrewWithToughnessAbility extends StaticAbility implements MageSingleton {

    private static final CrewWithToughnessAbility instance = new CrewWithToughnessAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static CrewWithToughnessAbility getInstance() {
        return instance;
    }

    public CrewWithToughnessAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect("{this} crews Vehicles using its toughness rather than its power."));
    }

    public CrewWithToughnessAbility(CrewWithToughnessAbility crewWithToughnessAbility) {
        super(crewWithToughnessAbility);
    }

    @Override
    public CrewWithToughnessAbility copy() {
        return new CrewWithToughnessAbility(this);
    }
}
