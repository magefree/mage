
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class CrestedSunmareToken extends TokenImpl {

    public CrestedSunmareToken() {
        super("Horse Token", "5/5 white Horse creature token");
        power = new MageInt(5);
        toughness = new MageInt(5);
        color.setWhite(true);
        subtype.add(SubType.HORSE);
        cardType.add(CardType.CREATURE);
    }

    public CrestedSunmareToken(final CrestedSunmareToken token) {
        super(token);
    }

    public CrestedSunmareToken copy() {
        return new CrestedSunmareToken(this);
    }
}
