package mage.abilities.common;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author varaghar
 */
public class CrewWithToughnessAbility extends StaticAbility implements MageSingleton {

    private static final CrewWithToughnessAbility instance;

    static {
        instance = new CrewWithToughnessAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static CrewWithToughnessAbility getInstance() {
        return instance;
    }

    private CrewWithToughnessAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect("{this} crews Vehicles using its toughness rather than its power."));
    }

    @Override
    public CrewWithToughnessAbility copy() {
        return instance;
    }
}
