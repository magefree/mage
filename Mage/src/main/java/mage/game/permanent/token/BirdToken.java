

package mage.game.permanent.token;

import java.util.Arrays;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class BirdToken extends TokenImpl {

    public BirdToken() {
        super("Bird", "1/1 white Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        availableImageSetCodes.addAll(Arrays.asList("BNG", "RTR", "ZEN", "C16", "MM3", "DGM"));
    }

    public BirdToken(final BirdToken token) {
        super(token);
    }

    @Override
        public BirdToken copy() {
        return new BirdToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("BNG")) {
            this.setTokenType(1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            this.setTokenType(1);
        }
    }
}
