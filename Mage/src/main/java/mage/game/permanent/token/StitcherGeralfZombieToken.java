package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class StitcherGeralfZombieToken extends TokenImpl {

    public StitcherGeralfZombieToken() {
        this(1);
    }

    public StitcherGeralfZombieToken(int xValue) {
        super("Zombie Token", "X/X blue Zombie creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public StitcherGeralfZombieToken(final StitcherGeralfZombieToken token) {
        super(token);
    }

    public StitcherGeralfZombieToken copy() {
        return new StitcherGeralfZombieToken(this);
    }
}
