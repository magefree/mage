package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class PlantToken extends TokenImpl {

    public PlantToken() {
        super("Plant Token", "0/1 green Plant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PLANT);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public PlantToken(final PlantToken token) {
        super(token);
    }

    public PlantToken copy() {
        return new PlantToken(this);
    }
}
