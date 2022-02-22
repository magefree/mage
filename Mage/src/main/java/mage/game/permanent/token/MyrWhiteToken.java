package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class MyrWhiteToken extends TokenImpl {

    public MyrWhiteToken(int power_val, int toughness_val) {
        super("MyrWhite", power_val + "/" + toughness_val + " white Myr creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.MYR );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public MyrWhiteToken() {
        this(2, 2);
    }

    public MyrWhiteToken(final MyrWhiteToken token) {
        super(token);
    }

    public MyrWhiteToken copy() {
        return new MyrWhiteToken(this);
    }
}
