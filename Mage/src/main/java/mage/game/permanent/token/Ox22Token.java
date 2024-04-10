package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class Ox22Token extends TokenImpl {

    public Ox22Token() {
        super("Ox Token", "2/2 white Ox creature token");

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.OX);
        power = new MageInt(2);
        toughness = new MageInt(2);

    }

    private Ox22Token(final Ox22Token token) {
        super(token);
    }

    @Override
    public Ox22Token copy() {
        return new Ox22Token(this);
    }
}
