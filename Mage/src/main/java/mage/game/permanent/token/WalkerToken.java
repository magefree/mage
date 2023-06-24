package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WalkerToken extends TokenImpl {

    public WalkerToken() {
        super("Walker Token", "Walker token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public WalkerToken(final WalkerToken token) {
        super(token);
    }

    @Override
    public WalkerToken copy() {
        return new WalkerToken(this);
    }
}
