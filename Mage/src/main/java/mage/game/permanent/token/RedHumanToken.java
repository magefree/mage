

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class RedHumanToken extends TokenImpl {

    public RedHumanToken() {
        super("Human", "1/1 red Human creature token");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.HUMAN);

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("AVR", "EMN", "VOW");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("AVR")) {
            this.setTokenType(2);
        }
    }

    public RedHumanToken(final RedHumanToken token) {
        super(token);
    }

    public RedHumanToken copy() {
        return new RedHumanToken(this);
    }
}
