package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SalamnderWarriorToken extends TokenImpl {

    public SalamnderWarriorToken() {
        super("Salamander Warrior Token", "4/3 blue Salamander Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SALAMANDER);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(3);
    }

    public SalamnderWarriorToken(final SalamnderWarriorToken token) {
        super(token);
    }

    public SalamnderWarriorToken copy() {
        return new SalamnderWarriorToken(this);
    }
}
