package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LevelX2
 */
public final class KnightToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ORI", "RTR", "C15", "CMA", "DOM", "ELD"));
    }

    public KnightToken() {
        super("Knight", "2/2 white Knight creature token with vigilance");
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C15")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DOM")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(VigilanceAbility.getInstance());

        availableImageSetCodes = tokenImageSets;
    }

    public KnightToken(final KnightToken token) {
        super(token);
    }

    public KnightToken copy() {
        return new KnightToken(this);
    }
}
