package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class GiantsAmuletToken extends TokenImpl {

    public GiantsAmuletToken() {
        super("Giant Wizard", "4/4 blue Giant Wizard creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.GIANT);
        subtype.add(SubType.WIZARD);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public GiantsAmuletToken(final GiantsAmuletToken token) {
        super(token);
    }

    public GiantsAmuletToken copy() {
        return new GiantsAmuletToken(this);
    }
}
