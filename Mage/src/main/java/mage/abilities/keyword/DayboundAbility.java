package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 * TODO: Implement this
 */
public class DayboundAbility extends StaticAbility implements MageSingleton {

    private static final DayboundAbility instance;

    static {
        instance = new DayboundAbility();
        // instance.addIcon(DayboundAbilityIcon.instance); (needs to be added)
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DayboundAbility getInstance() {
        return instance;
    }

    private DayboundAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "daybound <i>(If a player casts no spells during their own turn, it becomes night next turn.)</i>";
    }

    @Override
    public DayboundAbility copy() {
        return instance;
    }
}
