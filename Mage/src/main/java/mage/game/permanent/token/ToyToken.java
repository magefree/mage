package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class ToyToken extends TokenImpl {

    public ToyToken() {
        super("Toy Token", "1/1 white Toy artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TOY);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private ToyToken(final ToyToken token) {
        super(token);
    }

    @Override
    public ToyToken copy() {
        return new ToyToken(this);
    }
}
