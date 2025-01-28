package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class Shark33Token extends TokenImpl {

    public Shark33Token() {
        super("Shark Token", "3/3 blue Shark creature token");

        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SHARK);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private Shark33Token(final Shark33Token token) {
        super(token);
    }

    public Shark33Token copy() {
        return new Shark33Token(this);
    }
}
