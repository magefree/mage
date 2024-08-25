package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WallWhiteToken extends TokenImpl {

    public WallWhiteToken() {
        super("Wall Token", "0/4 white Wall creature token with defender");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        color.setWhite(true);
        power = new MageInt(0);
        toughness = new MageInt(4);

        addAbility(DefenderAbility.getInstance());
    }

    private WallWhiteToken(final WallWhiteToken token) {
        super(token);
    }

    public WallWhiteToken copy() {
        return new WallWhiteToken(this);
    }
}
