package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class ZombieStarToken extends TokenImpl {

    public ZombieStarToken() {
        this(0, 0);
    }

    public ZombieStarToken(int zPower, int zToughness) {
        super("Zombie Token", String.valueOf(zPower) + '/' + String.valueOf(zToughness) + " black Zombie creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(zPower);
        toughness = new MageInt(zToughness);
    }

    protected ZombieStarToken(final ZombieStarToken token) {
        super(token);
    }

    public ZombieStarToken copy() {
        return new ZombieStarToken(this);
    }
}
