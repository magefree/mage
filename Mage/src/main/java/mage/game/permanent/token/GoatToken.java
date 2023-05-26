package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class GoatToken extends TokenImpl {

    public GoatToken() {
        super("Goat Token", "0/1 white Goat creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.GOAT);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public GoatToken(final GoatToken token) {
        super(token);
    }

    public GoatToken copy() {
        return new GoatToken(this);
    }
}
