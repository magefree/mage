package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class DemonFlyingToken extends TokenImpl {

    public DemonFlyingToken() {
        this(1);
    }

    public DemonFlyingToken(int xValue) {
        super("Demon Token", "X/X black Demon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C14", "C21", "NCC", "DDC", "CNS");
    }

    public DemonFlyingToken(final DemonFlyingToken token) {
        super(token);
    }

    public DemonFlyingToken copy() {
        return new DemonFlyingToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C14")) {
            setTokenType(2);
        }
    }
}
