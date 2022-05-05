

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class PenumbraKavuToken extends TokenImpl {

    public PenumbraKavuToken() {
        super("Kavu Token", "3/3 black Kavu creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.KAVU);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public PenumbraKavuToken(final PenumbraKavuToken token) {
        super(token);
    }

    public PenumbraKavuToken copy() {
        return new PenumbraKavuToken(this);
    }
}
