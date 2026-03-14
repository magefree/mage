package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author fireshoes
 */
public final class Cat11LifelinkToken extends TokenImpl {

    public Cat11LifelinkToken() {
        super("Cat Token", "1/1 white Cat creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
    }

    protected Cat11LifelinkToken(final Cat11LifelinkToken token) {
        super(token);
    }

    public Cat11LifelinkToken copy() {
        return new Cat11LifelinkToken(this);
    }

}
