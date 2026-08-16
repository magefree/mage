package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class RomulanToken extends TokenImpl {

    public RomulanToken() {
        super("Romulan Token", "2/2 black Romulan creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROMULAN);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private RomulanToken(final RomulanToken token) {
        super(token);
    }

    public RomulanToken copy() {
        return new RomulanToken(this);
    }
}
