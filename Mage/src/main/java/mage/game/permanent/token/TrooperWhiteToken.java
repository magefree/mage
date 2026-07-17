package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Styxo
 */
public final class TrooperWhiteToken extends TokenImpl {

    public TrooperWhiteToken() {
        super("Trooper Token", "1/1 white Trooper creature token");

        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TROOPER);

        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private TrooperWhiteToken(final TrooperWhiteToken token) {
        super(token);
    }

    public TrooperWhiteToken copy() {
        return new TrooperWhiteToken(this);
    }
}
