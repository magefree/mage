package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ElephantToken extends TokenImpl {

    public ElephantToken() {
        super("Elephant Token", "3/3 green Elephant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEPHANT);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public ElephantToken(final ElephantToken token) {
        super(token);
    }

    public ElephantToken copy() {
        return new ElephantToken(this);
    }
}