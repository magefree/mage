package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class GoblinArmyToken extends TokenImpl {

    public GoblinArmyToken() {
        super("Goblin Army Token", "0/0 black Goblin Army creature token");

        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.ARMY);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private GoblinArmyToken(final GoblinArmyToken token) {
        super(token);
    }

    @Override
    public GoblinArmyToken copy() {
        return new GoblinArmyToken(this);
    }
}
