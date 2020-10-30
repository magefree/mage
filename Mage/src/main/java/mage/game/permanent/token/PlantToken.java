package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class PlantToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("WWK", "DDP", "OGW"));
    }

    public PlantToken() {
        super("Plant", "0/1 green Plant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PLANT);
        power = new MageInt(0);
        toughness = new MageInt(1);

        availableImageSetCodes = tokenImageSets;
    }

    public PlantToken(final PlantToken token) {
        super(token);
    }

    public PlantToken copy() {
        return new PlantToken(this);
    }
}
