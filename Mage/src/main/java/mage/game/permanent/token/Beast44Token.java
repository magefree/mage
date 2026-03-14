package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class Beast44Token extends TokenImpl {

    public Beast44Token() {
        super("Beast Token", "4/4 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    protected Beast44Token(final Beast44Token token) {
        super(token);
    }

    @Override
    public Beast44Token copy() {
        return new Beast44Token(this);
    }
}
