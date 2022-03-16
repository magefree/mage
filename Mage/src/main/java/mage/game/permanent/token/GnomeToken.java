

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class GnomeToken extends TokenImpl {

    public GnomeToken() {
        super("Gnome Token", "1/1 colorless Gnome artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GNOME);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public GnomeToken(final GnomeToken token) {
        super(token);
    }

    public GnomeToken copy() {
        return new GnomeToken(this);
    }
}
