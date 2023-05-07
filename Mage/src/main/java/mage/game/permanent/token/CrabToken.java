package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class CrabToken extends TokenImpl {

    public CrabToken() {
        super("Crab Token", "0/3 blue Crab creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.CRAB);
        power = new MageInt(0);
        toughness = new MageInt(3);
    }

    public CrabToken(final CrabToken token) {
        super(token);
    }

    public CrabToken copy() {
        return new CrabToken(this);
    }
}
