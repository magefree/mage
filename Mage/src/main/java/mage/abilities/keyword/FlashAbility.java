

package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FlashAbility extends StaticAbility implements MageSingleton {

    private static final FlashAbility instance =  new FlashAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static FlashAbility getInstance() {
        return instance;
    }

    private FlashAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "Flash";
    }

    @Override
    public FlashAbility copy() {
        return instance;
    }

}
