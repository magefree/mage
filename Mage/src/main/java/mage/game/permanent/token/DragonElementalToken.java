package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DragonElementalToken extends TokenImpl {

    public DragonElementalToken() {
        super("Dragon Token", "4/4 red Dragon Elemental creature token with flying and prowess");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
        addAbility(new ProwessAbility());
    }

    private DragonElementalToken(final DragonElementalToken token) {
        super(token);
    }

    public DragonElementalToken copy() {
        return new DragonElementalToken(this);
    }
}
