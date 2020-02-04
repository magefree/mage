
package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

/**
 * 702.77. Wither
 *
 * 702.77a. Wither is a static ability. Damage dealt to a creature by a source
 * with wither isn't marked on that creature. Rather, it causes that many -1/-1
 * counters to be put on that creature. See rule 119.3.
 *
 * 702.77b. If a permanent leaves the battlefield before an effect causes it to
 * deal damage, its last known information is used to determine whether it had
 * wither.
 *
 * 702.77c. The wither rules function no matter what zone an object with wither
 * deals damage from.
 *
 * 702.77d. Multiple instances of wither on the same object are redundant.
 *
 * @author nantuko
 */
public class WitherAbility extends StaticAbility implements MageSingleton {

    private static final WitherAbility instance = new WitherAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static WitherAbility getInstance() {
        return instance;
    }

    private WitherAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "wither <i>(This deals damage to creatures in the form of -1/-1 counters.)</i>";
    }

    @Override
    public WitherAbility copy() {
        return instance;
    }

}
