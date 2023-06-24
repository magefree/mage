package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class BeastToken2 extends TokenImpl {

    public BeastToken2() {
        super("Beast Token", "4/4 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public BeastToken2(final BeastToken2 token) {
        super(token);
    }

    @Override
    public BeastToken2 copy() {
        return new BeastToken2(this);
    }
}
