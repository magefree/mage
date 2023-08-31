package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Elemental44WUToken extends TokenImpl {

    public Elemental44WUToken() {
        super("Elemental Token", "4/4 white and blue Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private Elemental44WUToken(final Elemental44WUToken token) {
        super(token);
    }

    @Override
    public Elemental44WUToken copy() {
        return new Elemental44WUToken(this);
    }
}
