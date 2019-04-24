

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class InsectToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("M10", "MM2", "SOI"));
    }

    public InsectToken() {
        this((String)null);
    }

    public InsectToken(String setCode) {
        super("Insect", "1/1 green Insect creature token");
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = tokenImageSets;
    }

    public InsectToken(final InsectToken token) {
        super(token);
    }

    public InsectToken copy() {
        return new InsectToken(this);
    }
}