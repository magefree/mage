package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;


/**
 * @author BetaSteward_at_googlemail.com
 */
public final class BeastToken4 extends TokenImpl {

    public BeastToken4() {
        super("Beast Token", "2/2 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(2);
        toughness = new MageInt(2);

    }

    public BeastToken4(final BeastToken4 token) {
        super(token);
    }

    @Override
    public BeastToken4 copy() {
        return new BeastToken4(this);
    }
}
