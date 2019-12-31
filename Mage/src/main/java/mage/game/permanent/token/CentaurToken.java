package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LevelX2
 */
public final class CentaurToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("RTR", "MM3", "RNA", "C19"));
    }

    public CentaurToken() {
        super("Centaur", "3/3 green Centaur creature token");
        cardType.add(CardType.CREATURE);
        availableImageSetCodes = tokenImageSets;
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("RNA")) {
            setTokenType(RandomUtil.nextInt(2) + 1); // randomly take image 1 or 2
        }
        color.setGreen(true);
        subtype.add(SubType.CENTAUR);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public CentaurToken(final CentaurToken token) {
        super(token);
    }

    public CentaurToken copy() {
        return new CentaurToken(this);
    }
}
