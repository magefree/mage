package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class CatBeastToken extends TokenImpl {

    public CatBeastToken() {
        super("Cat Beast Token", "2/2 white Cat Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        subtype.add(SubType.BEAST);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("ZNR", "NCC");
    }

    public CatBeastToken(final CatBeastToken token) {
        super(token);
    }

    public CatBeastToken copy() {
        return new CatBeastToken(this);
    }
}
