package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class FaerieGreenToken extends TokenImpl {

    public FaerieGreenToken(int power_val, int toughness_val) {
        super("FaerieGreen", power_val + "/" + toughness_val + " green Faerie creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.FAERIE );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public FaerieGreenToken() {
        this(2, 2);
    }

    public FaerieGreenToken(final FaerieGreenToken token) {
        super(token);
    }

    public FaerieGreenToken copy() {
        return new FaerieGreenToken(this);
    }
}
