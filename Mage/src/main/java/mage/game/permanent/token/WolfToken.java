package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class WolfToken extends TokenImpl {

    public WolfToken() {
        super("Wolf Token", "2/2 green Wolf creature token");

        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WOLF);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public WolfToken(final WolfToken token) {
        super(token);
    }

    @Override
    public Token copy() {
        return new WolfToken(this);
    }
}
