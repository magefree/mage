

package mage.abilities.keyword;

import java.io.ObjectStreamException;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com (spjspj)
 */
public class SquirrellinkAbility extends StaticAbility implements MageSingleton {

    private static final SquirrellinkAbility instance =  new SquirrellinkAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static SquirrellinkAbility getInstance() {
        return instance;
    }

    private SquirrellinkAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "Squirrellink <i>(Damage dealt by this creature also causes you to create that many 1/1 green Squirrel creature tokens.)</i>";
    }

    @Override
    public SquirrellinkAbility copy() {
        return instance;
    }
}
