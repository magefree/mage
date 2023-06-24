package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Quercitron
 */
public final class ThrullToken extends TokenImpl {

    public ThrullToken() {
        super("Thrull Token", "1/1 black Thrull creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THRULL);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public ThrullToken(final ThrullToken token) {
        super(token);
    }

    public ThrullToken copy() {
        return new ThrullToken(this);
    }
}
