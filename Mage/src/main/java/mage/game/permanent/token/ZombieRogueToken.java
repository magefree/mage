package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class ZombieRogueToken extends TokenImpl {

    public ZombieRogueToken() {
        super("Zombie Rogue Token", "2/2 blue and black Zombie Rogue creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setBlue(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.ROGUE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private ZombieRogueToken(final ZombieRogueToken token) {
        super(token);
    }

    @Override
    public ZombieRogueToken copy() {
        return new ZombieRogueToken(this);
    }
}
