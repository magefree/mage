package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Styxo
 */
public final class DroidToken extends TokenImpl {

    public DroidToken() {
        super("Droid Token", "1/1 colorless Droid creature token");

        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DROID);

        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    protected DroidToken(final DroidToken token) {
        super(token);
    }

    public DroidToken copy() {
        return new DroidToken(this);
    }
}
