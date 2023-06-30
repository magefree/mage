package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SpiritGreenXToken extends TokenImpl {

    public SpiritGreenXToken() {
        this(0);
    }

    public SpiritGreenXToken(int xValue) {
        super("Spirit Token", "X/X green Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setGreen(true);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public SpiritGreenXToken(final SpiritGreenXToken token) {
        super(token);
    }

    public SpiritGreenXToken copy() {
        return new SpiritGreenXToken(this);
    }
}
