package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class MyrToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C14", "MM2", "NPH", "SOM"));
    }

    public MyrToken() {
        this((String)null);
    }

    public MyrToken(String expansionSetCode) {
        super("Myr", "1/1 colorless Myr artifact creature token");
        this.setOriginalExpansionSetCode(expansionSetCode);
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.MYR);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = tokenImageSets;
    }

    public MyrToken(final MyrToken token) {
        super(token);
    }

    public MyrToken copy() {
        return new MyrToken(this);
    }
}