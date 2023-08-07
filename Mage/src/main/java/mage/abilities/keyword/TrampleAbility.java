package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.CardIconImpl;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TrampleAbility extends StaticAbility implements MageSingleton {

    private static final TrampleAbility instance;

    static {
        instance = new TrampleAbility();
        instance.addIcon(CardIconImpl.ABILITY_TRAMPLE);
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static TrampleAbility getInstance() {
        return instance;
    }

    private TrampleAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "trample";
    }

    @Override
    public TrampleAbility copy() {
        return instance;
    }

}
