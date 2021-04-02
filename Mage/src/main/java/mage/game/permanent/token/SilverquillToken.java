package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SilverquillToken extends TokenImpl {

    public SilverquillToken() {
        super("Inkling", "2/1 white and black Inkling creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.INKLING);
        power = new MageInt(2);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
    }

    private SilverquillToken(final SilverquillToken token) {
        super(token);
    }

    @Override
    public SilverquillToken copy() {
        return new SilverquillToken(this);
    }
}
