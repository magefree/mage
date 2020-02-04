
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DoubleStrikeAbility extends StaticAbility implements MageSingleton {

    private static final DoubleStrikeAbility instance = new DoubleStrikeAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DoubleStrikeAbility getInstance() {
        return instance;
    }

    private DoubleStrikeAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "double strike";
    }

    @Override
    public DoubleStrikeAbility copy() {
        return instance;
    }

}
