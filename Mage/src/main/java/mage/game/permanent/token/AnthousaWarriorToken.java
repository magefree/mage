

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class AnthousaWarriorToken extends TokenImpl {

    public AnthousaWarriorToken() {
        super("", "2/2 Warrior creatures");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public AnthousaWarriorToken(final AnthousaWarriorToken token) {
        super(token);
    }

    public AnthousaWarriorToken copy() {
        return new AnthousaWarriorToken(this);
    }
}

