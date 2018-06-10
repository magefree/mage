package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class AngelToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("AVR", "C14", "CFX", "GTC", "ISD", "M14", "ORI", "SOI", "ZEN", "C15", "MM3"));
    }

    public AngelToken() {
        this((String)null);
    }

    public AngelToken(String setCode) {
        super("Angel", "4/4 white Angel creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public AngelToken(final AngelToken token) {
        super(token);
    }

    public AngelToken copy() {
        return new AngelToken(this);
    }
}
