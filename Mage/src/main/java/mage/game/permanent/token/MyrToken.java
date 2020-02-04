package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MyrToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C14", "MM2", "NPH", "SOM", "MH1"));
    }

    public MyrToken() {
        this((String) null);
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