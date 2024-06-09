package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RabbitToken extends TokenImpl {

    public RabbitToken() {
        super("Rabbit Token", "1/1 white Rabbit creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.RABBIT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private RabbitToken(final RabbitToken token) {
        super(token);
    }

    @Override
    public RabbitToken copy() {
        return new RabbitToken(this);
    }
}
