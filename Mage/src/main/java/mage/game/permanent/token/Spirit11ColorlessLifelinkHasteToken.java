package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author weirddan455
 */
public class Spirit11ColorlessLifelinkHasteToken extends TokenImpl {

    public Spirit11ColorlessLifelinkHasteToken() {
        super("Spirit Token", "1/1 colorless Spirit creature token with lifelink and haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);

        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    private Spirit11ColorlessLifelinkHasteToken(final Spirit11ColorlessLifelinkHasteToken token) {
        super(token);
    }

    @Override
    public Spirit11ColorlessLifelinkHasteToken copy() {
        return new Spirit11ColorlessLifelinkHasteToken(this);
    }
}
