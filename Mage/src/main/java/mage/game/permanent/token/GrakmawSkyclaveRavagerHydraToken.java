package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class GrakmawSkyclaveRavagerHydraToken extends TokenImpl {

    public GrakmawSkyclaveRavagerHydraToken() {
        this(0);
    }

    public GrakmawSkyclaveRavagerHydraToken(int xValue) {
        super("Hydra Token", "X/X black and green Hydra creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.HYDRA);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        availableImageSetCodes = Arrays.asList("ZNR");
    }

    private GrakmawSkyclaveRavagerHydraToken(final GrakmawSkyclaveRavagerHydraToken token) {
        super(token);
    }

    public GrakmawSkyclaveRavagerHydraToken copy() {
        return new GrakmawSkyclaveRavagerHydraToken(this);
    }
}
