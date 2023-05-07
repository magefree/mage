package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class GrismoldPlantToken extends TokenImpl {

    public GrismoldPlantToken() {
        super("Plant Token", "1/1 green Plant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PLANT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public GrismoldPlantToken(final GrismoldPlantToken token) {
        super(token);
    }

    public GrismoldPlantToken copy() {
        return new GrismoldPlantToken(this);
    }
}
 