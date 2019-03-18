

package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

/**
 * This is marker ability that is used only for rule display.
 * You should use {@see BlocksIfAbleTargetEffect} for real effect.
 *
 * @author magenoxx_at_googlemail.com
 */
public class BlocksThisTurnMarkerAbility extends StaticAbility implements MageSingleton {

    private static final BlocksThisTurnMarkerAbility instance =  new BlocksThisTurnMarkerAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static BlocksThisTurnMarkerAbility getInstance() {
        return instance;
    }

    private BlocksThisTurnMarkerAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public String getRule() {
        return "{this} blocks this turn if able";
    }

    @Override
    public BlocksThisTurnMarkerAbility copy() {
        return instance;
    }

}
