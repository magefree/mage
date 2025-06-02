package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author nantuko
 */
public final class SpiritXXToken extends TokenImpl {

    public SpiritXXToken() {
        this(0);
    }

    public SpiritXXToken(int amount) {
        super("Spirit Token", amount + '/' + amount + " white Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(amount);
        toughness = new MageInt(amount);
    }

    private SpiritXXToken(final SpiritXXToken token) {
        super(token);
    }

    @Override
    public SpiritXXToken copy() {
        return new SpiritXXToken(this);
    }
}
