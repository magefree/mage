package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.icon.abilities.HexproofAbilityIcon;
import mage.constants.Zone;
import mage.game.Game;

/**
 * an abstract base class for hexproof abilities
 *
 * @author TheElk801
 */
public abstract class HexproofBaseAbility extends SimpleStaticAbility implements MageSingleton {

    HexproofBaseAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addIcon(HexproofAbilityIcon.instance);
    }

    public abstract boolean checkObject(MageObject source, Game game);
}
