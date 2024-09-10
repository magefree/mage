package mage.cards;

import mage.MageInt;

/**
 * @author JayDi85
 */
public interface ModalDoubleFacedCardHalf extends SubCard<ModalDoubleFacedCard> {

    @Override
    ModalDoubleFacedCardHalf copy();

    void setPT(int power, int toughness);

    void setPT(MageInt power, MageInt toughness);
}
