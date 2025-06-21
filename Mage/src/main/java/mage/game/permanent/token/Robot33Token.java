package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Robot33Token extends TokenImpl {

    public Robot33Token() {
        super("Robot Token", "3/3 colorless Robot artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private Robot33Token(final Robot33Token token) {
        super(token);
    }

    public Robot33Token copy() {
        return new Robot33Token(this);
    }
}
