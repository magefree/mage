

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
public class ShroudAbility extends StaticAbility implements MageSingleton {

    private static final ShroudAbility instance;

    static {
        instance = new ShroudAbility();
        instance.addIcon(
                new CardIconImpl(CardIconType.ABILITY_HEXPROOF, "Shroud"));
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ShroudAbility getInstance() {
        return instance;
    }

    private ShroudAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "shroud";
    }

    @Override
    public ShroudAbility copy() {
        return instance;
    }

}
