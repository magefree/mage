

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SaprolingToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList(
                "10E",
                "ALA",
                "DDE",
                "DDH",
                "DDJ",
                "M12",
                "M13",
                "M14",
                "MM2",
                "MM3",
                "MMA",
                "RTR",
                "C15",
                "MM3",
                "C16", // 2 different token images...
                "CMA",
                "VMA", // 2 different token, one with DIFFERENT stats, "Saproling Burst" create different token, see https://scryfall.com/card/tvma/12
                "E02",
                "RIX",
                "DOM" // 3 different token images
        ));
    }

    public SaprolingToken() {
        this(null, 0);
    }

    public SaprolingToken(String setCode) {
        this(setCode, 0);
    }

    public SaprolingToken(String setCode, int tokenType) {
        super("Saproling", "1/1 green Saproling creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DOM")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SAPROLING);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SaprolingToken(final SaprolingToken token) {
        super(token);
    }

    public SaprolingToken copy() {
        return new SaprolingToken(this);
    }
}