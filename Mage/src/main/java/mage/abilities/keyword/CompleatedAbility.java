package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 */
public class CompleatedAbility extends StaticAbility implements MageSingleton {

    private static final CompleatedAbility instance;

    static {
        instance = new CompleatedAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static CompleatedAbility getInstance() {
        return instance;
    }

    private CompleatedAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "compleated";
    }

    @Override
    public CompleatedAbility copy() {
        return instance;
    }

}
