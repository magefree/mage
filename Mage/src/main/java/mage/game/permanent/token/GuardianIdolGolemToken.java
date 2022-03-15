

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class GuardianIdolGolemToken extends TokenImpl {

    public GuardianIdolGolemToken() {
        super("Golem Token", "2/2 Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public GuardianIdolGolemToken(final GuardianIdolGolemToken token) {
        super(token);
    }

    public GuardianIdolGolemToken copy() {
        return new GuardianIdolGolemToken(this);
    }
}
