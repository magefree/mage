
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class EdgarMarkovToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("C17"));
    }

    public EdgarMarkovToken() {
        super("Vampire", "1/1 black Vampire creature token");
        availableImageSetCodes = tokenImageSets;
        setExpansionSetCodeForImage("C17");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public EdgarMarkovToken(final EdgarMarkovToken token) {
        super(token);
    }

    public EdgarMarkovToken copy() {
        return new EdgarMarkovToken(this);
    }
}
