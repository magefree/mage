package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class NoFlyingSpiritWhiteToken extends TokenImpl {

    public NoFlyingSpiritWhiteToken() {
        super("Spirit Token", "1/1 white Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private NoFlyingSpiritWhiteToken(final NoFlyingSpiritWhiteToken token) {
        super(token);
    }

    @Override
    public NoFlyingSpiritWhiteToken copy() {
        return new NoFlyingSpiritWhiteToken(this);
    }
}
