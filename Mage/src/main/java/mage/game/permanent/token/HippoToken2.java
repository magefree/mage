

package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Stravant
 */
public final class HippoToken2 extends TokenImpl {

    public HippoToken2() {
        super("Hippo Token", "3/3 green Hippo creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.HIPPO);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }
    public HippoToken2(final HippoToken2 token) {
        super(token);
    }

    public HippoToken2 copy() {
        return new HippoToken2(this);
    }
}
