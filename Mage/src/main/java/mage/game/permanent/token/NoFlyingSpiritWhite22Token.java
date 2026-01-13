package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class NoFlyingSpiritWhite22Token extends TokenImpl {

    public NoFlyingSpiritWhite22Token() {
        super("Spirit Token", "2/2 white Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private NoFlyingSpiritWhite22Token(final NoFlyingSpiritWhite22Token token) {
        super(token);
    }

    @Override
    public NoFlyingSpiritWhite22Token copy() {
        return new NoFlyingSpiritWhite22Token(this);
    }
}
