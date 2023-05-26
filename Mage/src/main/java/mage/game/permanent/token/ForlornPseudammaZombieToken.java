package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ForlornPseudammaZombieToken extends TokenImpl {

    public ForlornPseudammaZombieToken() {
        super("Zombie Token", "2/2 black Zombie enchantment creature token");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public ForlornPseudammaZombieToken(final ForlornPseudammaZombieToken token) {
        super(token);
    }

    public ForlornPseudammaZombieToken copy() {
        return new ForlornPseudammaZombieToken(this);
    }
}

