package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WeirdXXToken extends TokenImpl {

    public WeirdXXToken() {
        this(0);
    }

    public WeirdXXToken(int xValue) {
        super("Weird Token", "X/X blue and red Weird creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.WEIRD);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    private WeirdXXToken(final WeirdXXToken token) {
        super(token);
    }

    public WeirdXXToken copy() {
        return new WeirdXXToken(this);
    }
}
