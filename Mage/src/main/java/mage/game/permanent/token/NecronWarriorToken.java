package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class NecronWarriorToken extends TokenImpl {

    public NecronWarriorToken() {
        super("Necron Warrior Token", "2/2 black Necron Warrior artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.NECRON);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public NecronWarriorToken(final NecronWarriorToken token) {
        super(token);
    }

    @Override
    public NecronWarriorToken copy() {
        return new NecronWarriorToken(this);
    }
}
