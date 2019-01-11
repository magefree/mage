package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * Hexproof from Monocolored (This creature or player can't be the target of monocolored
 * spells or abilities your opponents control.)
 *
 * @author TheElk801
 */
public class HexproofFromMonocoloredAbility extends SimpleStaticAbility implements MageSingleton {

    private static final HexproofFromMonocoloredAbility instance;

    static {
        instance = new HexproofFromMonocoloredAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromMonocoloredAbility getInstance() {
        return instance;
    }

    private HexproofFromMonocoloredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public HexproofFromMonocoloredAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from monocolored <i>(This creature can't be the target of monocolored spells or abilities your opponents control.)</i>";
    }
}
