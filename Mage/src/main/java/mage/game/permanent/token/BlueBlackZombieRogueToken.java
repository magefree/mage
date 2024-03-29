package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class BlueBlackZombieRogueToken extends TokenImpl {

    public BlueBlackZombieRogueToken() {
        super("Zombie Rogue Token", "blue and black Zombie Rogue creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE, SubType.ROGUE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private BlueBlackZombieRogueToken(final BlueBlackZombieRogueToken token) {
        super(token);
    }

    public BlueBlackZombieRogueToken copy() {
        return new BlueBlackZombieRogueToken(this);
    }
}
