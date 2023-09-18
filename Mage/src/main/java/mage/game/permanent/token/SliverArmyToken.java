package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SliverArmyToken extends TokenImpl {

    public SliverArmyToken() {
        super("Sliver Army Token", "0/0 black Sliver Army creature token");

        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SLIVER);
        subtype.add(SubType.ARMY);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private SliverArmyToken(final SliverArmyToken token) {
        super(token);
    }

    @Override
    public SliverArmyToken copy() {
        return new SliverArmyToken(this);
    }
}
