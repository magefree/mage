package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author lagdotcom
 */
public final class WallToken extends TokenImpl {

    public WallToken() {
        super("Wall Token", "0/2 colorless Wall artifact creature token with defender");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        power = new MageInt(0);
        toughness = new MageInt(2);
        addAbility(DefenderAbility.getInstance());
    }

    public WallToken(final WallToken token) {
        super(token);
    }

    public WallToken copy() {
        return new WallToken(this);
    }
}
