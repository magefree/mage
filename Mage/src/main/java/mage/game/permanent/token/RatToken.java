package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LevelX2
 */
public final class RatToken extends TokenImpl {

    public RatToken() {
        super("Rat", "1/1 black Rat creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("C17", "CHK", "GTC", "SHM", "ELD", "ZNC");
    }

    public RatToken(final RatToken token) {
        super(token);
    }

    public RatToken copy() {
        return new RatToken(this);
    }
}
