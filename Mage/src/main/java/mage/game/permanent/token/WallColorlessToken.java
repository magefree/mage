package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WallColorlessToken extends TokenImpl {

    public WallColorlessToken() {
        super("Wall Token", "0/4 colorless Wall creature token with defender");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        power = new MageInt(0);
        toughness = new MageInt(4);
        addAbility(DefenderAbility.getInstance());
    }

    private WallColorlessToken(final WallColorlessToken token) {
        super(token);
    }

    public WallColorlessToken copy() {
        return new WallColorlessToken(this);
    }
}
