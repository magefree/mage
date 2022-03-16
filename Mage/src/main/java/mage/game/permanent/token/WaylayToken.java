
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class WaylayToken extends TokenImpl {

    public WaylayToken() {
        super("Knight Token", "2/2 white Knight creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public WaylayToken(final WaylayToken token) {
        super(token);
    }

    public WaylayToken copy() {
        return new WaylayToken(this);
    }
}
