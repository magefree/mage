
package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.CardIconImpl;
import mage.abilities.icon.CardIconType;

import java.io.ObjectStreamException;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReachAbility extends StaticAbility implements MageSingleton {

    private static final ReachAbility instance;

    static {
        instance = new ReachAbility();
        instance.addIcon(
                new CardIconImpl(CardIconType.ABILITY_REACH, "Reach"));
    }

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
