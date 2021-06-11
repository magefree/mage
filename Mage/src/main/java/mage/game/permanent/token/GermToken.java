package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author spjspj
 */
public final class GermToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("NPH", "MBS", "SOM", "EMA", "C16"));
    }

    public GermToken() {
        this(null, 0);
    }

    public GermToken(String setCode) {
        this(setCode, 0);
    }

    public GermToken(String setCode, int tokenType) {
        super("Phyrexian Germ", "0/0 black Phyrexian Germ creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.GERM);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }

    public GermToken(final GermToken token) {
        super(token);
    }

    @Override
    public GermToken copy() {
        return new GermToken(this);
    }
}
