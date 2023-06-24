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
public final class ChainersTormentNightmareToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("DOM"));
    }

    public ChainersTormentNightmareToken() {
        this(0);
    }

    ;

    public ChainersTormentNightmareToken(int xValue) {
        super("Nightmare Horror Token", "X/X black Nightmare Horror creature token");

        cardType.add(CardType.CREATURE);
        subtype.add(SubType.NIGHTMARE);
        subtype.add(SubType.HORROR);
        color.setBlack(true);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public ChainersTormentNightmareToken(final ChainersTormentNightmareToken token) {
        super(token);
    }

    public ChainersTormentNightmareToken copy() {
        return new ChainersTormentNightmareToken(this);
    }
}
