package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class RatWhiteToken extends TokenImpl {

    public RatWhiteToken(int power_val, int toughness_val) {
        super("RatWhite", power_val + "/" + toughness_val + " white Rat creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.RAT );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public RatWhiteToken() {
        this(1, 1);
    }

    public RatWhiteToken(final RatWhiteToken token) {
        super(token);
    }

    public RatWhiteToken copy() {
        return new RatWhiteToken(this);
    }
}
