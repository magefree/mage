package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class UnicornToken extends TokenImpl {

    public UnicornToken() {
        super("Unicorn Token", "2/2 white Unicorn creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.UNICORN);
        color.setWhite(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private UnicornToken(final UnicornToken token) {
        super(token);
    }

    @Override
    public UnicornToken copy() {
        return new UnicornToken(this);
    }
}