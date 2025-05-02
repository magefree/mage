package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class Rat11LifelinkToken extends TokenImpl {

    public Rat11LifelinkToken() {
        super("Rat Token", "1/1 black Rat creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(LifelinkAbility.getInstance());
    }

    private Rat11LifelinkToken(final Rat11LifelinkToken token) {
        super(token);
    }

    @Override
    public Rat11LifelinkToken copy() {
        return new Rat11LifelinkToken(this);
    }
}
