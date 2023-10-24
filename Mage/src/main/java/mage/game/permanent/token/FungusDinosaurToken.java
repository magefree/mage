package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class FungusDinosaurToken extends TokenImpl {

    public FungusDinosaurToken(int xValue) {
        super("Fungus Dinosaur Token", "X/X green Fungus Dinosaur creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.FUNGUS);
        subtype.add(SubType.DINOSAUR);
        color.setGreen(true);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    private FungusDinosaurToken(final FungusDinosaurToken token) {
        super(token);
    }

    public FungusDinosaurToken copy() {
        return new FungusDinosaurToken(this);
    }
}
