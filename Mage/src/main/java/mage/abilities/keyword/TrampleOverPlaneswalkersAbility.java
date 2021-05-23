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
        instance.addIcon(TrampleAbilityIcon.instance);
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
    public String getRule() { return "trample over planeswalkers"; }

    @Override
    public TrampleOverPlaneswalkersAbility copy() {
        return instance;
    }

}
