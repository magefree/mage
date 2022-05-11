

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class SerfToken extends TokenImpl {

    public SerfToken() {
        super("Serf Token", "0/1 black Serf creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SERF);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public SerfToken(final SerfToken token) {
        super(token);
    }

    public SerfToken copy() {
        return new SerfToken(this);
    }
}
