package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class BorgToken extends TokenImpl {

    public BorgToken() {
        super("Borg Token", "1/1 colorless Borg artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BORG);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private BorgToken(final BorgToken token) {
        super(token);
    }

    public BorgToken copy() {
        return new BorgToken(this);
    }
}
