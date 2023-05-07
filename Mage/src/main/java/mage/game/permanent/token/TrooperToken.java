package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Styxo
 */
public final class TrooperToken extends TokenImpl {

    public TrooperToken() {
        super("Trooper Token", "1/1 white Trooper creature token");

        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TROOPER);

        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public TrooperToken(final TrooperToken token) {
        super(token);
    }

    public TrooperToken copy() {
        return new TrooperToken(this);
    }
}
