package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class MyrToken extends TokenImpl {

    public MyrToken() {
        super("Myr Token", "1/1 colorless Myr artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.MYR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public MyrToken(final MyrToken token) {
        super(token);
    }

    public MyrToken copy() {
        return new MyrToken(this);
    }
}