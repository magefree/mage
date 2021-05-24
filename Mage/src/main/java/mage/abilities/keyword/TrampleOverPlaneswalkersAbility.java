package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.abilities.TrampleAbilityIcon;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author Fubs
 */
public class TrampleOverPlaneswalkersAbility extends StaticAbility implements MageSingleton {

    private static final TrampleOverPlaneswalkersAbility instance;

    static {
        instance = new TrampleOverPlaneswalkersAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static TrampleOverPlaneswalkersAbility getInstance() {
        return instance;
    }

    private TrampleOverPlaneswalkersAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "Trample over planeswalkers <i>(This creature can deal excess combat damage to the controller of the planeswalker it's attacking.)</i>";
    }

    @Override
    public TrampleOverPlaneswalkersAbility copy() {
        return instance;
    }

}
