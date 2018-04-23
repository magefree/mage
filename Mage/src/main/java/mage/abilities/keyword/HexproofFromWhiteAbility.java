package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Zone;

/**
 * Hexproof from white (This creature or player can't be the target of white
 * spells or abilities your opponents control.)
 *
 * @author igoudt
 */
public class HexproofFromWhiteAbility extends SimpleStaticAbility implements MageSingleton {

    private static final HexproofFromWhiteAbility instance;

    static {
        instance = new HexproofFromWhiteAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofFromWhiteAbility getInstance() {
        return instance;
    }

    private HexproofFromWhiteAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public HexproofFromWhiteAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from white <i>(This creature can't be the target of white spells or abilities your opponents control.)</i>";
    }
}
