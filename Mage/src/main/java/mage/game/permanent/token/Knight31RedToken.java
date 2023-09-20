package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LevelX2
 */
public final class Knight31RedToken extends TokenImpl {

    public Knight31RedToken() {
        super("Knight Token", "3/1 red Knight creature token");

        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(3);
        toughness = new MageInt(1);
    }

    protected Knight31RedToken(final Knight31RedToken token) {
        super(token);
    }

    public Knight31RedToken copy() {
        return new Knight31RedToken(this);
    }
}
