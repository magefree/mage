package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Horror3Token extends TokenImpl {

    public Horror3Token() {
        super("Horror Token", "2/2 black Horror creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HORROR);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private Horror3Token(final Horror3Token token) {
        super(token);
    }

    public Horror3Token copy() {
        return new Horror3Token(this);
    }
}
