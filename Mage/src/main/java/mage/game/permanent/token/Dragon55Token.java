package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class Dragon55Token extends TokenImpl {

    public Dragon55Token() {
        super("Dragon Token", "5/5 red Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(5);
        toughness = new MageInt(5);

        addAbility(FlyingAbility.getInstance());
    }

    protected Dragon55Token(final Dragon55Token token) {
        super(token);
    }

    public Dragon55Token copy() {
        return new Dragon55Token(this);
    }
}
