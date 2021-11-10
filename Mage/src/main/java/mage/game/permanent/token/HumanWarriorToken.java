package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class HumanWarriorToken extends TokenImpl {

    public HumanWarriorToken() {
        super("Human Warrior Token", "1/1 white Human Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("KHM");
    }

    private HumanWarriorToken(final HumanWarriorToken token) {
        super(token);
    }

    @Override
    public HumanWarriorToken copy() {
        return new HumanWarriorToken(this);
    }
}
