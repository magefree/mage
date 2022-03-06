package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class RakkaMarElementalToken extends TokenImpl {

    public RakkaMarElementalToken() {
        super("Elemental", "3/1 red Elemental creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());
    }

    public RakkaMarElementalToken(final RakkaMarElementalToken token) {
        super(token);
    }

    public RakkaMarElementalToken copy() {
        return new RakkaMarElementalToken(this);
    }
}
