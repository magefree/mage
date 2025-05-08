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
        this.setRuleAtTheTop(true);
    }

    @Override
    public String getRule() {
        return "read ahead";
    }

    @Override
    public ReadAheadAbility copy() {
        return instance;
    }

}
