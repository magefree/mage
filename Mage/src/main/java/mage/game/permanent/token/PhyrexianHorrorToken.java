package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PhyrexianHorrorToken extends TokenImpl {

    public PhyrexianHorrorToken(int xValue) {
        super("Phyrexian Token", "X/1 red Phyrexian Horror creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.HORROR);
        power = new MageInt(xValue);
        toughness = new MageInt(1);

        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("ONE");
    }

    public PhyrexianHorrorToken(final PhyrexianHorrorToken token) {
        super(token);
    }

    @Override
    public PhyrexianHorrorToken copy() {
        return new PhyrexianHorrorToken(this);
    }
}
