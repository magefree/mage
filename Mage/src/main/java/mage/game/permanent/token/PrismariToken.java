package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PrismariToken extends TokenImpl {

    public PrismariToken() {
        super("Elemental", "4/4 blue and red Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private PrismariToken(final PrismariToken token) {
        super(token);
    }

    @Override
    public PrismariToken copy() {
        return new PrismariToken(this);
    }
}
