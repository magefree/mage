package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class CribSwapShapeshifterWhiteToken extends TokenImpl {

    public CribSwapShapeshifterWhiteToken() {
        super("Shapeshifter Token", "1/1 colorless Shapeshifter creature token with changeling");
        this.setOriginalExpansionSetCode("LRW");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHAPESHIFTER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new ChangelingAbility());

        availableImageSetCodes = Arrays.asList("LRW", "C15", "CM2", "C18", "2XM", "CLB");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CLB")) {
            this.setTokenType(1);
        }
    }

    public CribSwapShapeshifterWhiteToken(final CribSwapShapeshifterWhiteToken token) {
        super(token);
    }

    public CribSwapShapeshifterWhiteToken copy() {
        return new CribSwapShapeshifterWhiteToken(this);
    }
}
