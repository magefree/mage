package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author fireshoes
 */
public final class ServoToken extends TokenImpl {

    public ServoToken() {
        super("Servo Token", "1/1 colorless Servo artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SERVO);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public ServoToken(final ServoToken token) {
        super(token);
    }

    @Override
    public ServoToken copy() {
        return new ServoToken(this);
    }

}
