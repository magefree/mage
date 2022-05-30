package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class CallTheSkyBreakerElementalToken extends TokenImpl {

    public CallTheSkyBreakerElementalToken() {
        super("Elemental Token", "5/5 blue and red Elemental creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(5);
        toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C15", "CMD", "EMA", "EVE", "C21", "NCC");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("EMA")) {
            setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("NCC")) {
            setTokenType(2);
        }
    }

    public CallTheSkyBreakerElementalToken(final CallTheSkyBreakerElementalToken token) {
        super(token);
    }

    public CallTheSkyBreakerElementalToken copy() {
        return new CallTheSkyBreakerElementalToken(this);
    }
}
