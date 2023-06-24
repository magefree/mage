package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author fireshoes
 */
public final class PrismToken extends TokenImpl {

    public PrismToken() {
        super("Prism Token", "0/1 colorless Prism artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PRISM);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public PrismToken(final PrismToken token) {
        super(token);
    }

    @Override
    public PrismToken copy() {
        return new PrismToken(this);
    }
}
