package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public class SamuraiToken extends TokenImpl {

    public SamuraiToken() {
        super("Samurai token", "2/2 white Samurai creature token with vigilance.");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SAMURAI);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(VigilanceAbility.getInstance());
    }

    private SamuraiToken(final SamuraiToken token) {
        super(token);
    }

    @Override
    public SamuraiToken copy() {
        return new SamuraiToken(this);
    }
}
