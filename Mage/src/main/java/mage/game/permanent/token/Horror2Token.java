package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Horror2Token extends TokenImpl {

    public Horror2Token() {
        super("Horror Token", "1/1 black Horror creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HORROR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public Horror2Token(final Horror2Token token) {
        super(token);
    }

    public Horror2Token copy() {
        return new Horror2Token(this);
    }
}
