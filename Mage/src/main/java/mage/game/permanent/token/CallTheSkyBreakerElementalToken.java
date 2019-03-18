
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class CallTheSkyBreakerElementalToken extends TokenImpl {

    public CallTheSkyBreakerElementalToken() {
        super("Elemental", "5/5 blue and red Elemental creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("EMA")) {
            setTokenType(2);
        }
        power = new MageInt(5);
        toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
    }

    public CallTheSkyBreakerElementalToken(final CallTheSkyBreakerElementalToken token) {
        super(token);
    }

    public CallTheSkyBreakerElementalToken copy() {
        return new CallTheSkyBreakerElementalToken(this);
    }
}
