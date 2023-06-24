package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class OmnathElementalToken extends TokenImpl {

    public OmnathElementalToken() {
        super("Elemental Token", "5/5 red and green Elemental creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);

        color.setRed(true);
        color.setGreen(true);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    public OmnathElementalToken(final OmnathElementalToken token) {
        super(token);
    }

    public OmnathElementalToken copy() {
        return new OmnathElementalToken(this);
    }

}
