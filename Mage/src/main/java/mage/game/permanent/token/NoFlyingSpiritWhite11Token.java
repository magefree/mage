package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class NoFlyingSpiritWhite11Token extends TokenImpl {

    public NoFlyingSpiritWhite11Token() {
        super("Spirit Token", "1/1 white Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private NoFlyingSpiritWhite11Token(final NoFlyingSpiritWhite11Token token) {
        super(token);
    }

    @Override
    public NoFlyingSpiritWhite11Token copy() {
        return new NoFlyingSpiritWhite11Token(this);
    }
}
