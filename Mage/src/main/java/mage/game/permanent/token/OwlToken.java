

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class OwlToken extends TokenImpl {

    public OwlToken() {
        super("Bird", "1/1 blue Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    public OwlToken(final OwlToken token) {
        super(token);
    }

    public OwlToken copy() {
        return new OwlToken(this);
    }
}
