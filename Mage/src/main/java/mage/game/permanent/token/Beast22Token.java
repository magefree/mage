package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;


/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Beast22Token extends TokenImpl {

    public Beast22Token() {
        super("Beast Token", "2/2 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(2);
        toughness = new MageInt(2);

    }

    protected Beast22Token(final Beast22Token token) {
        super(token);
    }

    @Override
    public Beast22Token copy() {
        return new Beast22Token(this);
    }
}
