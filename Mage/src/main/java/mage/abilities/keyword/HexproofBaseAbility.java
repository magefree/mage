package mage.abilities.keyword;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.icon.abilities.HexproofAbilityIcon;
import mage.constants.Zone;
import mage.game.Game;

import java.util.HashSet;
import java.util.Set;

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

    public static Set<HexproofBaseAbility> getFromColor(ObjectColor color) {
        Set<HexproofBaseAbility> abilities = new HashSet<>();
        if (color.isWhite()) {
            abilities.add(HexproofFromWhiteAbility.getInstance());
        }
        if (color.isBlue()) {
            abilities.add(HexproofFromBlueAbility.getInstance());
        }
        if (color.isBlack()) {
            abilities.add(HexproofFromBlackAbility.getInstance());
        }
        if (color.isRed()) {
            abilities.add(HexproofFromRedAbility.getInstance());
        }
        if (color.isGreen()) {
            abilities.add(HexproofFromGreenAbility.getInstance());
        }
        return abilities;
    }

    public static HexproofBaseAbility getFirstFromColor(ObjectColor color) {
        if (color.isWhite()) {
            return HexproofFromWhiteAbility.getInstance();
        } else if (color.isBlue()) {
            return HexproofFromBlueAbility.getInstance();
        } else if (color.isBlack()) {
            return HexproofFromBlackAbility.getInstance();
        } else if (color.isRed()) {
            return HexproofFromRedAbility.getInstance();
        } else if (color.isGreen()) {
            return HexproofFromGreenAbility.getInstance();
        } else {
            return null;
        }
    }
}
