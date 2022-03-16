
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class ApeToken extends TokenImpl {

    public ApeToken() {
        super("Ape Token", "2/2 green Ape creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.APE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public ApeToken(final ApeToken token) {
        super(token);
    }

    public ApeToken copy() {
        return new ApeToken(this);
    }
}
