package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author fireshoes
 */
public final class BeastToken3 extends TokenImpl {

    public BeastToken3() {
        super("Beast Token", "4/2 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(2);
    }

    public BeastToken3(final BeastToken3 token) {
        super(token);
    }

    @Override
    public BeastToken3 copy() {
        return new BeastToken3(this);
    }
}
