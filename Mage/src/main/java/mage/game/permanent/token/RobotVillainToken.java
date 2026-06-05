package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class RobotVillainToken extends TokenImpl {

    public RobotVillainToken() {
        super("Robot Villain", "2/2 colorless Robot Villain artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        subtype.add(SubType.VILLAIN);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private RobotVillainToken(final RobotVillainToken token) {
        super(token);
    }

    public RobotVillainToken copy() {
        return new RobotVillainToken(this);
    }
}
