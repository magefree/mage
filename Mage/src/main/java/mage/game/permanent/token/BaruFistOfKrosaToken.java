

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class BaruFistOfKrosaToken extends TokenImpl {

    public BaruFistOfKrosaToken() {
       this(1);
    }

    public BaruFistOfKrosaToken(int xValue) {
        super("Wurm Token", "X/X green Wurm creature token, where X is the number of lands you control");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public BaruFistOfKrosaToken(final BaruFistOfKrosaToken token) {
        super(token);
    }

    public BaruFistOfKrosaToken copy() {
        return new BaruFistOfKrosaToken(this);
    }
}

