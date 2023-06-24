package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ApeToken extends TokenImpl {

    public ApeToken() {
        super("Ape Token", "3/3 green Ape creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.APE);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public ApeToken(final ApeToken token) {
        super(token);
    }

    public ApeToken copy() {
        return new ApeToken(this);
    }
}
