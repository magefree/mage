package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LevelX2
 */
public final class KnightToken extends TokenImpl {

    public KnightToken() {
        super("Knight Token", "2/2 white Knight creature token with vigilance");

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(VigilanceAbility.getInstance());
    }

    public KnightToken(final KnightToken token) {
        super(token);
    }

    public KnightToken copy() {
        return new KnightToken(this);
    }
}
