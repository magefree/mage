package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class FaerieWhiteToken extends TokenImpl {

    public FaerieWhiteToken(int power_val, int toughness_val) {
        super("FaerieWhite", power_val + "/" + toughness_val + " white Faerie creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.FAERIE );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public FaerieWhiteToken() {
        this(2, 2);
    }

    public FaerieWhiteToken(final FaerieWhiteToken token) {
        super(token);
    }

    public FaerieWhiteToken copy() {
        return new FaerieWhiteToken(this);
    }
}
