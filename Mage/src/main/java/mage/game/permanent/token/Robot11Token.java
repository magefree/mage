package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Robot11Token extends TokenImpl {

    public Robot11Token() {
        super("Robot Token", "1/1 colorless Robot artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private Robot11Token(final Robot11Token token) {
        super(token);
    }

    public Robot11Token copy() {
        return new Robot11Token(this);
    }
}
