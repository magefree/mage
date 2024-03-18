package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Gremlin11Token extends TokenImpl {

    public Gremlin11Token() {
        super("Gremlin Token", "1/1 red Gremlin creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GREMLIN);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private Gremlin11Token(final Gremlin11Token token) {
        super(token);
    }

    public Gremlin11Token copy() {
        return new Gremlin11Token(this);
    }
}
