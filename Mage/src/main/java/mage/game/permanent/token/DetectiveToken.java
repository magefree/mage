package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DetectiveToken extends TokenImpl {

    public DetectiveToken() {
        super("Detective Token", "2/2 white and blue Detective creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        subtype.add(SubType.DETECTIVE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private DetectiveToken(final DetectiveToken token) {
        super(token);
    }

    public DetectiveToken copy() {
        return new DetectiveToken(this);
    }
}
