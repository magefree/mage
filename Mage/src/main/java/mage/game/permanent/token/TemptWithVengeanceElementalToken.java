
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author spjspj
 */
public final class TemptWithVengeanceElementalToken extends TokenImpl {

    public TemptWithVengeanceElementalToken() {
        super("Elemental Token", "1/1 red Elemental creature tokens with haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);

        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    public TemptWithVengeanceElementalToken(final TemptWithVengeanceElementalToken token) {
        super(token);
    }

    public TemptWithVengeanceElementalToken copy() {
        return new TemptWithVengeanceElementalToken(this);
    }
}
