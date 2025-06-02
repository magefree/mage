package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Knight33Token extends TokenImpl {

    public Knight33Token() {
        super("Knight Token", "3/3 white Knight creature token");

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private Knight33Token(final Knight33Token token) {
        super(token);
    }

    public Knight33Token copy() {
        return new Knight33Token(this);
    }
}
