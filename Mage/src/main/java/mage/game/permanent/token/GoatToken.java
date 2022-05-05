package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LoneFox
 */
public final class GoatToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("EVE", "M13", "M14", "C14", "ELD", "THB"));
    }

    public GoatToken() {
        this(null, 0);
    }

    public GoatToken(String setCode) {
        this(setCode, 0);
    }

    public GoatToken(String setCode, int tokenType) {
        super("Goat Token", "0/1 white Goat creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.GOAT);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public GoatToken(final GoatToken token) {
        super(token);
    }

    public GoatToken copy() {
        return new GoatToken(this);
    }
}
