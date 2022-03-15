
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class ZombieToken2 extends TokenImpl {

    public ZombieToken2() {
        this(0, 0);
    }

    public ZombieToken2(int zPower, int zToughness) {
        super("Zombie Token", String.valueOf(zPower) + '/' + String.valueOf(zToughness) + " black Zombie creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(zPower);
        toughness = new MageInt(zToughness);
        setOriginalExpansionSetCode("EMN");
    }

    public ZombieToken2(final ZombieToken2 token) {
        super(token);
    }

    public ZombieToken2 copy() {
        return new ZombieToken2(this);
    }
}
