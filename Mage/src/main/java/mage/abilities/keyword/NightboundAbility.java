package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 * TODO: Implement this
 */
public class NightboundAbility extends StaticAbility implements MageSingleton {

    private static final NightboundAbility instance;

    static {
        instance = new NightboundAbility();
        // instance.addIcon(NightboundAbilityIcon.instance); (needs to be added)
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static NightboundAbility getInstance() {
        return instance;
    }

    private NightboundAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "nightbound <i>(If a player casts at least two spells during their own turn, it becomes day next turn.)</i>";
    }

    @Override
    public NightboundAbility copy() {
        return instance;
    }
}
