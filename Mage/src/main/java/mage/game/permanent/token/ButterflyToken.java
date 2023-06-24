package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ButterflyToken extends TokenImpl {

    public ButterflyToken() {
        super("Butterfly", "1/1 green Insect creature token with flying named Butterfly");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
    }

    public ButterflyToken(final ButterflyToken token) {
        super(token);
    }

    public ButterflyToken copy() {
        return new ButterflyToken(this);
    }
}
