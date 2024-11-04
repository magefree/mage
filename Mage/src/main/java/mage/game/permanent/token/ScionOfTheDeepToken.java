package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

public final class ScionOfTheDeepToken extends TokenImpl {

    public ScionOfTheDeepToken() {
        super("Scion of the Deep", "Scion of the Deep, a legendary 8/8 blue Octopus creature token");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.OCTOPUS);
        power = new MageInt(8);
        toughness = new MageInt(8);
    }

    private ScionOfTheDeepToken(final ScionOfTheDeepToken token) {
        super(token);
    }

    public ScionOfTheDeepToken copy() {
        return new ScionOfTheDeepToken(this);
    }
}
