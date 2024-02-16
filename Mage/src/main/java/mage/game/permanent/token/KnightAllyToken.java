package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class KnightAllyToken extends TokenImpl {

    public KnightAllyToken() {
        super("Knight Ally Token", "2/2 white Knight Ally creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KNIGHT);
        subtype.add(SubType.ALLY);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private KnightAllyToken(final KnightAllyToken token) {
        super(token);
    }

    public KnightAllyToken copy() {
        return new KnightAllyToken(this);
    }

}
