

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author spjspj
 */
public final class ElementalCatToken extends TokenImpl {

    public ElementalCatToken() {
        super("Elemental Cat Token", "1/1 red Elemental Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        subtype.add(SubType.CAT);
        addAbility(HasteAbility.getInstance());
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public ElementalCatToken(final ElementalCatToken token) {
        super(token);
    }

    public ElementalCatToken copy() {
        return new ElementalCatToken(this);
    }
}
