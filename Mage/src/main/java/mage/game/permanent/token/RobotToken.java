package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RobotToken extends TokenImpl {

    public RobotToken() {
        super("Robot Token", "3/3 colorless Robot artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private RobotToken(final RobotToken token) {
        super(token);
    }

    public RobotToken copy() {
        return new RobotToken(this);
    }
}
