package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class SnailToken extends TokenImpl {

    public SnailToken() {
        super("Snail Token", "1/1 black Snail creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SNAIL);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private SnailToken(final SnailToken token) {
        super(token);
    }

    @Override
    public SnailToken copy() {
        return new SnailToken(this);
    }
}
