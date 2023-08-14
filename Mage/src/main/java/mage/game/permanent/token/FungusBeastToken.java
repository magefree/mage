package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class FungusBeastToken extends TokenImpl {

    public FungusBeastToken() {
        super("Fungus Beast Token", "4/4 green Fungus Beast creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.FUNGUS);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);

        addAbility(TrampleAbility.getInstance());
    }

    private FungusBeastToken(final FungusBeastToken token) {
        super(token);
    }

    public FungusBeastToken copy() {
        return new FungusBeastToken(this);
    }
}
