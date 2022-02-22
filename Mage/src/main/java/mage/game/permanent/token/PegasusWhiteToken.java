package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class PegasusWhiteToken extends TokenImpl {

    public PegasusWhiteToken(int power_val, int toughness_val) {
        super("PegasusWhite", power_val + "/" + toughness_val + " white Pegasus creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.PEGASUS );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public PegasusWhiteToken() {
        this(2, 2);
    }

    public PegasusWhiteToken(final PegasusWhiteToken token) {
        super(token);
    }

    public PegasusWhiteToken copy() {
        return new PegasusWhiteToken(this);
    }
}
