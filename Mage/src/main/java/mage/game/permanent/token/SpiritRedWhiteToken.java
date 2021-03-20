package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SpiritRedWhiteToken extends TokenImpl {

    public SpiritRedWhiteToken() {
        super("Spirit", "3/2 red and white Spirit creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(3);
        toughness = new MageInt(2);
    }

    private SpiritRedWhiteToken(final SpiritRedWhiteToken token) {
        super(token);
    }

    @Override
    public SpiritRedWhiteToken copy() {
        return new SpiritRedWhiteToken(this);
    }
}
