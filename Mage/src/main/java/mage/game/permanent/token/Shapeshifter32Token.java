package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class Shapeshifter32Token extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("CLB"));
    }

    public Shapeshifter32Token() {
        super("Shapeshifter Token", "3/2 colorless Shapeshifter creature token with changeling");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHAPESHIFTER);
        power = new MageInt(3);
        toughness = new MageInt(2);
        addAbility(new ChangelingAbility());
    }

    public Shapeshifter32Token(final Shapeshifter32Token token) {
        super(token);
    }

    public Shapeshifter32Token copy() {
        return new Shapeshifter32Token(this);
    }
}
