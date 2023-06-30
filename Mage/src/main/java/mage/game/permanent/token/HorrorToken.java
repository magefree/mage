package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class HorrorToken extends TokenImpl {

    public HorrorToken() {
        super("Horror Token", "4/4 black Horror creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HORROR);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public HorrorToken(final HorrorToken token) {
        super(token);
    }

    public HorrorToken copy() {
        return new HorrorToken(this);
    }
}
