package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class PrimoTheIndivisibleToken extends TokenImpl {

    public PrimoTheIndivisibleToken() {
        super("Primo, the Indivisible", "Primo, the Indivisible, a legendary 0/0 green and blue Fractal creature token");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.FRACTAL);
        color.setGreen(true);
        color.setBlue(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private PrimoTheIndivisibleToken(final PrimoTheIndivisibleToken token) {
        super(token);
    }

    public PrimoTheIndivisibleToken copy() {
        return new PrimoTheIndivisibleToken(this);
    }
}
