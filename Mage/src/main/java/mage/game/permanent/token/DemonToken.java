package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class DemonToken extends TokenImpl {

    public DemonToken() {
        super("Demon Token", "5/5 black Demon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes.addAll(Arrays.asList("AVR", "C14", "DD3A", "ISD", "ORI", "M20", "M21", "2XM", "DDR"));
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

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C14")) {
            setTokenType(1);
        }
    }
}
