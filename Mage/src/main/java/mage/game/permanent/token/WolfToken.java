package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class WolfToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("BNG", "C14", "CNS", "FNMP", "ISD", "LRW", "M10", "M14", "MM2", "SHM", "SOM",
                "ZEN", "SOI", "C15", "M15", "WAR", "M20"));
    }

    public WolfToken() {
        this(null, 0);
    }

    public WolfToken(String setCode) {
        this(setCode, 0);
    }

    public WolfToken(String setCode, int tokenType) {
        super("Wolf", "2/2 green Wolf creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);

        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WOLF);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ISD")) {
            this.setTokenType(2);
        }
    }

    public WolfToken(final WolfToken token) {
        super(token);
    }

    @Override
    public Token copy() {
        return new WolfToken(this);
    }
}
