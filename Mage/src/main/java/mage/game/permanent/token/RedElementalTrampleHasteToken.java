package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 * @author spjspj
 */
public final class RedElementalTrampleHasteToken extends TokenImpl {

    public RedElementalTrampleHasteToken() {
        super("Elemental Token", "7/1 red Elemental creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(7);
        toughness = new MageInt(1);
        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    protected RedElementalTrampleHasteToken(final RedElementalTrampleHasteToken token) {
        super(token);
    }

    public RedElementalTrampleHasteToken copy() {
        return new RedElementalTrampleHasteToken(this);
    }
}
