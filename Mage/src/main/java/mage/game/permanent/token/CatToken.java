package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class CatToken extends TokenImpl {

    public CatToken() {
        super("Cat Token", "2/2 white Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public CatToken(final CatToken token) {
        super(token);
    }

    public CatToken copy() {
        return new CatToken(this);
    }
}
