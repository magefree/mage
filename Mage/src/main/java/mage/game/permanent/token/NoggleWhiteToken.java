package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class NoggleWhiteToken extends TokenImpl {

    public NoggleWhiteToken(int power_val, int toughness_val) {
        super("NoggleWhite", power_val + "/" + toughness_val + " white Noggle creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.NOGGLE );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public NoggleWhiteToken() {
        this(2, 2);
    }

    public NoggleWhiteToken(final NoggleWhiteToken token) {
        super(token);
    }

    public NoggleWhiteToken copy() {
        return new NoggleWhiteToken(this);
    }
}
