package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author @stwalsh4118
 */
public class DoublestrikeSamuraiToken extends TokenImpl {

    public DoublestrikeSamuraiToken() {
        super("Samurai Token", "2/2 white Samurai creature token with double strike.");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SAMURAI);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(DoubleStrikeAbility.getInstance());

        setOriginalExpansionSetCode("ONE");
    }

    private DoublestrikeSamuraiToken(final DoublestrikeSamuraiToken token) {
        super(token);
    }

    @Override
    public DoublestrikeSamuraiToken copy() {
        return new DoublestrikeSamuraiToken(this);
    }
}
