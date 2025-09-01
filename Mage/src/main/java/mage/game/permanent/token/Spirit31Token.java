package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Spirit31Token extends TokenImpl {

    public Spirit31Token() {
        super("Spirit Token", "3/1 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(3);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    private Spirit31Token(final Spirit31Token token) {
        super(token);
    }

    @Override
    public Spirit31Token copy() {
        return new Spirit31Token(this);
    }
}
