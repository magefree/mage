package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author weirddan455
 */
public class Spirit22Token extends TokenImpl {

    public Spirit22Token() {
        super("Spirit Token", "2/2 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
    }

    private Spirit22Token(final Spirit22Token token) {
        super(token);
    }

    @Override
    public Spirit22Token copy() {
        return new Spirit22Token(this);
    }
}
