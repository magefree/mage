package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author fireshoes
 */
public final class Beast42Token extends TokenImpl {

    public Beast42Token() {
        super("Beast Token", "4/2 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(2);
    }

    protected Beast42Token(final Beast42Token token) {
        super(token);
    }

    @Override
    public Beast42Token copy() {
        return new Beast42Token(this);
    }
}
