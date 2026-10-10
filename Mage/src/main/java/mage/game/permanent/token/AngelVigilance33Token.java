package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class AngelVigilance33Token extends TokenImpl {

    public AngelVigilance33Token() {
        super("Angel Token", "3/3 white Angel creature token with flying and vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(FlyingAbility.getInstance());
        addAbility(VigilanceAbility.getInstance());
    }

    private AngelVigilance33Token(final AngelVigilance33Token token) {
        super(token);
    }

    public AngelVigilance33Token copy() {
        return new AngelVigilance33Token(this);
    }
}
