package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author htrajan
 */
public final class DogToken2 extends TokenImpl {

    public DogToken2() {
        super("Dog", "1/1 white Dog creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DOG);

        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private DogToken2(final DogToken2 token) {
        super(token);
    }

    public DogToken2 copy() {
        return new DogToken2(this);
    }

}
