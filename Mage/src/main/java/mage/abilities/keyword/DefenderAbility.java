package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.CardIconImpl;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DefenderAbility extends StaticAbility implements MageSingleton {

    private static final DefenderAbility instance;

    static {
        instance = new DefenderAbility();
        instance.addIcon(CardIconImpl.ABILITY_DEFENDER);
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DefenderAbility getInstance() {
        return instance;
    }

    private DefenderAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "defender";
    }

    @Override
    public DefenderAbility copy() {
        return instance;
    }

}
