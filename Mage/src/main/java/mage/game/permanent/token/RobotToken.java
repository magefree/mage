package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RobotToken extends TokenImpl {

    public RobotToken() {
        super("Robot Token", "4/4 colorless Robot artifact creature token with \"This creature can't block.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield)
                        .setText("this creature can't block")
        ));
    }

    public RobotToken(final RobotToken token) {
        super(token);
    }

    public RobotToken copy() {
        return new RobotToken(this);
    }
}
