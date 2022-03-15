package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class TrollWarriorToken extends TokenImpl {

    public TrollWarriorToken() {
        super("Troll Warrior Token", "4/4 green Troll Warrior creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TROLL);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(4);

        addAbility(TrampleAbility.getInstance());

        availableImageSetCodes = Arrays.asList("KHM");
    }

    private TrollWarriorToken(final TrollWarriorToken token) {
        super(token);
    }

    public TrollWarriorToken copy() {
        return new TrollWarriorToken(this);
    }
}
