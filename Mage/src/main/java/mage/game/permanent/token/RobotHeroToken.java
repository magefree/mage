package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class RobotHeroToken extends TokenImpl {

    public RobotHeroToken() {
        super("Robot Hero Token", "2/1 colorless Robot Hero artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        subtype.add(SubType.HERO);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private RobotHeroToken(final RobotHeroToken token) {
        super(token);
    }

    public RobotHeroToken copy() {
        return new RobotHeroToken(this);
    }
}
