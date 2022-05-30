package mage.abilities.common;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 */
public class ChooseABackgroundAbility extends StaticAbility implements MageSingleton {

    private static final ChooseABackgroundAbility instance = new ChooseABackgroundAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ChooseABackgroundAbility getInstance() {
        return instance;
    }

    private ChooseABackgroundAbility() {
        super(Zone.ALL, new InfoEffect("choose a background <i>(You can have a Background as a second commander.)</i>"));
    }

    @Override
    public ChooseABackgroundAbility copy() {
        return instance;
    }
}
