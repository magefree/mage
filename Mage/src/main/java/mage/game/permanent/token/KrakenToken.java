package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */

public final class KrakenToken extends TokenImpl {

    public KrakenToken() {
        super("Kraken", "8/8 blue Kraken creature token");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.KRAKEN);
        this.color.setBlue(true);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
    }

    private KrakenToken(final KrakenToken token) {
        super(token);
    }

    public KrakenToken copy() {
        return new KrakenToken(this);
    }
}
