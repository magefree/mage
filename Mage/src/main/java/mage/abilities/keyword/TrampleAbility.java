
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TrampleAbility extends StaticAbility implements MageSingleton {

    private static final TrampleAbility instance = new TrampleAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static TrampleAbility getInstance() {
        return instance;
    }

    private TrampleAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "trample";
    }

    @Override
    public TrampleAbility copy() {
        return instance;
    }

}
