package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class SpiritToken extends TokenImpl {

    public SpiritToken() {
        super("Spirit Token", "1/1 colorless Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SpiritToken(final SpiritToken token) {
        super(token);
    }

    @Override
    public SpiritToken copy() {
        return new SpiritToken(this);
    }
}