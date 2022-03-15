

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author TheElk801
 */
public final class CarrionBlackInsectToken extends TokenImpl {

    public CarrionBlackInsectToken() {
        super("Insect Token", "0/1 black Insect creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }
    public CarrionBlackInsectToken(final CarrionBlackInsectToken token) {
        super(token);
    }

    public CarrionBlackInsectToken copy() {
        return new CarrionBlackInsectToken(this);
    }
    
}
