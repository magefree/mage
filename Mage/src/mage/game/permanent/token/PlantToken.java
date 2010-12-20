package mage.game.permanent.token;

import mage.Constants.CardType;
import mage.MageInt;

public class PlantToken extends Token {
    public PlantToken() {
        super("Plant", "0/1 green Plant creature");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Plant");
        power = new MageInt(0);
        toughness = new MageInt(1);
    }
}
