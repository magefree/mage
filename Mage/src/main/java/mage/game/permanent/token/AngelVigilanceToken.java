package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class AngelVigilanceToken extends TokenImpl {

    public AngelVigilanceToken() {
        super("Angel Token", "4/4 white Angel creature token with flying and vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
        addAbility(VigilanceAbility.getInstance());
    }

    private AngelVigilanceToken(final AngelVigilanceToken token) {
        super(token);
    }

    public AngelVigilanceToken copy() {
        return new AngelVigilanceToken(this);
    }
}
