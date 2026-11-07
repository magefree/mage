package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author muz
 */
public final class PorthosToken extends TokenImpl {

    public PorthosToken() {
        super("Porthos", "Porthos, a legendary 1/1 green Dog creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        this.supertype.add(SuperType.LEGENDARY);
        subtype.add(SubType.DOG);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private PorthosToken(final PorthosToken token) {
        super(token);
    }

    public PorthosToken copy() {
        return new PorthosToken(this);
    }
}
