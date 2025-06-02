package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Elephant55Token extends TokenImpl {

    public Elephant55Token() {
        super("Elephant Token", "5/5 green Elephant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEPHANT);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    private Elephant55Token(final Elephant55Token token) {
        super(token);
    }

    public Elephant55Token copy() {
        return new Elephant55Token(this);
    }
}
