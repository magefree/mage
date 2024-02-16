package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class BoarToken extends TokenImpl {

    public BoarToken() {
        super("Boar Token", "3/3 green Boar creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BOAR);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private BoarToken(final BoarToken token) {
        super(token);
    }

    public BoarToken copy() {
        return new BoarToken(this);
    }
}
