

package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Stravant
 */
public final class Hippo33Token extends TokenImpl {

    public Hippo33Token() {
        super("Hippo Token", "3/3 green Hippo creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.HIPPO);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    protected Hippo33Token(final Hippo33Token token) {
        super(token);
    }

    public Hippo33Token copy() {
        return new Hippo33Token(this);
    }
}
