package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ZombieArmyToken extends TokenImpl {

    public ZombieArmyToken() {
        super("Zombie Army Token", "0/0 black Zombie Army creature token");

        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.ARMY);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private ZombieArmyToken(final ZombieArmyToken token) {
        super(token);
    }

    @Override
    public ZombieArmyToken copy() {
        return new ZombieArmyToken(this);
    }
}
