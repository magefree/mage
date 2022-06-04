package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class WhiteBlackSpiritToken extends TokenImpl {

    public WhiteBlackSpiritToken() {
        super("Spirit Token", "1/1 white and black Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes.addAll(Arrays.asList("GTC", "MH1", "C15", "C21", "UMA"));
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("UMA")) {
            this.setTokenType(2);
        }
        if (getOriginalExpansionSetCode().equals("C15")) {
            this.setTokenType(2);
        }
    }

    public WhiteBlackSpiritToken(final WhiteBlackSpiritToken token) {
        super(token);
    }

    public WhiteBlackSpiritToken copy() {
        return new WhiteBlackSpiritToken(this);
    }
}
