
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FirstStrikeAbility extends StaticAbility implements MageSingleton {

    private static final FirstStrikeAbility instance = new FirstStrikeAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static FirstStrikeAbility getInstance() {
        return instance;
    }

    private FirstStrikeAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "first strike";
    }

    @Override
    public FirstStrikeAbility copy() {
        return instance;
    }

}
