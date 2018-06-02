
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class ReefWormKrakenToken extends TokenImpl {

    public ReefWormKrakenToken() {
        super("Kraken", "9/9 blue Kraken creature token");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.KRAKEN);
        power = new MageInt(9);
        toughness = new MageInt(9);
    }

    public ReefWormKrakenToken(final ReefWormKrakenToken token) {
        super(token);
    }

    public ReefWormKrakenToken copy() {
        return new ReefWormKrakenToken(this);
    }
}
