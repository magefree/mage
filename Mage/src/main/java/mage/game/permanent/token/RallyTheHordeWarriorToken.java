
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class RallyTheHordeWarriorToken extends TokenImpl {

    public RallyTheHordeWarriorToken() {
        super("Warrior Token", "1/1 red Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public RallyTheHordeWarriorToken(final RallyTheHordeWarriorToken token) {
        super(token);
    }

    public RallyTheHordeWarriorToken copy() {
        return new RallyTheHordeWarriorToken(this);
    }
}
