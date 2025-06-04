package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class NymphToken extends TokenImpl {

    public NymphToken() {
        super("Nymph Token", "2/2 white Nymph enchantment creature token");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.NYMPH);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private NymphToken(final NymphToken token) {
        super(token);
    }

    public NymphToken copy() {
        return new NymphToken(this);
    }
}
