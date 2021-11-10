

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class DovescapeToken extends TokenImpl {

    public DovescapeToken() {
        super("Bird Token", "1/1 white and blue Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
    public DovescapeToken(final DovescapeToken token) {
        super(token);
    }

    public DovescapeToken copy() {
        return new DovescapeToken(this);
    }
}
