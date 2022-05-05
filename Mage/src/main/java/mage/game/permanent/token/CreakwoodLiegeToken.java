

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class CreakwoodLiegeToken extends TokenImpl {

    public CreakwoodLiegeToken() {
        super("Worm Token", "1/1 black and green Worm creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.WORM);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public CreakwoodLiegeToken(final CreakwoodLiegeToken token) {
        super(token);
    }

    public CreakwoodLiegeToken copy() {
        return new CreakwoodLiegeToken(this);
    }
}
