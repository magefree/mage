package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class PlanewideCelebrationToken extends TokenImpl {

    public PlanewideCelebrationToken() {
        super("Citizen Token", "2/2 Citizen creature token that's all colors");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        color.setBlack(true);
        color.setRed(true);
        color.setGreen(true);

        subtype.add(SubType.CITIZEN);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public PlanewideCelebrationToken(final PlanewideCelebrationToken token) {
        super(token);
    }

    public PlanewideCelebrationToken copy() {
        return new PlanewideCelebrationToken(this);
    }
}
