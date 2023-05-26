package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PhyrexianZombieToken extends TokenImpl {

    public PhyrexianZombieToken() {
        super("Phyrexian Zombie Token", "2/2 black Phyrexian Zombie creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public PhyrexianZombieToken(final PhyrexianZombieToken token) {
        super(token);
    }

    @Override
    public PhyrexianZombieToken copy() {
        return new PhyrexianZombieToken(this);
    }
}
