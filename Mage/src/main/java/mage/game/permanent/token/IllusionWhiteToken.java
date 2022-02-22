package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class IllusionWhiteToken extends TokenImpl {

    public IllusionWhiteToken(int power_val, int toughness_val) {
        super("IllusionWhite", power_val + "/" + toughness_val + " white Illusion creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ILLUSION );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public IllusionWhiteToken() {
        this(2, 2);
    }

    public IllusionWhiteToken(final IllusionWhiteToken token) {
        super(token);
    }

    public IllusionWhiteToken copy() {
        return new IllusionWhiteToken(this);
    }
}
