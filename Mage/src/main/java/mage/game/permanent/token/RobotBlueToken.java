package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RobotBlueToken extends TokenImpl {

    public RobotBlueToken() {
        super("Robot Token", "3/3 blue Robot Warrior artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ROBOT);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private RobotBlueToken(final RobotBlueToken token) {
        super(token);
    }

    public RobotBlueToken copy() {
        return new RobotBlueToken(this);
    }
}
