package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.CardIconImpl;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FirstStrikeAbility extends StaticAbility implements MageSingleton {

    private static final FirstStrikeAbility instance;

    static {
        instance = new FirstStrikeAbility();
        instance.addIcon(CardIconImpl.ABILITY_FIRST_STRIKE);
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static FirstStrikeAbility getInstance() {
        return instance;
    }

    private FirstStrikeAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "first strike";
    }

    @Override
    public FirstStrikeAbility copy() {
        return instance;
    }

}
