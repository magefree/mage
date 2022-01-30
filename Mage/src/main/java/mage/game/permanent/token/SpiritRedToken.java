package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SpiritRedToken extends TokenImpl {

    public SpiritRedToken() {
        super("Spirit token", "2/2 red Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setRed(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public SpiritRedToken(final SpiritRedToken token) {
        super(token);
    }

    public SpiritRedToken copy() {
        return new SpiritRedToken(this);
    }
}
