package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class BirdSoldier44Token extends TokenImpl {

    public BirdSoldier44Token() {
        super("Bird Soldier Token", "4/4 white Bird Soldier creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);

        color.setWhite(true);
        power = new MageInt(4);
        toughness = new MageInt(4);

        addAbility(FlyingAbility.getInstance());
    }

    private BirdSoldier44Token(final BirdSoldier44Token token) {
        super(token);
    }

    public BirdSoldier44Token copy() {
        return new BirdSoldier44Token(this);
    }
}
