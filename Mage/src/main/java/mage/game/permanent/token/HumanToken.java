package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class HumanToken extends TokenImpl {

    public HumanToken() {
        super("Human Token", "1/1 white Human creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes.addAll(Arrays.asList("DKA", "AVR", "FNMP", "RNA", "ELD", "C19", "C20", "MID", "VOW", "NCC"));
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

        if (getOriginalExpansionSetCode().equals("VOW")) {
            this.setTokenType(2);
        }
    }
}
