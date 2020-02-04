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
public final class ElephantToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C14", "CNS", "DDD", "MM2", "WWK", "OGW", "C15", "DD3GVL", "MM3", "CMA", "MH1"));
    }

    public ElephantToken() {
        this((String) null);
    }

    public ElephantToken(String setCode) {
        super("Elephant", "3/3 green Elephant creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEPHANT);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public ElephantToken(final ElephantToken token) {
        super(token);
    }

    public ElephantToken copy() {
        return new ElephantToken(this);
    }

}