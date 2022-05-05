package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class CatToken3 extends TokenImpl {

    public CatToken3() {
        super("Cat Token", "1/1 white Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("ZNR");
    }

    private CatToken3(final CatToken3 token) {
        super(token);
    }

    public CatToken3 copy() {
        return new CatToken3(this);
    }
}
