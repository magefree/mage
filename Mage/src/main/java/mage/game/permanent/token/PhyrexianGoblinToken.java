package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PhyrexianGoblinToken extends TokenImpl {

    public PhyrexianGoblinToken() {
        super("Phyrexian Goblin Token", "1/1 red Phyrexian Goblin creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.GOBLIN);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public PhyrexianGoblinToken(final PhyrexianGoblinToken token) {
        super(token);
    }

    public PhyrexianGoblinToken copy() {
        return new PhyrexianGoblinToken(this);
    }
}
