package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class GoblinRogueToken extends TokenImpl {

    public GoblinRogueToken() {
        super("Goblin Rogue Token", "1/1 black Goblin Rogue creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.ROGUE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public GoblinRogueToken(final GoblinRogueToken token) {
        super(token);
    }

    public GoblinRogueToken copy() {
        return new GoblinRogueToken(this);
    }
}
