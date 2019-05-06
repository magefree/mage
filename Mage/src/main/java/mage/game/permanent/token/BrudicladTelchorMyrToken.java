package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class BrudicladTelchorMyrToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C18"));
    }

    public BrudicladTelchorMyrToken() {
        this((String)null);
    }

    public BrudicladTelchorMyrToken(String expansionSetCode) {
        super("Myr", "2/1 blue Myr artifact creature token");
        this.setOriginalExpansionSetCode(expansionSetCode);
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.MYR);
        color.setBlue(true);
        power = new MageInt(2);
        toughness = new MageInt(1);

        availableImageSetCodes = tokenImageSets;
    }

    public BrudicladTelchorMyrToken(final BrudicladTelchorMyrToken token) {
        super(token);
    }

    public BrudicladTelchorMyrToken copy() {
        return new BrudicladTelchorMyrToken(this);
    }
}
