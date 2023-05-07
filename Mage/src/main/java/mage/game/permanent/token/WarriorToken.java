package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class WarriorToken extends TokenImpl {

    public WarriorToken() {
        super("Warrior Token", "1/1 white Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public WarriorToken(final WarriorToken token) {
        super(token);
    }

    @Override
    public WarriorToken copy() {
        return new WarriorToken(this);
    }
}
