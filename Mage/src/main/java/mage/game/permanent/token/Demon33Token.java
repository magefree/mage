package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Demon33Token extends TokenImpl {

    public Demon33Token() {
        super("Demon Token", "3/3 black Demon creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public Demon33Token(final Demon33Token token) {
        super(token);
    }

    @Override
    public Demon33Token copy() {
        return new Demon33Token(this);
    }
}
