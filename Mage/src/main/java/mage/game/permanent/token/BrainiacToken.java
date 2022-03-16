

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class BrainiacToken extends TokenImpl {

    public BrainiacToken() {
        super("Brainiac Token", "1/1 red Brainiac creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.BRAINIAC);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public BrainiacToken(final BrainiacToken token) {
        super(token);
    }

    public BrainiacToken copy() {
        return new BrainiacToken(this);
    }
}
