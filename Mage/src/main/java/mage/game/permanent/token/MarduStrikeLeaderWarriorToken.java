package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class MarduStrikeLeaderWarriorToken extends TokenImpl {

    public MarduStrikeLeaderWarriorToken() {
        super("Warrior Token", "2/1 black Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(1);
    }

    public MarduStrikeLeaderWarriorToken(final MarduStrikeLeaderWarriorToken token) {
        super(token);
    }

    public MarduStrikeLeaderWarriorToken copy() {
        return new MarduStrikeLeaderWarriorToken(this);
    }
}
