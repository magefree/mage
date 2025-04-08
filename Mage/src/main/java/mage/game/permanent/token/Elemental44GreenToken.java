package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class Elemental44GreenToken extends TokenImpl {

    public Elemental44GreenToken() {
        super("Elemental Token", "4/4 green Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private Elemental44GreenToken(final Elemental44GreenToken token) {
        super(token);
    }

    public Elemental44GreenToken copy() {
        return new Elemental44GreenToken(this);
    }
}
