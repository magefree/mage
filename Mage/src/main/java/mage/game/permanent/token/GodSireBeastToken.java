
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class GodSireBeastToken extends TokenImpl {

    public GodSireBeastToken() {
        super("Beast Token", "8/8 Beast creature token that's red, green, and white");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        color.setRed(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(8);
        toughness = new MageInt(8);
    }

    public GodSireBeastToken(final GodSireBeastToken token) {
        super(token);
    }

    public GodSireBeastToken copy() {
        return new GodSireBeastToken(this);
    }
}
