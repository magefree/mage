
 
package mage.abilities.keyword.special;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

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
