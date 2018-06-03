

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class KioraKrakenToken extends TokenImpl {

    public KioraKrakenToken() {
        super("Kraken", "9/9 blue Kraken creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.KRAKEN);
        power = new MageInt(9);
        toughness = new MageInt(9);
        this.setOriginalExpansionSetCode("BNG");
    }

    public KioraKrakenToken(final KioraKrakenToken token) {
        super(token);
    }

    public KioraKrakenToken copy() {
        return new KioraKrakenToken(this);
    }
}
