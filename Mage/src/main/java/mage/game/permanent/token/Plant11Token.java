package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class Plant11Token extends TokenImpl {

    public Plant11Token() {
        super("Plant Token", "1/1 green Plant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PLANT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private Plant11Token(final Plant11Token token) {
        super(token);
    }

    public Plant11Token copy() {
        return new Plant11Token(this);
    }
}
