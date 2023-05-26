package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class AngelToken extends TokenImpl {

    public AngelToken() {
        super("Angel Token", "4/4 white Angel creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(4);
        toughness = new MageInt(4);

        addAbility(FlyingAbility.getInstance());
    }

    public AngelToken(final AngelToken token) {
        super(token);
    }

    public AngelToken copy() {
        return new AngelToken(this);
    }
}
