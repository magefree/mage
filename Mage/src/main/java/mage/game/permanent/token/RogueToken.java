package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author jmharmon
 */

public final class RogueToken extends TokenImpl {

    public RogueToken() {
        super("Rogue", "2/2 black Rogue creature token with hexproof");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ROGUE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(HexproofAbility.getInstance());
    }

    public RogueToken(final RogueToken token) {
        super(token);
    }

    public RogueToken copy() {
        return new RogueToken(this);
    }
}
