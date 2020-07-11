package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AngelToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    public AngelToken() {
        super("Angel", "4/4 white Angel creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("APC", "AVR", "C14", "C15", "C18", "CON", "DDQ", "GTC",
                "ISD", "M14", "MM3", "NEM", "OGW", "ORI", "PC2", "SCG", "SOI", "ZEN", "C20", "M21");
    }

    public AngelToken(final AngelToken token) {
        super(token);
    }

    public AngelToken copy() {
        return new AngelToken(this);
    }
}
