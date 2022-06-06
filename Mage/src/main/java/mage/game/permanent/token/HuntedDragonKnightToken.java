package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class HuntedDragonKnightToken extends TokenImpl {

    public HuntedDragonKnightToken() {
        super("Knight Token", "2/2 white Knight creature tokens with first strike");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FirstStrikeAbility.getInstance());

        availableImageSetCodes = Arrays.asList("ORI", "RTR", "C15", "CM2");
    }

    public HuntedDragonKnightToken(final HuntedDragonKnightToken token) {
        super(token);
    }

    public HuntedDragonKnightToken copy() {
        return new HuntedDragonKnightToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C15")) {
            setTokenType(1);
        }
    }
}
