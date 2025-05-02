package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class WallFlyingToken extends TokenImpl {

    public WallFlyingToken() {
        super("Wall Token", "0/4 white Wall creature token with defender and flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        color.setWhite(true);
        power = new MageInt(0);
        toughness = new MageInt(4);

        addAbility(DefenderAbility.getInstance());
        addAbility(FlyingAbility.getInstance());
    }

    private WallFlyingToken(final WallFlyingToken token) {
        super(token);
    }

    public WallFlyingToken copy() {
        return new WallFlyingToken(this);
    }
}
