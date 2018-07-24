package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author EikePeace
 */
public final class OxTokenLorado extends TokenImpl {

    public OxTokenLorado() {
        super("Ox", "1/1 white Ox creature token");

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.OX);
        power = new MageInt(1);
        toughness = new MageInt(1);

    }

    public OxTokenLorado(final OxTokenLorado token) {
        super(token);
    }

    @Override
    public OxTokenLorado copy() {
        return new OxTokenLorado(this);
    }
}
