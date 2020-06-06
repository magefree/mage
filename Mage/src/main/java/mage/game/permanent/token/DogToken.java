package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class DogToken extends TokenImpl {

    public DogToken() {
        super("Dog", "1/1 green Dog creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DOG);

        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private DogToken(final DogToken token) {
        super(token);
    }

    public DogToken copy() {
        return new DogToken(this);
    }
}
