package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author LevelX2
 */
public final class KnightToken extends TokenImpl {

    public KnightToken() {
        super("Knight Token", "2/2 white Knight creature token with vigilance");

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(VigilanceAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C15", "CMA", "ORI", "RTR", "M19", "ELD", "M21", "AFC", "MIC", "DOM", "2X2", "DMC");
    }

    public KnightToken(final KnightToken token) {
        super(token);
    }

    public KnightToken copy() {
        return new KnightToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C15")) {
            setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DOM")) {
            setTokenType(RandomUtil.nextInt(2) + 1);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DMC")) {
            setTokenType(1);
        }
    }
}
