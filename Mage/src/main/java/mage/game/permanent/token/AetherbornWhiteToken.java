package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class AetherbornWhiteToken extends TokenImpl {

    public AetherbornWhiteToken(int power_val, int toughness_val) {
        super("AetherbornWhite", power_val + "/" + toughness_val + " white Aetherborn creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.AETHERBORN );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public AetherbornWhiteToken() {
        this(2, 2);
    }

    public AetherbornWhiteToken(final AetherbornWhiteToken token) {
        super(token);
    }

    public AetherbornWhiteToken copy() {
        return new AetherbornWhiteToken(this);
    }
}
