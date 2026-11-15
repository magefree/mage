package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class MindlessOneToken extends TokenImpl {

    public MindlessOneToken() {
        super("Mindless-One Token", "3/1 black Mindless-One creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.MINDLESS_ONE);
        power = new MageInt(3);
        toughness = new MageInt(1);
    }

    private MindlessOneToken(final MindlessOneToken token) {
        super(token);
    }

    @Override
    public MindlessOneToken copy() {
        return new MindlessOneToken(this);
    }
}
