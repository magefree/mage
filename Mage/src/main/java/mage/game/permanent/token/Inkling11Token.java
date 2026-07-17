package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Inkling11Token extends TokenImpl {

    public Inkling11Token() {
        super("Inkling Token", "1/1 white and black Inkling creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.INKLING);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    private Inkling11Token(final Inkling11Token token) {
        super(token);
    }

    @Override
    public Inkling11Token copy() {
        return new Inkling11Token(this);
    }
}
