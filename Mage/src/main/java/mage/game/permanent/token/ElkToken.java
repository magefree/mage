package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ElkToken extends TokenImpl {

    public ElkToken() {
        super("Elk Token", "3/3 green Elk creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELK);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private ElkToken(final ElkToken token) {
        super(token);
    }

    public ElkToken copy() {
        return new ElkToken(this);
    }
}
