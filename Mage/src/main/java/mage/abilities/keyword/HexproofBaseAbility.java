package mage.abilities.keyword;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconImpl;
import mage.abilities.icon.CardIconType;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * an abstract base class for hexproof abilities
 *
 * @author TheElk801
 */
public abstract class HexproofBaseAbility extends SimpleStaticAbility implements MageSingleton {

    protected HexproofBaseAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public abstract boolean checkObject(MageObject sourceObject, Ability source, Game game);

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

    public abstract String getCardIconHint(Game game);

    @Override
    public List<CardIcon> getIcons(Game game) {
        if (game == null) {
            return Collections.singletonList(CardIconImpl.ABILITY_HEXPROOF);
        }

        // dynamic icon (example: colored hexproof)
        return Collections.singletonList(new CardIconImpl(CardIconType.ABILITY_HEXPROOF,
                CardUtil.getTextWithFirstCharUpperCase(getCardIconHint(game))));
    }
}
