package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class GuenhwyvarToken extends TokenImpl {

    public GuenhwyvarToken() {
        super("Guenhwyvar", "Guenhwyvar, a legendary 4/1 green Cat creature token with trample");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CAT);
        power = new MageInt(4);
        toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AFR");
    }

    private GuenhwyvarToken(final GuenhwyvarToken token) {
        super(token);
    }

    public GuenhwyvarToken copy() {
        return new GuenhwyvarToken(this);
    }
}
