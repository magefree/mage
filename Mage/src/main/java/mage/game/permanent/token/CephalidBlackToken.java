package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class CephalidBlackToken extends TokenImpl {

    public CephalidBlackToken(int power_val, int toughness_val) {
        super("CephalidBlack", power_val + "/" + toughness_val + " black Cephalid creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.CEPHALID );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public CephalidBlackToken() {
        this(2, 2);
    }

    public CephalidBlackToken(final CephalidBlackToken token) {
        super(token);
    }

    public CephalidBlackToken copy() {
        return new CephalidBlackToken(this);
    }
}
