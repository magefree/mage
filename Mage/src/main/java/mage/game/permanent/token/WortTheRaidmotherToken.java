

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class WortTheRaidmotherToken extends TokenImpl {

    public WortTheRaidmotherToken() {
        super("Goblin Warrior", "1/1 red and green Goblin Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public WortTheRaidmotherToken(final WortTheRaidmotherToken token) {
        super(token);
    }

    public WortTheRaidmotherToken copy() {
        return new WortTheRaidmotherToken(this);
    }
}
