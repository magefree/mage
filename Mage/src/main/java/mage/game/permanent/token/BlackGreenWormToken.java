
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class BlackGreenWormToken extends TokenImpl {

    public BlackGreenWormToken() {
        super("Worm Token", "1/1 black and green Worm creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.WORM);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("EVE", "MMA", "MM2", "C16", "C18", "2X2");
    }

    public BlackGreenWormToken(final BlackGreenWormToken token) {
        super(token);
    }

    public BlackGreenWormToken copy() {
        return new BlackGreenWormToken(this);
    }
}
