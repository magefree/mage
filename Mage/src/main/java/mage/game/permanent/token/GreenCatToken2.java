package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GreenCatToken2 extends TokenImpl {

    public GreenCatToken2() {
        super("Cat", "2/2 green Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CAT);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private GreenCatToken2(final GreenCatToken2 token) {
        super(token);
    }

    public GreenCatToken2 copy() {
        return new GreenCatToken2(this);
    }
}
