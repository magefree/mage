package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class AssassinBlueToken extends TokenImpl {

    public AssassinBlueToken(int power_val, int toughness_val) {
        super("AssassinBlue", power_val + "/" + toughness_val + " blue Assassin creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ASSASSIN );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public AssassinBlueToken() {
        this(2, 2);
    }

    public AssassinBlueToken(final AssassinBlueToken token) {
        super(token);
    }

    public AssassinBlueToken copy() {
        return new AssassinBlueToken(this);
    }
}
