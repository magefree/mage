package mage.game.permanent.token;

import mage.abilities.token.FoodAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author jmharmon
 */

public final class FoodToken extends TokenImpl {

    public FoodToken() {
        super("Food Token", "Food token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.FOOD);

        this.addAbility(new FoodAbility(false));
    }

    protected FoodToken(final FoodToken token) {
        super(token);
    }

    public FoodToken copy() {
        return new FoodToken(this);
    }
}
