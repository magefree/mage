
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class ElementalAppealElementalToken extends TokenImpl {

    public ElementalAppealElementalToken() {
        super("Elemental", "7/1 red Elemental creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(7);
        toughness = new MageInt(1);
        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    public ElementalAppealElementalToken(final ElementalAppealElementalToken token) {
        super(token);
    }

    public ElementalAppealElementalToken copy() {
        return new ElementalAppealElementalToken(this);
    }
}
