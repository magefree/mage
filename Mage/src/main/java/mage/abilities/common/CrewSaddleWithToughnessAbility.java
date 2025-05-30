package mage.abilities.common;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 */
public class CrewSaddleWithToughnessAbility extends StaticAbility implements MageSingleton {

    private static final CrewSaddleWithToughnessAbility instance;

    static {
        instance = new CrewSaddleWithToughnessAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static CrewSaddleWithToughnessAbility getInstance() {
        return instance;
    }

    private CrewSaddleWithToughnessAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect("{this} saddles Mounts and crews Vehicles using its toughness rather than its power."));
    }

    @Override
    public CrewSaddleWithToughnessAbility copy() {
        return instance;
    }
}
