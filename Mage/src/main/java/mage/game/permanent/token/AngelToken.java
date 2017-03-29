package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;

public class AngelToken extends Token {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("AVR", "C14", "CFX", "GTC", "ISD", "M14", "ORI", "SOI", "ZEN", "C15", "MM3"));
    }

    public AngelToken() {
        this(null);
    }

    public AngelToken(String setCode) {
        super("Angel", "4/4 white Angel creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Angel");
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }
}
