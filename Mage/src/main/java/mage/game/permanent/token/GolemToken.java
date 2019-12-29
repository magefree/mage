package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author North
 */
public final class GolemToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("MM2", "NPH", "SOM", "MM3", "MH1", "M20"));
    }

    public GolemToken() {
        this((String) null);
    }

    public GolemToken(String setCode) {
        super("Golem", "3/3 colorless Golem artifact creature token");
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = tokenImageSets;
    }

    public GolemToken(final GolemToken token) {
        super(token);
    }

    public GolemToken copy() {
        return new GolemToken(this);
    }
}
