package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class DarkstarToken extends TokenImpl {

    public DarkstarToken() {
        super("Darkstar", "Darkstar, a legendary 2/2 white and black Dog creature token");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.DOG);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private DarkstarToken(final DarkstarToken token) {
        super(token);
    }

    @Override
    public DarkstarToken copy() {
        return new DarkstarToken(this);
    }
}
