
 
package mage.abilities.keyword.special;

import java.io.ObjectStreamException;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com & L_J
 */
public class JohanVigilanceAbility extends StaticAbility implements MageSingleton { // special instance of "attacking doesn't cause this to tap" granted by Johan's ability

    private static final JohanVigilanceAbility instance =  new JohanVigilanceAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static JohanVigilanceAbility getInstance() {
        return instance;
    }

    private JohanVigilanceAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "";
    }

    @Override
    public JohanVigilanceAbility copy() {
        return instance;
    }

}
