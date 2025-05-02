package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class PincherToken extends TokenImpl {

    public PincherToken() {
        super("Pincher Token", "2/2 colorless Pincher creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PINCHER);
        power = new MageInt(2);
        toughness = new MageInt(2);

    }

    private PincherToken(final PincherToken token) {
        super(token);
    }

    public PincherToken copy() {
        return new PincherToken(this);
    }
}
