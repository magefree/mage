package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class DemonWhiteToken extends TokenImpl {

    public DemonWhiteToken(int power_val, int toughness_val) {
        super("DemonWhite", power_val + "/" + toughness_val + " white Demon creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.DEMON );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public DemonWhiteToken() {
        this(2, 2);
    }

    public DemonWhiteToken(final DemonWhiteToken token) {
        super(token);
    }

    public DemonWhiteToken copy() {
        return new DemonWhiteToken(this);
    }
}
