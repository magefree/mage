package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class KnightWhiteBlueToken extends TokenImpl {

    public KnightWhiteBlueToken() {
        super("Knight Token", "2/2 white and blue Knight creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);

        addAbility(VigilanceAbility.getInstance());
    }

    public KnightWhiteBlueToken(final KnightWhiteBlueToken token) {
        super(token);
    }

    public KnightWhiteBlueToken copy() {
        return new KnightWhiteBlueToken(this);
    }

}
