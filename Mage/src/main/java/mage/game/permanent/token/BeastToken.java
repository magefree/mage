package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class BeastToken extends TokenImpl {

    public BeastToken() {
        super("Beast Token", "3/3 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public BeastToken(final BeastToken token) {
        super(token);
    }

    @Override
    public BeastToken copy() {
        return new BeastToken(this);
    }
}
