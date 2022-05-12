package mage.cards;

import mage.MageInt;

/**
 * @author JayDi85
 */
public interface ModalDoubleFacesCardHalf extends SubCard<ModalDoubleFacesCard> {

    @Override
    ModalDoubleFacesCardHalf copy();

    void setPT(int power, int toughness);

    void setPT(MageInt power, MageInt toughness);
}
