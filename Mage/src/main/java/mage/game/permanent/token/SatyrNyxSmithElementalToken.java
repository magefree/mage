package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class SatyrNyxSmithElementalToken extends TokenImpl {

    public SatyrNyxSmithElementalToken() {
        super("Elemental Token", "3/1 red Elemental enchantment creature token with haste");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    public SatyrNyxSmithElementalToken(final SatyrNyxSmithElementalToken token) {
        super(token);
    }

    public SatyrNyxSmithElementalToken copy() {
        return new SatyrNyxSmithElementalToken(this);
    }
}
