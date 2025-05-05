package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 */
public class ReadAheadAbility extends StaticAbility implements MageSingleton {

    private static final ReadAheadAbility instance;

    static {
        instance = new ReadAheadAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ReadAheadAbility getInstance() {
        return instance;
    }

    private ReadAheadAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "read ahead <i>(Choose a chapter and start with that many lore counters. Add one after your draw step. Skipped chapters don't trigger.)</i>";
    }

    @Override
    public ReadAheadAbility copy() {
        return instance;
    }

}
