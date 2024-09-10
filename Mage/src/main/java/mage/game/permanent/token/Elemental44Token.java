package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Elemental44Token extends TokenImpl {

    public Elemental44Token() {
        super("Elemental Token", "4/4 blue and red Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private Elemental44Token(final Elemental44Token token) {
        super(token);
    }

    @Override
    public Elemental44Token copy() {
        return new Elemental44Token(this);
    }
}
