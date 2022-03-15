package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class GreenDogToken extends TokenImpl {

    public GreenDogToken() {
        super("Dog Token", "1/1 green Dog creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DOG);

        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private GreenDogToken(final GreenDogToken token) {
        super(token);
    }

    public GreenDogToken copy() {
        return new GreenDogToken(this);
    }
}
