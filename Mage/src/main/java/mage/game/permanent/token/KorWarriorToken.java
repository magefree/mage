package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class KorWarriorToken extends TokenImpl {

    public KorWarriorToken() {
        super("Kor Warrior Token", "1/1 white Kor Warrior creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.KOR);
        subtype.add(SubType.WARRIOR);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("ZNR");
    }

    public KorWarriorToken(final KorWarriorToken token) {
        super(token);
    }

    public KorWarriorToken copy() {
        return new KorWarriorToken(this);
    }
}
