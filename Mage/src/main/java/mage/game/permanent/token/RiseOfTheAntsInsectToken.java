package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class RiseOfTheAntsInsectToken extends TokenImpl {

    public RiseOfTheAntsInsectToken() {
        super("Insect", "3/3 green Insect creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes.addAll(Arrays.asList("MID"));
    }

    public RiseOfTheAntsInsectToken(final RiseOfTheAntsInsectToken token) {
        super(token);
    }

    public RiseOfTheAntsInsectToken copy() {
        return new RiseOfTheAntsInsectToken(this);
    }
}