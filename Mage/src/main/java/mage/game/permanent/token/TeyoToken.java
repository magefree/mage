package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class TeyoToken extends TokenImpl {

    public TeyoToken() {
        super("Wall Token", "0/3 white Wall creature token with defender");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.WALL);
        power = new MageInt(0);
        toughness = new MageInt(3);
        addAbility(DefenderAbility.getInstance());
    }

    public TeyoToken(final TeyoToken token) {
        super(token);
    }

    public TeyoToken copy() {
        return new TeyoToken(this);
    }
}
