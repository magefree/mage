package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class BirdToken extends TokenImpl {

    public BirdToken() {
        super("Bird Token", "1/1 white Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("BNG", "CSP", "DGM", "JUD", "MM3", "RTR", "VMA", "ZEN",
                "MH1", "C16", "C20", "M21", "ZNC", "KHC", "MH2");
    }

    public BirdToken(final BirdToken token) {
        super(token);
    }

    @Override
    public BirdToken copy() {
        return new BirdToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            setTokenType(2);
        }
    }
}
