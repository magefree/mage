package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class MinotaurToken extends TokenImpl {

    public MinotaurToken() {
        super("Minotaur Token", "2/3 red Minotaur creature token");
        cardType.add(CardType.CREATURE);
        color.setColor(ObjectColor.RED);
        subtype.add(SubType.MINOTAUR);
        power = new MageInt(2);
        toughness = new MageInt(3);
    }

    private MinotaurToken(final MinotaurToken token) {
        super(token);
    }

    public MinotaurToken copy() {
        return new MinotaurToken(this);
    }
}
