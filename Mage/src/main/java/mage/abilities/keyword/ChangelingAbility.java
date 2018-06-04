

package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;


/**
 * October 1, 2012
 * 702.71. Changeling
 *  702.71a Changeling is a characteristic-defining ability. "Changeling" means "This object
 *  is every creature type." This ability works everywhere, even outside the game. See rule 604.3.
 *  702.71b Multiple instances of changeling on the same object are redundant.
 *
 * @author nantuko
 */
public class ChangelingAbility extends StaticAbility implements MageSingleton {
    private static final ChangelingAbility instance =  new ChangelingAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ChangelingAbility getInstance() {
        return instance;
    }

    private ChangelingAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "Changeling <i>(This card is every creature type.)</i>";
    }

    @Override
    public ChangelingAbility copy() {
        return instance;
    }
}
