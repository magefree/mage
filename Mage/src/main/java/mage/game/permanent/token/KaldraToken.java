

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.constants.SuperType;

/**
 *
 * @author spjspj
 */
public final class KaldraToken extends TokenImpl {

    public KaldraToken() {
        super("Kaldra", "legendary 4/4 colorless Avatar creature token named Kaldra");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.AVATAR);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public KaldraToken(final KaldraToken token) {
        super(token);
    }

    public KaldraToken copy() {
        return new KaldraToken(this);
    }
}
