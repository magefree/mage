package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Cat11Token extends TokenImpl {

    public Cat11Token() {
        super("Cat Token", "1/1 white Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private Cat11Token(final Cat11Token token) {
        super(token);
    }

    public Cat11Token copy() {
        return new Cat11Token(this);
    }
}
