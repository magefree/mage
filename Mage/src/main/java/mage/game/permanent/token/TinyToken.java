package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author  PurpleCrowbar
 */
public final class TinyToken extends TokenImpl {

    public TinyToken() {
        super("Tiny", "Tiny, a legendary 2/2 green Dog Detective creature token with trample");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.DOG, SubType.DETECTIVE);
        power = new MageInt(2);
        toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private TinyToken(final TinyToken token) {
        super(token);
    }

    public TinyToken copy() {
        return new TinyToken(this);
    }
}
