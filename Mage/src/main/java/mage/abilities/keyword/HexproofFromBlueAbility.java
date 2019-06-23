package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * Hexproof from blue (This creature or player can't be the target of black
 * spells or abilities your opponents control.)
 *
 * @author igoudt
 */
public class HexproofFromBlueAbility extends SimpleStaticAbility implements MageSingleton {

    private static final HexproofFromBlueAbility instance;

    static {
        instance = new HexproofFromBlueAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromBlueAbility getInstance() {
        return instance;
    }

    private HexproofFromBlueAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public HexproofFromBlueAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from blue <i>(This creature can't be the target of blue spells or abilities your opponents control.)</i>";
    }
}
