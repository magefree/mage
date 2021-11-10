package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ReflectionBlueToken extends TokenImpl {

    public ReflectionBlueToken() {
        super("Reflection Token", "3/2 blue Reflection creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.REFLECTION);
        power = new MageInt(3);
        toughness = new MageInt(2);
    }

    private ReflectionBlueToken(final ReflectionBlueToken token) {
        super(token);
    }

    public ReflectionBlueToken copy() {
        return new ReflectionBlueToken(this);
    }
}
