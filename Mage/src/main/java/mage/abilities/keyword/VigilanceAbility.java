package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.CardIconImpl;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class VigilanceAbility extends StaticAbility implements MageSingleton {

    private static final VigilanceAbility instance;

    static {
        instance = new VigilanceAbility();
        instance.addIcon(CardIconImpl.ABILITY_VIGILANCE);
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static VigilanceAbility getInstance() {
        return instance;
    }

    private VigilanceAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "vigilance";
    }

    @Override
    public VigilanceAbility copy() {
        return instance;
    }

}
