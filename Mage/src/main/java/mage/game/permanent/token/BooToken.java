package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class BooToken extends TokenImpl {

    public BooToken() {
        super("Boo", "Boo, a legendary 1/1 red Hamster creature token with trample and haste");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.HAMSTER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AFR", "CLB");
    }

    private BooToken(final BooToken token) {
        super(token);
    }

    public BooToken copy() {
        return new BooToken(this);
    }
}
