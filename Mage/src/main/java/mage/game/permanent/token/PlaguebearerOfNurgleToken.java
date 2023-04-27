package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PlaguebearerOfNurgleToken extends TokenImpl {

    public PlaguebearerOfNurgleToken() {
        super("Plaguebearer of Nurgle", "1/3 black Demon creature token named Plaguebearer of Nurgle");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(1);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("40K");
    }

    public PlaguebearerOfNurgleToken(final PlaguebearerOfNurgleToken token) {
        super(token);
    }

    public PlaguebearerOfNurgleToken copy() {
        return new PlaguebearerOfNurgleToken(this);
    }
}
