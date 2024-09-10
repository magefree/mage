package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SalamanderWarriorToken extends TokenImpl {

    public SalamanderWarriorToken() {
        super("Salamander Warrior Token", "4/3 blue Salamander Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SALAMANDER);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(3);
    }

    private SalamanderWarriorToken(final SalamanderWarriorToken token) {
        super(token);
    }

    public SalamanderWarriorToken copy() {
        return new SalamanderWarriorToken(this);
    }
}
