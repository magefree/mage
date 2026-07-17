package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class MerfolkWhiteBlueToken extends TokenImpl {

    public MerfolkWhiteBlueToken() {
        super("Merfolk Token", "1/1 white and blue Merfolk creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        subtype.add(SubType.MERFOLK);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private MerfolkWhiteBlueToken(final MerfolkWhiteBlueToken token) {
        super(token);
    }

    public MerfolkWhiteBlueToken copy() {
        return new MerfolkWhiteBlueToken(this);
    }
}
