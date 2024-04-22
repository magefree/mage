package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.CardIconImpl;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DoubleStrikeAbility extends StaticAbility implements MageSingleton {

    private static final DoubleStrikeAbility instance;

    static {
        instance = new DoubleStrikeAbility();
        instance.addIcon(CardIconImpl.ABILITY_DOUBLE_STRIKE);
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DoubleStrikeAbility getInstance() {
        return instance;
    }

    private DoubleStrikeAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "double strike";
    }

    @Override
    public DoubleStrikeAbility copy() {
        return instance;
    }

}
