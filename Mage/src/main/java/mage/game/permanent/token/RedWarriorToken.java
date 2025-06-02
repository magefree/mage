package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author balazskristof
 */
public final class RedWarriorToken extends TokenImpl {

    public RedWarriorToken() {
        super("Warrior Token", "1/1 red Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private RedWarriorToken(final RedWarriorToken token) {
        super(token);
    }

    @Override
    public RedWarriorToken copy() {
        return new RedWarriorToken(this);
    }
}
