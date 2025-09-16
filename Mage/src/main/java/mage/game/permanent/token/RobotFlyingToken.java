package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RobotFlyingToken extends TokenImpl {

    public RobotFlyingToken() {
        super("Robot Token", "1/1 colorless Robot artifact creature tokens with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    private RobotFlyingToken(final RobotFlyingToken token) {
        super(token);
    }

    public RobotFlyingToken copy() {
        return new RobotFlyingToken(this);
    }
}
