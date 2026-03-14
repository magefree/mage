package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class NoFlyingSpiritWhite33Token extends TokenImpl {

    public NoFlyingSpiritWhite33Token() {
        super("Spirit Token", "3/3 white Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private NoFlyingSpiritWhite33Token(final NoFlyingSpiritWhite33Token token) {
        super(token);
    }

    @Override
    public NoFlyingSpiritWhite33Token copy() {
        return new NoFlyingSpiritWhite33Token(this);
    }
}
