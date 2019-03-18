

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class WhiteElementalToken extends TokenImpl {

    public WhiteElementalToken() {
        super("Elemental", "4/4 white Elemental creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        setTokenType(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    public WhiteElementalToken(final WhiteElementalToken token) {
        super(token);
    }

    public WhiteElementalToken copy() {
        return new WhiteElementalToken(this);
    }
}
