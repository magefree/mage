

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class DjinnToken extends TokenImpl {

    public DjinnToken() {
        super("Djinn Token", "5/5 colorless Djinn artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DJINN);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }

    public DjinnToken(final DjinnToken token) {
        super(token);
    }

    public DjinnToken copy() {
        return new DjinnToken(this);
    }
}
