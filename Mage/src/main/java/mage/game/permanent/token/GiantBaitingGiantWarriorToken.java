
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author spjspj
 */
public final class GiantBaitingGiantWarriorToken extends TokenImpl {

    public GiantBaitingGiantWarriorToken() {
        super("Giant Warrior Token", "4/4 red and green Giant Warrior creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.GIANT);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(HasteAbility.getInstance());
    }

    public GiantBaitingGiantWarriorToken(final GiantBaitingGiantWarriorToken token) {
        super(token);
    }

    public GiantBaitingGiantWarriorToken copy() {
        return new GiantBaitingGiantWarriorToken(this);
    }
}
