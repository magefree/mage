package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

public final class PlantToken extends TokenImpl {

    public PlantToken() {
        super("Plant", "0/1 green Plant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PLANT);
        power = new MageInt(0);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("WWK", "DDP", "OGW", "ZNR");
    }

    public PlantToken(final PlantToken token) {
        super(token);
    }

    public PlantToken copy() {
        return new PlantToken(this);
    }
}
