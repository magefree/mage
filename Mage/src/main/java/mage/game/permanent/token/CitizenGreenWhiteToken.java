package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class CitizenGreenWhiteToken extends TokenImpl {

    public CitizenGreenWhiteToken() {
        super("Citizen Token", "1/1 green and white Citizen creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);

        subtype.add(SubType.CITIZEN);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("SNC");
    }

    private CitizenGreenWhiteToken(final CitizenGreenWhiteToken token) {
        super(token);
    }

    public CitizenGreenWhiteToken copy() {
        return new CitizenGreenWhiteToken(this);
    }
}
