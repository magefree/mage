package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author weirddan455
 */
public class RatRogueToken extends TokenImpl {

    public RatRogueToken() {
        super("Rat Rogue Token", "1/1 black Rat Rogue creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        subtype.add(SubType.ROGUE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private RatRogueToken(final RatRogueToken token) {
        super(token);
    }

    @Override
    public RatRogueToken copy() {
        return new RatRogueToken(this);
    }
}
