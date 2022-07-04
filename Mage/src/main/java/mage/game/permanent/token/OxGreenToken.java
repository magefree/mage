package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class OxGreenToken extends TokenImpl {

    public OxGreenToken() {
        super("Ox Token", "4/4 green Ox creature token");

        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.OX);
        power = new MageInt(4);
        toughness = new MageInt(4);

        availableImageSetCodes = Arrays.asList("CLB");
    }

    public OxGreenToken(final OxGreenToken token) {
        super(token);
    }

    @Override
    public OxGreenToken copy() {
        return new OxGreenToken(this);
    }
}
