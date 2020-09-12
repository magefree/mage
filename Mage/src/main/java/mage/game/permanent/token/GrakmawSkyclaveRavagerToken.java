package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GrakmawSkyclaveRavagerToken extends TokenImpl {

    public GrakmawSkyclaveRavagerToken(int xValue) {
        super("Hydra", "X/X black and green Hydra creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.HYDRA);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    private GrakmawSkyclaveRavagerToken(final GrakmawSkyclaveRavagerToken token) {
        super(token);
    }

    public GrakmawSkyclaveRavagerToken copy() {
        return new GrakmawSkyclaveRavagerToken(this);
    }
}
