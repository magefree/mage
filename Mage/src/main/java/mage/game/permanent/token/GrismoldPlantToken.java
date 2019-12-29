package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GrismoldPlantToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C19"));
    }

    public GrismoldPlantToken() {
        super("Plant", "1/1 green Plant creature");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PLANT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = tokenImageSets;
    }

    public GrismoldPlantToken(final GrismoldPlantToken token) {
        super(token);
    }

    public GrismoldPlantToken copy() {
        return new GrismoldPlantToken(this);
    }
}
 