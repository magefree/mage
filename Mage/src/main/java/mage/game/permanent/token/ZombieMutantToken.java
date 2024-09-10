package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class ZombieMutantToken extends TokenImpl {

    public ZombieMutantToken() {
        super("Zombie Mutant Token", "2/2 black Zombie Mutant creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.MUTANT);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private ZombieMutantToken(final ZombieMutantToken token) {
        super(token);
    }

    @Override
    public ZombieMutantToken copy() {
        return new ZombieMutantToken(this);
    }
}
