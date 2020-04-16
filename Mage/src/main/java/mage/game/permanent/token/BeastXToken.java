package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class BeastXToken extends TokenImpl {

    public BeastXToken(int xValue) {
        super("Beast", "X/X green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        addAbility(TrampleAbility.getInstance());
    }

    private BeastXToken(final BeastXToken token) {
        super(token);
    }

    @Override
    public BeastXToken copy() {
        return new BeastXToken(this);
    }
}
