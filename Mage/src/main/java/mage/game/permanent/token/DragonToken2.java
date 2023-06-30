package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class DragonToken2 extends TokenImpl {

    public DragonToken2() {
        super("Dragon Token", "5/5 red Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(5);
        toughness = new MageInt(5);

        addAbility(FlyingAbility.getInstance());
    }

    public DragonToken2(final DragonToken2 token) {
        super(token);
    }

    public DragonToken2 copy() {
        return new DragonToken2(this);
    }
}
