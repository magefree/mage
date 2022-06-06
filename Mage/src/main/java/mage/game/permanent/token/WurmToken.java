package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author magenoxx_at_gmail.com
 */
public final class WurmToken extends TokenImpl {

    public WurmToken() {
        super("Wurm Token", "6/6 green Wurm creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(6);
        toughness = new MageInt(6);

        availableImageSetCodes = Arrays.asList("C19", "EMA", "GPT", "JUD", "M12", "M13", "MM3", "ODY", "VMA", "C21", "DDS");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("VMA")) {
            this.setTokenType(2);
        }
    }

    public WurmToken(final WurmToken token) {
        super(token);
    }

    public WurmToken copy() {
        return new WurmToken(this);
    }
}
