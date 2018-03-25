package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * Hexproof from black (This creature or player can't be the target of black spells or abilities
 * your opponents control.)
 *
 * @author igoudt
 */
public class HexproofFromBlackAbility extends SimpleStaticAbility implements MageSingleton {

    private static final HexproofFromBlackAbility instance;

    static {
        instance = new HexproofFromBlackAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromBlackAbility getInstance() {
        return instance;
    }

    private HexproofFromBlackAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public HexproofFromBlackAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from black";
    }
}
