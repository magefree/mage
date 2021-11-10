package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class GreenCatToken extends TokenImpl {

    public GreenCatToken() {
        super("Cat Token", "1/1 green Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CAT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes.addAll(Arrays.asList("6ED", "M21"));
    }

    public GreenCatToken(final GreenCatToken token) {
        super(token);
    }

    public GreenCatToken copy() {
        return new GreenCatToken(this);
    }
}
