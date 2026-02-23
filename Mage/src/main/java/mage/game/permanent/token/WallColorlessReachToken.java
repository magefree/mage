package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WallColorlessReachToken extends TokenImpl {

    public WallColorlessReachToken() {
        super("Wall Token", "0/3 colorless Wall creature token with defender and reach");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        power = new MageInt(0);
        toughness = new MageInt(3);
        addAbility(DefenderAbility.getInstance());
        addAbility(ReachAbility.getInstance());
    }

    private WallColorlessReachToken(final WallColorlessReachToken token) {
        super(token);
    }

    public WallColorlessReachToken copy() {
        return new WallColorlessReachToken(this);
    }
}
