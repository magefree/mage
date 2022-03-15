package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JayDi85
 */
public final class ResearchDevelopmentToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("DIS"));
    }

    public ResearchDevelopmentToken() {
        super("Elemental Token", "3/1 red Elemental creature token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
    }

    public ResearchDevelopmentToken(final ResearchDevelopmentToken token) {
        super(token);
    }

    public ResearchDevelopmentToken copy() {
        return new ResearchDevelopmentToken(this);
    }
}
