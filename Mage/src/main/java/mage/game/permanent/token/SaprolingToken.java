package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
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
                "NEM",
                "RTR",
                "C15",
                "MM3",
                "INV",
                "C16", // 2 different token images...
                "CMA",
                "VMA", // 2 different token, one with DIFFERENT stats, "Saproling Burst" create different token, see https://scryfall.com/card/tvma/12
                "E02",
                "RIX",
                "DOM", // 3 different token images
                "C19",
                "C20",
                "M21",
                "ZNC",
                "CMR",
                "TSR",
                "C21",
                "AFC",
                "NEC",
                "2XM",
                "NCC",
                "CM2",
                "PCA"
        ));
    }

    public SaprolingToken() {
        super("Saproling Token", "1/1 green Saproling creature token");
        availableImageSetCodes = tokenImageSets;
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

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("NEM")) {
            this.setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("VMA")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DOM")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
    }
}