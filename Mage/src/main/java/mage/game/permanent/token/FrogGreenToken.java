package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class FrogGreenToken extends TokenImpl {

    public FrogGreenToken() {
        super("Frog Token", "1/1 green Frog creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.FROG);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private FrogGreenToken(final FrogGreenToken token) {
        super(token);
    }

    public FrogGreenToken copy() {
        return new FrogGreenToken(this);
    }
}
