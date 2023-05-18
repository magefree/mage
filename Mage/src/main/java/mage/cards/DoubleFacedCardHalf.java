package mage.cards;

import mage.MageInt;

/**
 * @author JayDi85
 */
public interface DoubleFacedCardHalf extends SubCard<DoubleFacedCard> {

    @Override
    DoubleFacedCardHalf copy();

    default void setPT(int power, int toughness) {
        this.setPT(new MageInt(power), new MageInt(toughness));
    }

    void setPT(MageInt power, MageInt toughness);
}
