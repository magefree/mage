package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class Beast44TrampleToken extends TokenImpl {

    public Beast44TrampleToken() {
        super("Beast Token", "4/4 green Beast creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);

        addAbility(TrampleAbility.getInstance());
    }

    private Beast44TrampleToken(final Beast44TrampleToken token) {
        super(token);
    }

    @Override
    public Beast44TrampleToken copy() {
        return new Beast44TrampleToken(this);
    }
}
