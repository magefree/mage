
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class ElementalMasteryElementalToken extends TokenImpl {

    public ElementalMasteryElementalToken() {
        super("Elemental Token", "1/1 red Elemental creature token with haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("SHM")) {
            this.setTokenType(2);
        }
    }

    public ElementalMasteryElementalToken(final ElementalMasteryElementalToken token) {
        super(token);
    }

    public ElementalMasteryElementalToken copy() {
        return new ElementalMasteryElementalToken(this);
    }
}
