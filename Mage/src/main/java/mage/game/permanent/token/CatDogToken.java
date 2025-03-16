package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ChesseTheWasp
 */
public final class CatDogToken extends TokenImpl {

    public CatDogToken() {
        super("Cat Dog Token", "1/1 White Cat Dog");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        subtype.add(SubType.DOG);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private CatDogToken(final CatDogToken token) {
        super(token);
    }

    public CatDogToken copy() {
        return new CatDogToken(this);
    }
}
