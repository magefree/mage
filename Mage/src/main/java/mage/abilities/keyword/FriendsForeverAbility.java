package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 */
public class FriendsForeverAbility extends StaticAbility implements MageSingleton {

    private static final FriendsForeverAbility instance = new FriendsForeverAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static FriendsForeverAbility getInstance() {
        return instance;
    }

    private FriendsForeverAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "Friends forever <i>(You can have two commanders if both have friends forever.)</i>";
    }

    @Override
    public FriendsForeverAbility copy() {
        return instance;
    }
}
