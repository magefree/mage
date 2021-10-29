package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */

public final class KrakenHexproofToken extends TokenImpl {

    public KrakenHexproofToken() {
        super("Kraken", "8/8 blue Kraken creature token with hexproof");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.KRAKEN);
        this.color.setBlue(true);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.addAbility(HexproofAbility.getInstance());
    }

    private KrakenHexproofToken(final KrakenHexproofToken token) {
        super(token);
    }

    public KrakenHexproofToken copy() {
        return new KrakenHexproofToken(this);
    }
}
