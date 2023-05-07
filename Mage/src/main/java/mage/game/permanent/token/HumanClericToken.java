package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class HumanClericToken extends TokenImpl {

    public HumanClericToken() {
        super("Human Cleric Token", "1/1 white and black Human Cleric creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.CLERIC);
        color.setWhite(true);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public HumanClericToken(final HumanClericToken token) {
        super(token);
    }

    public HumanClericToken copy() {
        return new HumanClericToken(this);
    }
}
