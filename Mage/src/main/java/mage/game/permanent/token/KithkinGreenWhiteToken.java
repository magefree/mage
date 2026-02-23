package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class KithkinGreenWhiteToken extends TokenImpl {

    public KithkinGreenWhiteToken() {
        super("Kithkin Token", "1/1 green and white Kithkin creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.KITHKIN);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private KithkinGreenWhiteToken(final KithkinGreenWhiteToken token) {
        super(token);
    }

    public KithkinGreenWhiteToken copy() {
        return new KithkinGreenWhiteToken(this);
    }
}
