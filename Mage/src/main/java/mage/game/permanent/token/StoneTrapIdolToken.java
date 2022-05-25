

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class StoneTrapIdolToken extends TokenImpl {

    public StoneTrapIdolToken() {
        super("Construct Token", "6/12  colorless Construct artifact creature token with trample");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(6);
        toughness = new MageInt(12);
        addAbility(TrampleAbility.getInstance());

        availableImageSetCodes = Arrays.asList("WWK", "C18");
    }

    public StoneTrapIdolToken(final StoneTrapIdolToken token) {
        super(token);
    }

    public StoneTrapIdolToken copy() {
        return new StoneTrapIdolToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C18")) {
            this.setTokenType(2);
        }
    }
}
