package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ZombieToken extends TokenImpl {

    public ZombieToken() {
        super("Zombie Token", "2/2 black Zombie creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public ZombieToken(final ZombieToken token) {
        super(token);
    }

    @Override
    public ZombieToken copy() {
        return new ZombieToken(this);
    }
}
