

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
public final class DemonToken extends TokenImpl {

    public DemonToken() {
        super("Demon", "5/5 black Demon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
        availableImageSetCodes.addAll(Arrays.asList("ISD", "AVR", "C14", "ORI"));
    }

    public DemonToken(final DemonToken token) {
        super(token);
    }

    @Override
        public DemonToken copy() {
        return new DemonToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("C14")) {
            this.setTokenType(2);
        }
    }
}
