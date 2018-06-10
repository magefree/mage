

package mage.game.permanent.token;

import java.util.Arrays;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author LoneFox
 */
public final class HumanToken extends TokenImpl {

    public HumanToken() {
        super("Human", "1/1 white Human creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        availableImageSetCodes.addAll(Arrays.asList("DKA", "AVR", "FNMP"));
    }

    public HumanToken(final HumanToken token) {
        super(token);
    }

    @Override
        public HumanToken copy() {
        return new HumanToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("AVR")) {
            this.setTokenType(1);
        }
    }
}
