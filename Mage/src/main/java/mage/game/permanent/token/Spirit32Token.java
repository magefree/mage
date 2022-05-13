package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Spirit32Token extends TokenImpl {

    public Spirit32Token() {
        super("Spirit Token", "3/2 red and white Spirit creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(3);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("STX");
    }

    private Spirit32Token(final Spirit32Token token) {
        super(token);
    }

    @Override
    public Spirit32Token copy() {
        return new Spirit32Token(this);
    }
}
