package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class Dragon66Token extends TokenImpl {

    public Dragon66Token() {
        super("Dragon Token", "6/6 red Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
    }

    private Dragon66Token(final Dragon66Token token) {
        super(token);
    }

    public Dragon66Token copy() {
        return new Dragon66Token(this);
    }
}
