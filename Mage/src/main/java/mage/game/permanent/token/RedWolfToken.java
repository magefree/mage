package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class RedWolfToken extends TokenImpl {

    public RedWolfToken() {
        super("Wolf", "3/2 red Wolf creature token");

        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.WOLF);
        power = new MageInt(3);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("VOW");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("VOW")) {
            setTokenType(2);
        }
    }

    private RedWolfToken(final RedWolfToken token) {
        super(token);
    }

    @Override
    public RedWolfToken copy() {
        return new RedWolfToken(this);
    }
}
