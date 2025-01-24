package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ZombieWhiteToken extends TokenImpl {

    public ZombieWhiteToken() {
        super("Zombie Token", "1/1 white Zombie creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private ZombieWhiteToken(final ZombieWhiteToken token) {
        super(token);
    }

    @Override
    public ZombieWhiteToken copy() {
        return new ZombieWhiteToken(this);
    }
}
