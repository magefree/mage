package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class RedElementalToken extends TokenImpl {

    public RedElementalToken() {
        super("Elemental Token", "1/1 red Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public RedElementalToken(final RedElementalToken token) {
        super(token);
    }

    public RedElementalToken copy() {
        return new RedElementalToken(this);
    }
}
