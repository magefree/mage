package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class HumanRogueToken extends TokenImpl {

    public HumanRogueToken() {
        super("Human Token", "1/1 white Human Rogue creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.ROGUE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private HumanRogueToken(final HumanRogueToken token) {
        super(token);
    }

    @Override
    public HumanRogueToken copy() {
        return new HumanRogueToken(this);
    }
}
