package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ClownRobotToken extends TokenImpl {

    public ClownRobotToken() {
        super("Clown Robot Token", "1/1 white Clown Robot artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CLOWN);
        subtype.add(SubType.ROBOT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public ClownRobotToken(final ClownRobotToken token) {
        super(token);
    }

    public ClownRobotToken copy() {
        return new ClownRobotToken(this);
    }
}
