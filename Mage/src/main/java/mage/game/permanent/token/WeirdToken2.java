package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WeirdToken2 extends TokenImpl {

    public WeirdToken2() {
        this(0);
    }

    public WeirdToken2(int xValue) {
        super("Weird Token", "X/X blue and red Weird creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.WEIRD);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    private WeirdToken2(final WeirdToken2 token) {
        super(token);
    }

    public WeirdToken2 copy() {
        return new WeirdToken2(this);
    }
}
