package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class Kraken99Token extends TokenImpl {

    public Kraken99Token() {
        super("Kraken Token", "9/9 blue Kraken creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.KRAKEN);
        power = new MageInt(9);
        toughness = new MageInt(9);
    }

    public Kraken99Token(final Kraken99Token token) {
        super(token);
    }

    public Kraken99Token copy() {
        return new Kraken99Token(this);
    }
}
