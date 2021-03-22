package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class FractalToken extends TokenImpl {

    public FractalToken() {
        super("Fractal", "0/0 green and blue Fractal creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.FRACTAL);
        color.setGreen(true);
        color.setBlue(true);
        power = new MageInt(0);
        toughness = new MageInt(0);

        availableImageSetCodes = Arrays.asList("STX");
    }

    private FractalToken(final FractalToken token) {
        super(token);
    }

    public FractalToken copy() {
        return new FractalToken(this);
    }
}
