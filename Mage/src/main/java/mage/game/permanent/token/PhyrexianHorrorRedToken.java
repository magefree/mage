package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PhyrexianHorrorRedToken extends TokenImpl {

    public PhyrexianHorrorRedToken() {
        this(0);
    }

    public PhyrexianHorrorRedToken(int xValue) {
        super("Phyrexian Horror Token", "X/1 red Phyrexian Horror creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.HORROR);
        power = new MageInt(xValue);
        toughness = new MageInt(1);

        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    public PhyrexianHorrorRedToken(final PhyrexianHorrorRedToken token) {
        super(token);
    }

    @Override
    public PhyrexianHorrorRedToken copy() {
        return new PhyrexianHorrorRedToken(this);
    }
}
