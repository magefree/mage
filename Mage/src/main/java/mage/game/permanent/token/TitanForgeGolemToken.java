

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class TitanForgeGolemToken extends TokenImpl {

    public TitanForgeGolemToken() {
        super("Golem Token", "9/9 colorless Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(9);
        toughness = new MageInt(9);
    }

    public TitanForgeGolemToken(final TitanForgeGolemToken token) {
        super(token);
    }

    public TitanForgeGolemToken copy() {
        return new TitanForgeGolemToken(this);
    }
}
