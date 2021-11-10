package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author fireshoes
 */
public final class CatToken2 extends TokenImpl {

    public CatToken2() {
        super("Cat Token", "1/1 white Cat creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AKH", "M19", "IKO");
    }

    public CatToken2(final CatToken2 token) {
        super(token);
    }

    public CatToken2 copy() {
        return new CatToken2(this);
    }

}
