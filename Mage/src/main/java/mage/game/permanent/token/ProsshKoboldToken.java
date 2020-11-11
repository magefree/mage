

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class ProsshKoboldToken extends TokenImpl {

    public ProsshKoboldToken() {
        super("Kobolds of Kher Keep", "0/1 red Kobold creature tokens named Kobolds of Kher Keep");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.KOBOLD);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public ProsshKoboldToken(final ProsshKoboldToken token) {
        super(token);
    }

    public ProsshKoboldToken copy() {
        return new ProsshKoboldToken(this);
    }
}
