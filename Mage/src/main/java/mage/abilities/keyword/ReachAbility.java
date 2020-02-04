
package mage.abilities.keyword;

import java.io.ObjectStreamException;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReachAbility extends StaticAbility implements MageSingleton {

    private static final ReachAbility instance = new ReachAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ReachAbility getInstance() {
        return instance;
    }

    private ReachAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "reach";
    }

    @Override
    public ReachAbility copy() {
        return instance;
    }

}
