
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class AlienWarriorToken extends TokenImpl {

    public AlienWarriorToken() {
        super("Alien Warrior Token", "2/2 red Alien Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ALIEN);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    protected AlienWarriorToken(final AlienWarriorToken token) {
        super(token);
    }

    public AlienWarriorToken copy() {
        return new AlienWarriorToken(this);
    }
}
