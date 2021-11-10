

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class LinvalaAngelToken extends TokenImpl {

    public LinvalaAngelToken() {
        super("Angel Token", "3/3 white Angel creature token with flying");
        setOriginalExpansionSetCode("OGW");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(FlyingAbility.getInstance());
    }

    public LinvalaAngelToken(final LinvalaAngelToken token) {
        super(token);
    }

    public LinvalaAngelToken copy() {
        return new LinvalaAngelToken(this);
    }
}
