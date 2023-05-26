package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LevelX2
 */
public final class RatToken extends TokenImpl {

    public RatToken() {
        super("Rat Token", "1/1 black Rat creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public RatToken(final RatToken token) {
        super(token);
    }

    public RatToken copy() {
        return new RatToken(this);
    }
}
