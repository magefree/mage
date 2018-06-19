package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class AngelToken2 extends TokenImpl {

    public AngelToken2() {
        super("Angel", "4/4 white Angel creature token with flying and vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
        addAbility(VigilanceAbility.getInstance());
    }

    public AngelToken2(final AngelToken2 token) {
        super(token);
    }

    public AngelToken2 copy() {
        return new AngelToken2(this);
    }
}
